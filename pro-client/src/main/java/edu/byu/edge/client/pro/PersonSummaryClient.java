package edu.byu.edge.client.pro;

import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;

/**
 * This specifies the local contract for the service.
 *
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public interface PersonSummaryClient {
	/**
	 * Retrieves the person info based on netId.
	 *
	 * @param netId the netId to look up
	 * @return the PersonSummary data
	 */
	public PersonSummaryServiceType getSummaryByNetId(String netId);
}
