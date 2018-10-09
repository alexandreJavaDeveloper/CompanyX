package com.companyx.model;

public class MoneyTransfer implements DataParsing {

	private String senderAccountNumber;

	private String receiverAccountNumber;

	private String moneyToTransfer;

	public String getSenderAccountNumber() {
		return this.senderAccountNumber;
	}

	public void setSenderAccountNumber(final String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getReceiverAccountNumber() {
		return this.receiverAccountNumber;
	}

	public void setReceiverAccountNumber(final String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public String getMoneyToTransfer() {
		return this.moneyToTransfer;
	}

	public void setMoneyToTransfer(final String moneyToTransfer) {
		this.moneyToTransfer = moneyToTransfer;
	}
}