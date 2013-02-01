package edu.byu.swing.models.pager;

import java.awt.Component;
import java.util.List;

/**
 *
 * @author jmooreoa
 */
public interface PersonPagerModel<C extends Component> extends PagerModel<String, C> {
    void setResultSet(List<String> resultSet);
}
