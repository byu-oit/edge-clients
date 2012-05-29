package edu.byu.edge.client.pro.impl;

import edu.byu.edge.client.pro.PersonSummaryClient;
import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public class PersonSummaryClientImpl extends BaseClient implements PersonSummaryClient {
	private static final Logger LOG = Logger.getLogger(PersonSummaryClientImpl.class);

	public PersonSummaryClientImpl(
										  final String baseUrl,
										  final SharedSecretNonceEncodingFilter sharedSecretNonceEncodingFilter,
										  final int readTimeout
								  ) {
		super(baseUrl + "/personSummary.cgi/", sharedSecretNonceEncodingFilter, readTimeout);
	}

	@Override
	public PersonSummaryServiceType getSummaryByNetId(final String netId) {
		return getResource().path(netId).accept(MediaType.APPLICATION_XML_TYPE).get(PersonSummaryServiceType.class);
	}
}
