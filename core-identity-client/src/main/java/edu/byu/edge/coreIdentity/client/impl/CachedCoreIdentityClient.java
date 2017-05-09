package edu.byu.edge.coreIdentity.client.impl;

import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 5/9/2017.
 * edge-clients
 */
public class CachedCoreIdentityClient extends CoreIdentityClientImpl implements CoreIdentityClient {
	public CachedCoreIdentityClient(TokenHeaderProvider tokenHeaderProvider) {
		super(tokenHeaderProvider);
	}

	public CachedCoreIdentityClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		super(tokenHeaderProvider, baseUrl);
	}

	@Override
	@Cacheable(value = "coreIdentity", unless = "#result == null")
	public CoreIdentity getCoreIdentityByPersonId(String personId) throws RestHttpException, IOException {
		return super.getCoreIdentityByPersonId(personId);
	}

	@Override
	@Cacheable(value = "coreIdentity", unless = "#result == null")
	public CoreIdentity getCoreIdentityByByuId(String byuId) {
		return super.getCoreIdentityByByuId(byuId);
	}

	@Override
	@Cacheable(value = "coreIdentity", unless = "#result == null")
	public CoreIdentity getCoreIdentityByNetId(String netId) throws IOException, RestHttpException {
		return super.getCoreIdentityByNetId(netId);
	}
}
