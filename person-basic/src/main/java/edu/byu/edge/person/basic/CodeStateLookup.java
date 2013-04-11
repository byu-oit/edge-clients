package edu.byu.edge.person.basic;

import edu.byu.edge.person.basic.domain.CodeState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 8:58 PM
 */
public interface CodeStateLookup {

	List<CodeState> getStateByCountry(String country);
}
