package edu.byu.edge.coreIdentity.client;

import edu.byu.edge.coreIdentity.domain.CoreIdentity;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public interface CoreIdentityClient {

	CoreIdentity getCoreIdentityByPersonId(String personId);

	CoreIdentity getCoreIdentityByByuId(String byuId);

	CoreIdentity getCoreIdentityByNetId(String netId);
}
