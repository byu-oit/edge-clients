package edu.byu.swing.utilities;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 * Implementation of <code>GenericPopupMouseListener</code> built specifically
 * for handling right-click events in <code>JTable</code> objects.
 * 
 * Preserves all functionality of <code>GenericPopupMouseListener</code>, but
 * addition to displaying a popup menu, selects the row/column in which the mouse
 * was clicked.
 * 
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 */
public class TablePopupMouseListener  extends GenericPopupMouseListener{
    /**
     * Table on which to select rows-should be the same table on which the listener
     * is installed
     */
    protected final JTable table;

    /**
     * Creates a new TablePopupMouseListener for the specified table and which
     * displays the specified menu.
     * @param table JTable associated with this listener
     * @param menu popup menu to be displayed
     */
    public TablePopupMouseListener(JTable table, JPopupMenu menu) {
        super(menu);
        this.table = table;
    }
    /**
     * Displays popup menu and selects cell in table where click occured
     */
    @Override
    public void checkPopup(MouseEvent me) {
        super.checkPopup(me);
        if (me.isPopupTrigger()) {
            Point point = me.getPoint();
            int row = table.rowAtPoint(point);
            int col = table.columnAtPoint(point);
            table.getColumnModel().getSelectionModel().setSelectionInterval(col, col);
            table.getSelectionModel().setSelectionInterval(row, row);
        }
    }
}
