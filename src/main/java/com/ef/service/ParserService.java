package com.ef.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import com.ef.exception.CommandLineArgsException;
import com.ef.model.ImportLogFile;
import com.ef.model.ImportLogFile.ImportLogFileStatus;
import com.ef.model.ServerAccess;
import com.ef.model.ServerBlocked;
import com.ef.model.ServerBlocked.ServerBlockedDurations;
import com.ef.persistence.HibernateUtil;
import com.ef.util.FileUtil;
import com.ef.util.ParserUtil;
import com.ef.util.StringUtil;

/**
 * Parser bussiness logic methods.
 * @author patriciaespada
 *
 */
public class ParserService {

	private static final Logger LOGGER = Logger.getGlobal();
	
	private ImportLogFileService importLogFileService = new ImportLogFileService();
	private ServerAccessService serverAccessService = new ServerAccessService();
	private ServerBlockedService serverBlockedService = new ServerBlockedService();
	
	/**
	 * Validates command line arguments rules.
	 * @param argsMap
	 * @return
	 * @throws CommandLineArgsException
	 * @throws ParseException
	 */
	public boolean validateCommandLineArgs(Map<String, String> argsMap) throws CommandLineArgsException {
		if (!argsMap.containsKey(ParserUtil.ACCESSLOG_ARG) 
				|| !argsMap.containsKey(ParserUtil.START_DATE_ARG) 
				|| !argsMap.containsKey(ParserUtil.DURATION_ARG) 
				|| !argsMap.containsKey(ParserUtil.THRESHOLD_ARG)) {
			throw new CommandLineArgsException("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number");
		}

		File file = new File(argsMap.get(ParserUtil.ACCESSLOG_ARG));
		if (!file.exists()) {
			throw new CommandLineArgsException("File " + argsMap.get(ParserUtil.ACCESSLOG_ARG) + "doesn't exists!");			
		}

		try {
			Date date = ParserUtil.startDateFormatter.parse(argsMap.get(ParserUtil.START_DATE_ARG));
			if (!argsMap.get(ParserUtil.START_DATE_ARG).equals(ParserUtil.startDateFormatter.format(date))) {
				throw new CommandLineArgsException("Argument --startDate has to have the following format yyyy-MM-dd.HH:mm:ss.");
			}
		} catch (ParseException pe) {
			throw new CommandLineArgsException("Argument --startDate has to have the following format yyyy-MM-dd.HH:mm:ss.");
		}

		String duration = argsMap.get(ParserUtil.DURATION_ARG);
		try {
			if (ServerBlockedDurations.valueOf(duration.toUpperCase()) == null) {
				throw new CommandLineArgsException("Argument --duration can only be hourly or daily.");
			}
		} catch (IllegalArgumentException iae) {
			throw new CommandLineArgsException("Argument --duration can only be hourly or daily.");
		}

		try {
			Integer threshold = Integer.valueOf(argsMap.get(ParserUtil.THRESHOLD_ARG));
			if (threshold < 0) {
				throw new CommandLineArgsException("Argument --threshold has to be a positive integer.");
			}
		} catch (NumberFormatException nfe) {
			throw new CommandLineArgsException("Argument --threshold has to be a positive integer.");
		}

		return true;
	}

	/**
	 * Read file, parse each line, and add the necessary info into the database.
	 * @param file
	 * @return id of the imported log file
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public Integer readFileAndStoreIntoDB(String file) throws NoSuchAlgorithmException, IOException {
		String md5CheckSum = FileUtil.md5CheckSum(file);

		ImportLogFile importLogFile = importLogFileService.findByFileAndCheckSum(file, md5CheckSum);
		if (importLogFile == null) {
			// insert the file to be processed
			ImportLogFile newImportLogFile = importLogFileService.insert(file, md5CheckSum);

			// read each line of the file and insert it
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
				try (Session session = HibernateUtil.getSessionFactory().openSession()) {
					try {
						session.beginTransaction();
						final int[] i = {0};
						LOGGER.log(Level.INFO, "Wait while we add all file rows into the DB...");
						reader.lines().forEach(
								line -> {
									String[] lineArr = line.split("\\|");
									try {						
										ServerAccess serverAccess = serverAccessService.create(newImportLogFile.getId(), ParserUtil.dbDateformatter.parse(lineArr[0]), lineArr[1], 
												StringUtil.unquote(lineArr[2]), Integer.valueOf(lineArr[3]), StringUtil.unquote(lineArr[4]));

										session.save(serverAccess);

										if (i[0] % 1000 == 0) { // insert in batch
											session.flush();
											session.clear();
										}
									} catch (ParseException pe) {
										LOGGER.log(Level.WARNING, "Error occurred on parsing log file line " + i + ": " + line, pe);
									} finally {
										i[0]++;
									}
								}
								);
						session.getTransaction().commit();

						newImportLogFile.setStatus(ImportLogFileStatus.SUCCESS);
						importLogFileService.update(newImportLogFile);

						LOGGER.log(Level.INFO, "This file {0} was imported successfully! Imported {1} records", new Object[] { file, Integer.valueOf(i[0]) });
					} catch (Exception e) {
						session.getTransaction().rollback();
						newImportLogFile.setStatus(ImportLogFileStatus.FAIL);
						importLogFileService.update(newImportLogFile);	
						throw e;
					}
				}
			}
			return newImportLogFile.getId();
		} else {
			LOGGER.log(Level.INFO, "This file {0} is already in the database!", file);
			return importLogFile.getId();
		}
	}

	/**
	 * Show and block ip's that exceed the allowed number of requests
	 * @param importLogFileId
	 * @param startDateStr
	 * @param durationStr
	 * @param thresholdStr
	 * @throws ParseException
	 */
	public void showAndBlockIpsThatExceedNumOfRequests(Integer importLogFileId, String startDateStr, String durationStr, String thresholdStr) throws ParseException {
		Date startDate = ParserUtil.startDateFormatter.parse(startDateStr);
		ServerBlockedDurations durations = ServerBlockedDurations.valueOf(durationStr.toUpperCase());
		Date endDate = ParserUtil.addDuration(startDate, durations);
		Integer threshold = Integer.valueOf(thresholdStr);

		List<ServerBlocked> serversBlocked = serverBlockedService.findBlockedServers(importLogFileId, startDate, endDate, durations, threshold);
		StringBuilder printServersBlocked = new StringBuilder();
		serversBlocked.stream().forEach(
				serverBlocked -> {
					serverBlockedService.insert(serverBlocked);
					printServersBlocked.append("\t").append(serverBlocked.getIp().toString()).append("\n");
				}
				);

		LOGGER.log(Level.INFO, "IP's that exceed the allowed request number: \n" + printServersBlocked.toString());
	}

}
