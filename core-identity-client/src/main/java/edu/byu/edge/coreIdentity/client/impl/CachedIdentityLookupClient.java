package edu.byu.edge.coreIdentity.client.impl;

import edu.byu.edge.coreIdentity.client.IdentityLookupClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.domain.IdentityLookupSummary;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.List;

/**
 * Created by Scott Hutchings on 5/9/2017.
 * edge-clients
 */
public class CachedIdentityLookupClient extends IdentityLookupClientImpl implements IdentityLookupClient {
	public CachedIdentityLookupClient(TokenHeaderProvider tokenHeaderProvider) {
		super(tokenHeaderProvider);
	}

	public CachedIdentityLookupClient(TokenHeaderProvider tokenHeaderProvider, String baseUrl) {
		super(tokenHeaderProvider, baseUrl);
	}

	@Override
	@Cacheable(value = "identityLookupCache", unless = "#result == null")
	public List<IdentityLookupSummary> searchBy(String searchParam) throws RestHttpException, IOException {
		return super.searchBy(searchParam);
	}

	@Override
	@Cacheable(value = "identityLookupCache", unless = "#result == null")
	public List<IdentityLookupSummary> searchByFirstAndLastName(String firstName, String lastName) throws RestHttpException, IOException {
		return super.searchByFirstAndLastName(firstName, lastName);
	}

	@Override
	@Cacheable(value = "identityLookupCache", unless = "#result == null")
	public IdentityLookupSummary findByPersonId(String personId) throws RestHttpException, IOException {
		return super.findByPersonId(personId);
	}

	@Override
	@Cacheable(value = "identityLookupCache", unless = "#result == null")
	public List<IdentityLookupSummary> search(SearchFor searchFor, SearchBy searchBy, int pageNumber, String searchParam) throws RestHttpException, IOException {
		return super.search(searchFor, searchBy, pageNumber, searchParam);
	}
}
