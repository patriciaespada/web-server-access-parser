package com.ef.exception;

/**
 * A custom exception to deal with command line arguments errors.
 * @author patriciaespada
 *
 */
public class CommandLineArgsException extends Exception {

	private static final long serialVersionUID = 4002697487611904647L;

	public CommandLineArgsException(String msg) {
		super(msg);
	}

}
