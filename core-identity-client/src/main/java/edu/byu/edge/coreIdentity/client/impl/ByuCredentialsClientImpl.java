package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.byu.edge.coreIdentity.client.ByuCredentialsClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.rest.HttpRestBuilder;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 9/1/2017.
 * edge-clients
 */
public class ByuCredentialsClientImpl implements ByuCredentialsClient {
	private static final Logger LOG = LogManager.getLogger(ByuCredentialsClientImpl.class);

	static final String DEFAULT_BASE_URL = "https://api.byu.edu:443/byuapi/persons/v2";

	private final TokenHeaderProvider tokenHeaderProvider;
	private final String baseUrl;
	private final ObjectMapper mapper;

	public ByuCredentialsClientImpl(TokenHeaderProvider tokenHeaderProvider) {
		this.tokenHeaderProvider = tokenHeaderProvider;
		this.baseUrl = DEFAULT_BASE_URL;
		this.mapper = new ObjectMapper();
	}

	public ByuCredentialsClientImpl(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		this.tokenHeaderProvider = tokenHeaderProvider;
		this.baseUrl = baseUrl;
		this.mapper = new ObjectMapper();
	}

	public ByuCredentialsClientImpl(TokenHeaderProvider tokenHeaderProvider, String baseUrl, ObjectMapper mapper) {
		this.tokenHeaderProvider = tokenHeaderProvider;
		this.baseUrl = baseUrl;
		this.mapper = mapper;
	}

	@Override
	public String getLdsCmisIdAssociatedWithByuId(String byuId) throws RestHttpException, IOException {
		LOG.trace("getLdsCmisIdAssociatedWithByuId: " + byuId);
		final String result = new HttpRestBuilder(baseUrl + "/" + byuId + "/credentials")
				.accept("application/json")
				.authorization(tokenHeaderProvider.getTokenHeaderValue())
				.get();
		String cmisId = null;
		try {
			final JsonNode rootNode = mapper.readTree(result);
			final ArrayNode values = (ArrayNode) rootNode.findPath("values");
			for (JsonNode value : values) {
				if (value.findPath("credential_type").findPath("value").asText().equalsIgnoreCase("LDS_CMIS_ID")) {
					cmisId = value.findPath("credential_id").findPath("value").asText();
				}
			}
		} catch (JsonMappingException e) {
			LOG.warn(e.getMessage() + " -- for result: " + result, e);
			return null;
		}
		return cmisId;
	}
}
