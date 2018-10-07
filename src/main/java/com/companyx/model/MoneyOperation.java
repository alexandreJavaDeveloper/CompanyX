package com.companyx.model;

public class MoneyOperation implements DataParsing {

	private String accountNumber;

	private String money;

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(final String money) {
		this.money = money;
	}
}