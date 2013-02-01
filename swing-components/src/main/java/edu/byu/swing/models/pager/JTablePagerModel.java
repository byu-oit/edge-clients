/**
 * Name: JTablePagerModel.java
 * Date Created: Aug 11, 2009
 */
package edu.byu.swing.models.pager;

import edu.byu.swing.components.table.YTable;
import edu.byu.swing.models.MutableListTableModel;
import java.util.List;
import javax.swing.*;
import java.awt.EventQueue;

/**
 *
 * @param <T>
 * @author tylers2
 */
public abstract class JTablePagerModel<T> implements PagerModel<T, JTable> {

    private final MutableListTableModel<T> model;
    private JTable table;

    public JTablePagerModel(MutableListTableModel<T> model) {
        this.model = model;
    }

    @Override
    public synchronized JTable getComponent() {
        if (table == null) {
            Runnable r = new Runnable() {
                public void run() {
                    table = new YTable(model);
                }
            };
            if (EventQueue.isDispatchThread()) {
                r.run();
            } else {
                try {
                    EventQueue.invokeAndWait(r);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to create JTable for pager model", e);
                }
            }
        }
        return table;
    }

    @Override
    public void clear() {
        model.clear();
    }

    public abstract List<T> getDataset(int start, int end);

    @Override
    public void loadDataset(int start, int end) {
        List<T> data = getDataset(start, end);
		table.setVisible(false);
		try {
			model.clear();
			if (data != null && !data.isEmpty()) {
	            model.addAll(data);
	        }
		} finally {
			table.setVisible(true);
			if (model.getRowCount() > 0) {
				table.getSelectionModel().setSelectionInterval(0, 0);
			}
		}
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getSelectedItem() {
        int index = table.getSelectedRow();
        if (index == -1 || index >= table.getRowCount()) {
            return null;
        }
        index = table.convertRowIndexToModel(index);
        return model.getRow(index);
    }

    @Override
    public ListSelectionModel getSelectionModel() {
        return table.getSelectionModel();
    }
}
