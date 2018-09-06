package com.companyx.exception;

import javax.ws.rs.core.Response.Status;

public abstract class InternalCommonException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with the message parameter.
	 *
	 * @param message
	 */
	public InternalCommonException(final String message) {
		super(message);
	}

	/**
	 * @return Status of response of the current Exception
	 */
	public abstract Status getResponseStatus();
}