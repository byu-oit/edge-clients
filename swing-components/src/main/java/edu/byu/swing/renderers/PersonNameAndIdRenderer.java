package edu.byu.swing.renderers;

import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author jmooreoa
 */
public class PersonNameAndIdRenderer extends PersonRendererBase {
    private final PersonClient client;

    @Autowired
    public PersonNameAndIdRenderer(@Qualifier("personServiceClient") PersonClient client) {
        this.client = client;
    }

    @Override
    protected String doRetreiveValue(String personId) {
        PersonType personType = client.getPersonByPersonId(personId, GeneralRequestFilter.NAME_INFORMATION, GeneralRequestFilter.IDENTIFIERS);
        if (personType == null) {
            return null;
        }
        return String.format("%s (%s)", personType.getPersonName().getSortName(), personType.getPersonIdentifier().getByuId());
    }

}
