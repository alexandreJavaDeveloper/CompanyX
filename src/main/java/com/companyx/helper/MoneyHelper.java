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
	 * @param moneyToTransferStr
	 * @return money transformed into BigDecimal. In case of fail, exception is throw
	 * @throws InvalidAttributesException
	 */
	public static BigDecimal transformTransferMoney(final String moneyToTransferStr) throws InvalidAttributesException {
		BigDecimal moneyToTransfer;
		try {
			moneyToTransfer = new BigDecimal(moneyToTransferStr);
		} catch (final NumberFormatException e) {
			throw new InvalidAttributesException(StringsI18N.INVALID_MONEY_TRANSFER);
		}
		return moneyToTransfer;
	}
}