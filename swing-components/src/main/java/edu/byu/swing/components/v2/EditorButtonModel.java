package edu.byu.swing.components.v2;

/**
 * Button model for an HTMLEditor.  If a method returns true, the corresponding
 * button will be displayed in the editor.
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see HTMLEditor
 * @see DefaultEditorButtonModel
 */
public interface EditorButtonModel {
    /**
     *
     * @return {@code true} if new button should be shown
     */
    public boolean showNewButton();
    /**
     *
     * @return {@code true} if new button should be shown
     */
    public boolean showOpenButton();
    /**
     *
     * @return {@code true} if new button should be shown
     */
    public boolean showSaveButton();
    /**
     *
     * @return {@code true} if new button should be shown
     */
    public boolean showPrintButton();
    /**
     * Note: The exit button is only shown if the editor is displayed in a
     * frame or a dialog.
     * @return {@code true} if new button should be shown
     */
    public boolean showExitButton();
}
