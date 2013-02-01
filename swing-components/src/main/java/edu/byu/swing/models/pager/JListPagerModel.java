/**
 * Name: ComboBoxPageListener.java
 * Date Created: Aug 11, 2009
 */
package edu.byu.swing.models.pager;

import edu.byu.swing.models.MutableListComboBoxModel;
import java.util.List;
import javax.swing.*;
import java.awt.EventQueue;

/**
 *
 * @param <T>
 * @author tylers2
 */
public abstract class JListPagerModel<T> implements PagerModel<T, JList> {

    private final MutableListComboBoxModel<T> model = new MutableListComboBoxModel<T>();
    private JList list;

    @Override
    public synchronized JList getComponent() {
        if (list == null) {
            Runnable r = new Runnable() {
                public void run() {
                    list = new JList(model);
                }
            };
            if (EventQueue.isDispatchThread()) {
                r.run();
            } else {
                try {
                    EventQueue.invokeAndWait(r);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to create JList for pager model", e);
                }
            }
        }
        return list;
    }

    public abstract List<T> getDataset(int start, int end);

    public void clear() {
        model.clear();
    }

    @Override
    public void loadDataset(int start, int end) {
        List<T> data = getDataset(start, end);
        model.clear();
        model.addAll(data);
        if (!data.isEmpty()) {
            list.setSelectedIndex(0);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getSelectedItem() {
        return (T) list.getSelectedValue();
    }

    @Override
    public ListSelectionModel getSelectionModel() {
        return list.getSelectionModel();
    }
}
