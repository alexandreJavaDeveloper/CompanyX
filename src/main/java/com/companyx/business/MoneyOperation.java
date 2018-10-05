package com.companyx.business;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Responsible for operations involving money without transactions.
 */
public class MoneyOperation {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MoneyOperation.class.getName());
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

		MoneyOperation.LOGGER.log(Level.INFO, StringsI18N.START_ACCOUNT_BALANCE_MONEY);

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		MoneyOperation.LOGGER.log(Level.INFO, StringsI18N.END_ACCOUNT_BALANCE_MONEY);

		return account.getMoney();
	}
}