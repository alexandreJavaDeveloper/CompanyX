package com.companyx.mediatype;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.mock.RepositoryMock;
import com.companyx.model.Account;

/**
 * Class responsible to represents the response request when transported to outside as JSON format.
 *
 * NOTE: JSON format is defined. In case to change the media type response, create another response class and use it.
 */
@XmlRootElement
public class JSONMoneyTransferResponse {

	private String senderAccountNumber;

	private String receiverAccountNumber;

	private BigDecimal senderCurrentMoney;

	private BigDecimal receiverCurrentMoney;

	private Date dateTransaction;

	/**
	 * The constructor creates the response to be prepared to be transported in the response.
	 *
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @throws AccountNotFoundException
	 * @throws InternalSystemError
	 */
	public JSONMoneyTransferResponse(final String receiverAccountNumber, final String senderAccountNumber)
			throws AccountNotFoundException, InternalSystemError {
		this.createResponse(receiverAccountNumber, senderAccountNumber);
	}

	/**
	 * Create the response request to be transported in the response.
	 *
	 * @param receiverAccountNumber
	 * @param senderAccountNumber
	 * @throws AccountNotFoundException
	 * @throws InternalSystemError
	 */
	protected void createResponse(final String receiverAccountNumber, final String senderAccountNumber)
			throws AccountNotFoundException, InternalSystemError {

		final Account receiver = RepositoryMock.getInstance().find(receiverAccountNumber);
		final Account sender = RepositoryMock.getInstance().find(senderAccountNumber);

		this.dateTransaction = new Date();
		this.receiverAccountNumber = receiver.getAccountNumber();
		this.receiverCurrentMoney = receiver.getMoney();
		this.senderAccountNumber = sender.getAccountNumber();
		this.senderCurrentMoney = sender.getMoney();
	}

	public String getSenderAccountNumber() {
		return this.senderAccountNumber;
	}

	public String getReceiverAccountNumber() {
		return this.receiverAccountNumber;
	}

	public BigDecimal getSenderCurrentMoney() {
		return this.senderCurrentMoney;
	}

	public BigDecimal getReceiverCurrentMoney() {
		return this.receiverCurrentMoney;
	}

	public Date getDateTransaction() {
		return this.dateTransaction;
	}
}