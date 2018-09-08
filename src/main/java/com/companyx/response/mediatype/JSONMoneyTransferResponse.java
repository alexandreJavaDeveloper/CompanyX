package com.companyx.response.mediatype;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.companyx.response.Response;

/**
 * Class responsible to represents and generate the response request when transported to outside as JSON format,
 * independent of the model is necessary to getting these values. Dependencies of external classes are not accepted,
 * except XmlRootElement (for generating JSON response).
 *
 * NOTE: JSON format is defined. In case to change the media type response, please create another class.
 */
@XmlRootElement
public class JSONMoneyTransferResponse implements Response {

	private String senderAccountNumber;

	private String receiverAccountNumber;

	private BigDecimal senderCurrentMoney;

	private BigDecimal receiverCurrentMoney;

	private Date dateTransaction;

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

	public BigDecimal getSenderCurrentMoney() {
		return this.senderCurrentMoney;
	}

	public void setSenderCurrentMoney(final BigDecimal senderCurrentMoney) {
		this.senderCurrentMoney = senderCurrentMoney;
	}

	public BigDecimal getReceiverCurrentMoney() {
		return this.receiverCurrentMoney;
	}

	public void setReceiverCurrentMoney(final BigDecimal receiverCurrentMoney) {
		this.receiverCurrentMoney = receiverCurrentMoney;
	}

	public Date getDateTransaction() {
		return this.dateTransaction;
	}

	public void setDateTransaction(final Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
}