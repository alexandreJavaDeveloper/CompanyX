package com.companyx.service.response;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.companyx.exception.InternalCommonException;

public class ResponseService {

	/**
	 * Prepare the error response printing the appropriate log and creating the Response with a error message.
	 *
	 * @param logger
	 * @param exception
	 * @return Response
	 */
	public Response prepareErrorResponse(final Logger logger, final InternalCommonException exception){
		logger.log(Level.SEVERE, "Status code response [" + exception.getResponseStatus().getStatusCode() + "] "
				+ "- Message: " + exception.getMessage(), exception);

		return javax.ws.rs.core.Response.serverError().
				header("message", exception.getMessage()).
				status(exception.getResponseStatus()).build();
	}
}