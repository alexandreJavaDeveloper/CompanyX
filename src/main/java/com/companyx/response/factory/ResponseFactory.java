package com.companyx.response.factory;

import java.util.Date;

import com.companyx.exception.AccountNotFoundException;
import com.companyx.exception.InternalSystemError;
import com.companyx.helper.MoneyFormatter;
import com.companyx.model.Account;
import com.companyx.response.Response;
import com.companyx.response.mediatype.JSONMoneyTransferResponse;

/**
 * Factory for generating any kind of media type of responses.
 */
public class ResponseFactory {

	/**
	 * Generates and return the JSON response.
	 *
	 * @param receiver
	 * @param sender
	 * @return JSON response
	 * @throws AccountNotFoundException
	 * @throws InternalSystemError
	 */
	public Response fetchJSONReponse(final Account receiver, final Account sender)
			throws AccountNotFoundException, InternalSystemError {

		final Response response = new JSONMoneyTransferResponse();

		response.setDateTransaction(new Date());
		response.setReceiverAccountNumber(receiver.getAccountNumber());
		response.setReceiverCurrentMoney(MoneyFormatter.formattMoney(receiver.getMoney()));
		response.setSenderAccountNumber(sender.getAccountNumber());
		response.setSenderCurrentMoney(MoneyFormatter.formattMoney(sender.getMoney()));

		return response;
	}
}