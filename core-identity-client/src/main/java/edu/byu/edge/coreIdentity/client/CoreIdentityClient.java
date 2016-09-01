package edu.byu.edge.coreIdentity.client;

import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;

import java.io.IOException;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public interface CoreIdentityClient {

	CoreIdentity getCoreIdentityByPersonId(String personId) throws RestHttpException, IOException;

	CoreIdentity getCoreIdentityByByuId(String byuId);

	CoreIdentity getCoreIdentityByNetId(String netId) throws IOException, RestHttpException;
}
