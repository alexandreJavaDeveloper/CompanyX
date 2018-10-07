package com.companyx.business;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;

public class MoneyBasicOperatorTest {

	private MoneyBasicOperator moneyBasicOperator;

	@Before
	public void setup() throws InvalidAttributesException {
		this.moneyBasicOperator = new MoneyBasicOperator();
		RepositoryMock.getInstance().resetData();
	}

	@Test(expected=AccountNotFoundException.class)
	public void invalidGetAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = "fdsjkfsdhiufsd";
		this.moneyBasicOperator.getAccountBalance(accountNumber);
	}

	@Test(expected=AccountNotFoundException.class)
	public void invalid2GetAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = null;
		this.moneyBasicOperator.getAccountBalance(accountNumber);
	}

	@Test
	public void getAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = "1A";

		RepositoryMock.getInstance().resetData();
		final BigDecimal accountBalance = this.moneyBasicOperator.getAccountBalance(accountNumber);
		Assert.assertTrue(accountBalance.compareTo(new BigDecimal(1500.50)) == 0);
	}
}