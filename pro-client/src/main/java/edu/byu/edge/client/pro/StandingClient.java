package edu.byu.edge.client.pro;

import edu.byu.edge.client.pro.domain.standing.RecStdAcadStandingServiceType;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 8/6/12
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 8/6/12
 */
public interface StandingClient {

	/**
	 * Returns the academic standing for the person.
	 * @param personId the person id to look up
	 * @return the academic standing
	 */
	public RecStdAcadStandingServiceType getStandingByPersonId(final String personId);
}
