package edu.byu.swing.components.table;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell render for dates.  Allows setting of a custom formatter.  Is the default
 * renderer for {@code Date} objects in {@code YTable}
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 * @see DateCellEditor
 * @see DateFormat
 * @see edu.byu.swing.components.table.YTable
 */
public class DateCellRenderer extends SubstanceDefaultTableCellRenderer {

    private DateFormat format;

    /**
     * Creates a new DateCellRenderer with default date format
     */
    public DateCellRenderer() {
        this(DateFormat.getDateInstance(DateFormat.MEDIUM));
    }

    /**
     * Creates a new DateCellRenderer with the specified format
     * @param format format to use for display
     */
    public DateCellRenderer(DateFormat format) {
        this.format = format;
    }

    /**
     * Gets current formatter
     * @return current format
     */
    public DateFormat getFormat() {
        return format;
    }

    /**
     * Sets the formatter for the renderer to use
     * @param format format to use
     */
    public void setFormat(DateFormat format) {
        this.format = format;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Date date = (Date) value;
        Object newValue = value == null ? null : format.format(date);
        return super.getTableCellRendererComponent(table, newValue, isSelected, hasFocus, row, column);
    }
}
