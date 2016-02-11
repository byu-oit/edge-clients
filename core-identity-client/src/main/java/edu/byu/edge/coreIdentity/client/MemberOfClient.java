package edu.byu.edge.coreIdentity.client;

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
	public boolean isPersonMemberOfGroup(String personId, String group) throws IdentityServiceException;
}
