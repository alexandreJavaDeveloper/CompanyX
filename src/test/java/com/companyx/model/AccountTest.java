package com.companyx.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

	@Test
	public void hashCodeAccountTest() {
		final Account account = new Account("1A", new BigDecimal(10));
		final int expected = 1584;
		Assert.assertEquals(expected, account.hashCode());
	}

	@Test
	public void equalsAccountTest() {
		final Account account = new Account("1A", new BigDecimal(10));
		final Account account2 = new Account("2A", new BigDecimal(10));
		final Account account3 = new Account("1A", new BigDecimal(10));

		Assert.assertFalse(account.equals(account2));
		Assert.assertTrue(account.equals(account3));
	}

	@Test
	public void toStringAccountTest() {
		final Account account = new Account("1A", new BigDecimal(10));
		Assert.assertEquals("Account [accountNumber=1A, money=10]", account.toString());
	}
}