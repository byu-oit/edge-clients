package edu.byu.swing.renderers;

import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 8, 2009
 */
public class PersonNameRenderer extends PersonRendererBase {
    private final PersonClient personClient;

    @Autowired
    public PersonNameRenderer(PersonClient personClient) {
        this.personClient = personClient;
    }

    protected String doRetreiveValue(String personId) {
        PersonType personType = personClient.getPersonByPersonId(personId, GeneralRequestFilter.NAME_INFORMATION);
        if (personType == null) {
            return null;
        }

        return personType.getPersonName().getSortName();
    }
}
