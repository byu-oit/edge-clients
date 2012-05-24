package edu.byu.edge.client.pro.impl;

import edu.byu.edge.client.pro.ProClient;
import edu.byu.security.hmac.jersey.BaseClient;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public class ProClientImpl extends BaseClient implements ProClient {
	private static final Logger LOG = Logger.getLogger(ProClientImpl.class);

	protected final String baseUrl;

	public ProClientImpl(
			final String baseUrl,
			final SharedSecretNonceEncodingFilter sharedSecretNonceEncodingFilter,
			final int readTimeout
	) {
		super(sharedSecretNonceEncodingFilter, readTimeout);
		this.baseUrl = baseUrl;
	}

	@Override
	public String getData() {
		return getClient().resource(baseUrl + "/personSummary.cgi/").path("xsd").accept(MediaType.WILDCARD_TYPE).get(String.class);
	}
}
