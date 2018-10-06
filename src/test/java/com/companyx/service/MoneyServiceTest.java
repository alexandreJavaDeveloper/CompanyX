package com.companyx.service;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.helper.MoneyHelper;
import com.companyx.mock.RepositoryMock;
import com.companyx.service.MoneyService;

public class MoneyServiceTest {

	private MoneyService moneyService;

	@Before
	public void setup() throws InvalidAttributesException {
		this.moneyService = new MoneyService();
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void invalidAccountBalanceServiceTest() throws InvalidAttributesException {
		final String accountNumber = "fds789fsd79";
		final Response response = this.moneyService.accountBalanceService(accountNumber);
		Assert.assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void accountBalanceServiceTest() throws InvalidAttributesException {
		final String accountNumber = "1A";
		final Response response = this.moneyService.accountBalanceService(accountNumber);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

		final String money1A = (String) response.getEntity();
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1500.50)), money1A);
	}
}