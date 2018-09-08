package com.companyx.response;

import java.util.Date;

/**
 * Represents a response independent of the media type.
 */
public interface Response {

	void setDateTransaction(Date date);

	Date getDateTransaction();

	void setReceiverAccountNumber(String accountNumber);

	String getReceiverAccountNumber();

	void setReceiverCurrentMoney(String money);

	String getReceiverCurrentMoney();

	void setSenderAccountNumber(String accountNumber);

	String getSenderAccountNumber();

	void setSenderCurrentMoney(String money);

	String getSenderCurrentMoney();
}