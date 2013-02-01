package edu.byu.swing.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import edu.byu.swing.utilities.ComponentUtil;
import org.apache.log4j.Logger;

/**
 * A list-backed TableModel
 * @param <T> Row type
 * @author tylers2
 * @version 1.1.0
 * @since 1.0.0
 */
public abstract class ListTableModel<T> extends AbstractTableModel {

    private final List<T> data = new Vector<T>();
    private final Class<?>[] columnClasses;
    private final String[] columnNames;
    private final Logger LOG = Logger.getLogger(getClass());

    /**
     * Creates an empty ListTableModel with specifed column names and classes
     * @param columnNames column names
     * @param columnClasses column types
     */
    public ListTableModel(String[] columnNames, Class<?>[] columnClasses) {
        this(new ArrayList<T>(), columnNames, columnClasses);
    }

    /**
     * Creates an empty ListTableModel with the specified column names
     * @param columnNames column names
     */
    public ListTableModel(String[] columnNames) {
        this(new ArrayList<T>(), columnNames, Object.class);
    }

    /**
     * Creates an empty ListTableModel with the specified column names, with all
     * columns being of type klass
     * @param columnNames column names
     * @param klass data type of all columns
     */
    public ListTableModel(String[] columnNames, Class<?> klass) {
        this(new ArrayList<T>(), columnNames, klass);
    }

    /**
     * Creates a ListTableModel containing the specified data, column names,
     * and column class
     * @param data data for model
     * @param columnNames column names
     * @param klass data type of all columns
     */
    public ListTableModel(Collection<? extends T> data, String[] columnNames, Class<?> klass) {
        columnClasses = new Class<?>[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            columnClasses[i] = klass;
        }

        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        if (columnNames == null) {
            throw new IllegalArgumentException("Column names cannot be null");
        }

        for (T each : data) {
            this.data.add(each);
        }
        this.columnNames = columnNames;
    }

    /**
     * Creates a ListTableModel containing the specified data, column names,
     * and column classes
     * @param data data for model
     * @param columnNames column names
     * @param columnClasses column classes
     */
    public ListTableModel(Collection<? extends T> data, String[] columnNames, Class<?>[] columnClasses) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        if (columnNames == null) {
            throw new IllegalArgumentException("Column names cannot be null");
        }

        if (columnClasses == null) {
            throw new IllegalArgumentException("Column classes cannot be null");
        }

        if (columnNames.length != columnClasses.length) {
            throw new IllegalArgumentException("Column names and column classes must be of same length");
        }

        for (T each : data) {
            this.data.add(each);
        }
        this.columnNames = columnNames;
        this.columnClasses = columnClasses;
    }

    /**
     * Creates a ListTableModel containing the specified data, column names,
     * and column classes
     * @param data data for model
     * @param columnNames column names
     * @param columnClasses column classes
     */
    public ListTableModel(T[] data, String[] columnNames, Class<?>[] columnClasses) {
        this(Arrays.asList(data), columnNames, columnClasses);
    }

    /**
     * Sets data for model
     * @param data new data
     */
    public void setData(Collection<? extends T> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    /**
     * Sets data for model
     * @param data new data
     */
    public void setData(T[] data) {
        setData(Arrays.asList(data));
    }

    /**
     * Gets current data list
     * @return copy of current data list
     */
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    /**
     * Gets the values at the specified indices
     * @param indices
     * @return list of requested values
     */
    public List<T> getRows(int[] indices) {
        if (indices.length == 0) {
            throw new IndexOutOfBoundsException("No index selected");
        }
        List<T> dataSet = new ArrayList<T>();
        for (int i : indices) {
            dataSet.add(getRow(i));
        }
        return dataSet;
    }

    /**
     * Adds a row to the table model
     * @param row data for row
     * @return row index
     */
    public int addRow(T row) {
        data.add(row);
        int index = data.indexOf(row);
        fireTableRowsInserted(index, index);
        return index;
    }

    /**
     * Adds all elements from the specified collection to the end of this model.
     * @param collection
     */
    public void addAll(Collection<? extends T> collection) {
        if (collection == null) {
            LOG.warn("adding null collection to table model");
            return;
        }
        data.addAll(collection);
        int lastRow = data.size() - 1;
        int firstRow = lastRow - collection.size();
        fireTableRowsInserted(firstRow, lastRow);
    }

    /**
     * Adds element at the specified index
     * @param index index at which to add element
     * @param element element to add
     */
    public void add(int index, T element) {
        data.add(index, element);
        fireTableRowsInserted(index, index);
    }

    /**
     * Removes specified element from model
     * @param row element to remove
     * @return {@code true} if model changed as a result of this operation
     */
    public boolean removeRow(T row) {
        int index = data.indexOf(row);
        return removeRow(index);
    }

    /**
     * Removes element at specified index from model
     * @param index index of the element to be removed
     * @return {@code true} if model changed as a result of this operation
     */
    public boolean removeRow(int index) {
        T result = data.remove(index);
        fireTableRowsDeleted(index, index);
        return result != null;
    }

    /**
     * Returns the element at the specified index
     * @param index index of element
     * @return element at index
     */
    public T getRow(int index) {
        if (index >= data.size()) {
            throw new IndexOutOfBoundsException("index exceeds table length (" + index + " > " + data.size() + ")");
        }
		if (index < 0) {
			LOG.error("index < 0 on getRow");
		}
        return data.get(index);
    }

    /**
     * Returns number of rows currently in model
     * @return number of rows currently in model
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns {@code true} if this model is empty
     * @return {@code true} if this model is empty
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return convertIfPrimitive(columnClasses[columnIndex]);
    }

    private Class<?> convertIfPrimitive(Class<?> c) {
        if (!c.isPrimitive()) {
            return c;
        }
        if (c.equals(byte.class)) {
            return Byte.class;
        } else if (c.equals(short.class)) {
            return Short.class;
        } else if (c.equals(int.class)) {
            return Integer.class;
        } else if (c.equals(long.class)) {
            return Long.class;
        } else if (c.equals(float.class)) {
            return Float.class;
        } else if (c.equals(double.class)) {
            return Double.class;
        } else if (c.equals(boolean.class)) {
            return Boolean.class;
        } else if (c.equals(char.class)) {
            return Character.class;
        }
        return c;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * {@inheritDoc }
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns names of columns in this model
     * @return names of columns in this model
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * {@inheritDoc }
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex > getColumnCount()) {
            throw new IndexOutOfBoundsException("index exceeds table width (" + columnIndex + " > " + getColumnCount() + ")");
        }
        T row = getRow(rowIndex);
        return getDataFromRow(row, columnIndex);
    }

    /**
     * Clears all data from model
     */
    public void clear() {
        data.clear();
        fireTableDataChanged();
    }

    /**
     * Returns index of specified item
     * @param item
     * @return index of specified item
     */
    public int indexOf(T item) {
        return data.indexOf(item);
    }

    public boolean contains(T item) {
        return data.contains(item);
    }

    /**
     * Returns the data at the specified column from the specified row.
     * @param row row object from which to get data
     * @param column column index of data to get
     * @return data at specified column
     */
    public abstract Object getDataFromRow(T row, int column);

    @Override
    public void fireTableCellUpdated(final int row, final int column) {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableCellUpdated(row, column);
            }
        });
    }

    @Override
    public void fireTableChanged(final TableModelEvent e) {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableChanged(e);
            }
        });
    }

    @Override
    public void fireTableDataChanged() {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableDataChanged();
            }
        });
    }

    @Override
    public void fireTableRowsDeleted(final int firstRow, final int lastRow) {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableRowsDeleted(firstRow, lastRow);
            }
        });
    }

    @Override
    public void fireTableRowsInserted(final int firstRow, final int lastRow) {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableRowsInserted(firstRow, lastRow);
            }
        });
    }

    @Override
    public void fireTableRowsUpdated(final int firstRow, final int lastRow) {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableRowsUpdated(firstRow, lastRow);
            }
        });
    }

    @Override
    public void fireTableStructureChanged() {
        ComponentUtil.invokeAndWait(new Runnable() {

            public void run() {
                ListTableModel.super.fireTableStructureChanged();
            }
        });
    }
}
