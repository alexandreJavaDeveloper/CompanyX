package com.companyx.exception;

import javax.ws.rs.core.Response.Status;

public class InvalidAttributesException extends InternalCommonException {

	private static final long serialVersionUID = 1L;

	private final Status RESPONSE_STATUS = Status.BAD_REQUEST;

	public InvalidAttributesException(final String message) {
		super(message);
	}

	@Override
	public Status getResponseStatus() {
		return this.RESPONSE_STATUS;
	}
}