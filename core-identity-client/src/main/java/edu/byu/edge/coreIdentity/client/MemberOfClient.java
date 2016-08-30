package edu.byu.edge.coreIdentity.client;

import edu.byu.edge.coreIdentity.client.exceptions.IdentityServiceException;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;

import java.io.IOException;

/**
 * Created by eric on 2/3/16.
 */
public interface MemberOfClient {
	/**
	 * Find whether or not a person is a member of the specified GRO group
	 * @param personId Person's ID
	 * @param group Group's ID
	 * @return True if they are, False if they aren't
	 */
	public boolean isPersonMemberOfGroup(String personId, String group) throws RestHttpException, IOException;
}
