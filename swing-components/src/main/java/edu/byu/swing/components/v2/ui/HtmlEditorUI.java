package edu.byu.swing.components.v2.ui;

import edu.byu.swing.components.v2.HTMLEditor;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.html.HTMLDocument;

/**
 * UI for HTMLEditor
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see HTMLEditor
 * @see BasicHtmlEditorUI
 */
public abstract class HtmlEditorUI extends ComponentUI {
    public abstract JEditorPane getTextComponent();

    public abstract String getText();

    public abstract void setText(String text);

    public abstract JMenuBar generateMenuBar();

    public abstract JFrame generateFrame();

    public abstract JDialog generateDialog(Frame parent);

    public abstract void loadDocument(HTMLDocument doc);

    public abstract void setEditable(boolean editable);

    public abstract void enableComponents(boolean enabled);

    public abstract boolean isEditable();
}
