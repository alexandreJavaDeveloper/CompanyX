package com.companyx.rest;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.companyx.business.MoneyTransaction;
import com.companyx.exception.InternalCommonException;
import com.companyx.helper.MoneyHelper;
import com.companyx.model.MoneyDeposit;
import com.companyx.model.MoneyTransfer;
import com.companyx.reader.MediaTypeReader;

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
	 * {@value data} expected as JSON format:
	 * 		{receiverAccountNumber, senderAccountNumber, moneyToTransfer}
	 *
	 * @param data JSON data
	 * @return Response
	 */
	@POST
	@Produces(MoneyTransferService.MEDIA_TYPE)
	@Consumes(MoneyTransferService.MEDIA_TYPE)
	@Path("/money")
	public Response transferMoneyService(final String data) {

		MoneyTransferService.LOGGER.log(Level.INFO, "Start of transfer money service...");

		try {
			final MoneyTransfer moneyTransfer = (MoneyTransfer) this.reader.readJSONData(data, MoneyTransfer.class);

			final String receiverAccountNumber = moneyTransfer.getReceiverAccountNumber();
			final String senderAccountNumber = moneyTransfer.getSenderAccountNumber();
			final BigDecimal moneyToTransfer = MoneyHelper.transformTransferMoney(moneyTransfer.getMoneyToTransfer());

			// execute the transaction
			this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			MoneyTransferService.LOGGER.log(Level.INFO, "Ending of transfer money service...");

			// return the response
			return javax.ws.rs.core.Response.ok().build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(
					Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] "
							+ "- Message: " + exception.getMessage(), exception);

			return javax.ws.rs.core.Response.serverError().status(exception.getResponseStatus()).build();
		}
	}

	/**
	 * As a deposit is made in this service, is assumed that an authorization is made before this call.
	 *
	 * {@value data} expected as JSON format:
	 * 		{accountNumber, moneyToDeposit}
	 *
	 * @param data JSON data
	 * @return Response
	 */
	@POST
	@Produces(MoneyTransferService.MEDIA_TYPE)
	@Consumes(MoneyTransferService.MEDIA_TYPE)
	@Path("/deposits")
	public Response depositMoneyService(final String data) {
		MoneyTransferService.LOGGER.log(Level.INFO, "Start of deposit money service...");

		try {
			final MoneyDeposit moneyDeposit = (MoneyDeposit) this.reader.readJSONData(data, MoneyDeposit.class);

			final String accountNumber = moneyDeposit.getAccountNumber();

			final BigDecimal moneyToDeposit = MoneyHelper.transformTransferMoney(moneyDeposit.getMoneyToDeposit());

			this.moneyTransaction.deposit(accountNumber, moneyToDeposit);

			// return the response
			return javax.ws.rs.core.Response.ok().build();

		} catch (final InternalCommonException exception) {
			MoneyTransferService.LOGGER.log(
					Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] "
							+ "- Message: " + exception.getMessage(), exception);

			return javax.ws.rs.core.Response.serverError().status(exception.getResponseStatus()).build();
		}
	}
}