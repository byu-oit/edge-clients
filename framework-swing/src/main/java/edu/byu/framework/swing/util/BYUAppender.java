/**
 * Name: StatusAppender.java
 * Date Created: Jan 30, 2009
 */
package edu.byu.framework.swing.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author tylers2
 */
public class BYUAppender extends AppenderSkeleton {

    private static List<BYUAppenderListener> listeners = Collections.synchronizedList(new LinkedList<BYUAppenderListener>());

    public static boolean removeListener(BYUAppenderListener o) {
        return listeners.remove(o);
    }

    public static boolean addListener(BYUAppenderListener e) {
        return listeners.add(e);
    }

    @Override
    protected void append(LoggingEvent le) {
        for (BYUAppenderListener listener : listeners) {
            listener.log(le);
        }
    }

    @Override
    public void close() {
        System.out.println("closing status appender....");
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
