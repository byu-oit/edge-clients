package edu.byu.framework.swing.exceptions;

/**
 *
 * @author jmooreoa
 */
public interface DebugAppender {
    CharSequence getDebugData(Throwable t);
}
