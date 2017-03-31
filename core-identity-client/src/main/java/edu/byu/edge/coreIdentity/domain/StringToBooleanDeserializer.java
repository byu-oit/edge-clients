package edu.byu.edge.coreIdentity.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 3/31/2017.
 * edge-clients
 */
public class StringToBooleanDeserializer extends JsonDeserializer<Boolean> {
	@Override
	public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return "Y".equalsIgnoreCase(jsonParser.getText());
	}
}
