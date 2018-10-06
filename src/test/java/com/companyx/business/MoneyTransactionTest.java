package com.companyx.business;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.business.MoneyTransaction;
import com.companyx.exception.InternalCommonException;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.exception.InvalidMoneyException;
import com.companyx.helper.MoneyHelper;
import com.companyx.mock.RepositoryMock;
import com.companyx.service.MoneyService;

public class MoneyTransactionTest {

	private MoneyTransaction transaction;

	private MoneyService moneyService;

	@Before
	public void setup() throws InvalidAttributesException {
		this.transaction = new MoneyTransaction();
		this.moneyService = new MoneyService();
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void transferTest() throws InternalCommonException {
		final BigDecimal moneyToTransfer = new BigDecimal(10);

		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		this.transaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

		Response response = this.moneyService.accountBalanceService("1A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		final String money1A = (String) response.getEntity();

		response = this.moneyService.accountBalanceService("2A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		final String money2A = (String) response.getEntity();

		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1510.50)), money1A);
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(3990.30)), money2A);
	}

	@Test(expected=InvalidMoneyException.class)
	public void invalidTransferTest() throws InternalCommonException {
		final BigDecimal moneyToTransfer = new BigDecimal(-4);

		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		this.transaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

		Assert.fail();
	}
}