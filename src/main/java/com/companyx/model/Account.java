package com.companyx.model;

import java.math.BigDecimal;

import com.companyx.exception.InsufficientFundsException;
import com.companyx.exception.InternalSystemError;
import com.companyx.i18n.StringsI18N;

public class Account implements Cloneable {

	private final String accountNumber;

	private BigDecimal money;

	public Account(final String accountNumber, final BigDecimal money) {
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
	 */
	public synchronized void sumMoney(final BigDecimal moneyToSum) {
		this.money = new BigDecimal(this.money.doubleValue() + moneyToSum.doubleValue());
	}

	/**
	 * Subtract the current money with the {@value moneyToSum} parameter.
	 *
	 * @param moneyToSubtract
	 * @throws InsufficientFundsException
	 */
	public synchronized void subtractMoney(final BigDecimal moneyToSubtract) throws InsufficientFundsException {
		// if there is no account balance
		if (moneyToSubtract.doubleValue() > this.money.doubleValue())
			throw new InsufficientFundsException(StringsI18N.INSUFFICIENT_FUNDS);

		this.money = new BigDecimal(this.money.doubleValue() - moneyToSubtract.doubleValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.accountNumber == null) ? 0 : this.accountNumber.hashCode());
		result = prime * result + ((this.money == null) ? 0 : this.money.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (this.accountNumber == null) return false;
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
	 * Clones this account.
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