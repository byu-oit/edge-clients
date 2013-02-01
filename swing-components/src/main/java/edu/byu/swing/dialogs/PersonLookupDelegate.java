package edu.byu.swing.dialogs;

import java.util.List;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 11, 2009
 */
public interface PersonLookupDelegate {

    /**
     * Does a search based on the given input and returns back a list of personIds
     * @param search
     * @return
     */
    public List<String> lookup(String search);
}
