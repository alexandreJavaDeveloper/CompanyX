package com.companyx.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InsufficientFundsException;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.exception.InvalidMoneyException;
import com.companyx.mock.RepositoryMock;

public class AccountTest {

	@Before
	public void setup() throws InvalidAttributesException {
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void hashCodeAccountTest()  {
		try {
			final Account account = new Account("1A", new BigDecimal(10));
			final int expected = 1584;
			Assert.assertEquals(expected, account.hashCode());
		} catch (final InvalidAttributesException  e) {
			Assert.fail();
		}
	}

	@Test
	public void equalsAccountTest(){
		try {
			final Account account = new Account("1A", new BigDecimal(10));
			final Account account2 = new Account("2A", new BigDecimal(10));
			final Account account3 = new Account("1A", new BigDecimal(10));

			Assert.assertFalse(account.equals(account2));
			Assert.assertTrue(account.equals(account3));
		} catch (final InvalidAttributesException  e) {
			Assert.fail();
		}
	}

	@Test(expected=InvalidMoneyException.class)
	public void invalidMoneyToSumMoneyTest() throws InvalidAttributesException, InvalidMoneyException {
		final Account account = new Account("1A", new BigDecimal(10));
		final BigDecimal moneyToSum = new BigDecimal(-1);
		account.sumMoney(moneyToSum);
	}

	@Test
	public void validMoneyToSumMoneyTest() {
		try {
			final Account account = new Account("1A", new BigDecimal(10));
			BigDecimal moneyToSum = new BigDecimal(0);

			account.sumMoney(moneyToSum);
			Assert.assertEquals(10, account.getMoney().intValue());

			moneyToSum = new BigDecimal(10);
			account.sumMoney(moneyToSum);
			Assert.assertEquals(20, account.getMoney().intValue());

			moneyToSum = new BigDecimal(10.53);
			account.sumMoney(moneyToSum);
			final double doubleValue = new BigDecimal(30.53).doubleValue();
			Assert.assertTrue(doubleValue == account.getMoney().doubleValue());

		} catch (InvalidAttributesException | InvalidMoneyException e) {
			Assert.fail();
		}
	}

	@Test(expected=InvalidMoneyException.class)
	public void invalidMoneyToSubtractMoneyTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		final Account account = new Account("1A", new BigDecimal(10));
		final BigDecimal moneyToSubtract = new BigDecimal(-1);
		account.subtractMoney(moneyToSubtract);
	}

	@Test(expected=InsufficientFundsException.class)
	public void invalid2MoneyToSubtractMoneyTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		final Account account = new Account("1A", new BigDecimal(10));
		final BigDecimal moneyToSubtract = new BigDecimal(1000);
		account.subtractMoney(moneyToSubtract);
	}

	@Test(expected=InvalidMoneyException.class)
	public void invalid3MoneyToSubtractMoneyTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		final Account account = new Account("1A", new BigDecimal(10));
		final BigDecimal moneyToSubtract = null;
		account.subtractMoney(moneyToSubtract);
	}

	@Test
	public void subtractMoneyTest() {

		try {

			RepositoryMock.getInstance().resetData();

			final Account account = new Account("1A", new BigDecimal(1500.50));

			BigDecimal moneyToSubtract = new BigDecimal(1500.40);
			account.subtractMoney(moneyToSubtract);

			moneyToSubtract = new BigDecimal(0.10);
			account.subtractMoney(moneyToSubtract);

		} catch (final InvalidAttributesException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (final InsufficientFundsException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (final InvalidMoneyException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void subtract2MoneyTest() {

		try {

			RepositoryMock.getInstance().resetData();

			final Account account = new Account("1A", new BigDecimal(1500.50));

			BigDecimal moneyToSubtract = new BigDecimal(1500.403456789765467);
			account.subtractMoney(moneyToSubtract);

			moneyToSubtract = new BigDecimal(0.10);
			account.subtractMoney(moneyToSubtract);

		} catch (final InvalidAttributesException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (final InsufficientFundsException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (final InvalidMoneyException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test(expected=InsufficientFundsException.class)
	public void invalid4MoneyToSubtractMoneyTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {

		RepositoryMock.getInstance().resetData();

		final Account account = new Account("1A", new BigDecimal(1500.50));
		BigDecimal moneyToSubtract = new BigDecimal(1500.40);
		account.subtractMoney(moneyToSubtract);

		moneyToSubtract = new BigDecimal(0.11);
		account.subtractMoney(moneyToSubtract);
	}

	@Test(expected=InsufficientFundsException.class)
	public void invalid5MoneyToSubtractMoneyTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {

		RepositoryMock.getInstance().resetData();

		final Account account = new Account("1A", new BigDecimal(1500.50));
		BigDecimal moneyToSubtract = new BigDecimal(1500.40);
		account.subtractMoney(moneyToSubtract);

		moneyToSubtract = new BigDecimal(0.106999);
		account.subtractMoney(moneyToSubtract);
	}

	@Test(expected=InvalidAttributesException.class)
	public void invalidNewAccountTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		new Account("1A", null);
	}

	@Test(expected=InvalidAttributesException.class)
	public void invalid2NewAccountTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		new Account(null, new BigDecimal(10));
	}

	@Test
	public void validNewAccountTest() throws InvalidAttributesException, InsufficientFundsException, InvalidMoneyException {
		final Account account = new Account("AAA32", new BigDecimal(10.6578976));
		Assert.assertTrue(Double.parseDouble("10.66") == account.getMoney().doubleValue());
	}

	@Test
	public void validMoneyToSubtractMoneyTest() {
		try {
			final Account account = new Account("1A", new BigDecimal(10));
			BigDecimal moneyToSum = new BigDecimal(0);

			account.sumMoney(moneyToSum);
			Assert.assertEquals(10, account.getMoney().intValue());

			moneyToSum = new BigDecimal(10);
			account.sumMoney(moneyToSum);
			Assert.assertEquals(20, account.getMoney().intValue());

			moneyToSum = new BigDecimal(10.53);
			account.sumMoney(moneyToSum);
			final double doubleValue = new BigDecimal(30.53).doubleValue();
			Assert.assertTrue(doubleValue == account.getMoney().doubleValue());
		} catch (InvalidAttributesException | InvalidMoneyException e) {
			Assert.fail();
		}
	}

	@Test
	public void methodGetMoneyNotChangeableTest() {
		try {
			final BigDecimal moneyAccount = new BigDecimal(10);
			final Account account = new Account("1A", moneyAccount);
			final BigDecimal moneyToSum = new BigDecimal(100000);
			final BigDecimal money = account.getMoney();
			money.add(moneyToSum);
			Assert.assertTrue(moneyAccount.doubleValue() == account.getMoney().doubleValue());
		} catch (final InvalidAttributesException  e) {
			Assert.fail();
		}
	}

	@Test
	public void toStringAccountTest()  {
		try {
			final Account account = new Account("1A", new BigDecimal(10));
			Assert.assertEquals("Account [accountNumber=1A, money=10.00]", account.toString());
		} catch (final InvalidAttributesException  e) {
			Assert.fail();
		}
	}
}