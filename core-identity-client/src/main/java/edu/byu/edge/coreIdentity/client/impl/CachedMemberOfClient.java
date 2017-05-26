package edu.byu.edge.coreIdentity.client.impl;

import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 5/9/2017.
 * edge-clients
 */
public class CachedMemberOfClient extends MemberOfClientImpl implements MemberOfClient {

	private static final Logger LOG = LogManager.getLogger(CachedMemberOfClient.class);

	public CachedMemberOfClient(TokenHeaderProvider tokenHeaderProvider) {
		super(tokenHeaderProvider);
	}

	public CachedMemberOfClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		super(tokenHeaderProvider, baseUrl);
	}

	@Override
	@Cacheable(value = "isMemberCache", unless = "#result == false")
	public boolean isPersonMemberOfGroup(String personId, String group) throws RestHttpException, IOException {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Is member of - cached: " + personId + ":" + group);
		}
		return super.isPersonMemberOfGroup(personId, group);
	}
}
