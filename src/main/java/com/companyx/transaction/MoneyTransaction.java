package com.companyx.transaction;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.InternalCommonException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Unique class to execute all transactions of money.
 */
public class MoneyTransaction {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MoneyTransaction.class.getName());
	}

	/**
	 * Performs the transfer between two accounts. In case of error rollback action is called.
	 * The operation is syncronized since the operation of finding accounts until saving.
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

			// update the accounts into Database memory
			RepositoryMock.getInstance().updateAccount(receiver);
			RepositoryMock.getInstance().updateAccount(sender);

			MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.END_TRANSFER_MONEY);

		} catch (final InternalCommonException e) {

			this.rollbackTransferAccounts(backupReceiver, backupSender, e);
			throw e;
		}
	}

	private void rollbackTransferAccounts(final Account backupReceiver, final Account backupSender, final Exception e) {
		MoneyTransaction.LOGGER.log(Level.SEVERE, StringsI18N.TRANSFER_MONEY_ERROR, e);

		RepositoryMock.getInstance().rollback(backupReceiver);
		RepositoryMock.getInstance().rollback(backupSender);
	}
}