package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.byu.edge.coreIdentity.client.ByuCredentialsClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 11/14/2017.
 * edge-clients
 */
public class CachedByuCredentialClient extends ByuCredentialsClientImpl implements ByuCredentialsClient {
	public CachedByuCredentialClient(TokenHeaderProvider tokenHeaderProvider) {
		super(tokenHeaderProvider);
	}

	public CachedByuCredentialClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		super(tokenHeaderProvider, baseUrl);
	}

	public CachedByuCredentialClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl, ObjectMapper mapper) {
		super(tokenHeaderProvider, baseUrl, mapper);
	}

	@Override
	@Cacheable(value = "credentialCache", unless = "#result == null")
	public String getLdsCmisIdAssociatedWithByuId(String byuId) throws RestHttpException, IOException {
		return super.getLdsCmisIdAssociatedWithByuId(byuId);
	}
}
