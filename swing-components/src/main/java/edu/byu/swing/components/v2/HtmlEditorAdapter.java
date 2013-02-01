package edu.byu.swing.components.v2;

/**
 * Default implementation of HtmlEditorListener.
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 */
public abstract class HtmlEditorAdapter implements HtmlEditorListener {

    /**
     * {@inheritDoc }
     */
    public boolean newActionPerformed(EditorEvent e) {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean saveActionPerformed(EditorEvent e) {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean openActionPerformed(EditorEvent e) {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean printActionPerformed(EditorEvent e) {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean exitActionPerformed(EditorEvent e) {
        return true;
    }
}
