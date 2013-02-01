package edu.byu.swing.components.table;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Cell editor that will only allow a certain number of numeric characters.
 * @author jmooreoa
 * @see LimitedTextCellEditor
 * @see NumberCellEditor
 * @version 1.0.0
 * @since 1.0.0
 */
public class LimitedNumberCellEditor extends LimitedTextCellEditor {
    /**
     * Creates a new LimitedNumberCellEditor with the specified length
     * @param limit maximum allowed characters
     */
    public LimitedNumberCellEditor(int limit) {
        super(limit);
        super.getField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.setKeyChar('\0');
                }
            }
        });
    }
}
