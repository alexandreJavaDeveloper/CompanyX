package com.companyx.model;

public class MoneyDeposit implements DataParsing {

	private String accountNumber;

	private String moneyToDeposit;

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMoneyToDeposit() {
		return this.moneyToDeposit;
	}

	public void setMoneyToDeposit(final String moneyToDeposit) {
		this.moneyToDeposit = moneyToDeposit;
	}
}