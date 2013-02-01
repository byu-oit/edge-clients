package edu.byu.swing.components.v2;

/**
 * Default implementation of EditorButtonModel.  The set methods return the current
 * instance of the model to allow method chaining.
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see EditorButtonModel
 * @see HTMLEditor
 */
public class DefaultEditorButtonModel implements EditorButtonModel {

    private boolean showNew;
    private boolean showOpen;
    private boolean showSave;
    private boolean showPrint;
    private boolean showExit;

    /**
     *
     * @param showNew
     * @param showOpen
     * @param showSave
     * @param showPrint
     */
    public DefaultEditorButtonModel(boolean showNew, boolean showOpen, boolean showSave, boolean showPrint) {
        this.showNew = showNew;
        this.showOpen = showOpen;
        this.showSave = showSave;
        this.showPrint = showPrint;
    }

    /**
     *
     * @param showNew
     * @param showOpen
     * @param showSave
     * @param showPrint
     * @param showExit
     */
    public DefaultEditorButtonModel(boolean showNew, boolean showOpen, boolean showSave, boolean showPrint, boolean showExit) {
        this.showNew = showNew;
        this.showOpen = showOpen;
        this.showSave = showSave;
        this.showPrint = showPrint;
        this.showExit = showExit;
    }

    public DefaultEditorButtonModel() {
    }

    /**
     *
     * @param showExit
     * @return this
     */
    public DefaultEditorButtonModel setShowExit(boolean showExit) {
        this.showExit = showExit;
        return this;
    }

    /**
     *
     * @param showNew
     * @return this
     */
    public DefaultEditorButtonModel setShowNew(boolean showNew) {
        this.showNew = showNew;
        return this;
    }

    /**
     *
     * @param showOpen
     * @return this
     */
    public DefaultEditorButtonModel setShowOpen(boolean showOpen) {
        this.showOpen = showOpen;
        return this;
    }

    /**
     *
     * @param showPrint
     * @return this
     */
    public DefaultEditorButtonModel setShowPrint(boolean showPrint) {
        this.showPrint = showPrint;
        return this;
    }

    /**
     *
     * @param showSave
     * @return this
     */
    public DefaultEditorButtonModel setShowSave(boolean showSave) {
        this.showSave = showSave;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    public boolean showNewButton() {
        return showNew;
    }

    /**
     * {@inheritDoc }
     */
    public boolean showOpenButton() {
        return showOpen;
    }

    /**
     * {@inheritDoc }
     */
    public boolean showSaveButton() {
        return showSave;
    }

    /**
     * {@inheritDoc }
     */
    public boolean showPrintButton() {
        return showPrint;
    }

    /**
     * {@inheritDoc }
     */
    public boolean showExitButton() {
        return showExit;
    }
}
