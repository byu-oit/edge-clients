/**
 * Name: StatusEventListener.java
 * Date Created: Jan 30, 2009
 */
package edu.byu.framework.swing.util;

import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author tylers2
 */
public interface BYUAppenderListener {

    public void log(LoggingEvent loggingEvent);
}
