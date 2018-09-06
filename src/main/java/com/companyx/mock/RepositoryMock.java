package com.companyx.mock;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.i18n.StringsI18N;
import com.companyx.model.Account;

/**
 * Represents the Database memory.
 */
public class RepositoryMock {

	private static RepositoryMock instance;

	// key = accountNumber, value = Account. Accounts are in safe mode. Only this class changes their behavior.
	private Map<String, Account> accounts;

	/**
	 * Private constructor.
	 */
	private RepositoryMock() {
		// Do nothing
	}

	public static RepositoryMock getInstance() {
		if (RepositoryMock.instance == null)
			RepositoryMock.instance = new RepositoryMock();

		return RepositoryMock.instance;
	}

	/**
	 * Initialize the memory data store.
	 */
	public void initialize() {
		RepositoryMock.instance.accounts = new HashMap<String, Account>(4);
		RepositoryMock.instance.buildAccounts();
	}

	private void buildAccounts() {
		this.accounts.put("1A", new Account("1A", new BigDecimal(1500.50)));
		this.accounts.put("2A", new Account("2A", new BigDecimal(4000.30)));
		this.accounts.put("3A", new Account("3A", new BigDecimal(9880.00)));
		this.accounts.put("4A", new Account("4A", new BigDecimal(10000.40)));
	}

	/**
	 * Find the Account by the {@value accountNumber}.
	 *
	 * @param accountNumber
	 * @return Account
	 * @throws AccountNotFoundException
	 * @throws InternalSystemError
	 */
	public Account find(final String accountNumber) throws AccountNotFoundException, InternalSystemError {
		final Account account = RepositoryMock.instance.accounts.get(accountNumber);

		if (account == null)
			throw new AccountNotFoundException(StringsI18N.ACCOUNT_NOT_FOUND + accountNumber);

		// clone to not allow change the behavior of the account
		return account.cloneAccount();
	}

	/**
	 * Do a rollback transaction of the {@value account} parameter.
	 *
	 * TODO create a rollback without parameters. Do rollback of all operation. In that case,
	 * would be better using JDBC, like: "Connection conn; conn.rollback()".
	 *
	 * @param account Account
	 */
	public void rollback(final Account account) {
		// in this case, just put the old (backup) account
		this.updateAccount(account);
	}

	/**
	 * Updates the account in the Database memory.
	 *
	 * @param account Account
	 */
	public void updateAccount(final Account account) {
		RepositoryMock.instance.accounts.put(account.getAccountNumber(), account);
	}
}