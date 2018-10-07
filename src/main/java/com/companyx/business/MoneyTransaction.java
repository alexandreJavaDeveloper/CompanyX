package com.companyx.business;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.InternalCommonException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Class to execute all transactions of money.
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
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @param moneyToTransfer
	 * @throws InternalCommonException
	 */
	public synchronized void transfer(final String receiverAccountNumber, final String senderAccountNumber,
			final BigDecimal moneyToTransfer) throws InternalCommonException {

		MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.START_TRANSFER_MONEY);

		final Account receiver = RepositoryMock.getInstance().find(receiverAccountNumber);

		final Account sender = RepositoryMock.getInstance().find(senderAccountNumber);

		final Account backupReceiver = receiver.cloneAccount();

		final Account backupSender = sender.cloneAccount();

		try {

			sender.subtractMoney(moneyToTransfer);

			receiver.sumMoney(moneyToTransfer);

			// update the accounts
			RepositoryMock.getInstance().updateAccount(receiver);
			RepositoryMock.getInstance().updateAccount(sender);

			MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.END_TRANSFER_MONEY);

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, StringsI18N.TRANSFER_MONEY_ERROR, e);

			// in case of any problem the rollback should be done
			// TODO in case of fail? What to do? Give the responsibility to the database?
			RepositoryMock.getInstance().rollback(backupReceiver);
			RepositoryMock.getInstance().rollback(backupSender);
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

		MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.START_DEPOSIT_MONEY);

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		final Account backupAccount = account.cloneAccount();

		try {

			account.sumMoney(moneyToDeposit);

			// update the account
			RepositoryMock.getInstance().updateAccount(account);

			MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.END_DEPOSIT_MONEY);

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, StringsI18N.DEPOSIT_MONEY_ERROR, e);

			// in case of any problem the rollback should be done
			// TODO in case of fail? What to do? Give the responsibility to the database?
			RepositoryMock.getInstance().rollback(backupAccount);
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

		MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.START_CASH_WITHDRAW);

		final Account account = RepositoryMock.getInstance().find(accountNumber);

		final Account backupAccount = account.cloneAccount();

		try {

			account.subtractMoney(cashToWithdraw);

			// update the account
			RepositoryMock.getInstance().updateAccount(account);

			MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.END_CASH_WITHDRAW);

		} catch (final InternalCommonException e) {

			MoneyTransaction.LOGGER.log(Level.SEVERE, StringsI18N.CASH_WITHDRAW_ERROR, e);

			// in case of any problem the rollback should be done
			// TODO in case of fail? What to do? Give the responsibility to the database?
			RepositoryMock.getInstance().rollback(backupAccount);
			throw e;
		}
	}
}