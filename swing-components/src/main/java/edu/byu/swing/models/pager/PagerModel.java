/**
 * Name: PagerListener.java
 * Date Created: Aug 11, 2009
 */
package edu.byu.swing.models.pager;

import javax.swing.*;
import java.awt.Component;

/**
 *
 * @param <T>
 * @param <C>
 * @author tylers2
 */
public interface PagerModel<T, C extends Component> {

    public void loadDataset(int start, int end);

    public int getPageSize();

    public int getTotal();

    public C getComponent();

    public T  getSelectedItem();

    public void clear();

    public ListSelectionModel getSelectionModel();
}
