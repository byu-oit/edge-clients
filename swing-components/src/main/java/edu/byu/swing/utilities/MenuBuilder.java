package edu.byu.swing.utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import org.jdesktop.swingx.JXTitledSeparator;

/**
 *The {@code MenuBuilder} class provides several methods for building
 * menu bars, toolbars, and popup menus from collections of {@code Action}
 * objects.
 * 
 * <p>The methods require either a {@code Collection} or an {@code Array}.
 * Actions will be used to create the individual items/buttons.  See individual
 * method documentation for details on how to construct the requred array.
 * 
 * @see javax.swing.Action
 * @see javax.swing.AbstractAction
 * @see javax.swing.JMenuBar
 * @see javax.swing.JMenu
 * @see javax.swing.JMenuItem
 * @see javax.swing.JPopupMenu
 * @see javax.swing.JToolBar
 * 
 * @author Joseph Moore
 * @version 1.1.0
 * @since 1.0.0
 */
public final class MenuBuilder {
    private MenuBuilder() {}

    /**
     * Builds a {@code JMenuBar} object based on Arrays
     * of {@code Action} objects.
     *
     * <p>Each vararg represents one menu.  The first object in each array will
     * be used to create the menu itself.  If it is an {@code Action}, it will be used
     * to create the menu.  If not, the {@code toString()} method will be used to
     * set the name of the menu.  Subsequent objects will be assesed
     * based on their class: If it is an array, it will be used to create a sub-menu
     * (once again using the first object to create the menu); if it
     * is an {@code Action}, it will be used to create a {@code JMenuItem};
     * if it is a {@code Component}, it will be placed directly in the menu;
     * if it is a non-empty string, a {@code JXTitledSeparator} will be created;
     * for anything else {@code JSeparator}, a new {@code JSeparator} will
     * be created.
     *
     * @param objects Arrays containing menu definitions
     * @return  A ready-to-use JMenuBar
     */
    
    public static JMenuBar buildMenuBar(Object[]... objects) {
        //Initialize JMenuBar that will be returned
        JMenuBar bar = new JMenuBar();
        
        //Iterate through the collection, analyze, and build menu
        for(Object[] array : objects) {
            bar.add(createMenu(array));
        }
        //Return populated JMenuBar
        return bar;
    }

    /**
     * Builds a {@code JMenuBar} object based on {@code Collection}s
     * of {@code Action} objects.
     *
     * <p>Each vararg represents one menu.  The first object in each {@code Collection} will
     * be used to create the menu itself, and subsequent objects will be assesed
     * based on their class: If it is a {@code Collection} or Array, it will be used to create a sub-menu
     * (once again using the first object to create the menu); if it
     * is an {@code Action}, it will be used to create a {@code JMenuItem};
     * if it is a {@code Component}, it will be placed directly in the menu;
     * if it is a non-empty string, a {@code JXTitledSeparator} will be created;
     * for anything else {@code JSeparator}, a new {@code JSeparator} will
     * be created.
     *
     * @param objects Collections containing menu definitions
     * @return  A ready-to-use JMenuBar
     * @see #buildMenu(java.lang.String, java.lang.Object[])
     */
    public static JMenuBar buildMenuBar(Collection<?>... objects) {
        JMenuBar result = new JMenuBar();
        for (Collection each : objects) {
            result.add(createMenu(each.toArray()));
        }
        return result;
    }

    /**
     * Builds a menu defined by objects
     * @param name name of menu
     * @param objects menu definition
     * @return ready-to-use JMenu
     */
    public static JMenu buildMenu(String name, Object... objects) {
        JMenu menu = new JMenu(name);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }

    /**
     * Builds a menu defined by objects
     * @param action action to define menu
     * @param objects menu definition
     * @return ready-to-use JMenu
     */
    public static JMenu buildMenu(Action action, Object... objects) {
        JMenu menu = new JMenu(action);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }

    /**
     * Builds a menu defined by objects
     * @param name name of menu
     * @param objects menu definition
     * @return ready-to-use JMenu
     * @deprecated use buildMenu (Collection, String) instead
     */
    @Deprecated
    public static JMenu buildMenu(String name, Collection<?> objects) {
        JMenu menu = new JMenu(name);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }
    /**
     * Builds a menu defined by objects
     * @param objects
     * @param name
     * @return
     */
    public static JMenu buildMenu(Collection<?> objects, String name) {
        JMenu menu = new JMenu(name);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }

    /**
     *
     * @param action
     * @param objects
     * @return
     * @deprecated use buildMenu(Collection, Action) instead
     */
    @Deprecated
    public static JMenu buildMenu(Action action, Collection<?> objects) {
        JMenu menu = new JMenu(action);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }
    /**
     * Builds a menu defined by objects
     * @param objects menu definition
     * @param action action to define menu
     * @return ready-to-use JMenu
     */
    public static JMenu buildMenu(Collection<?> objects, Action action) {
        JMenu menu = new JMenu(action);
        for (Object o : objects) {
            menu.add(buildItem(o));
        }
        return menu;
    }

    /**
     * Builds a {@code JPopupMenu} based on a set of objects. Object processing
     * is identical to that in {@code buildMenu()}.
     *
     * @param name name of the popup menu
     * @param objects objects from which to create menu
     * @return A ready-to-use JPopupMenu
     *
     * @see #buildMenu(java.lang.String, java.lang.Object[])
     */

    public static JPopupMenu buildPopup(String name, Object... objects) {
        JPopupMenu menu = new JPopupMenu(name);
        for (Object obj: objects) {
            menu.add(buildItem(obj));
        }
        return menu;
    }
    
    /**
     * Builds a {@code JToolBar} object based on a {@code Collection}
     * of {@code Action} objects.
     * 
     * <p>The {@code Collection} should contain either {@code Action}
     * objects, representing eventual buttons in the toolbar, or 
     * {@code JSeperator} objects.
     * 
     * @param actions a collection defining the structure of the toolbar
     * @param name the name of the toolbar, displayed when undocked
     * @return A ready-to-use JToolBar object
     */
    public static JToolBar buildToolBar(Collection<Object> actions, String name) {
        
        //Create toolbar
        JToolBar bar = new JToolBar(name);
        //Iterate through each object
        for (Object obj : actions) {
            if (obj instanceof Action) {
                JButton button = new JButton((Action) obj);
                if (button.getIcon() != null) {
                    button.setHideActionText(true);
                }
                bar.add(button);
            } else if (obj instanceof Component && !(obj instanceof JSeparator)) {
                bar.add((Component) obj);
            } else {
                bar.addSeparator();
            }
        }
        return bar;
    }
    /**
     * Builds a JToolBar.
     * <p>Each object will be processed as follows:
     * <ul><li>{@code Action} objects will create a JButton with hideActionText = <tt>false</tt></li>
     * <li>{@code Component} objects will be added to the toolbar as-is</li>
     * <li>Any other object will create a new {@code JSeparator}</li></ul>
     *
     * @param name name of the toolbar
     * @param objects objects to add
     * @return A ready-to-use JToolBar
     */

    public static JToolBar buildToolBar(String name, Object... objects) {
        JToolBar bar = new JToolBar(name);
        for (Object obj: objects) {
            if (obj instanceof Action) {
                JButton button = new JButton((Action) obj);
                if (button.getIcon() != null) {
                    button.setHideActionText(true);
                }
                bar.add(button);
            } else if (obj instanceof Component && !(obj instanceof JSeparator)) {
                bar.add((Component) obj);
            } else {
                bar.addSeparator();
            }
        }
        return bar;
    }
    
    /**
     * Builds a {@code JFrame} object with a {@code JMenuBar},
     * {@code JPopupMenu}, and {@code JToolbar} based on the
     * {@code Action} objects passed to the method.
     * 
     * <p>If one of the pre-built elements is not desired, pass a <tt>null</tt>
     * value to the method.
     * 
     * <p>The method will return a JFrame with specified name, dimension, and
     * menues.  The layout manager (if toolbar is non-<tt>null</tt>) will be 
     * {@code BorderLayout}.  The method also applies a
     * {@code GenericPopupMouseListener} to handle the popup menu/mouse
     * interaction.
     * 
     * @param name the title that will be displayed in the frame
     * @param dim the dimensions of the frame
     * @param menubar actions from which to build menu bar for the frame
     * @param popup actions from which to build popup menu
     * @param toolbar actions from which to build toolbar
     * @return A ready-to-use JFrame
     * 
     * @see JFrame
     * @see GenericPopupMouseListener
     * @see BorderLayout
     * @see #buildMenu(java.lang.String, java.lang.Object[])
     * @see #buildToolBar(java.lang.String, java.lang.Object[])
     * @see #buildPopup(java.lang.String, java.lang.Object[])
     */
    public static JFrame buildFrame(String name, Dimension dim, Object[][] menubar, Object[] popup, Object[] toolbar) {
        JFrame frame = new JFrame(name);

        if (dim != null) {
            frame.setSize(dim);
        }

        if (menubar != null) {
            frame.setJMenuBar(buildMenuBar(menubar));
        }
        if (toolbar != null) {
            frame.setLayout(new BorderLayout());
            frame.add(buildToolBar("Toolbar", toolbar), BorderLayout.NORTH);
        }
        if (popup != null) {
            frame.addMouseListener(new GenericPopupMouseListener(buildPopup("Popup", popup)));
        }

        return frame;
    }

    /**
     * Builds a {@code JDialog} object with a {@code JMenuBar},
     * {@code JPopupMenu}, and {@code JToolbar} based on the
     * {@code Action} objects passed to the method.
     *
     * <p>If one of the pre-built elements is not desired, pass a <tt>null</tt>
     * value to the method.
     *
     * <p>The method will return a JDialog with specified name, dimension, and
     * menues.  If a toolbar is desired, the layout of the content pane will be
     * {@code BorderLayout}.  The method also applies a
     * {@code GenericPopupMouseListener} to handle the popup menu/mouse
     * interaction.
     *
     * @param parent parent frame
     * @param modal modal or non-modal dialog
     * @param name the title that will be displayed in the frame
     * @param dim the dimensions of the frame
     * @param menubar actions from which to build menu bar for the frame
     * @param popup actions from which to build popup menu
     * @param toolbar actions from which to build toolbar
     * @return A ready-to-use JDialog
     *
     * @see JDialog
     * @see GenericPopupMouseListener
     * @see BorderLayout
     * @see #buildMenu(java.lang.String, java.lang.Object[])
     * @see #buildToolBar(java.lang.String, java.lang.Object[])
     * @see #buildPopup(java.lang.String, java.lang.Object[])
     */
    public static JDialog buildDialog(Frame parent, boolean modal, String name, Dimension dim, Object[][] menubar, Object[] popup, Object[] toolbar) {
        JDialog dialog = new JDialog(parent, modal);
        dialog.setTitle(name);
        if (dim != null)
            dialog.setSize(dim);

        if (menubar != null) {
            dialog.setJMenuBar(buildMenuBar(menubar));
        }
        if (toolbar != null) {
            dialog.setLayout(new BorderLayout());
            dialog.add(buildToolBar("Toolbar", toolbar), BorderLayout.NORTH);
        }
        if (popup != null) {
            dialog.addMouseListener(new GenericPopupMouseListener(buildPopup("Popup", popup)));
        }

        return dialog;
    }   
    
    
    //=====================================
    // UTILITY METHODS
    //=====================================
    //-------------------------------------------------------------------------
    // Builds an individual JMenu based on map with name as the displayed name
    // Used by buildMenu() and checkObject()
    //-------------------------------------------------------------------------

    private static JMenu createMenu(Object[] objects) {
        JMenu menu;
        if (objects[0] instanceof Action) {
            menu = new JMenu((Action) objects[0]);
        } else {
            menu = new JMenu(objects[0].toString());
        }
        for (int i = 1; i < objects.length; i++) {
            menu.add(buildItem(objects[i]));
        }
        return menu;
    }

    private static JComponent buildItem(Object obj) {
        if (obj instanceof Object[]) {
            return createMenu((Object[]) obj);
        } else if (obj instanceof Collection<?>) {
            return createMenu(((Collection<?>) obj).toArray());
        } else if (obj instanceof Action) {
            return new JMenuItem((Action) obj);
        } else if (obj instanceof JMenuItem) {
            return (JMenuItem) obj;
        } else if ((obj instanceof JComponent) && !(obj instanceof JSeparator)) {
            return (JComponent) obj;
        } else if (obj instanceof String && !((String)obj).isEmpty()) {
            return new JXTitledSeparator(" " + (String)obj);
        }
        else {
            return new JXTitledSeparator("");
        }
    }
}
