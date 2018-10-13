package com.companyx.model;

import java.math.BigDecimal;

import com.companyx.business.MoneyTransaction;
import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InsufficientFundsException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.exception.InvalidMoneyException;
import com.companyx.i18n.StringsI18N;
import com.companyx.mock.RepositoryMock;

/**
 * Represents an account for performing money transaction by #{@link MoneyTransaction} class.
 */
public class Account implements Cloneable {

	// used for setting the amount precision of the 'money' attribute
	private static final int DECIMAL_SCALE_MONEY = 2;

	// used for ensuring the security of the 'money' attribute
	private final Object lock = new Object();

	// represents the account number of the account
	private final String accountNumber;

	// safe attribute. Represents the current money/cash of the account
	private BigDecimal money;

	/**
	 * Constructor of Account object.
	 *
	 * @param accountNumber not null
	 * @param money not null
	 * @throws InvalidAttributesException
	 */
	public Account(final String accountNumber, final BigDecimal money) throws InvalidAttributesException {
		if (accountNumber == null || money == null)
			throw new InvalidAttributesException(StringsI18N.ACCOUNT_NUMBER_MONEY_REQUIRED);

		this.accountNumber = accountNumber;
		this.money = money.setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP); // setting the amount precision
	}

	/**
	 * Safe method.
	 * Sum the current money with the {@value moneyToSum} parameter.
	 *
	 * @param moneyToSum
	 * @throws InvalidMoneyException
	 * @throws InvalidAttributesException
	 * @throws InternalSystemError
	 * @throws AccountNotFoundException
	 */
	public void sumMoney(final BigDecimal moneyToSum) throws InvalidMoneyException, AccountNotFoundException, InternalSystemError, InvalidAttributesException {

		if (moneyToSum == null || moneyToSum.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidMoneyException();

		synchronized (this.lock) {

			this.money = RepositoryMock.getInstance().find(this.accountNumber).getMoney();

			this.money = this.money.add(moneyToSum). // setting the amount precision
					setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * Safe method.
	 * Subtract the current money with the {@value moneyToSubtract} parameter.
	 *
	 * @param moneyToSubtract
	 * @throws InsufficientFundsException
	 * @throws InvalidMoneyException
	 * @throws InvalidAttributesException
	 * @throws InternalSystemError
	 * @throws AccountNotFoundException
	 */
	public void subtractMoney(final BigDecimal moneyToSubtract) throws InsufficientFundsException, InvalidMoneyException, AccountNotFoundException, InternalSystemError, InvalidAttributesException {

		if (moneyToSubtract == null || moneyToSubtract.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidMoneyException();

		synchronized (this.lock) {

			this.money = RepositoryMock.getInstance().find(this.accountNumber).getMoney();

			if (this.money.compareTo(moneyToSubtract.setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP)) < 0)
				throw new InsufficientFundsException(StringsI18N.INSUFFICIENT_FUNDS);

			this.money = this.money.subtract(moneyToSubtract). // setting the amount precision
					setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP);
		}
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	@Override
	public int hashCode() {
		return this.accountNumber == null ? 0 : this.accountNumber.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Account == false) return false;

		final Account mtm = (Account) obj;
		if (mtm.getAccountNumber() == null) return false;

		return this.accountNumber.equals(mtm.getAccountNumber());
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + this.accountNumber + ", money=" + this.money + "]";
	}

	/**
	 * Clones this current account.
	 *
	 * @return Account
	 * @throws InternalSystemError
	 */
	public Account cloneAccount() throws InternalSystemError {
		try {
			return (Account) this.clone();
		} catch (final CloneNotSupportedException e) {
			throw new InternalSystemError();
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}