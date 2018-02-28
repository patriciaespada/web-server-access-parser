package com.ef;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.ef.exception.CommandLineArgsException;
import com.ef.service.ParserService;

/**
 * Web server access parser application.
 * @author patriciaespada
 *
 */
public class Parser {

	private static final Logger LOGGER = Logger.getGlobal();

	public static void main(String[] args) throws IOException {
		LOGGER.log(Level.INFO, "Application started with command-line arguments: {0}", Arrays.toString(args));

		Map<String, String> argsMap = Arrays.stream(args)
				.map(s -> s.split("="))
				.collect(Collectors.toMap(a -> a[0], a -> a[1]));

		ParserService parser = new ParserService();
		
		// validate command line arguments
		try {
			parser.validateCommandLineArgs(argsMap);
		} catch (CommandLineArgsException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			System.exit(0);
		}

		// read file and insert into database
		Integer importLogFileId = null;
		try {
			importLogFileId = parser.readFileAndStoreIntoDB(argsMap.get("--accesslog"));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occurred while parsing the file", e);
			System.exit(0);
		}

		// show and block ip's that exceed the allowed number of requests
		try {
			parser.showAndBlockIpsThatExceedNumOfRequests(importLogFileId, argsMap.get("--startDate"), argsMap.get("--duration"), argsMap.get("--threshold"));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occurred while parsing the file", e);
			System.exit(0);
		}

		// exit application
		System.exit(0);
	}
}
