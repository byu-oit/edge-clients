package edu.byu.edge.client.pro.codes.impl;

import edu.byu.edge.client.pro.codes.CodesClient;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 5/31/12
 * Time: 5:08 PM
 */
public class CodesClientImpl extends BaseClient implements CodesClient {

	private static final Logger LOG = Logger.getLogger(CodesClientImpl.class);

	protected final String baseUrl;

	public CodesClientImpl(
			final String baseUrl,
			final SharedSecretNonceEncodingFilter sharedSecretNonceEncodingFilter,
			final int readTimeout
	) {
		super(baseUrl, sharedSecretNonceEncodingFilter, readTimeout);
		this.baseUrl = baseUrl;
//		+ "/codeMaintenance.cgi/"
	}

	@Override
	public String getData() {
		return getClient().resource(baseUrl ).path("documentation").accept(MediaType.WILDCARD_TYPE).get(String.class);
	}
}
