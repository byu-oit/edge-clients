package edu.byu.swing.components.v2;

import edu.byu.swing.components.v2.ui.BasicHtmlEditorUI;
import edu.byu.swing.components.v2.ui.HtmlEditorUI;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.io.*;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Editor for HTML documents.
 * @author jmooreoa
 * @version 2.0.0
 * @since 1.0.0
 */
public class HTMLEditor extends JComponent {

    private static final String uiClassId = "HtmlEditorUI";

    @Override
    public String getUIClassID() {
        return uiClassId;
    }

    public void setUI(HtmlEditorUI ui) {
        super.setUI(ui);
    }

    @Override
    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            setUI((HtmlEditorUI) UIManager.getUI(this));
        } else {
            setUI(BasicHtmlEditorUI.getUI(this));
        }
    }

    public HtmlEditorUI getUI() {
        return (HtmlEditorUI) ui;
    }
    protected List<HtmlEditorListener> listeners = new LinkedList<HtmlEditorListener>();
    protected EditorButtonModel buttonModel;

    public HTMLEditor() {
        this(new DefaultEditorButtonModel(false, false, false, true, true));
    }

    public HTMLEditor(EditorButtonModel model) {
        this.buttonModel = model;
        this.updateUI();
    }

    public boolean addEditorListener(HtmlEditorListener listener) {
        return listeners.add(listener);
    }

    public boolean removeEditorListener(HtmlEditorListener listener) {
        return listeners.remove(listener);
    }

    public EditorButtonModel getButtonModel() {
        return buttonModel;
    }

    public void setText(String text) {
        if (text == null || text.trim().isEmpty()) {
            text = DEFAULT_TEXT;
        }
        StringBuilder sb = new StringBuilder(text);
        if (sb.indexOf("<p") == -1 && sb.indexOf("<body>") != -1) {
            sb.insert(sb.indexOf("<body>") + 6, " <p style=\"margin-top: 0\">");
            sb.insert(sb.indexOf("</body>"), "</p> ");
            text = sb.toString();
        }
        text = text.replaceAll("\\n(\\s)*", "\n");
        text = text.replaceAll("<br(\\s)*(/)*>(</br>)*", "<p></p>")
                .replaceAll("&#160;", "&nbsp;")
                .replaceAll("  ", "&nbsp;&nbsp;");

        String pre = System.getProperty("htmleditor.showPreButton");
        if (pre != null && pre.equals("true") && !PRE_STYLESHEET.matcher(text).matches()) {
            text = text.replaceFirst("(?x)<head>", "<head><style type=\"text/css\">pre{font-family: \"Times New Roman\";}</style>");
        }

        getUI().setText(replaceTabs(text));
    }

    public String getText() {
        String text = replaceTabs(getUI().getText());
        return text;
    }

    public JMenuBar getMenuBar() {
        return getUI().generateMenuBar();
    }

    public JFrame getFrame() {
        return getUI().generateFrame();
    }

    public JFrame showFrame(Point pos, Dimension dim) {
        JFrame frame = getFrame();
        frame.setSize(dim);
        frame.setLocation(pos);
        frame.setVisible(true);
        return frame;
    }

    public JDialog showDialog(Frame parent, Dimension dim) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JDialog getDialog(Frame parent) {
        return getUI().generateDialog(parent);
    }

    public void loadDocument(HTMLDocument doc) {
        getUI().loadDocument(doc);
    }

    public void setEditable(boolean editable) {
        boolean old = isEditable();
        getUI().setEditable(editable);
        firePropertyChange("editable", old, editable);
    }

    public boolean isEditable() {
        return getUI().isEditable();
    }

    @Override
    public void setEnabled(boolean enabled) {
        getUI().enableComponents(enabled);
        super.setEnabled(enabled);
    }

    /**
     * Clears the editor.  Clear differs from setText(null) in that it does not
     * clear out the undo/redo cache.
     */
    public void clear() {
        getTextArea().setText(null);
    }

    public JEditorPane getTextArea() {
        return getUI().getTextComponent();
    }

    public void fireNewActionPerformed(EditorEvent editorEvent) {
        boolean go = true;
        for (HtmlEditorListener each : listeners) {
            go = go && each.newActionPerformed(editorEvent);
        }
        if (go) {
            doNew();
        }
    }

    public void fireOpenActionPerformed(EditorEvent editorEvent) {
        boolean go = true;
        for (HtmlEditorListener each : listeners) {
            go = go && each.openActionPerformed(editorEvent);
        }
        if (go) {
            doOpen();
        }
    }

    public void fireSaveActionPerformed(EditorEvent editorEvent) {
        boolean go = true;
        for (HtmlEditorListener each : listeners) {
            go = go && each.saveActionPerformed(editorEvent);
        }
        if (go) {
            doSave();
        }
    }

    public void firePrintActionPerformed(EditorEvent editorEvent) {
        boolean go = true;
        for (HtmlEditorListener each : listeners) {
            go = go && each.printActionPerformed(editorEvent);
        }
        if (go) {
            print();
        }
    }

    private static final Pattern PRE_STYLESHEET = Pattern.compile("(?x)pre\\{font-family:\"Times New Roman\"\\}");

    public void print() {
//        String input = getText();
//        Tidy t = new Tidy();
//        t.setXHTML(true);
//        t.setDropEmptyParas(false);
//        t.setDocType("strict");
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        t.parse(new ByteArrayInputStream(input.getBytes()), output);

        String text = getText();//output.toString();
        text = text.replaceAll("<p[^>]*>(\\s)*</p>", "<br />");

        System.out.println(text);
        try {
            File f = File.createTempFile("swingprint", ".pdf");
            f.deleteOnExit();
            OutputStream os = new FileOutputStream(f);
            ITextRenderer renderer = new ITextRenderer();
            
            renderer.setDocumentFromString(text);
            renderer.layout();
            renderer.createPDF(os, true);
            os.close();
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to print", ex);
        }
    }

    public void exit() {
        boolean go = true;
        EditorEvent editorEvent = new EditorEvent(this);
        for (HtmlEditorListener each : listeners) {
            go = go && each.exitActionPerformed(editorEvent);
        }
        if (go) {
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w != null) {
                w.setVisible(false);
            }
        }
    }
    private static final String DEFAULT_TEXT = "<html><head></head><body><p style=\"margin-top: 0; margin-bottom: 0\"></p></body></html>";

    private void doNew() {
        clear();
        setText(DEFAULT_TEXT);
    }
    private JFileChooser jfc;

    private JFileChooser getFileChooser() {
        if (jfc == null) {
            jfc = new JFileChooser();
        }
        return jfc;
    }

    private void doOpen() {
        int result = getFileChooser().showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        setText(null);
        EditorKit kit = getTextArea().getEditorKit();
        try {
            FileReader reader = new FileReader(getFileChooser().getSelectedFile());
            Document d = kit.createDefaultDocument();
            kit.read(reader, d, 0);
            getTextArea().setDocument(d);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not open document", ex);
        }
    }

    private void doSave() {
        int result = getFileChooser().showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            FileWriter writer = new FileWriter(getFileChooser().getSelectedFile());
            getTextArea().write(writer);
            writer.flush();
        } catch (Exception ex) {
            throw new IllegalStateException("Could not save document", ex);
        }
    }

    @Override
    public void firePropertyChange(String property, Object oldValue, Object newValue) {
        super.firePropertyChange(property, oldValue, newValue);
    }


    private static String replaceTabs(String str) {
        String[] split = str.split("\t");
        if (split.length < 2) {
            return str;
        }
        String toReturn = "";
        for (int i = 0; i < split.length; i++) {
            String toAppend = split[i];
            if (i != split.length - 1) {
                toAppend = toAppend + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            }
            toReturn = toReturn + toAppend;
        }
        return toReturn;
    }
}