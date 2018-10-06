package com.companyx.exception;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

public abstract class InternalCommonException extends Exception implements Serializable {

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
	 * @return Response status of the current Exception
	 */
	public abstract Status getResponseStatus();
}