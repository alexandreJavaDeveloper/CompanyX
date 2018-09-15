package com.companyx.rest;

import java.math.BigDecimal;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.helper.MoneyHelper;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.response.mediatype.JSONMoneyTransferResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MoneyTransferServiceTest
{
	private MoneyTransferService service;

	@Before
	public void setup() throws InvalidAttributesException {
		this.service = new MoneyTransferService();
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void testServer() {
		try {
			final Client client = Client.create();

			final WebResource webResource = client.resource("http://localhost:8080/companyx/transfers/transfer");

			final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).
					post(ClientResponse.class, this.fetchJSONData("1A", "2A22", "100"));

			System.out.println(response.getStatus());

			System.out.println("Output from Server .... \n");
			final String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (final Exception e) {
			Assert.fail("Failed.");
			e.printStackTrace();
		}
	}

	@Test
	public void successTransferTest() throws InvalidAttributesException {
		Response response = this.service.transferMoneyService(this.fetchJSONData("1A", "2A", "100.34242342423"));
		JSONMoneyTransferResponse entity = (JSONMoneyTransferResponse) response.getEntity();

		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Assert.assertNotNull(entity.getDateTransaction());

		Assert.assertEquals("1A", entity.getReceiverAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1600.84)), entity.getReceiverCurrentMoney());
		Assert.assertEquals("2A", entity.getSenderAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(3899.96)), entity.getSenderCurrentMoney());

		RepositoryMock.getInstance().resetData();
		// sending 0 as money to transfer
		response = this.service.transferMoneyService(this.fetchJSONData("1A", "2A", "0"));
		entity = (JSONMoneyTransferResponse) response.getEntity();

		Assert.assertEquals("1A", entity.getReceiverAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1500.50)), entity.getReceiverCurrentMoney());
		Assert.assertEquals("2A", entity.getSenderAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(4000.30)), entity.getSenderCurrentMoney());
	}

	@Test
	public void invalidParametersTransferTest() {
		final Response response = this.service.transferMoneyService(this.fetchJSONData("1A", "2A", "testing Strings"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.INVALID_MONEY_TRANSFER, responseMessage);
	}

	@Test
	public void invalidParametersTransfer2Test() {
		final Response response = this.service.transferMoneyService(null);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.JSON_DATA_NULL, responseMessage);
	}

	@Test
	public void invalidParsingJSONTest() {
		final String jsonData = "{dsds,dsds,ds,ds,ds,ds,dsdsds,dsds,ds}";
		final Response response = this.service.transferMoneyService(jsonData);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.PARSING_JSON_ERROR, responseMessage);
	}

	@Test
	public void accountNotFoundTest() {
		final Response response = this.service.transferMoneyService(this.fetchJSONData("1hfduihfdsiA", "2A", "90"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.ACCOUNT_NOT_FOUND + "1hfduihfdsiA", responseMessage);
	}

	@Test
	public void insufficientFundsTest() {
		final Response response = this.service.transferMoneyService(this.fetchJSONData("1A", "2A", "5000.10"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		final String responseMessage = (String) response.getEntity();
		Assert.assertEquals(StringsI18N.INSUFFICIENT_FUNDS, responseMessage);
	}

	private String fetchJSONData(final String receiverAccountNumber, final String senderAccountNumber, final String moneyToTransfer) {
		return  "{\"receiverAccountNumber\":\"" + receiverAccountNumber + "\","
				+ "\"senderAccountNumber\":\"" + senderAccountNumber + "\","
				+ "\"moneyToTransfer\":\"" + moneyToTransfer + "\"}";
	}
}