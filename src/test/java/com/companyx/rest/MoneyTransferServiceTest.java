package com.companyx.rest;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.response.mediatype.JSONMoneyTransferResponse;

public class MoneyTransferServiceTest
{
	private MoneyTransferService service;

	@Before
	public void setup() throws InvalidAttributesException {
		this.service = new MoneyTransferService();
		RepositoryMock.getInstance().clean();
	}

	@Test
	public void successTransferTest() {
		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		final BigDecimal transferMoney = new BigDecimal(100);

		final Response response = this.service.transferMoneyService(receiverAccountNumber, senderAccountNumber, transferMoney);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

		final JSONMoneyTransferResponse entity = (JSONMoneyTransferResponse) response.getEntity();
		Assert.assertNotNull(entity.getDateTransaction());

		Assert.assertEquals(receiverAccountNumber, entity.getReceiverAccountNumber());
		Assert.assertEquals(new BigDecimal(1600.50), entity.getReceiverCurrentMoney());

		Assert.assertEquals(senderAccountNumber, entity.getSenderAccountNumber());
		Assert.assertEquals(new BigDecimal(3900.30), entity.getSenderCurrentMoney());
	}

	@Test
	public void userNotFoundTest() {
		final String receiverAccountNumber = "1hfduihfdsiA";
		final String senderAccountNumber = "2A";
		final BigDecimal transferMoney = new BigDecimal(90);

		final Response response = this.service.transferMoneyService(receiverAccountNumber, senderAccountNumber, transferMoney);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.ACCOUNT_NOT_FOUND + receiverAccountNumber, responseMessage);
	}

	@Test
	public void insufficientFundsTest() {
		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		final BigDecimal transferMoney = new BigDecimal(5000.10);

		final Response response = this.service.transferMoneyService(receiverAccountNumber, senderAccountNumber, transferMoney);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.INSUFFICIENT_FUNDS, responseMessage);
	}

	@Test
	public void exceptionExpectedTransferTest() {
		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		final BigDecimal transferMoney = new BigDecimal(5000.10);

		final Response response = this.service.transferMoneyService(receiverAccountNumber, senderAccountNumber, transferMoney);
		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.INSUFFICIENT_FUNDS, responseMessage);

		// just to test coverage as this class is not necessary to test
		new StringsI18N();
	}
}