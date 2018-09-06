package com.companyx.exception;

import javax.ws.rs.core.Response.Status;

import com.companyx.i18n.StringsI18N;

public class InternalSystemError extends InternalCommonException {

	private static final long serialVersionUID = 1L;

	private final Status RESPONSE_STATUS = Status.INTERNAL_SERVER_ERROR;

	public InternalSystemError() {
		super(StringsI18N.INTERNAL_ERROR);
	}

	@Override
	public Status getResponseStatus() {
		return this.RESPONSE_STATUS;
	}
}