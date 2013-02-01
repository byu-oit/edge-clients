/**
 * Name: JOptionPaneWrapper.java
 * Date Created: Feb 9, 2009
 */
package edu.byu.framework.swing.util;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author tylers2
 */
public class JOptionPaneWrapper {

    private final static Logger LOG = Logger.getLogger(JOptionPaneWrapper.class);

    public static int showOptionDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType, final Icon icon, final Object[] options, final Object initialValue) throws HeadlessException {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static void showMessageDialog(final Component parentComponent, final Object message, final String title, final int messageType, final Icon icon) throws HeadlessException {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showMessageDialog(parentComponent, message, title, messageType, icon);
            }
        };
        showDialog(r);
    }

    public static void showMessageDialog(final Component parentComponent, final Object message, final String title, final int messageType) throws HeadlessException {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
            }
        };
        showDialog(r);
    }

    public static void showMessageDialog(final Component parentComponent, final Object message) throws HeadlessException {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showMessageDialog(parentComponent, message);
            }
        };
        showDialog(r);
    }

    public static int showInternalOptionDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType, final Icon icon, final Object[] options, final Object initialValue) {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static void showInternalMessageDialog(final Component parentComponent, final Object message, final String title, final int messageType, final Icon icon) {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showInternalMessageDialog(parentComponent, message, title, messageType, icon);
            }
        };
        showDialog(r);
    }

    public static void showInternalMessageDialog(final Component parentComponent, final Object message, final String title, final int messageType) {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showInternalMessageDialog(parentComponent, message, title, messageType);
            }
        };
        showDialog(r);
    }

    public static void showInternalMessageDialog(final Component parentComponent, final Object message) {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showInternalMessageDialog(parentComponent, message);
            }
        };
        showDialog(r);
    }

    public static <T> T showInternalInputDialog(final Component parentComponent, final Object message, final String title, final int messageType, final Icon icon, final T[] selectionValues, final T initialSelectionValue) {
        final Object[] result = new Object[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);
            }
        };
        showDialog(r);
        return (T) result[0];
    }

    public static <T> T showInternalInputDialog(Component parentComponent, Object message, String title, int messageType, Icon icon, List<? extends T> selectionValues, T initialSelectionValue) {
        return showInternalInputDialog(parentComponent, message, title, messageType, icon, (T[]) selectionValues.toArray(), initialSelectionValue);
    }

    public static String showInternalInputDialog(final Component parentComponent, final Object message, final String title, final int messageType) {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalInputDialog(parentComponent, message, title, messageType);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static String showInternalInputDialog(final Component parentComponent, final String message) {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalInputDialog(parentComponent, message);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showInternalConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType, final Icon icon) {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalConfirmDialog(parentComponent, message, title, optionType, messageType, icon);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showInternalConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType) {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalConfirmDialog(parentComponent, message, title, optionType, messageType);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showInternalConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType) {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalConfirmDialog(parentComponent, message, title, optionType);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showInternalConfirmDialog(final Component parentComponent, final Object message) {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInternalConfirmDialog(parentComponent, message);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static <T> T showInputDialog(final Component parentComponent, final Object message, final String title, final int messageType, final Icon icon, final T[] selectionValues, final T initialSelectionValue) throws HeadlessException {
        final Object[] result = new Object[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);

            }
        };
        showDialog(r);
        return (T) result[0];
    }

    public static <T> T showInputDialog(Component parentComponent, Object message, String title, int messageType, Icon icon, List<? extends T> selectionValues, T initialSelectionValue) throws HeadlessException {
        return showInputDialog(parentComponent, message, title, messageType, icon, (T[]) selectionValues.toArray(), initialSelectionValue);
    }

    public static String showInputDialog(final Component parentComponent, final Object message, final String title, final int messageType) throws HeadlessException {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(parentComponent, message, title, messageType);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static String showInputDialog(final Component parentComponent, final Object message, final Object initialSelectionValue) {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(parentComponent, message, initialSelectionValue);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static String showInputDialog(final Component parentComponent, final Object message) throws HeadlessException {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(parentComponent, message);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static String showInputDialog(final Object message, final Object initialSelectionValue) {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(message, initialSelectionValue);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static String showInputDialog(final Object message) throws HeadlessException {
        final String[] result = new String[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showInputDialog(message);
            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType, final Icon icon) throws HeadlessException {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType, icon);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType, final int messageType) throws HeadlessException {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showConfirmDialog(final Component parentComponent, final Object message, final String title, final int optionType) throws HeadlessException {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);

            }
        };
        showDialog(r);
        return result[0];
    }

    public static int showConfirmDialog(final Component parentComponent, final Object message) throws HeadlessException {
        final int[] result = new int[1];
        Runnable r = new Runnable() {

            public void run() {
                result[0] = JOptionPane.showConfirmDialog(parentComponent, message);
            }
        };
        showDialog(r);
        return result[0];
    }

//    @SuppressWarnings("unchecked")
//    public static <T> T showInputDialog(final Component parent, final Object message, final String title,
//            final int type, final Icon icon, final T[] data, final T selected) {
//
//        final Object[] result = new Object[1];
//        Runnable r = new Runnable() {
//
//            public void run() {
//                Object output = JOptionPane.showInputDialog(parent, message, title, type, icon, data, selected);
//                result[0] = output;
//            }
//        };
//
//        showDialog(r);
//
//        return (T) result[0];
//    }
//
//    public static <T> T showInputDialog(Component parent, String message, String title,
//            int type, Icon icon, List<? extends T> data, T selected) {
//        return showInputDialog(parent, message, title, type, icon, (T[]) data.toArray(), selected);
//    }
//
//    public static String showInputDialog(final Component parent, final Object message) {
//        final Object[] result = new Object[1];
//        Runnable r = new Runnable() {
//
//            public void run() {
//                Object output = JOptionPane.showInputDialog(parent, message);
//                result[0] = output;
//            }
//        };
//
//        showDialog(r);
//        return (String) result[0];
//    }

    public static void showMessageDialog(String message, String title, int type) {
        showMessageDialog(null, message, title, type);
    }

    public static void showMessageDialog(String message, String title) {
        showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMessageDialog(final Component parent, final String message, final String title, final int type) {
        Runnable r = new Runnable() {

            public void run() {
                JOptionPane.showMessageDialog(parent, message, title, type);
            }
        };

        showDialog(r);
    }

    private static void showDialog(Runnable r) {
        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        try {
            EventQueue.invokeAndWait(r);
        } catch (Throwable t) {
            LOG.error("SEVERE: Unable to show dialog message", t);
//            System.exit(-1);
        }
    }
}
