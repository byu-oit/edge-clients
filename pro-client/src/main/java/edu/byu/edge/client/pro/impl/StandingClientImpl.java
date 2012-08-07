package edu.byu.edge.client.pro.impl;

import com.sun.jersey.api.client.UniformInterfaceException;
import edu.byu.commons.exception.ByuException;
import edu.byu.edge.client.pro.StandingClient;
import edu.byu.edge.client.pro.domain.standing.RecStdAcadStandingServiceType;
import edu.byu.security.hmac.jersey.SharedSecretNonceEncodingFilter;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import javax.ws.rs.core.MediaType;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 8/6/12
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 8/6/12
 */
public class StandingClientImpl extends BaseClient implements StandingClient {
	private static final Logger LOG = Logger.getLogger(StandingClientImpl.class);

	/**
	 * @param baseUrl the base url of the service
	 * @param filter the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 */
	public StandingClientImpl(final String baseUrl,
									 final SharedSecretNonceEncodingFilter filter,
									 final int readTimeout) {
		super(baseUrl, filter, readTimeout);
	}

	@Cacheable(key = "#personId", value = "academicStandingClientCache")
	@Override
	public RecStdAcadStandingServiceType getStandingByPersonId(final String personId) {
		try {
			return getResource().path(personId).accept(MediaType.APPLICATION_XML_TYPE).get(RecStdAcadStandingServiceType.class);
		} catch (final UniformInterfaceException e) {
			if (super.processExceptionForRetry(e)) {
				LOG.info("retrying GET due to '502 Bad Gateway'");
				return getResource().path(personId).accept(MediaType.APPLICATION_XML_TYPE).get(RecStdAcadStandingServiceType.class);
			} else {
				final Throwable t = processExceptionToCommon(e);
				if (ByuException.class.isAssignableFrom(t.getClass())) {
					throw (ByuException) t;
				} else {
					throw e;
				}
			}
		}
	}
}
