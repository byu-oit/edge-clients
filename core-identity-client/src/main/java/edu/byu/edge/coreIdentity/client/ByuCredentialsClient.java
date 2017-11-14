package edu.byu.edge.coreIdentity.client;

import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 9/1/2017.
 * edge-clients
 */
public interface ByuCredentialsClient {
	String getLdsCmisIdAssociatedWithByuId(String byuId) throws RestHttpException, IOException;
}
