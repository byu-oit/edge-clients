package edu.byu.swing.renderers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Implements caching support for all display renderers
 * Author: Tyler Southwick (tyler_southwick@byu.edu)
 * Date: Aug 8, 2009
 */
public abstract class PersonRendererBase implements PersonRenderer {
    private final ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

    public final String retreiveValue(String personId) {
        String value = cache.get(personId);
        if (value != null) {
            return value;
        }

        value = doRetreiveValue(personId);
        if (value == null) {
            return personId;
        }

        cache.put(personId, value);
        return value;
    }

    protected abstract String doRetreiveValue(String personId);
}
