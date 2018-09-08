package com.companyx.rest;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.companyx.exception.InternalCommonException;
import com.companyx.transaction.MoneyTransaction;

@Path("/transfers")
public class MoneyTransferService {

	private static final String FORMAT = MediaType.APPLICATION_JSON;

	private static final Logger LOGGER;

	private final MoneyTransaction moneyTransaction;

	static {
		LOGGER = Logger.getLogger(MoneyTransferService.class.getName());
	}

	/**
	 * Initialize fields and database memory. In case of real database won't be here this initialization.
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
	 * @return JSON result
	 */
	@POST
	@Produces(MoneyTransferService.FORMAT)
	@Path("/transfer/{receiverAccountNumber}/{senderAccountNumber}/{moneyToTransfer}")
	public Response transferMoneyService(
			@PathParam("receiverAccountNumber") final String receiverAccountNumber,
			@PathParam("senderAccountNumber") final String senderAccountNumber,
			@PathParam("moneyToTransfer") final BigDecimal moneyToTransfer) {

		try {
			// execute the transaction
			final com.companyx.response.Response response =
					this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			// return the response
			return Response.ok(response).build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			return Response.status(exception.getResponseStatus()).entity(exception.getMessage()).build();
		}
	}
}