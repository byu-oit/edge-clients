package edu.byu.swing.dialogs.impl;

import edu.byu.swing.dialogs.PersonLookupDelegate;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 11, 2009
 */
public abstract class PersonLookupDelegateBase implements PersonLookupDelegate {
    private final ConcurrentMap<String, List<String>> cache = new ConcurrentHashMap<String, List<String>>();

    public final List<String> lookup(String search) {
        if (cache.containsKey(search)) {
            return cache.get(search);
        }

        List<String> personIds = doLookup(search);
        cache.putIfAbsent(search, personIds);

        return personIds;
    }

    protected abstract List<String> doLookup(String search);
}
