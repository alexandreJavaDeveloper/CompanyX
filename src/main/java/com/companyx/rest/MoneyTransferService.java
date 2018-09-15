package com.companyx.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.companyx.exception.InternalCommonException;
import com.companyx.helper.MoneyHelper;
import com.companyx.model.MoneyTransfer;
import com.companyx.reader.MediaTypeReader;
import com.companyx.response.Response;
import com.companyx.transaction.MoneyTransaction;

/**
 * Service of transactions for money transfer. Receive the requests, call the execution class and response to
 * the requester, following the media type defined.
 */
@Path("/transfers")
public class MoneyTransferService {

	private static final String MEDIA_TYPE = MediaType.APPLICATION_JSON;

	private static final Logger LOGGER;

	private final MoneyTransaction moneyTransaction;

	private final MediaTypeReader reader;

	static {
		LOGGER = Logger.getLogger(MoneyTransferService.class.getName());
	}

	/**
	 * Basic constructor. Initialize the variables.
	 */
	public MoneyTransferService() {
		this.moneyTransaction = new MoneyTransaction();
		this.reader = new MediaTypeReader();
	}

	/**
	 * Transfer money between two accounts. In case of fail, an appropriate status code is returned.
	 * POST is defined because this method is not idempotent, not safe and the transaction is treated
	 * with money between two accounts.
	 *
	 * {@value jsonData} expected as JSON format:
	 * 		{receiverAccountNumber, senderAccountNumber, moneyToTransfer}
	 *
	 * @param jsonData
	 * @return Response in JSON format including the updated accounts
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Produces(MoneyTransferService.MEDIA_TYPE)
	@Consumes(MoneyTransferService.MEDIA_TYPE)
	@Path("/transfer")
	public javax.ws.rs.core.Response transferMoneyService(final String jsonData) {

		try {
			final MoneyTransfer moneyTransfer = this.reader.readMoneyTransfer(jsonData);

			final String receiverAccountNumber = moneyTransfer.getReceiverAccountNumber();
			final String senderAccountNumber = moneyTransfer.getSenderAccountNumber();
			final BigDecimal moneyToTransfer = MoneyHelper.transformTransferMoney(moneyTransfer.getMoneyToTransfer());

			// execute the transaction
			final Response response = this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			// return the response
			return javax.ws.rs.core.Response.ok(response).build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(
					Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] - Message: " + exception.getMessage(), exception);

			return javax.ws.rs.core.Response.serverError().status(exception.getResponseStatus()).build();
		}
	}
}