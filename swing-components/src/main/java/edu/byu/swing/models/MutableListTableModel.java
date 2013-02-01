/**
 * Name: MutableListTableModel.java
 * Date Created: Feb 3, 2009
 */
package edu.byu.swing.models;

import java.util.Collection;

/**
 * A mutable extension of ListTableModel
 * @param <T>
 * @author tylers2
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class MutableListTableModel<T> extends ListTableModel<T> {
    private static final long serialVersionUID = 2966731537081072248L;

    /**
     * Creates a ListTableModel containing the specified data, column names,
     * and column classes
     * @param data data for model
     * @param columnNames column names
     * @param columnClasses column classes
     */
    public MutableListTableModel(T[] data, String[] columnNames, Class<?>[] columnClasses) {
        super(data, columnNames, columnClasses);
    }

    /**
     * Creates a ListTableModel containing the specified data, column names,
     * and column classes
     * @param data data for model
     * @param columnNames column names
     * @param columnClasses column classes
     */
    public MutableListTableModel(Collection<? extends T> data, String[] columnNames, Class<?>[] columnClasses) {
        super(data, columnNames, columnClasses);
    }

    /**
     * Creates a MutableListTableModel containing the specified data, column names,
     * and column class
     * @param data data for model
     * @param columnNames column names
     * @param klass data type of all columns
     */
    public MutableListTableModel(Collection<? extends T> data, String[] columnNames, Class<?> klass) {
        super(data, columnNames, klass);
    }

     /**
     * Creates an empty MutableListTableModel with the specified column names, with all
     * columns being of type klass
     * @param columnNames column names
     * @param klass data type of all columns
     */
    public MutableListTableModel(String[] columnNames, Class<?> klass) {
        super(columnNames, klass);
    }

    /**
     * Creates an empty MutableListTableModel with the specified column names
     * @param columnNames column names
     */
    public MutableListTableModel(String[] columnNames) {
        super(columnNames);
    }

    /**
     * Creates an empty MutableListTableModel with specifed column names and classes
     * @param columnNames column names
     * @param columnClasses column types
     */
    public MutableListTableModel(String[] columnNames, Class<?>[] columnClasses) {
        super(columnNames, columnClasses);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        T row = getRow(rowIndex);
        assert columnIndex <= getColumnCount();
        setValueAt(aValue, row, columnIndex);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Sets the value of the specified column in the specified row to aValue
     * @param aValue new value
     * @param row row object
     * @param columnIndex index of column to update
     */
    protected abstract void setValueAt(Object aValue, T row, int columnIndex);

}
