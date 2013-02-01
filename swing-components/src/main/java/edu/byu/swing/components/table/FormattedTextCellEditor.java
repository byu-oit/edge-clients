package edu.byu.swing.components.table;

import java.awt.event.MouseEvent;
import java.text.Format;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JTextField;

/**
 * A cell editor using a JFormattedTextField.  Blocks mouse events if the edit
 * is invalid.
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormattedTextCellEditor extends TextCellEditor {

    private final JFormattedTextField field;

    /**
     * Creates a new FormattedTextCellEditor with the specified formatterFactory and default value
     * @param factory formatter to use
     * @param defaultValue default display value
     */
    public FormattedTextCellEditor(AbstractFormatterFactory factory, Object defaultValue) {
        super(getEditor(factory, defaultValue));
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Creates a new FormattedTextCellEditor with the specified formatterFactory
     * @param factory formatter to use
     */
    public FormattedTextCellEditor(AbstractFormatterFactory factory) {
        super(getEditor(factory));
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Creates a new FormattedTextCellEditor with the specified formatter
     * @param formatter formatter to use
     */
    public FormattedTextCellEditor(AbstractFormatter formatter) {
        super(getEditor(formatter));
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Creates a new FormattedTextCellEditor with the specified format
     * @param format format to use
     */
    public FormattedTextCellEditor(Format format) {
        super(getEditor(format));
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Creates a new FormattedTextCellEditor with the specified default value,
     * which will also be used to create a format for the editor.
     * @param value
     */
    public FormattedTextCellEditor(Object value) {
        super(getEditor(value));
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Creates a new FormattedTextCellEditor with defined format
     */
    public FormattedTextCellEditor() {
        super(getEditor());
        field = (JFormattedTextField) super.editorComponent;
    }

    /**
     * Sets the formatter factory
     * @param tf new formatterFactory
     */
    public void setFormatterFactory(AbstractFormatterFactory tf) {
        field.setFormatterFactory(tf);
    }

    public static final int COMMIT = JFormattedTextField.COMMIT, COMMIT_OR_REVERT = JFormattedTextField.COMMIT_OR_REVERT,
        PERSIST = JFormattedTextField.PERSIST, REVERT = JFormattedTextField.REVERT;

    /**
     * Sets the focus lost behavior of the JFormattedTextField
     * @param behavior new JFormattedTextField behavior
     * @see JFormattedTextField.setFocusLostBehavior(int)
     */
    public void setFocusLostBehavior(int behavior) {
        field.setFocusLostBehavior(behavior);
    }

    /**
     * Gets current formatterFactory
     * @return current formatterFactory
     */
    public AbstractFormatterFactory getFormatterFactory() {
        return field.getFormatterFactory();
    }

    /**
     * Gets current formatter
     * @return current formatter
     */
    public AbstractFormatter getFormatter() {
        return field.getFormatter();
    }

    /**
     * Returns current focus lost behavior of the JFormattedTextField
     * @return current focus lost behavior
     */
    public int getFocusLostBehavior() {
        return field.getFocusLostBehavior();
    }

    /**
     * Commits the current edit
     * @throws java.text.ParseException if edit is invalid
     */
    public void commitEdit() throws ParseException {
        field.commitEdit();
    }

//Static helper methods for initialization
    private static JTextField getEditor() {
        return new JFormattedTextField();
    }

    private static JTextField getEditor(AbstractFormatterFactory factory, Object currentValue) {
        return new JFormattedTextField(factory, currentValue);
    }

    private static JTextField getEditor(AbstractFormatterFactory factory) {
        return new JFormattedTextField(factory);
    }

    private static JTextField getEditor(AbstractFormatter formatter) {
        return new JFormattedTextField(formatter);
    }

    private static JTextField getEditor(Format format) {
        return new JFormattedTextField(format);
    }

    private static JTextField getEditor(Object value) {
        return new JFormattedTextField(value);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (field.isEditValid()) {
            super.mouseClicked(e);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (field.isEditValid()) {
            super.mousePressed(e);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (field.isEditValid()) {
            super.mouseReleased(e);
        }
    }
}
