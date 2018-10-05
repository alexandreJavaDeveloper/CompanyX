package com.companyx.i18n;

public class StringsI18N {

	public static final String TRANSFER_MONEY_ERROR = "Transfer problem between two accounts.";

	// TODO replacement automatically with the account number in the message
	public static final String ACCOUNT_NOT_FOUND = "Account not found. Account number: ";

	public static final String INTERNAL_ERROR = "Internal error were occurred.";

	public static final String INSUFFICIENT_FUNDS = "Sorry, you do not have sufficient funds.";

	public static final String START_TRANSFER_MONEY = "Starting the money transfer between two accounts.";

	public static final String END_TRANSFER_MONEY = "Ending the money transfer between two accounts.";

	public static final String START_DEPOSIT_MONEY = "Starting the money deposit.";

	public static final String END_DEPOSIT_MONEY = "Ending the money deposit.";

	public static final String START_ACCOUNT_BALANCE_MONEY = "Starting the getting the account balance.";

	public static final String END_ACCOUNT_BALANCE_MONEY = "Ending the getting the account balance.";

	public static final String ACCOUNT_NUMBER_MONEY_REQUIRED = "The fields Account number and Money cannot be null.";

	public static final String INVALID_MONEY = "Invalid money value. Please, only more than 0.";

	public static final String INVALID_MONEY_TRANSFER = "Invalid money transfer parameter. Please, use like this example: \"1234.12\"";

	public static final String JSON_DATA_NULL = "The JSON data sent is null.";

	public static final String PARSING_JSON_ERROR = "The JSON data sent is invalid. Example of valid JSON data: "
			+ "{\"receiverAccountNumber\":\"1A\","
			+ "\"senderAccountNumber\":\"2A\","
			+ "\"moneyToTransfer\":\"12.10\"}";

	public static final String DEPOSIT_MONEY_ERROR = "Deposit money problem.";
}