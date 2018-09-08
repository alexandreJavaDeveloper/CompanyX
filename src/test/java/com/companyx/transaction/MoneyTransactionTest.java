package com.companyx.transaction;

import java.math.BigDecimal;

import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InternalCommonException;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;
import com.companyx.response.Response;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class MoneyTransactionTest extends JerseyTest {

	private MoneyTransaction transaction;

	/**
	 * Configuration of Jersey API
	 */
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}

	@Before
	public void setup() throws InvalidAttributesException {
		this.transaction = new MoneyTransaction();
		RepositoryMock.getInstance().clean();
	}

	@Test
	public void transferTest() throws InternalCommonException {
		final BigDecimal moneyToTransfer = new BigDecimal(10);

		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		final Response response = this.transaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

		Assert.assertEquals(receiverAccountNumber, response.getReceiverAccountNumber());
		Assert.assertEquals(new BigDecimal(1510.50), response.getReceiverCurrentMoney());
		Assert.assertEquals(senderAccountNumber, response.getSenderAccountNumber());
		Assert.assertEquals(new BigDecimal(3990.30), response.getSenderCurrentMoney());
	}

	@Test
	public void testUserFetchesSuccess() throws JSONException {
		//		RestAssured.expect().
		//		when().get("/companyx/transfers/test");
		//		final WebResource webResource = this.client().resource("http://localhost:8080/");
		//		final JSONObject json = webResource.path("/companyx/transfers/test2").post(JSONObject.class);
	}
}