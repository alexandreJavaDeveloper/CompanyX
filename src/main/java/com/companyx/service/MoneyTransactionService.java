package com.companyx.service;

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
import com.companyx.model.MoneyOperation;
import com.companyx.model.MoneyTransfer;
import com.companyx.reader.MediaTypeReader;
import com.companyx.service.response.ResponseService;

/**
 * Service of money transactions.
 * Receive the requests, call the execution class and response to the requester, following the media type defined.
 */
@Path("/transfers")
public class MoneyTransactionService {

	private static final String MEDIA_TYPE = MediaType.APPLICATION_JSON;

	private static final Logger LOGGER;

	private final MoneyTransaction moneyTransaction;

	private final ResponseService responseService;

	private final MediaTypeReader reader;

	static {
		LOGGER = Logger.getLogger(MoneyTransactionService.class.getName());
	}

	/**
	 * Basic constructor. Initialize the variables.
	 */
	public MoneyTransactionService() {
		this.moneyTransaction = new MoneyTransaction();
		this.responseService = new ResponseService();
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
	@Produces(MoneyTransactionService.MEDIA_TYPE)
	@Consumes(MoneyTransactionService.MEDIA_TYPE)
	@Path("/money")
	public Response transferMoneyService(final String data) {

		MoneyTransactionService.LOGGER.log(Level.INFO, "Start of transfer money service...");

		try {
			final MoneyTransfer moneyTransfer = (MoneyTransfer) this.reader.readJSONData(data, MoneyTransfer.class);

			final String receiverAccountNumber = moneyTransfer.getReceiverAccountNumber();
			final String senderAccountNumber = moneyTransfer.getSenderAccountNumber();
			final BigDecimal moneyToTransfer = MoneyHelper.translateMoney(moneyTransfer.getMoneyToTransfer());

			this.moneyTransaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

			MoneyTransactionService.LOGGER.log(Level.INFO, "Ending of transfer money service...");

			return Response.ok().build();

		} catch (final InternalCommonException exception) {
			return this.responseService.prepareErrorResponse(MoneyTransactionService.LOGGER, exception);
		}
	}

	/**
	 * As a money deposit is made in this service, is assumed that an authorization is made before this call.
	 *
	 * {@value data} expected as JSON format:
	 * 		{accountNumber, money}
	 *
	 * @param data JSON data
	 * @return Response
	 */
	@POST
	@Produces(MoneyTransactionService.MEDIA_TYPE)
	@Consumes(MoneyTransactionService.MEDIA_TYPE)
	@Path("/deposits")
	public Response moneyDepositService(final String data) {

		MoneyTransactionService.LOGGER.log(Level.INFO, "Start of deposit money service...");

		try {
			final MoneyOperation moneyDeposit = (MoneyOperation) this.reader.readJSONData(data, MoneyOperation.class);

			final String accountNumber = moneyDeposit.getAccountNumber();

			final BigDecimal moneyToDeposit = MoneyHelper.translateMoney(moneyDeposit.getMoney());

			this.moneyTransaction.deposit(accountNumber, moneyToDeposit);

			return Response.ok().build();

		} catch (final InternalCommonException exception) {
			return this.responseService.prepareErrorResponse(MoneyTransactionService.LOGGER, exception);
		}
	}

	/**
	 * As a cash withdraw is made in this service, is assumed that an authorization is made before this call.
	 *
	 * {@value data} expected as JSON format:
	 * 		{accountNumber, money}
	 *
	 * @param data JSON data
	 * @return Response
	 */
	@POST
	@Produces(MoneyTransactionService.MEDIA_TYPE)
	@Consumes(MoneyTransactionService.MEDIA_TYPE)
	@Path("/withdraw")
	public Response cashWithdrawService(final String data) {

		MoneyTransactionService.LOGGER.log(Level.INFO, "Start of withdraw money service...");

		try {

			final MoneyOperation cashWithdraw = (MoneyOperation) this.reader.readJSONData(data, MoneyOperation.class);

			final String accountNumber = cashWithdraw.getAccountNumber();

			final BigDecimal cashToWithdraw = MoneyHelper.translateMoney(cashWithdraw.getMoney());

			this.moneyTransaction.cashWithdraw(accountNumber, cashToWithdraw);

			return Response.ok().build();

		} catch (final InternalCommonException exception) {
			return this.responseService.prepareErrorResponse(MoneyTransactionService.LOGGER, exception);
		}
	}
}