package edu.byu.edge.client.pro.impl;

import edu.byu.edge.client.pro.PersonSummaryClient;
import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import javax.ws.rs.core.MediaType;

/**
 * This is the implementation to access the person service via the SOA Registry using shared secret nonce-encoding security.
 *
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public class PersonSummaryClientImpl extends BaseClient implements PersonSummaryClient {
	private static final Logger LOG = Logger.getLogger(PersonSummaryClientImpl.class);

	/**
	 *
	 * @param baseUrl the base url of the service
	 * @param sharedSecretNonceEncodingFilter the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 */
	public PersonSummaryClientImpl(
										  final String baseUrl,
										  final SharedSecretNonceEncodingFilter sharedSecretNonceEncodingFilter,
										  final int readTimeout
								  ) {
		super(baseUrl + "/personSummary.cgi/", sharedSecretNonceEncodingFilter, readTimeout);
	}

	@Cacheable(key = "#netId", value = "personSummaryClientCache")
	@Override
	public PersonSummaryServiceType getSummaryByNetId(final String netId) {
		return getResource().path(netId).accept(MediaType.APPLICATION_XML_TYPE).get(PersonSummaryServiceType.class);
	}

	@Cacheable(key = "#personId", value = "personSummaryClientCache")
	@Override
	public PersonSummaryServiceType getSummaryByPersonId(final String personId) {
		return getResource().path(personId).accept(MediaType.APPLICATION_XML_TYPE).get(PersonSummaryServiceType.class);
	}
}
