package edu.byu.swing.components.table;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * A TableCellEditor that only allows number entry.
 * @date Apr 23, 2009
 * @author bradle2
 * @version 1.0.0
 * @since 1.0.0
 * @see TextCellEditor
 */
public class NumberCellEditor extends TextCellEditor {

    /**
     * Creates a cell editor that only allows users to enter digits
     * @param limit maximum size of contents
     */
    public NumberCellEditor() {
        super(generateField());
    }

    private static JTextField generateField() {
        JTextField field = new JTextField();

        //This key listener only allows digits to be added to the TextField's text
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.setKeyChar('\0');
                }
            }
        });
        return field;
    }
}
