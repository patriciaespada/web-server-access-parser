package com.ef.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ef.exception.CommandLineArgsException;
import com.ef.util.ParserUtil;

public class ParserServiceTest {
	
	private ParserService parserService = new ParserService();

	@Test
	public void testMissingCommandLineArgs() {
		Map<String, String> argsMap = new HashMap<>();
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number", e.getMessage());
		}
		
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number", e.getMessage());
		}
		
		argsMap.put(ParserUtil.START_DATE_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number", e.getMessage());
		}
		
		argsMap.put(ParserUtil.DURATION_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number", e.getMessage());
		}
		
		argsMap.put(ParserUtil.THRESHOLD_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertNotEquals("Missing arguments in the application execution:\n"
					+ "java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=hourly/daily --threshold=number", e.getMessage());
		}
	}

	@Test
	public void testInvalidFile() {
		Map<String, String> argsMap = new HashMap<>();
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "xpto.txt");
		argsMap.put(ParserUtil.START_DATE_ARG, "");
		argsMap.put(ParserUtil.DURATION_ARG, "");
		argsMap.put(ParserUtil.THRESHOLD_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("File " + argsMap.get(ParserUtil.ACCESSLOG_ARG) + "doesn't exists!", e.getMessage());
		}
		
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "access.log");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertNotEquals("File " + argsMap.get(ParserUtil.ACCESSLOG_ARG) + "doesn't exists!", e.getMessage());
		}
	}

	@Test
	public void testInvalidStartDate() {
		Map<String, String> argsMap = new HashMap<>();
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "access.log");
		argsMap.put(ParserUtil.START_DATE_ARG, "");
		argsMap.put(ParserUtil.DURATION_ARG, "");
		argsMap.put(ParserUtil.THRESHOLD_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --startDate has to have the following format yyyy-MM-dd.HH:mm:ss.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.START_DATE_ARG, "2018-31-08");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --startDate has to have the following format yyyy-MM-dd.HH:mm:ss.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.START_DATE_ARG, "2017-01-01.10:00:00");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertNotEquals("Argument --startDate has to have the following format yyyy-MM-dd.HH:mm:ss.", e.getMessage());
		}
	}

	@Test
	public void testInvalidDuration() {
		Map<String, String> argsMap = new HashMap<>();
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "access.log");
		argsMap.put(ParserUtil.START_DATE_ARG, "2017-01-01.10:00:00");
		argsMap.put(ParserUtil.DURATION_ARG, "");
		argsMap.put(ParserUtil.THRESHOLD_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --duration can only be hourly or daily.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.DURATION_ARG, "XPTO");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --duration can only be hourly or daily.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.DURATION_ARG, "HOURLY");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertNotEquals("Argument --duration can only be hourly or daily.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.DURATION_ARG, "DAILY");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertNotEquals("Argument --duration can only be hourly or daily.", e.getMessage());
		}
	}

	@Test
	public void testInvalidThreshold() {
		Map<String, String> argsMap = new HashMap<>();
		argsMap.put(ParserUtil.ACCESSLOG_ARG, "access.log");
		argsMap.put(ParserUtil.START_DATE_ARG, "2017-01-01.10:00:00");
		argsMap.put(ParserUtil.DURATION_ARG, "HOURLY");
		argsMap.put(ParserUtil.THRESHOLD_ARG, "");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --threshold has to be a positive integer.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.THRESHOLD_ARG, "a");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --threshold has to be a positive integer.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.THRESHOLD_ARG, "-1");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(false);
		} catch (CommandLineArgsException e) {
			assertEquals("Argument --threshold has to be a positive integer.", e.getMessage());
		}
		
		argsMap.put(ParserUtil.THRESHOLD_ARG, "1");
		try {
			parserService.validateCommandLineArgs(argsMap);
			assertTrue(true);
		} catch (CommandLineArgsException e) {
			assertTrue(false);
		}
	}
}
