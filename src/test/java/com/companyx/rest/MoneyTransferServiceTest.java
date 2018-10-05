package com.companyx.rest;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.helper.MoneyHelper;
import com.companyx.mock.RepositoryMock;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;

public class MoneyTransferServiceTest
{
	private MoneyTransferService moneyTransferService;

	private MoneyService moneyService;

	@Before
	public void setup() throws InvalidAttributesException {
		this.moneyTransferService = new MoneyTransferService();
		this.moneyService = new MoneyService();
		RepositoryMock.getInstance().resetData();
	}

	//	@Test
	//	public void testServer() {
	//		try {
	//			final Client client = Client.create();
	//
	//			final WebResource webResource = client.resource("http://localhost:8080/transfers/transfer");
	//
	//			final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).
	//					post(ClientResponse.class, this.fetchJSONData("1A", "2A22", "100"));
	//
	//			System.out.println(response.getStatus());
	//
	//			System.out.println("Output from Server .... \n");
	//			final String output = response.getEntity(String.class);
	//			System.out.println(output);
	//
	//		} catch (final Exception e) {
	//			Assert.fail("Failed.");
	//			e.printStackTrace();
	//		}
	//	}

	@Test
	public void successTransferTest() throws InvalidAttributesException {
		this.moneyTransferService.transferMoneyService(this.fetchJSONData("1A", "2A", "100.34242342423"));

		Response response = this.moneyService.accountBalanceService("1A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		String money1A = (String) response.getEntity();

		response = this.moneyService.accountBalanceService("2A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		String money2A = (String) response.getEntity();

		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1600.84)), money1A);
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(3899.96)), money2A);

		// reset the database
		RepositoryMock.getInstance().resetData();

		// sending 0 as money to transfer
		response = this.moneyTransferService.transferMoneyService(this.fetchJSONData("1A", "2A", "0"));

		response = this.moneyService.accountBalanceService("1A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		money1A = (String) response.getEntity();

		response = this.moneyService.accountBalanceService("2A");
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		money2A = (String) response.getEntity();

		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1500.50)), money1A);
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(4000.30)), money2A);
	}

	@Test
	public void invalidParametersTransferTest() {
		final Response response = this.moneyTransferService.transferMoneyService(this.fetchJSONData("1A", "2A", "testing Strings"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void invalidParametersTransfer2Test() {
		final Response response = this.moneyTransferService.transferMoneyService(null);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void invalidParsingJSONTest() {
		final String jsonData = "{dsds,dsds,ds,ds,ds,ds,dsdsds,dsds,ds}";
		final Response response = this.moneyTransferService.transferMoneyService(jsonData);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void accountNotFoundTest() {
		final Response response = this.moneyTransferService.transferMoneyService(this.fetchJSONData("1hfduihfdsiA", "2A", "90"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void insufficientFundsTest() {
		final Response response = this.moneyTransferService.transferMoneyService(this.fetchJSONData("1A", "2A", "5000.10"));
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void depositMoneyServiceTest() {
		// TODO
	}

	private String fetchJSONData(final String receiverAccountNumber, final String senderAccountNumber, final String moneyToTransfer) {
		return  "{\"receiverAccountNumber\":\"" + receiverAccountNumber + "\","
				+ "\"senderAccountNumber\":\"" + senderAccountNumber + "\","
				+ "\"moneyToTransfer\":\"" + moneyToTransfer + "\"}";
	}
}