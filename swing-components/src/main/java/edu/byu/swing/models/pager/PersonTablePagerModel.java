package edu.byu.swing.models.pager;

import edu.byu.swing.components.table.PersonCellRenderer;
import edu.byu.swing.components.table.PersonCellRenderer.DisplayType;
import edu.byu.swing.components.table.YTableColumn;
import edu.byu.swing.models.MutableListTableModel;
import edu.byu.swing.utilities.BeanHelper;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jmooreoa
 */
public class PersonTablePagerModel extends JTablePagerModel<String> implements PersonPagerModel<JTable> {

    private static final List<DisplayType> DEFAULT_DISPLAY_TYPES;
    private final Map<DisplayType, Integer> COLUMN_WIDTHS = new EnumMap<DisplayType, Integer>(DisplayType.class);

    private final List<DisplayType> displayTypes = new LinkedList<DisplayType>();

    public PersonTablePagerModel() {
        this(DEFAULT_DISPLAY_TYPES);
    }

    public PersonTablePagerModel(List<DisplayType> displayTypes) {
        super(new PersonIdTableModel());
        this.displayTypes.addAll(displayTypes);
    }

    @Override
    public synchronized JTable getComponent() {
        final JTable table = super.getComponent();
        table.setColumnModel(getColumnModel());
        return table;
    }

    private TableColumnModel getColumnModel() {
        TableColumnModel tcm = new DefaultTableColumnModel();
        for (DisplayType each : displayTypes) {
            tcm.addColumn(getColumnFor(each));
        }
        return tcm;
    }

    private TableColumn getColumnFor(DisplayType type) {
        PersonCellRenderer r = BeanHelper.getBean(PersonCellRenderer.class);
        r.setType(type);
        return new YTableColumn(0, getColumnWidth(type), type.getDisplayName(), true, r, null);
    }
    
    private List<String> resultSet;

    @Override
    public void setResultSet(List<String> resultSet) {
        this.resultSet = resultSet;
    }

    private static class PersonIdTableModel extends MutableListTableModel<String> {

        public PersonIdTableModel() {
            super(new String[] {"Person ID"}, String.class);
        }

        @Override
        protected void setValueAt(Object aValue, String row, int columnIndex) {}

        @Override
        public Object getDataFromRow(String row, int column) {
            return row;
        }
    }

    @Override
    public List<String> getDataset(int start, int end) {
        return resultSet.subList(start, end);
    }

    @Override
    public int getPageSize() {
        return 12;
    }

    @Override
    public int getTotal() {
        return resultSet.size();
    }

    public void setColumnWidth(DisplayType type, int width) {
        COLUMN_WIDTHS.put(type, width);
    }

    static {
        DEFAULT_DISPLAY_TYPES = new LinkedList<DisplayType>();
        DEFAULT_DISPLAY_TYPES.add(DisplayType.SORT_NAME);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.NET_ID);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.BYU_ID);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.DATE_OF_BIRTH);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.GENDER);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.EMPLOYMENT_STATUS);
        DEFAULT_DISPLAY_TYPES.add(DisplayType.STUDENT_STATUS);
    }

    private static final int CHAR_WIDTH = 7;

    public int getColumnWidth(DisplayType type) {
        if (COLUMN_WIDTHS.containsKey(type)) {
            return COLUMN_WIDTHS.get(type);
        }

        int chars = 0;

        switch (type) {
            case SORT_NAME:
                chars = 35;
                break;
            case ADDRESS_EMG:
            case ADDRESS_MAL:
            case ADDRESS_PRM:
            case ADDRESS_RES:
            case ADDRESS_WRK:
                chars = 38;
                break;
            case BYU_ID:
            case NET_ID:
            case SSN:
                chars = 9;
                break;
            case EMAIL:
                chars = 30;
                break;
            case DATE_OF_BIRTH:
            case EMPLOYMENT:
            case EMPLOYMENT_STATUS:
            case STUDENT_STATUS:
            case PREFERRED_FIRST_NAME:
                chars = 12;
                break;
            case GENDER:
            case MARITAL_STATUS:
            case RELIGION:
                chars = 6;
                break;
            case PHONE_EMG:
            case PHONE_MAL:
            case PHONE_PRM:
            case PHONE_RES:
            case PHONE_WRK:
                chars = 12;
                break;
            case NAME_PREFIX:
            case NAME_SUFFIX:
                chars = 6;
                break;
            case REST_OF_NAME:
                chars = 15;
                break;
            case SURNAME:
                chars = 13;
                break;
        }
        return CHAR_WIDTH * chars;
    }

}
