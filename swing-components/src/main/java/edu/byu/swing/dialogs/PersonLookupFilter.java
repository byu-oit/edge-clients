package edu.byu.swing.dialogs;

import java.util.Collection;

/**
 *
 * @author jmooreoa
 */
public interface PersonLookupFilter {
    public void filter(Collection<String> personIds);

    public static final PersonLookupFilter EMPTY_FILTER = new PersonLookupFilter() {

        @Override
        public void filter(Collection<String> personIds) {
        }

    };
}
