package com.companyx.reader;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;
import com.companyx.model.MoneyTransfer;

public class MediaTypeReader {

	private final ObjectMapper mapper;

	public MediaTypeReader() {
		this.mapper = new ObjectMapper();
	}

	/**
	 * @param jsonData
	 * @return MoneyTransfer deserialized
	 * @throws InvalidAttributesException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public MoneyTransfer readMoneyTransfer(final String jsonData) throws InvalidAttributesException {
		if (jsonData == null)
			throw new InvalidAttributesException(StringsI18N.JSON_DATA_NULL);

		//JSON from String to Object
		try {
			return this.mapper.readValue(jsonData, MoneyTransfer.class);
		} catch (final IOException e) {
			throw new InvalidAttributesException(StringsI18N.PARSING_JSON_ERROR);
		}
	}
}