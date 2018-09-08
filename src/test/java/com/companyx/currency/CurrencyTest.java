package com.companyx.currency;

import org.junit.Assert;
import org.junit.Test;

import com.companyx.helper.Currency;

public class CurrencyTest {

	@Test
	public void currencyTest() {
		Currency currency = Currency.EURO;
		Assert.assertEquals("€", currency.symbol());

		currency = Currency.POUND_STERLING;
		Assert.assertEquals("£", currency.symbol());
	}
}