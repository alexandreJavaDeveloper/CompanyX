package com.companyx.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Class for formatting money values.
 */
public class MoneyFormatter {

	private MoneyFormatter () {
		// Do nothing
	}

	public static String formattMoney(final BigDecimal money) {
		return NumberFormat.getCurrencyInstance().format(money);
	}
}