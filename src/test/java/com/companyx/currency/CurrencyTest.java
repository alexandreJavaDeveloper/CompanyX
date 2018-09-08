package com.companyx.currency;

import org.junit.Assert;
import org.junit.Test;

import com.companyx.helper.Currency;
import com.companyx.i18n.StringsI18N;

public class CurrencyTest {

	@Test
	public void currencyTest() {
		Currency currency = Currency.EURO;
		Assert.assertEquals("€", currency.symbol());

		currency = Currency.POUND_STERLING;
		Assert.assertEquals("£", currency.symbol());

		// just to the Test Coverage as this class is not necessary to test
		new StringsI18N();
	}
}