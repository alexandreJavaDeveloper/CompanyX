package com.companyx.model;

import java.math.BigDecimal;

import com.companyx.exception.InsufficientFundsException;
import com.companyx.exception.InternalSystemError;
import com.companyx.exception.InvalidAttributesException;
import com.companyx.exception.InvalidMoneyException;
import com.companyx.i18n.StringsI18N;
import com.companyx.transaction.MoneyTransaction;

/**
 * Represents an account for performing money transaction by #{@link MoneyTransaction}.
 */
public class Account implements Cloneable {

	private static final int DECIMAL_SCALE_MONEY = 2;

	private final String accountNumber;

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
		this.money = money;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	/**
	 * Sum the current money with the {@value moneyToSum} parameter.
	 *
	 * @param moneyToSum
	 * @throws InvalidMoneyException
	 */
	public void sumMoney(final BigDecimal moneyToSum) throws InvalidMoneyException {
		if (moneyToSum.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidMoneyException();

		this.money = this.money.add(moneyToSum). // setting the amount precision
				setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Subtract the current money with the {@value moneyToSubtract} parameter.
	 *
	 * @param moneyToSubtract
	 * @throws InsufficientFundsException
	 * @throws InvalidMoneyException
	 */
	public void subtractMoney(final BigDecimal moneyToSubtract) throws InsufficientFundsException, InvalidMoneyException {
		if (moneyToSubtract.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidMoneyException();

		if (this.money.compareTo(moneyToSubtract) < 0)
			throw new InsufficientFundsException(StringsI18N.INSUFFICIENT_FUNDS);

		this.money = this.money.subtract(moneyToSubtract). // setting the amount precision
				setScale(Account.DECIMAL_SCALE_MONEY, BigDecimal.ROUND_HALF_UP);
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