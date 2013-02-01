package edu.byu.swing.dialogs.impl;

import edu.byu.framework.swing.util.JOptionPaneWrapper;
import edu.byu.pro.wsci.InformationRequestFilter;
import edu.byu.pro.wsci.PersonClient;
import edu.byu.pro.wsci.filters.GeneralRequestFilter;
import edu.byu.swing.components.Pager;
import edu.byu.swing.components.PagerDialog;
import edu.byu.swing.dialogs.PersonLookup;
import edu.byu.swing.dialogs.PersonLookupDialog;
import edu.byu.swing.models.pager.PersonPagerModel;
import edu.byu.swing.models.pager.PersonTablePagerModel;
import edu.byu.swing.renderers.PersonRenderer;
import edu.byu.swing.renderers.DefaultPersonRendererBean;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 8, 2009
 */
public class PersonLookupDialogImpl implements PersonLookupDialog {

    private final PersonClient personClient;
    private final PersonLookup personLookup;
    private final static Logger LOG = Logger.getLogger(PersonLookupDialogImpl.class);
    private final static ExecutorService executor = Executors.newScheduledThreadPool(6);
    private final PersonRenderer defaultRenderer;
    private PersonPagerModel pagerModel = new PersonTablePagerModel();

    public PersonLookupDialogImpl(@Qualifier("defaultPersonLookupRendererBean") DefaultPersonRendererBean defaultRendererBean,
                                  PersonClient personClient, PersonLookup personLookup) {
        this.personClient = personClient;
        this.personLookup = personLookup;
        this.defaultRenderer = defaultRendererBean.getRenderer();
        LOG.info("using renderer: " + defaultRenderer);
    }

    public PersonType lookupPerson(Component parent, String search, boolean dialog, InformationRequestFilter... filters) {
        return lookupPerson(parent, search, defaultRenderer, dialog, filters);
    }

    public PersonType lookupPerson(Component parent, String search, PersonRenderer renderer,
            boolean dialog, InformationRequestFilter... filters) {
        String personId = lookupPersonId(parent, search, dialog, renderer);
        List<InformationRequestFilter> f = new ArrayList<InformationRequestFilter>(Arrays.asList(filters));
        if (!f.contains(GeneralRequestFilter.DEMOGRAPHICS)) {
            f.add(GeneralRequestFilter.DEMOGRAPHICS);
        }
        PersonType person = personClient.getPersonByPersonId(personId, f.toArray(new InformationRequestFilter[]{}));
        if(person!=null && person.getPersonDemographics()!=null && person.getPersonDemographics().isRestricted()){
            JOptionPaneWrapper.showMessageDialog(parent, "This person's BYU information is restricted.");
        }
        return person;
    }

    public PersonType lookupPerson(Component parent, String search, InformationRequestFilter... filters) {
        return lookupPerson(parent, search, defaultRenderer, true, filters);
    }

    public PersonType lookupPerson(Component parent, String search, PersonRenderer renderer, InformationRequestFilter... filters) {
        return lookupPerson(parent, search, renderer, true, filters);
    }

    public String lookupPersonId(Component component, String search) {
        return lookupPersonId(component, search, true);
    }

    public String lookupPersonId(Component component, String search, boolean dialog) {
        return lookupPersonId(component, search, true, defaultRenderer);
    }

    @Override
    public PersonPagerModel getPagerModel() {
        return pagerModel;
    }

    @Override
    public void setPagerModel(PersonPagerModel model) {
        if (model == null) {
            throw new IllegalArgumentException("pager model cannot be null");
        }
        this.pagerModel = model;
    }

    public String lookupPersonId(final Component component, final String search,
            final boolean dialog, final PersonRenderer renderer) {
        final List<String> personIds = personLookup.lookupPersonIds(search);

        if (personIds.isEmpty()) {
            return null;
        }

        if (personIds.size() == 1) {
            return personIds.get(0);
        }

        final PersonPagerModel model = getPagerModel();
        model.setResultSet(personIds);

        final String[] data = new String[1];
        Runnable r = new Runnable() {
            public void run() {
                LOG.debug("showing input dialog box");
                Pager<String> pager = new Pager<String>(model);
                PagerDialog<String> pagerDialog = new PagerDialog<String>(null, pager);
                pagerDialog.setTitle("Person Chooser");
                pagerDialog.setVisible(true);
                LOG.debug("input dialog box closed");

                String selectedItem = pagerDialog.getSelectedItem();
                if (selectedItem == null) {
                    return;
                }

                data[0] = selectedItem;
            }
        };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            try {
                EventQueue.invokeAndWait(r);
            } catch (Exception e) {
                throw new RuntimeException("Unable to show dialog", e);
            }
        }
        return data[0];
    }
}
