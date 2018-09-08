package com.companyx.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a response independent of the media type.
 */
public interface Response {

	void setDateTransaction(Date date);

	Date getDateTransaction();

	void setReceiverAccountNumber(String accountNumber);

	String getReceiverAccountNumber();

	void setReceiverCurrentMoney(BigDecimal money);

	BigDecimal getReceiverCurrentMoney();

	void setSenderAccountNumber(String accountNumber);

	String getSenderAccountNumber();

	void setSenderCurrentMoney(BigDecimal money);

	BigDecimal getSenderCurrentMoney();
}