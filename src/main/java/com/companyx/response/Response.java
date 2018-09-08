package com.companyx.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a response independent of the media type.
 */
public interface Response {

	void setDateTransaction(Date date);

	void setReceiverAccountNumber(String accountNumber);

	void setReceiverCurrentMoney(BigDecimal money);

	void setSenderAccountNumber(String accountNumber);

	void setSenderCurrentMoney(BigDecimal money);
}