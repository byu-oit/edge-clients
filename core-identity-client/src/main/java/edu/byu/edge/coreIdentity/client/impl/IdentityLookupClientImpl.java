package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.byu.auth.client.AccessTokenClient;
import edu.byu.edge.coreIdentity.client.IdentityLookupClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.rest.HttpRestBuilder;
import edu.byu.edge.coreIdentity.domain.IdentityLookupSummary;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public class IdentityLookupClientImpl implements IdentityLookupClient {
	private static final Logger LOG = Logger.getLogger(IdentityLookupClientImpl.class);

	private static final String BASE_URL = "https://api.byu.edu:443/domains/legacy/identity/person/personLookup/v1/";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final ArrayList<IdentityLookupSummary> EMPTY_LIST = new ArrayList<IdentityLookupSummary>();

	private AccessTokenClient accessTokenClient;

	public IdentityLookupClientImpl(AccessTokenClient accessTokenClient) {
		this.accessTokenClient = accessTokenClient;
	}

	@Override
	public List<IdentityLookupSummary> searchBy(String searchParam) throws RestHttpException, IOException {
		final String url = BASE_URL + URLEncoder.encode(searchParam, "UTF-8");
		return _doGet(url);
	}

	@Override
	public List<IdentityLookupSummary> searchByFirstAndLastName(String firstName, String lastName) throws RestHttpException, IOException {
		final String url = BASE_URL + URLEncoder.encode(lastName, "UTF-8") + "/" + URLEncoder.encode(firstName, "UTF-8");
		return _doGet(url);
	}

	@Override
	public IdentityLookupSummary findByPersonId(String personId) throws RestHttpException, IOException {
		final List<IdentityLookupSummary> results = search(SearchFor.ALL, SearchBy.PERSON_ID, 1, personId);
		if (results != null && !results.isEmpty() && results.size() == 1){
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<IdentityLookupSummary> search(SearchFor searchFor, SearchBy searchBy, int pageNumber, String searchParam) throws RestHttpException, IOException {
		final String url = BASE_URL + searchFor + "/" + searchBy + "/" + pageNumber + "/" + URLEncoder.encode(searchParam, "UTF-8");
		return _doGet(url);
	}

	private List<IdentityLookupSummary> _doGet(final String url) throws RestHttpException, IOException {
		final String result = new HttpRestBuilder(url)
				.accept("application/json")
				.contentType("application/json")
				.authorization(accessTokenClient.obtainAuthorizationHeaderString())
				.get();

		final JsonNode jsonNode = MAPPER.readTree(result);
		final JsonNode resultSet = jsonNode.findPath("result_set");
		if (!resultSet.isMissingNode()){
			final IdentityLookupSummary[] searchResults = MAPPER.treeToValue(resultSet, IdentityLookupSummary[].class);
			return Arrays.asList(searchResults);
		}
		return EMPTY_LIST;
	}
}
