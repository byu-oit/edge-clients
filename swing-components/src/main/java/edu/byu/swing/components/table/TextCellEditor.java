package edu.byu.swing.components.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A text-based cell editor with fixes for focus bugs in JTable.  Default editor
 * for {@code String} objects in {@code YTable}.
 *
 * KNOWN BUGS: Does not allow partial selection of text in editor-it's all or nothing.
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.0.0
 * @see YTable
 */
public class TextCellEditor extends DefaultCellEditor implements MouseListener, MouseMotionListener {
    private final JTextField field;

    /**
     * Creates a new TextCellEditor
     */
    public TextCellEditor () {
        this(new JTextField());
    }

    /**
     * Creates a new TextCellEditor using the specified JTextField
     * @param comp
     */
    public TextCellEditor(JTextField field) {
        super(field);
        this.field = field;
        field.addFocusListener(new FocusListener() {
            Component glassPane;
            public void focusGained(FocusEvent e) {
                glassPane = SwingUtilities.getRootPane(TextCellEditor.this.field).getGlassPane();
                glassPane.addMouseListener(TextCellEditor.this);
                glassPane.addMouseMotionListener(TextCellEditor.this);
                glassPane.setVisible(true);
            }
            public void focusLost(FocusEvent e) {
                stopCellEditing();
                glassPane.removeMouseListener(TextCellEditor.this);
                glassPane.removeMouseMotionListener(TextCellEditor.this);
                glassPane.setVisible(false);
            }
        });
    }

    /**
     * Returns field.  For use by subclasses
     * @return field
     */
    protected JTextField getField() {
        return field;
    }

    /**
     * {@inheritDoc }
     */
    public void mouseClicked(MouseEvent e) {
        if (!fieldContainsClick(e)) {
            field.transferFocus();
        } else {
            MouseEvent e2 = new MouseEvent(field, e.getID() + 12, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
            for (MouseListener each : field.getMouseListeners()) {
                each.mouseClicked(e2);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    public void mousePressed(MouseEvent e) {
        if (!fieldContainsClick(e)) {
            field.transferFocus();
        } else {
            MouseEvent e2 = new MouseEvent(field, e.getID() + 12, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
            for (MouseListener each : field.getMouseListeners()) {
                each.mousePressed(e2);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    public void mouseReleased(MouseEvent e) {
        if (!fieldContainsClick(e)) {
            field.transferFocus();
        } else {
            MouseEvent e2 = new MouseEvent(field, e.getID() + 12, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
            for (MouseListener each : field.getMouseListeners()) {
                each.mouseReleased(e2);
            }
        }
    }

    private boolean fieldContainsClick(MouseEvent e) {
        Point loc = field.getLocationOnScreen();
        Dimension d = field.getBounds().getSize();
        Rectangle r = new Rectangle(loc, d);
        return r.contains(e.getLocationOnScreen());
    }

    /**
     * {@inheritDoc }
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * {@inheritDoc }
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * {@inheritDoc }
     */
    public void mouseDragged(MouseEvent e) {
        if (fieldContainsClick(e)) {
            Point pt = e.getLocationOnScreen();
            Point loc = field.getLocationOnScreen();
            pt.translate((int)-loc.getX(), (int)-loc.getY());

            System.out.println(pt);
            System.out.println(field.viewToModel(e.getPoint()));

            field.moveCaretPosition(field.viewToModel(e.getPoint()));
            MouseEvent e2 = new MouseEvent(field, e.getID() + 12, e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
            for (MouseMotionListener each : field.getMouseMotionListeners()) {
                each.mouseDragged(e2);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    public void mouseMoved(MouseEvent e) {
    }

}
