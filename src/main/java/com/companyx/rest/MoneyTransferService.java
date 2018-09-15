package com.companyx.rest;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.companyx.exception.InternalCommonException;
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

	static {
		LOGGER = Logger.getLogger(MoneyTransferService.class.getName());
	}

	/**
	 * Basic constructor. Initialize the money transaction object.
	 */
	public MoneyTransferService() {
		this.moneyTransaction = new MoneyTransaction();
	}

	/**
	 * Transfer money between two accounts. In case of fail, an appropriate status code is returned.
	 * POST is defined because this method is not idempotent, not safe and the transaction is treated
	 * with money between two accounts.
	 *
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @param moneyToTransfer
	 * @return response as JSON result
	 */
	@POST
	@Produces(MoneyTransferService.MEDIA_TYPE)
	@Path("/transfer/{receiverAccountNumber}/{senderAccountNumber}/{moneyToTransfer}")
	public javax.ws.rs.core.Response transferMoneyService(
			@PathParam("receiverAccountNumber") final String receiverAccountNumber,
			@PathParam("senderAccountNumber") final String senderAccountNumber,
			@PathParam("moneyToTransfer") final BigDecimal moneyToTransfer) {

		try {
			// execute the transaction
			final Response response = this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			// return the response
			return javax.ws.rs.core.Response.ok(response).build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(
					Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] - Message: " + exception.getMessage(), exception);

			return javax.ws.rs.core.Response.serverError().status(exception.getResponseStatus()).
					entity(exception.getMessage()).build();
		}
	}
}