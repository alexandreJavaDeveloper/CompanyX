package com.companyx.helper;

/**
 * TODO use currency when transfer money to other countries.
 */
public enum Currency {
	POUND_STERLING("£"), EURO("€");

	private String currency;

	private Currency(final String currency) {
		this.currency = currency;
	}

	public String symbol() {
		return this.currency;
	}
}