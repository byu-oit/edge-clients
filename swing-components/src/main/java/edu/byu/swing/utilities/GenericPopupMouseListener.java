package edu.byu.swing.utilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 * Provides a basic implementation of the <code>MouseAdapter</code> class
 * that may be used to cause a popup menu to appear when the use clicks on
 * a given component.  It also allows for the disabling of the popup feature
 * using setOpenable.
 * 
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 * @see JPopupMenu
 * @see MouseAdapter
 */
public class GenericPopupMouseListener extends MouseAdapter {

    /** Popup menu */
    protected JPopupMenu menu;

    /** Controls if popup should be opened */
    protected boolean openable;

    /**
     * Creates a new openable GenericPopupMouseListener with no popup menu.
     */
    public GenericPopupMouseListener() {
        this(null, true);
    }

    /**
     * Creates a new openable GenericPopupMouseListener to display the specified menu.
     * @param menu popup menu to be displayed
     */
    public GenericPopupMouseListener(JPopupMenu menu) {
        this(menu, true);
    }

    /**
     * Creates a new GenericPopupMouseListener to display the specified menu.
     * If {@code openable = true}, the menu will be openable
     * @param menu menu to use
     * @param openable whether or not to open menu on popup trigger
     */
    public GenericPopupMouseListener(JPopupMenu menu, boolean openable) {
        this.menu = menu;
        this.openable = openable;
    }

    /**
     * If {@code openable = true} and mouse click was a popup trigger, displays
     * the popup menu.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        checkPopup(me);
    }

    /**
     * If {@code openable = true} and mouse click was a popup trigger, displays
     * the popup menu.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        checkPopup(me);
    }

    /**
     * Method called by listener methods to check if the mouse event
     * is a popup trigger and if {@code openable = true}.  If so, displays popup menu.
     * 
     * @param me event passed by event trigger
     */
    protected void checkPopup(MouseEvent me) {
        if (menu == null) {
            throw new IllegalStateException("No menu has been defined");
        }
        if (me.isPopupTrigger() && isOpenable()) {
            menu.show(me.getComponent(), me.getX(), me.getY());
        }
    }

    /**
     * Sets whether or not the popup will be displayed when popup trigger is detected.
     * @param openable if {@code true}, popup will be displayed.
     */
    public void setOpenable(boolean openable) {
        this.openable = openable;
    }

    /**
     * Returns {@code true} if popup is openable
     * @return {@code true} if popup is openable
     */
    public boolean isOpenable() {
        return openable;
    }

    /**
     * Returns current menu
     * @return current menu
     */
    public JPopupMenu getMenu() {
        return menu;
    }

    /**
     * Sets PopupMenu
     * @param menu new PopupMenu to use
     */
    public void setMenu(JPopupMenu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("menu must be non-null");
        }
        this.menu = menu;
    }


}