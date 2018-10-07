package com.companyx.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;

/**
 * Class for formatting money values.
 */
public class MoneyHelper {

	private MoneyHelper () {
		// Do nothing
	}

	/**
	 * @param money
	 * @return formatted money
	 */
	public static String formattMoney(final BigDecimal money) {
		return NumberFormat.getCurrencyInstance().format(money);
	}

	/**
	 * @param moneyToTranslate
	 * @return Translates the string representation of a BigDecimal into a BigDecimal
	 * @throws InvalidAttributesException
	 */
	public static BigDecimal translateMoney(final String moneyToTranslate) throws InvalidAttributesException {
		BigDecimal moneyToTransfer;
		try {
			moneyToTransfer = new BigDecimal(moneyToTranslate);
		} catch (final NumberFormatException e) {
			throw new InvalidAttributesException(StringsI18N.INVALID_MONEY_TRANSFER);
		}
		return moneyToTransfer;
	}
}