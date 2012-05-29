package edu.byu.edge.client.pro;

import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
public interface PersonSummaryClient {
	/**
	 *
	 *
	 * @param netId the netId to look up
	 * @return the PersonSummary data
	 */
	public PersonSummaryServiceType getSummaryByNetId(String netId);
}
