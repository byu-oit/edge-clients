package edu.byu.swing.dialogs.impl;

import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.PersonIdentifier;
import static edu.byu.pro.wsci.PersonIdentifier.*;
import edu.byu.pro.wsci.PersonRequest;
import static edu.byu.pro.wsci.filters.GeneralRequestFilter.IDENTIFIERS;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

import static java.util.Collections.EMPTY_LIST;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 11, 2009
 */
public final class IdentifierLookupDelegate extends PersonLookupDelegateBase implements InitializingBean, BeanNameAware {
    private PersonIdentifier identifier;
    private String beanName;
    private final PersonClient personClient;
    private final static Logger LOG = Logger.getLogger(IdentifierLookupDelegate.class);
    private final ConcurrentMap<String, Set<String>> cache = new ConcurrentHashMap<String, Set<String>>();

    public IdentifierLookupDelegate(PersonClient personClient) {
        this.personClient = personClient;
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        if (identifier == null) {
            throw new IllegalStateException("no identifier specified for " + beanName);
        }
        LOG.info("Created identifier lookup delegate for: " + identifier);
    }

    @Override
    public final void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public final void setPersonIdentifier(String identifier) {
        if (identifier == null) {
            return;
        }

        if (identifier.equalsIgnoreCase("netId")) {
            this.identifier = NET_ID;
            return;
        }

        if (identifier.equalsIgnoreCase("byuId")) {
            this.identifier = BYU_ID;
            return;
        }

        if (identifier.equalsIgnoreCase("ssn")) {
            this.identifier = SSN;
            return;
        }
    }

    @Override
    protected final List<String> doLookup(String search) {
        LOG.debug("searching for " + identifier + " with [" + search + "]");
        PersonRequest personRequest = new PersonRequest();
        personRequest.setIdentifier(identifier, search);
        personRequest.add(IDENTIFIERS);
        PersonType personType = personClient.getPerson(personRequest);
        if (personType == null) {
            return EMPTY_LIST;
        }
        String personId = personType.getPersonIdentifier().getPersonId();
        return Arrays.asList(new String[] {personId});
    }
}
