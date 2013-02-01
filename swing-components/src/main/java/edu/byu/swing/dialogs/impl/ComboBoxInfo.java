package edu.byu.swing.dialogs.impl;

import edu.byu.swing.renderers.PersonRenderer;

import java.io.Serializable;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 8, 2009
 */
public class ComboBoxInfo implements Serializable, Comparable<ComboBoxInfo> {

    private static final long serialVersionUID = -8227200846034303332L;
    private final String value;
    private final String personId;

    public ComboBoxInfo(String personId, PersonRenderer renderer) {
        value = renderer.retreiveValue(personId);
        this.personId = personId;
    }

    @Override
    public String toString() {
        return value;
    }

    public int compareTo(ComboBoxInfo o) {
        return value.compareTo(o.value);
    }

    public String getPersonId() {
        return personId;
    }
}
