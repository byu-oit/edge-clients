package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.byu.auth.client.AccessTokenClient;
import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.rest.HttpRestBuilder;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public class CoreIdentityClientImpl implements CoreIdentityClient {
	private static final Logger LOG = Logger.getLogger(CoreIdentityClientImpl.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private AccessTokenClient accessTokenClient;
	private final String baseUrl;

	public CoreIdentityClientImpl(AccessTokenClient accessTokenClient) {
		this.accessTokenClient = accessTokenClient;
		this.baseUrl = "https://api.byu.edu:443/domains/legacy/identity/person/PRO/personsummary/v1/";
	}

	public CoreIdentityClientImpl(AccessTokenClient accessTokenClient, String baseUrl) {
		this.accessTokenClient = accessTokenClient;
		this.baseUrl = baseUrl;
	}

	@Override
	public CoreIdentity getCoreIdentityByPersonId(String personId) throws RestHttpException, IOException {
		LOG.trace("getCoreIdentityByPersonId " + personId);
		final String url = baseUrl + personId;
		return _doGet(url);
	}

	@Override
	public CoreIdentity getCoreIdentityByByuId(String byuId) {
		return null;
	}

	@Override
	public CoreIdentity getCoreIdentityByNetId(String netId) throws IOException, RestHttpException {
		LOG.trace("getCoreIdentityByNetId " + netId);
		final String url = baseUrl + netId;
		return _doGet(url);
	}

	private CoreIdentity _doGet(final String url) throws RestHttpException, IOException {
		final String result = new HttpRestBuilder(url)
				.accept("application/json")
				.contentType("application/json")
				.authorization(accessTokenClient.obtainAuthorizationHeaderString())
				.get();
		final JsonNode root = MAPPER.readTree(result);
		final JsonNode response = root.findPath("response");
		if (!response.isMissingNode()){
			CoreIdentity coreIdentity = new CoreIdentity();
			final JsonNode summaryLine = response.path("summary_line");
			final JsonNode identifiers = response.path("identifiers");
			final JsonNode names = response.path("names");
			final JsonNode personalInformation = response.path("personal_information");
			coreIdentity.setPersonId(identifiers.path("person_id").asText());
			coreIdentity.setNetId(identifiers.path("net_id").asText());
			final String byuIdFormatted = identifiers.path("byu_id").asText();
			coreIdentity.setByuId(byuIdFormatted.replaceAll("-",""));
			coreIdentity.setByuIdFormatted(byuIdFormatted);
			coreIdentity.setPreferredName(names.path("preferred_name").asText());
			coreIdentity.setCompleteName(names.path("complete_name").asText());
			final String dateOfBirthStr = personalInformation.path("date_of_birth").asText();
			if (dateOfBirthStr != null && !dateOfBirthStr.equalsIgnoreCase("null")){
				try {
					coreIdentity.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			coreIdentity.setGender(personalInformation.path("gender").asText());
			coreIdentity.setReligion(personalInformation.path("religion").asText());
			coreIdentity.setRestricted(summaryLine.path("restricted").asBoolean());

			if (coreIdentity.getPersonId() == null || coreIdentity.getPersonId().equalsIgnoreCase("null")){ // if empty personId, not a person
				return null;
			}
			return coreIdentity;
		}
		return null;
	}
}
