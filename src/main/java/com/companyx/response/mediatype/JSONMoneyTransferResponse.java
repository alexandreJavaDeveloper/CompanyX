package com.companyx.response.mediatype;

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

	private String senderCurrentMoney;

	private String receiverCurrentMoney;

	private Date dateTransaction;

	@Override
	public String getSenderAccountNumber() {
		return this.senderAccountNumber;
	}

	@Override
	public void setSenderAccountNumber(final String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	@Override
	public String getReceiverAccountNumber() {
		return this.receiverAccountNumber;
	}

	@Override
	public void setReceiverAccountNumber(final String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	@Override
	public String getSenderCurrentMoney() {
		return this.senderCurrentMoney;
	}

	@Override
	public void setSenderCurrentMoney(final String senderCurrentMoney) {
		this.senderCurrentMoney = senderCurrentMoney;
	}

	@Override
	public String getReceiverCurrentMoney() {
		return this.receiverCurrentMoney;
	}

	@Override
	public void setReceiverCurrentMoney(final String receiverCurrentMoney) {
		this.receiverCurrentMoney = receiverCurrentMoney;
	}

	@Override
	public Date getDateTransaction() {
		return this.dateTransaction;
	}

	@Override
	public void setDateTransaction(final Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
}