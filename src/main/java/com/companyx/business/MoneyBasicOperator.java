package com.companyx.business;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Responsible for basic operations involving money without transactions.
 */
public class MoneyBasicOperator {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MoneyBasicOperator.class.getName());
	}

	/**
	 * Gets the account balance.
	 *
	 * @param accountNumber
	 * @return account balance
	 * @throws AccountNotFoundException
	 * @throws InternalSystemError
	 * @throws InvalidAttributesException
	 */
	public BigDecimal getAccountBalance(final String accountNumber) throws AccountNotFoundException, InternalSystemError, InvalidAttributesException {

		MoneyBasicOperator.LOGGER.log(Level.INFO, "Starting the getting the account balance.");

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		MoneyBasicOperator.LOGGER.log(Level.INFO, "Ending the getting the account balance.");

		return account.getMoney();
	}
}