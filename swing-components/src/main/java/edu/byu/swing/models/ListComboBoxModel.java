/**
 * Name: ListListModel.java
 * Date Created: Jan 27, 2009
 */
package edu.byu.swing.models;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * A model for use with a JList or a ComboBox.
 * @param <T> Type of contents
 * @author tylers2
 * @version 1.0.0
 * @since 1.0.0
 * @see ComboBoxModel
 * @see JList
 * @see JComboBox
 */
public class ListComboBoxModel<T> extends AbstractListModel implements ComboBoxModel {

    private static final long serialVersionUID = 7352570071950593067L;
    protected final List<T> data = new Vector<T>();
    private Object selectedItem;
    private final Logger LOG = Logger.getLogger(getClass());

    /**
     * Creates a new, empty model
     */
    public ListComboBoxModel() {
    }

    /**
     * Creates a new model with the specified data
     * @param data data for model
     */
    public ListComboBoxModel(Collection<? extends T> data) {
        this.data.addAll(data);
    }

    /**
     * Creates a new model with the specified data
     * @param data data for model
     */
    public ListComboBoxModel(T[] data) {
        this.data.addAll(Arrays.asList(data));
    }

    /**
     * {@inheritDoc }
     */
    public Object getElementAt(int index) {
        return data.get(index);
    }

    /**
     * {@inheritDoc }
     */
    public int getSize() {
        return data.size();
    }

    /**
     * Returns {@code true} if this model contains no elements
     * @return {@code true} if this model contains no elements
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns {@code true} if this model contains the specified object
     * @param o object
     * @return {@code true} if this model contains the specified object
     */
    public boolean contains(Object o) {
        return data.contains((T) o);
    }

    /**
     * Clears the model
     */
    public void clear() {
        int size = getSize();
        data.clear();
        fireIntervalRemoved(this, 0, size);
    }

    public void add(T t) {
        int size = data.size();
        data.add(t);
        fireIntervalAdded(this, size, size + 1);
    }

    /**
     * Adds all elements from data into the model
     * @param data elements to add
     */
    public void addAll(Collection<? extends T> data) {
        if (data == null) {
            LOG.warn("adding null collection to model");
            return;
        }
        this.data.addAll(data);
        int size = getSize();
        fireContentsChanged(this, 0, size);
    }

    /**
     * Adds all elements from data into the model
     * @param data elements to add
     */
    public void addAll(T[] data) {
        addAll(Arrays.asList(data));
    }

    /**
     * {@inheritDoc }
     */
    public Object getSelectedItem() {
        return selectedItem;
    }

    /**
     * {@inheritDoc }
     */
    public void setSelectedItem(Object anItem) {
        int index = data.indexOf(anItem);
        if (index == -1) {
            selectedItem = null;
            return;
        }
        this.selectedItem = data.get(index);
        fireContentsChanged(this, -1, -1);
    }

    /**
     * Returns a copy of the current data list
     * @return
     */
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    @Override
    protected void fireContentsChanged(final Object source, final int index0, final int index1) {
        if (SwingUtilities.isEventDispatchThread()) {
            ListComboBoxModel.super.fireContentsChanged(source, index0, index1);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ListComboBoxModel.super.fireContentsChanged(source, index0, index1);
                }
            });
        }
    }

    @Override
    protected void fireIntervalAdded(final Object source, final int index0, final int index1) {
        if (SwingUtilities.isEventDispatchThread()) {
            ListComboBoxModel.super.fireIntervalAdded(source, index0, index1);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ListComboBoxModel.super.fireIntervalAdded(source, index0, index1);
                }
            });
        }
    }

    @Override
    protected void fireIntervalRemoved(final Object source, final int index0, final int index1) {
        if (SwingUtilities.isEventDispatchThread()) {
            ListComboBoxModel.super.fireIntervalRemoved(source, index0, index1);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ListComboBoxModel.super.fireIntervalRemoved(source, index0, index1);
                }
            });
        }
    }
}
