package edu.byu.swing.components.v2;

/**
 * Event class for all HTMLEditor events
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see HTMLEditor
 */
public class EditorEvent {
    private final HTMLEditor source;
    private final long when;

    public EditorEvent(HTMLEditor source) {
        this.source = source;
        this.when = System.currentTimeMillis();
    }

    public EditorEvent(HTMLEditor source, long when) {
        this.source = source;
        this.when = when;
    }

    public HTMLEditor getSource() {
        return source;
    }

    public long getWhen() {
        return when;
    }

}
