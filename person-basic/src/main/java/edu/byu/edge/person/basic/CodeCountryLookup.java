package edu.byu.edge.person.basic;

import edu.byu.edge.person.basic.domain.CodeCountry;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 8:27 PM
 */
public interface CodeCountryLookup {

	List<CodeCountry> getAllCodeCountryCodes();
}
