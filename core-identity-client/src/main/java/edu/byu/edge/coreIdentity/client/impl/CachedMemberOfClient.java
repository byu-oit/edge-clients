package edu.byu.edge.coreIdentity.client.impl;

import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 5/9/2017.
 * edge-clients
 */
public class CachedMemberOfClient extends MemberOfClientImpl implements MemberOfClient {
	public CachedMemberOfClient(TokenHeaderProvider tokenHeaderProvider) {
		super(tokenHeaderProvider);
	}

	public CachedMemberOfClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		super(tokenHeaderProvider, baseUrl);
	}

	@Override
	@Cacheable(value = "isMember", unless = "#result == false")
	public boolean isPersonMemberOfGroup(String personId, String group) throws RestHttpException, IOException {
		return super.isPersonMemberOfGroup(personId, group);
	}
}
