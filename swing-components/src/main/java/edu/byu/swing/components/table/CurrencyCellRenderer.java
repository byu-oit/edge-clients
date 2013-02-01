package edu.byu.swing.components.table;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jmooreoa
 */
public class CurrencyCellRenderer extends SubstanceDefaultTableCellRenderer {
    private static final NumberFormat format = NumberFormat.getCurrencyInstance();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Number) {
            value = format.format(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }


}
