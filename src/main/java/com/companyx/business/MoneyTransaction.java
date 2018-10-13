package com.companyx.business;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.InternalCommonException;
import com.companyx.helper.MoneyHelper;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Performs all transactions of money.
 * Each operation is treated carrying about the rollback action into database/memory in case of fail.
 */
public class MoneyTransaction {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MoneyTransaction.class.getName());
	}

	/**
	 * Performs the transfer between two accounts. In case of error, rollback action is called.
	 * The operation is synchronized for ensuring the transfer between two accounts happens without another
	 * process changing at the same time.
	 *
	 * @param senderAccountNumber
	 * @param receiverAccountNumber
	 * @param moneyToTransfer
	 * @throws InternalCommonException
	 */
	public void transfer(final String senderAccountNumber, final String receiverAccountNumber,
			final BigDecimal moneyToTransfer) throws InternalCommonException {

		MoneyTransaction.LOGGER.log(Level.INFO, "Starting the money transfer between two accounts.");

		final Account sender = RepositoryMock.getInstance().find(senderAccountNumber);

		final Account receiver = RepositoryMock.getInstance().find(receiverAccountNumber);

		MoneyTransaction.LOGGER.log(Level.INFO, "Transaction sender from: " + senderAccountNumber + " to: "
				+ receiverAccountNumber + " the cash: " + MoneyHelper.formattMoney(moneyToTransfer));

		try {

			sender.subtractMoney(moneyToTransfer);
			receiver.sumMoney(moneyToTransfer);

			// update the accounts
			RepositoryMock.getInstance().updateAccount(sender);
			RepositoryMock.getInstance().updateAccount(receiver);

			MoneyTransaction.LOGGER.log(Level.INFO, "Ended the money transfer between two accounts.");

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, "Transfer problem between two accounts.", e);

			RepositoryMock.getInstance().rollback(senderAccountNumber);
			RepositoryMock.getInstance().rollback(receiverAccountNumber);
			throw e;
		}
	}

	/**
	 * Performs the deposit into the {@value accountNumber} account.
	 * In case of fail, rollback action is executed.
	 *
	 * @param accountNumber
	 * @param moneyToDeposit
	 * @throws InternalCommonException
	 */
	public void deposit(final String accountNumber, final BigDecimal moneyToDeposit) throws InternalCommonException {

		MoneyTransaction.LOGGER.log(Level.INFO, "Starting the money deposit.");

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		try {

			account.sumMoney(moneyToDeposit);

			RepositoryMock.getInstance().updateAccount(account);

			MoneyTransaction.LOGGER.log(Level.INFO, "Ended the money deposit.");

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, "Deposit money problem.", e);

			RepositoryMock.getInstance().rollback(accountNumber);
			throw e;
		}
	}

	/**
	 * Performs the cash withdraw into the {@value accountNumber} account.
	 * In case of fail, rollback action is executed.
	 *
	 * @param accountNumber
	 * @param cashToWithdraw
	 * @throws InternalCommonException
	 */
	public void cashWithdraw(final String accountNumber, final BigDecimal cashToWithdraw) throws InternalCommonException {

		MoneyTransaction.LOGGER.log(Level.INFO, "Starting the withdraw money.");

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		try {

			account.subtractMoney(cashToWithdraw);

			RepositoryMock.getInstance().updateAccount(account);

			MoneyTransaction.LOGGER.log(Level.INFO, "Ended the withdraw money.");

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, "Cash withdraw problem.", e);

			RepositoryMock.getInstance().rollback(accountNumber);
			throw e;
		}
	}
}