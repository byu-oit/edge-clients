package edu.byu.swing.dialogs.impl;

import edu.byu.pro.wsci.LookupRequest;
import edu.byu.pro.wsci.PersonClient;
import static edu.byu.pro.wsci.filters.GeneralRequestFilter.IDENTIFIERS;
import static edu.byu.ws.namespaces.pro.person_lookup_request_type.v1.PersonLookupTypeType.ALL;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;

import static java.util.Collections.EMPTY_LIST;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 11, 2009
 */
public class NameLookupDelegate extends PersonLookupDelegateBase {
    private final PersonClient personClient;
    private final static Logger LOG = Logger.getLogger(NameLookupDelegate.class);

    public NameLookupDelegate(PersonClient personClient) {
        this.personClient = personClient;
    }

    private boolean splitByComma(String search, LookupRequest lookupRequest) {
        String[] tokens = search.split(",");
        if (tokens.length == 1) {
            LOG.debug("is NOT  a comma search");
            return false;
        }

        lookupRequest.setSurname(tokens[0].trim());
        lookupRequest.setFirstName(tokens[1].trim());

        return true;
    }

    private void splitBySpace(String search, LookupRequest lookupRequest) {
        String[] tokens = search.split(" ");
        if (tokens.length == 1) {
            lookupRequest.setSurname(tokens[0].trim());
            lookupRequest.setFirstName("*");
        } else if(tokens.length == 2){
            lookupRequest.setSurname(tokens[1].trim());
            lookupRequest.setFirstName(tokens[0].trim());
        } else if(tokens.length == 3){
            lookupRequest.setSurname(tokens[1].trim() + " " + tokens[2].trim());
            lookupRequest.setFirstName(tokens[0].trim());
        } else {
            lookupRequest.setSurname(tokens[1].trim());
            lookupRequest.setFirstName(tokens[0].trim());
        }
    }

    @Override
    protected final List<String> doLookup(String search) {
        LookupRequest request = new LookupRequest();
        request.add(IDENTIFIERS);
        request.setWhatToLookup(ALL);
        Boolean spaceSearch = false;

        if (!splitByComma(search, request)) {
            LOG.debug("doing a space search");
            splitBySpace(search, request);
            spaceSearch = true;//Need to search for middle Names
        }

        LOG.debug("surname: " + request.getSurname());
        LOG.debug("firstname: " + request.getFirstName());

        List<PersonType> list = personClient.lookupPerson(request);

        if (list.isEmpty() && spaceSearch) {
            String[] tokens = search.split(" ");
            if(tokens.length == 3){
                LOG.debug("Searching for middle name instead of two last names");
                request.setFirstName(tokens[0].trim());
                request.setSurname(tokens[2].trim());
                list = personClient.lookupPerson(request);
            }
        }
        LOG.debug("surname: " + request.getSurname());
        LOG.debug("firstname: " + request.getFirstName());

        if (list.isEmpty()) {
            return EMPTY_LIST;
        }

        List<String> personIds = new ArrayList<String>(list.size());
        for (PersonType type : list) {
            String personId = type.getPersonIdentifier().getPersonId();
            personIds.add(personId);
        }
        return personIds;
    }
}
