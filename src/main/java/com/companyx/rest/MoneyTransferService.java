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
import com.companyx.mediatype.JSONMoneyTransferResponse;
import com.companyx.mock.RepositoryMock;
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
		RepositoryMock.getInstance().initialize();
	}

	/**
	 * TODO explicar porque POST
	 *
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @param moneyToTransfer
	 * @return
	 */
	@POST
	@Produces(MoneyTransferService.FORMAT)
	@Path("/transfer")
	public Response transferMoneyService(
			@PathParam("receiverAccountNumber") final String receiverAccountNumber,
			@PathParam("senderAccountNumber") final String senderAccountNumber,
			@PathParam("transferMoney") final BigDecimal moneyToTransfer) {

		try {
			// execute the transaction
			this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			// instantiate the response with JSON media type
			final JSONMoneyTransferResponse response = new JSONMoneyTransferResponse(receiverAccountNumber, senderAccountNumber);

			// return the response
			return Response.ok(response).build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
			return Response.status(exception.getResponseStatus()).entity(exception.getMessage()).build();
		}
	}
}