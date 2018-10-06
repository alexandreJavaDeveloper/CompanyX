package com.companyx.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.companyx.business.MoneyOperation;
import com.companyx.exception.InternalCommonException;
import com.companyx.helper.MoneyHelper;

@Path("/money")
public class MoneyService {

	private static final String MEDIA_TYPE = MediaType.APPLICATION_JSON;

	private static final Logger LOGGER;

	private final MoneyOperation moneyOperation;

	static {
		LOGGER = Logger.getLogger(MoneyService.class.getName());
	}

	/**
	 * Basic constructor. Initialize the variables.
	 */
	public MoneyService() {
		this.moneyOperation = new MoneyOperation();
	}

	/**
	 * Gets the account balance.
	 *
	 * @param accountNumber
	 * @return formatted (following the current currency) account balance
	 */
	@GET
	@Consumes(MoneyService.MEDIA_TYPE)
	@Path("/balances/{accountNumber}")
	public Response accountBalanceService(@QueryParam("accountNumber") final String accountNumber) {

		MoneyService.LOGGER.log(Level.INFO, "Start of account balance service...");

		try {

			final String accountBalance = MoneyHelper.formattMoney(this.moneyOperation.getAccountBalance(accountNumber));

			MoneyService.LOGGER.log(Level.INFO, "Ending of account balance service...");

			// return the response
			return javax.ws.rs.core.Response.ok(accountBalance).build();

		} catch (final InternalCommonException exception) {
			MoneyService.LOGGER.log(
					Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] "
							+ "- Message: " + exception.getMessage(), exception);

			return javax.ws.rs.core.Response.serverError().
					header("message", exception.getMessage()).
					status(exception.getResponseStatus()).build();
		}
	}
}