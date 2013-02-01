package edu.byu.swing.components.table;

import edu.byu.swing.components.LimitedTextField;
import javax.swing.JTextField;

/**
 * Cell editor that limits length of text
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 */
public class LimitedTextCellEditor extends TextCellEditor {
    /**
     * Creates a cell editor with limited length
     * @param limit maximum size of contents
     */
    public LimitedTextCellEditor(int limit) {
        super(generateField(limit));
    }

    private static JTextField generateField(int limit) {
        LimitedTextField field = new LimitedTextField(limit);
        return field;
    }
}
