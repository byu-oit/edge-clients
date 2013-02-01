package edu.byu.swing.dialogs;

import edu.byu.pro.wsci.InformationRequestFilter;
import edu.byu.swing.models.pager.PersonPagerModel;
import edu.byu.swing.renderers.PersonRenderer;
import edu.byu.ws.namespaces.pro.person_type.v2.PersonType;

import java.awt.*;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 8, 2009
 */
public interface PersonLookupDialog {
    /**
     * Searches using the given parameters and returns a personId.
     * Will always show dialog box.
     *
     * @param component
     * @param search
     * @return
     */
    public String lookupPersonId(Component component, String search);

    /**
     * Searches using the given parameters and returns a personId and will
     * only show the dialog if dialog==true;
     *
     * @param component
     * @param search
     * @param dialog
     * @return
     */
    public String lookupPersonId(Component component, String search, boolean dialog, PersonRenderer renderer);

    /**
     * Searches using the given parameters and returns a personId and will
     * only show the dialog if dialog==true;
     *
     * @param component
     * @param search
     * @param dialog
     * @return
     */
    public String lookupPersonId(Component component, String search, boolean dialog);

    /**
     * @param parent
     * @param search
     * @param filters
     * @return
     * @see String lookupPersonId(java.awt.Component, java.lang.String)
     *      Searches using lookupPersonId and will return a personType with the desired filters
     */
    public PersonType lookupPerson(Component parent, String search, InformationRequestFilter... filters);
    public PersonType lookupPerson(Component parent, String search, PersonRenderer renderer, InformationRequestFilter... filters);

    /**
     * @param parent
     * @param search
     * @param dialog
     * @param filters
     * @return
     * @see String lookupPersonId(java.awt.Component, java.lang.String)
     *      Searches using lookupPersonId and will return a personType with the desired filters
     */
    public PersonType lookupPerson(Component parent, String search, boolean dialog, InformationRequestFilter... filters);


    public PersonPagerModel getPagerModel();
    public void setPagerModel(PersonPagerModel model);
}
