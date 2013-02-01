/*
 * Filename: PersonLookupIimpl
 * Created: Nov 25, 2008
 */
package edu.byu.swing.dialogs.impl;

import edu.byu.framework.swing.util.BYUCallable;
import edu.byu.framework.swing.util.Retry;
import edu.byu.pro.wsci.InformationRequestFilter;
import edu.byu.swing.dialogs.PersonLookup;
import edu.byu.swing.dialogs.PersonLookupDelegate;
import edu.byu.swing.dialogs.PersonLookupDialog;
import edu.byu.swing.dialogs.PersonLookupFilter;
import edu.byu.swing.models.pager.PersonPagerModel;
import edu.byu.swing.renderers.PersonRenderer;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.awt.*;
import static java.util.Collections.EMPTY_LIST;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author tylers2
 * @version 1.0.1
 * @since 1.0.0
 */
public class PersonLookupImpl implements PersonLookup {

    private PersonLookupDialog personLookupDialog;
    private final static Logger LOG = Logger.getLogger(PersonLookupImpl.class);
    private final List<PersonLookupDelegate> lookupDelegates = new ArrayList<PersonLookupDelegate>();
    private final ExecutorService executorService = Executors.newScheduledThreadPool(8);
    private final PersonLookupDelegate netIdDelegate;
    private static PersonLookupFilter filter = PersonLookupFilter.EMPTY_FILTER;

    @Autowired
    public PersonLookupImpl(@Qualifier("netIdLookupDelegate") PersonLookupDelegate netIdDelegate,
                            List<PersonLookupDelegate> lookupDelegates) {
        this.lookupDelegates.addAll(lookupDelegates);
        this.netIdDelegate = netIdDelegate;
        this.lookupDelegates.remove(netIdDelegate);
    }

    public static void setFilter(PersonLookupFilter filter) {
        PersonLookupImpl.filter = filter;
    }

    public void setPersonLookupDialog(PersonLookupDialog personLookupDialog) {
        this.personLookupDialog = personLookupDialog;
    }

    public List<String> lookupPersonIds(final String search) {
        if (search == null || search.trim().length() == 0) {
            return EMPTY_LIST;
        }

        //a personId search starts with an =
        if (search.charAt(0) == '=') {
            String personId = search.substring(1);
            return Arrays.asList(personId);
        }

        Future<List<String>> netIdFuture = executorService.submit(new BYUCallable<List<String>>() {
            @Override
            @Retry
            protected List<String> doInBackground() throws Exception {
                return netIdDelegate.lookup(search);
            }
        });

        List<Future<List<String>>> futures = new LinkedList<Future<List<String>>>();
        for (final PersonLookupDelegate delegate : lookupDelegates) {
            futures.add(executorService.submit(new BYUCallable<List<String>>() {
                @Retry
                @Override
                public List<String> doInBackground() throws Exception {
                    return delegate.lookup(search);
                }
            }));
        }

        List<String> personIds = new ArrayList<String>();
        for (Future<List<String>> future : futures) {
            try {
                personIds.addAll(future.get());
            } catch (Exception e) {
                LOG.warn("Error getting future objects", e);
                personIds.clear();
                break;
            }
        }

        try {
            List<String> personIdNetId = netIdFuture.get();
            personIds.removeAll(personIdNetId);
            personIds.addAll(0, personIdNetId);
        } catch (Exception e) {
            throw new RuntimeException("Error searching. Check authorizations.", e);
        }
        filter.filter(personIds);
        return personIds;
    }

    public PersonType lookupPerson(Component parent, String search, boolean dialog, InformationRequestFilter... filters) {
        return personLookupDialog.lookupPerson(parent, search, dialog, filters);
    }

    public PersonType lookupPerson(Component parent, String search, InformationRequestFilter... filters) {
        return personLookupDialog.lookupPerson(parent, search, filters);
    }

    public PersonType lookupPerson(Component parent, String search, PersonRenderer renderer, InformationRequestFilter... filters) {
        return personLookupDialog.lookupPerson(parent, search, renderer, filters);
    }

    public String lookupPersonId(Component component, String search) {
        return personLookupDialog.lookupPersonId(component, search);
    }

    public String lookupPersonId(Component component, String search, boolean dialog) {
        return personLookupDialog.lookupPersonId(component, search, dialog);
    }

    public String lookupPersonId(Component component, String search, boolean dialog, PersonRenderer renderer) {
        return personLookupDialog.lookupPersonId(component, search, dialog, renderer);
    }

    @Override
    public PersonPagerModel getPagerModel() {
        return personLookupDialog.getPagerModel();
    }

    @Override
    public void setPagerModel(PersonPagerModel model) {
        personLookupDialog.setPagerModel(model);
    }
}
