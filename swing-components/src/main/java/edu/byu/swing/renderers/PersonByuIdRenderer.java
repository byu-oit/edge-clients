package edu.byu.swing.renderers;

import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: jmooreoa
 * Date: Apr 15, 2010
 * Time: 2:29:25 PM
 */
public class PersonByuIdRenderer extends PersonRendererBase {
 private final PersonClient client;

    @Autowired
    public PersonByuIdRenderer(@Qualifier("personServiceClient") PersonClient client) {
        this.client = client;
    }

    @Override
    protected String doRetreiveValue(String personId) {
        PersonType personType = client.getPersonByPersonId(personId, GeneralRequestFilter.IDENTIFIERS);
        if (personType == null) {
            return null;
        }
        return personType.getPersonIdentifier().getByuId();
    }
}
