package edu.byu.swing.components.table;

import edu.byu.swing.components.SteppedComboBox;
import edu.byu.swing.models.MutableListComboBoxModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 * A combobox-based cell editor.  Uses SteppedComboBox rather than a normal
 * JComboBox to improve display in tables.
 * @author jmooreoa
 * @param <T> type of options
 * @version 1.1.0
 * @since 1.0.0
 * @see SteppedComboBox
 */
public class ComboBoxCellEditor<T> extends DefaultCellEditor {
    
    private final SteppedComboBox<T> editor;

    /**
     * Creates an empty cell editor
     */
    public ComboBoxCellEditor() {
        super(getEditorComponent());
        editor = (SteppedComboBox<T>) editorComponent;
        editor.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                stopCellEditing();
            }
        });
        super.setClickCountToStart(2);
    }

    /**
     * Creates a cell editor with the specified options
     * @param options
     */
    public ComboBoxCellEditor(List<? extends T> options) {
        this();
        setOptions(options);
    }

    /**
     * Creates a cell editor with the specified options
     * @param options
     */
    public ComboBoxCellEditor(T[] options) {
        this();
        setOptions(options);
    }

	protected JComboBox getComboBox() {
		return editor;
	}

    private static <U> JComboBox getEditorComponent() {
        return new SteppedComboBox<U>();
    }

    /**
     * Sets options in combo box
     * @param options
     */
    public void setOptions(List<? extends T> options) {
        editor.setOptions(options);
    }

    /**
     * Sets options in combo box
     * @param options
     */
    public void setOptions(T[] options) {
        editor.setOptions(options);
    }

    /**
     * Returns the model for this editor.
     * @return the model for this editor
     */
    public MutableListComboBoxModel<T> getComboBoxModel() {
        return editor.getComboBoxModel();
    }

    /**
     * Clears the current selection
     */
    public void clearSelection() {
        editor.clearSelection();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setSelectedItem(value);
        int width = table.getColumnModel().getColumn(column).getWidth();
        int height = table.getRowHeight(row);
        editor.setPreferredSize(new Dimension(width, height));
        return editor;
    }



}
