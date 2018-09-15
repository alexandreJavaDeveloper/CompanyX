package com.companyx.transaction;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InternalCommonException;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.exception.InvalidMoneyException;
import com.companyx.helper.MoneyHelper;
import com.companyx.mock.RepositoryMock;
import com.companyx.response.Response;

public class MoneyTransactionTest {

	private MoneyTransaction transaction;

	@Before
	public void setup() throws InvalidAttributesException {
		this.transaction = new MoneyTransaction();
		RepositoryMock.getInstance().resetData();
	}

	@Test
	public void transferTest() throws InternalCommonException {
		final BigDecimal moneyToTransfer = new BigDecimal(10);

		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		final Response response = this.transaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

		Assert.assertEquals(receiverAccountNumber, response.getReceiverAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(1510.50)), response.getReceiverCurrentMoney());
		Assert.assertEquals(senderAccountNumber, response.getSenderAccountNumber());
		Assert.assertEquals(MoneyHelper.formattMoney(new BigDecimal(3990.30)), response.getSenderCurrentMoney());
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