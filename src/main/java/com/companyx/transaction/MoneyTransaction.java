package com.companyx.transaction;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.companyx.exception.InternalCommonException;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;
import com.companyx.response.Response;
import com.companyx.response.factory.ResponseFactory;

/**
 * Unique class to execute all transactions of money.
 *
 * The class {@link Account} has two methods where is changed the money of the account, but is changed only
 * whether this class decide to do that.
 */
public class MoneyTransaction {

	private static final Logger LOGGER;

	private final ResponseFactory responseFactory;

	static {
		LOGGER = Logger.getLogger(MoneyTransaction.class.getName());
	}

	public MoneyTransaction() {
		this.responseFactory = new ResponseFactory();
	}

	/**
	 * Performs the transfer between two accounts. In case of error rollback action is called.
	 * The operation is synchronized since the operation of finding accounts until saving.
	 *
	 * NOTE: the Response is returned just to ensure all calls to RepositoryMock is called only in this class for
	 * avoiding threading problems, as for generating is necessary retrieving the accounts into database.
	 *
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @param moneyToTransfer
	 * @return Response
	 * @throws InternalCommonException
	 */
	public synchronized Response transfer(final String receiverAccountNumber, final String senderAccountNumber,
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

			// generates the JSON response. On this method comment explain why the response is here
			final Response response = this.responseFactory.fetchJSONReponse(receiver, sender);

			MoneyTransaction.LOGGER.log(Level.INFO, StringsI18N.END_TRANSFER_MONEY);

			return response;
		} catch (final InternalCommonException e) {

			// in case of any problem the rollback should be done
			this.rollbackTransferAccounts(backupReceiver, backupSender, e);
			throw e;
		}
	}

	private void rollbackTransferAccounts(final Account backupReceiver, final Account backupSender, final Exception e)
			throws InvalidAttributesException {
		MoneyTransaction.LOGGER.log(Level.SEVERE, StringsI18N.TRANSFER_MONEY_ERROR, e);

		// TODO in case of fail? What to do? Give the responsibility to the database?
		RepositoryMock.getInstance().rollback(backupReceiver);
		RepositoryMock.getInstance().rollback(backupSender);
	}
}