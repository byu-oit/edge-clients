package edu.byu.edge.client.apikey.da;

import edu.byu.edge.client.apikey.domain.SharedSecret;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/14/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/14/2012
 */
public interface SharedSecretDao {

	public SharedSecret findById(int credentialId);
	public List<SharedSecret> findByPersonId(String personId);
	public List<SharedSecret> findAllDepartment();
}
