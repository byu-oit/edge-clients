/**
 * Name: MutableListComboBoxModel.java
 * Date Created: Feb 10, 2009
 */
package edu.byu.swing.models;

import java.util.Collection;
import javax.swing.MutableComboBoxModel;

/**
 * A mutable extension of ListComboBoxModel
 * @param <T> type of element
 * @author tylers2
 * @version 1.1.0
 * @since 1.0.0
 * @see ListComboBoxModel
 * @see MutableComboBoxModel
 */
public class MutableListComboBoxModel<T> extends ListComboBoxModel<T> implements MutableComboBoxModel {

    private static final long serialVersionUID = -451083433195613636L;

    public MutableListComboBoxModel(T[] data) {
        super(data);
    }

    public MutableListComboBoxModel(Collection<? extends T> data) {
        super(data);
    }

    public MutableListComboBoxModel() {
    }

    /**
     * {@inheritDoc }
     */
    public void addElement(Object obj) {
        int lastIndex = getSize();
        insertElementAt(obj, lastIndex);
    }

    /**
     * {@inheritDoc }
     */
    public void removeElement(Object obj) {
        int index = data.indexOf(obj);
        if (index == -1) {
            return;
        }
        removeElementAt(index);
    }

    /**
     * {@inheritDoc }
     */
    @SuppressWarnings("unchecked")
    public void insertElementAt(Object obj, int index) {
        data.add(index, (T)obj);
        fireIntervalAdded(this, index, index);
    }

    /**
     * {@inheritDoc }
     */
    public void removeElementAt(int index) {
        data.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    /**
     * Replaces the element at index with the specified element
     * @param obj
     * @param index
     */
    public void replaceElementAt(T obj, int index) {
        data.remove(index);
        data.add(index, obj);
        fireContentsChanged(this, index, index);
    }
}
