/*
 * Filename: LookupDialog
 * Created: Nov 25, 2008
 */
package edu.byu.swing.dialogs;

import java.util.List;

/**
 * @author tylers2
 */
public interface PersonLookup extends PersonLookupDialog {

    public List<String> lookupPersonIds(String search);

}
