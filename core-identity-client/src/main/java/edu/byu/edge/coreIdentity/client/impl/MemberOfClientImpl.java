package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.rest.HttpRestBuilder;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by eric on 2/3/16.
 */
public class MemberOfClientImpl implements MemberOfClient {
	private static final Logger LOG = LogManager.getLogger(MemberOfClientImpl.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private TokenHeaderProvider tokenHeaderProvider;
	private final String baseUrl;

	public MemberOfClientImpl(TokenHeaderProvider tokenHeaderProvider) {
		this.tokenHeaderProvider = tokenHeaderProvider;
		this.baseUrl = "https://api.byu.edu:443/domains/legacy/identity/access/ismember/v1/";
	}

	public MemberOfClientImpl(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		this.tokenHeaderProvider = tokenHeaderProvider;
		this.baseUrl = baseUrl;
	}

	@Override
	public boolean isPersonMemberOfGroup(String personId, String group) throws RestHttpException, IOException {
		final String url = baseUrl + URLEncoder.encode(group, "UTF-8").replace("+", "%20") + "/" + personId;
		final String result = new HttpRestBuilder(url)
				.accept("application/json")
				.contentType("application/json")
				.authorization(tokenHeaderProvider.getTokenHeaderValue())
				.get();
		final JsonNode root = MAPPER.readTree(result);
		final JsonNode response = root.findPath("response");
		if (!response.isMissingNode()){
			final JsonNode isMemberNode = response.path("isMember");
			if (isMemberNode.isBoolean()){
				final boolean isMember = isMemberNode.asBoolean();
				LOG.trace("isPersonMemberOfGroup " + personId + " " + group + " = " + isMember);
				return isMember;
			}
		}
		LOG.trace("isPersonMemberOfGroup " + personId + " " + group + " = " + false);
		return false;
	}
}
