package com.companyx.exception;

import javax.ws.rs.core.Response.Status;

import com.companyx.i18n.StringsI18N;

public class InvalidMoneyException extends InternalCommonException {

	private static final long serialVersionUID = 1L;

	private final Status RESPONSE_STATUS = Status.BAD_REQUEST;

	public InvalidMoneyException() {
		super(StringsI18N.INVALID_MONEY);
	}

	@Override
	public Status getResponseStatus() {
		return this.RESPONSE_STATUS;
	}
}