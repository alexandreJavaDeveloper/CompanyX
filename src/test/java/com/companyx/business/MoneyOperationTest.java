package com.companyx.business;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;

public class MoneyOperationTest {

	private MoneyOperation moneyOperation;

	@Before
	public void setup() throws InvalidAttributesException {
		this.moneyOperation = new MoneyOperation();
		RepositoryMock.getInstance().resetData();
	}

	@Test(expected=AccountNotFoundException.class)
	public void invalidGetAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = "fdsjkfsdhiufsd";
		this.moneyOperation.getAccountBalance(accountNumber);
	}

	@Test(expected=AccountNotFoundException.class)
	public void invalid2GetAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = null;
		this.moneyOperation.getAccountBalance(accountNumber);
	}

	@Test
	public void getAccountBalanceTest() throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {
		final String accountNumber = "1A";

		RepositoryMock.getInstance().resetData();
		final BigDecimal accountBalance = this.moneyOperation.getAccountBalance(accountNumber);
		Assert.assertTrue(accountBalance.compareTo(new BigDecimal(1500.50)) == 0);
	}
}