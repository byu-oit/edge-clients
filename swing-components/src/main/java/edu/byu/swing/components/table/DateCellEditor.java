package edu.byu.swing.components.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.plaf.basic.BasicDatePickerUI;

/**
 * This class allows tables to use SwingX DatePickers in Table Cells.
 * Made especially for the YTable.  Fixes some focus issues in tables-forces cell
 * to stop editing when user clicks off of editor.
 * 
 * @date Apr 14, 2009
 * @author bradle2
 * @version 1.0.0
 * @since 1.0.0
 * @see JXDatePicker
 * @see DateCellRenderer
 * @see YTable
 * @see DateFormat
 */
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JXDatePicker editorComponent;

    /**
     * Creates a new DateCellEditor
     */
    public DateCellEditor() {
        editorComponent = new JXDatePicker();

        editorComponent.setUI(new DatePickerTableUI());

        editorComponent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                DateCellEditor.this.stopCellEditing();
                DateCellEditor.this.pickerShowing = false;
            }
        });
    }

    /**
     * Creates a new DateCellEditor and installs the specified formats.
     * @param formats An array of not-null formats to be used.  A null array will use the default formats.
     * @throws NullPointerException if any of the array contents are null
     */
    public DateCellEditor(DateFormat...formats) {
        this();
        editorComponent.setFormats(formats);
    }

    /**
     * Creates a new DateCellEditor and installs the specified formats.
     * @param formats An array of not-null formats to be used.  A null array will use the default formats.
     * @throws NullPointerException if any of the array contents are null
     */
    public DateCellEditor(String...formats) {
        this();
        editorComponent.setFormats(formats);
    }

    /**
     * {@inheritDoc }
     */
    public Object getCellEditorValue() {        
        return editorComponent.getDate();
    }

    /**
     * {@inheritDoc }
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editorComponent.setDate((Date) value);
        editorComponent.requestFocus();
        return editorComponent;
    }
    
    private boolean pickerShowing = false;

    private class DatePickerTableUI extends BasicDatePickerUI implements ActionListener {


        @Override
        protected JButton createPopupButton() {
            JButton button = super.createPopupButton();
            button.addActionListener(this);
            return button;
        }

        public void actionPerformed(ActionEvent e) {
            pickerShowing = !pickerShowing;
            if (!pickerShowing) {
                stopCellEditing();
            }
        }
    }
}
