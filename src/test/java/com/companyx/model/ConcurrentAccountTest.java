package com.companyx.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;
import com.companyx.service.MoneyService;
import com.companyx.service.MoneyTransactionService;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrentAccountTest {

	private MoneyTransactionService moneyTransactionService;

	private MoneyService moneyService;

	private final static int THREAD_COUNT = 10;

	@Before
	public void setup() throws InvalidAttributesException {
		this.moneyTransactionService = new MoneyTransactionService();
		this.moneyService = new MoneyService();
		RepositoryMock.getInstance().resetData();
	}


	/**
	 * Explanation about the test (number of threads defined in 10):
	 * 		actual money from 1A account = 1500.50
	 * 		1A sends in 10 times 1003.50, total = 497.00 at this time
	 * 		1A receives in 10 times 101.50, total: 497.00 + 101.50 = 598.50
	 *
	 * 		actual money from 2A account = 4000.30
	 * 		2A receives in 10 times 1003.50, total = 5003.80 at this time
	 * 		2A receives in 10 times 52.00, total: 5003.80 + 52.00 = 5055.80
	 */
	@Test
	@ThreadCount(ConcurrentAccountTest.THREAD_COUNT)
	public void executeTest() {

		this.moneyTransactionService.transferService(this.fetchJSONData("1A", "2A", "100.35"));

		this.moneyTransactionService.depositService(this.fetchJSONData("1A", "10.15"));

		this.moneyTransactionService.depositService(this.fetchJSONData("2A", "5.20"));
	}

	private String fetchJSONData(final String senderAccountNumber, final String receiverAccountNumber, final String moneyToTransfer) {
		return  "{\"senderAccountNumber\":\"" + senderAccountNumber + "\","
				+ "\"receiverAccountNumber\":\"" + receiverAccountNumber + "\","
				+ "\"moneyToTransfer\":\"" + moneyToTransfer + "\"}";
	}

	private String fetchJSONData(final String accountNumber, final String money) {
		return  "{\"accountNumber\":\"" + accountNumber + "\","
				+ "\"money\":\"" + money + "\"}";
	}

	@After
	public void after() {
		//		Response response = this.moneyService.accountBalanceService("1A");
		//		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		//		final String money1A = (String) response.getEntity();
		//
		//		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(598.50)), money1A);
		//
		//		response = this.moneyService.accountBalanceService("2A");
		//		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		//		final String money2A = (String) response.getEntity();
		//
		//		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(5055.80)), money2A);
	}
}