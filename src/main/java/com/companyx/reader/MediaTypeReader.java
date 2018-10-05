package com.companyx.reader;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.companyx.exception.InvalidAttributesException;
import com.companyx.i18n.StringsI18N;
import com.companyx.model.DataParsing;

public class MediaTypeReader {

	private final ObjectMapper mapper;

	public MediaTypeReader() {
		this.mapper = new ObjectMapper();
	}

	/**
	 * @param jsonData
	 * @param classType
	 * @return implementation class of the {@link DataParsing} with already the values deserialized
	 * @throws InvalidAttributesException
	 */
	public DataParsing readJSONData(final String jsonData, final Class<?> classType) throws InvalidAttributesException {
		if (jsonData == null || jsonData.isEmpty())
			throw new InvalidAttributesException(StringsI18N.INVALID_JSON_DATA);

		//JSON from String to Object
		try {
			return (DataParsing) this.mapper.readValue(jsonData, classType);
		} catch (final IOException e) {
			throw new InvalidAttributesException(StringsI18N.PARSING_JSON_ERROR);
		}
	}
}