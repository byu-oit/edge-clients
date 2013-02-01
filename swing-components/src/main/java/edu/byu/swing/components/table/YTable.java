package edu.byu.swing.components.table;

import java.util.Date;
import java.util.Vector;
import javax.lang.model.type.PrimitiveType;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * A table implementation containing several bug fixes.  Specifically, it provides
 * default cell renderers and editors for the following object types:
 * <ul><li>{@code String}</li>
 * <li>{@code Integer}</li>
 * <li>{@code Date}</li></ul>
 * {@code Boolean} objects do not need a custom editor/renderer, as the default
 * renderer/editor does not have the same focus problems as the other types.
 * <p>
 * Also contains several convenience methods for getting the selected rows.
 * @author jmooreoa
 * @version 1.0.1
 * @since 1.0.0
 * @see JTable
 * @see TextCellEditor
 * @see NumberCellEditor
 * @see DateCellEditor
 * @see DateCellRenderer
 */
public class YTable extends JTable {

    /**
     * Creates a new YTable with the specified data
     * @param rowData data
     * @param columnNames column names
     */
    public YTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        installFixes();
    }

    /**
     * Creates a new YTable with the specified data
     * @param rowData data
     * @param columnNames column names
     */
    public YTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        installFixes();
    }

    /**
     * Creates an empty YTable with the specified number of rows and columns
     * @param numRows rows
     * @param numColumns columns
     */
    public YTable(int numRows, int numColumns) {
        super(numRows, numColumns);
        installFixes();
    }

    /**
     * Creates a new YTable with the specified models
     * @param dm data model for the table
     * @param cm column model for the table
     * @param sm selection model for the table
     */
    public YTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        installFixes();
    }

    /**
     * Creates a new YTable with the specified models
     * @param dm data model for the table
     * @param cm column model for the table
     */
    public YTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        installFixes();
    }

    /**
     * Creates a new YTable with the specified data model
     * @param dm data model for the table
     */
    public YTable(TableModel dm) {
        super(dm);
        installFixes();
    }

    /**
     * Creates an empty YTable
     */
    public YTable() {
        installFixes();
    }

    private void installFixes() {
        setAutoCreateColumnsFromModel(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        installEditors();
    }

    private void installEditors() {
        setDefaultEditor(String.class, new TextCellEditor());
        setDefaultEditor(Integer.class, new NumberCellEditor());
        setDefaultEditor(Date.class, new DateCellEditor());
        setDefaultRenderer(Date.class, new DateCellRenderer());
        setRendererEditorPair(byte.class, Byte.class);
        setRendererEditorPair(short.class, Short.class);
        setRendererEditorPair(int.class, Integer.class);
        setRendererEditorPair(long.class, Long.class);
        setRendererEditorPair(float.class, Float.class);
        setRendererEditorPair(double.class, Double.class);
        setRendererEditorPair(boolean.class, Boolean.class);
        setRendererEditorPair(char.class, Character.class);
    }

    private void setRendererEditorPair(Class primitive, Class wrapper) {
        setDefaultRenderer(primitive, getDefaultRenderer(wrapper));
        setDefaultEditor(primitive, getDefaultEditor(wrapper));
    }

    /**
     * Gets the model index of the currently selected row.
     * @return index in model of selected row
     */
    public int getSelectedModelRow() {
        int result = getSelectedRow();
        if (result < 0) {
            return -1;
        }
        return convertRowIndexToModel(result);
    }

    /**
     * Returns the model indices of all selected rows
     * @return an array containing the indices or an empty array if no rows are selected
     */
    public int[] getSelectedModelRows() {
        int[] rows = getSelectedRows();
        if (rows.length == 0) {
            return new int[0];
        }
        int[] result = new int[rows.length];
        for (int i = 0; i < rows.length; i++) {
            int each = rows[i];
            if (each == -1) {
                result[i] = -1;
                continue;
            }
            result[i] = convertRowIndexToModel(each);
        }
        return result;
    }
}
