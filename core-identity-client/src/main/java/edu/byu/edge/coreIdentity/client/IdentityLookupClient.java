package edu.byu.edge.coreIdentity.client;

import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.domain.IdentityLookupSummary;

import java.io.IOException;
import java.util.List;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public interface IdentityLookupClient {

	List<IdentityLookupSummary> searchBy(String searchParam) throws RestHttpException, IOException;

	List<IdentityLookupSummary> searchByFirstAndLastName(String firstName, String lastName) throws RestHttpException, IOException;

	IdentityLookupSummary findByPersonId(String personId) throws RestHttpException, IOException;

	List<IdentityLookupSummary> search(SearchFor searchFor, SearchBy searchBy, int pageNumber, String searchParam) throws RestHttpException, IOException;

	public enum SearchFor{
		ALL,
		STUDENT,
		EMPLOYEE,
		BOTH
	}

	public enum SearchBy{
		NAME,
		HOMETOWN,
		PHONE,
		PERSON_ID
	}
}
