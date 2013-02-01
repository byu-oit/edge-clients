package edu.byu.swing.components.v2;

/**
 * Listener for the HTMLEditor.  Each method should return {@code true} if the
 * default action should proceed.  The editor will poll all registered listeners,
 * then, if none have blocked the default, it will run the default action associated
 * with the event.
 * <p>
 * <b>Default Actions:</b>
 * <table><tr><th>Event</th><th>Action</th></tr>
 * <tr><td>New</td><td>Clears out old document and replaces it with a blank one.</td></tr>
 * <tr><td>Save</td><td>Opens a Save File dialog</td></tr>
 * <tr><td>Open</td><td>Opens an Open File dialog</td></tr>
 * <tr><td>Exit</td><td>Closes open dialog/window</td></tr>
 * </table>
 *
 * <b>This implementation is subject to change</b>
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see HTMLEditor
 * @see EditorEvent
 */
public interface HtmlEditorListener {

    /**
     * Called when user clicks "New" button.
     * @param e
     * @return {@code true} if default new action should occur
     */
    public boolean newActionPerformed(EditorEvent e);

    /**
     * Called when user clicks "Save" button.
     * @param e
     * @return {@code true} if default save action should occur
     */
    public boolean saveActionPerformed(EditorEvent e);

    /**
     * Called when user clicks "Open" button.
     * @param e
     * @return {@code true} if default open action should occur
     */
    public boolean openActionPerformed(EditorEvent e);

    /**
     * Called when user clicks the "Print" button
     * @param e
     * @return {@code true} if default print action should occur
     */
    public boolean printActionPerformed(EditorEvent e);

    /**
     * Returns {@code true} if exit should be allowed to proceed.
     * @param e
     * @return
     */
    public boolean exitActionPerformed(EditorEvent e);
}
