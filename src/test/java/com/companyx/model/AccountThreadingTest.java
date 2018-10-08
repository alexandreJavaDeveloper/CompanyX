package com.companyx.model;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;

/**
 * Still doing the tests...
 */
public class AccountThreadingTest extends Thread implements Runnable {

	private Account account;

	@Before
	public void setup() throws InvalidAttributesException {
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void equalsAccountTest() throws InvalidAttributesException{

		final AccountThreadingTest ts1 = new AccountThreadingTest();
		ts1.account = new Account("1A", new BigDecimal(10));
		ts1.start();

		final AccountThreadingTest ts2 = new AccountThreadingTest();
		ts2.account = new Account("2A", new BigDecimal(20));
		ts2.start();

		final AccountThreadingTest ts3 = new AccountThreadingTest();
		ts3.account = new Account("3A", new BigDecimal(40));
		ts3.start();

	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			try {
				this.account.subtractMoney(new BigDecimal(100));

				try {
					Thread.sleep(200);
				} catch (final InterruptedException ex) {
					Logger.getLogger(AccountThreadingTest.class.getName()).log(Level.SEVERE, null, ex);
				}

				if (this.account.getMoney().doubleValue() < 0) {
					System.out.println("account is overdrawn!");
				}

				this.account.sumMoney(new BigDecimal(200));

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Money: " + this.account.getMoney());
	}
}