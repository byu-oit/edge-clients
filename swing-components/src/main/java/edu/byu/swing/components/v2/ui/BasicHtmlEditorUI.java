package edu.byu.swing.components.v2.ui;

import edu.byu.framework.swing.util.BYUTask;
import edu.byu.swing.components.v2.EditorButtonModel;
import edu.byu.swing.components.v2.EditorEvent;
import edu.byu.swing.components.v2.HTMLEditor;
import edu.byu.swing.icons.*;
import edu.byu.swing.models.MutableListComboBoxModel;
import edu.byu.swing.utilities.MenuBuilder;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.swing.text.StyledEditorKit.FontSizeAction;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTMLDocument;
import javax.swing.undo.UndoManager;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXEditorPane;

/**
 * Default implementation of the HtmlEditorUI
 * @author jmooreoa
 * @version 1.0.0
 * @since 1.2.0
 * @see HTMLEditor
 */
public class BasicHtmlEditorUI extends HtmlEditorUI implements MouseListener, PropertyChangeListener {

    //<editor-fold defaultstate="collapsed" desc="Constants">
    private static final String BOLD_KEY = "font-bold";
    private static final String CENTER_ALIGN_KEY = "center-justify";
    private static final String COPY_KEY = "copy";
    private static final String CUT_KEY = "cut";
    private static final String EXIT_KEY = "exit";
    private static final String FONT_FAMILY_KEY = "htmlEditor-fontFamily";
    private static final String FONT_SIZE_KEY = "htmlEditor-fontSize";
    private static final String ITALIC_KEY = "font-italic";
    private static final String LEFT_ALIGN_KEY = "left-justify";
    private static final String NEW_KEY = "new";
    private static final String OPEN_KEY = "open";
    private static final String PARAGRAPH_SELECTOR_KEY = "htmlEditor-paraSelect";
    private static final String PASTE_KEY = "paste";
    private static final String PRE_KEY = "InsertPre";
    private static final String PRINT_KEY = "print";
    private static final String REDO_KEY = "redo";
    private static final String RIGHT_ALIGN_KEY = "right-justify";
    private static final String SAVE_KEY = "save";
    private static final String UNDERLINE_KEY = "font-underline";
    private static final String UNDO_KEY = "undo";
    //</editor-fold>
    
    private final ActionMap actionMap = new ActionMap();
    protected HTMLEditor editor;
    protected JXEditorPane pane;
    private MutableListComboBoxModel<String> fontModel;
    private MutableListComboBoxModel<String> fontSizeModel;
    private ButtonGroup alignButtonGroup;
    protected MigLayout layout;
    private UndoManager undoer;
    private boolean installed;
    
    private final List<JComponent> COMPONENTS = new LinkedList<JComponent>();

    private static final Logger LOG = Logger.getLogger(BasicHtmlEditorUI.class);

    private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor TEXT_CURSOR = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);

    private static final Map<HTMLEditor, BasicHtmlEditorUI> UIS = new HashMap<HTMLEditor, BasicHtmlEditorUI>();

    public static BasicHtmlEditorUI getUI(HTMLEditor editor) {
        BasicHtmlEditorUI ui = UIS.get(editor);
        if (ui == null) {
            ui = new BasicHtmlEditorUI();
            UIS.put(editor, ui);
        }
        return ui;
    }

    protected BasicHtmlEditorUI() {}

    //<editor-fold defaultstate="collapsed" desc="Installation">
    @Override
    public void installUI(JComponent c) {
        this.editor = (HTMLEditor) c;
        installModels();
        installLayout();
        createTextComponent();
        fixActions();
        installDefaults();
        installPopupMenu();
        installComponents();
        installListeners();
        installed = true;
    }

    private void installModels() {
        fontModel = new MutableListComboBoxModel<String>();
        fontSizeModel = new MutableListComboBoxModel<String>();
    }

    private void installLayout() {
        this.layout = new MigLayout(new LC().fill(), new AC().fill(), new AC().fill());
        editor.setLayout(layout);
    }

    private void createTextComponent() {
        this.pane = new JXEditorPane();
        this.pane.setContentType("text/html");
        JScrollPane sp = new JScrollPane(pane);
        COMPONENTS.add(sp);
        editor.add(sp);
        this.pane.addMouseListener(this);
        pane.getDocument().addUndoableEditListener(new EditListener());
        pane.addCaretListener(new CaretSelectionListener());
    }

    private void installDefaults() {
    }

    private void installPopupMenu() {
        Object[] popup = new Object[6];
        popup[0] = getAction(CUT_KEY);
        popup[1] = getAction(COPY_KEY);
        popup[2] = getAction(PASTE_KEY);
        popup[3] = "";//Separator
        popup[4] = getAction(UNDO_KEY);
        popup[5] = getAction(REDO_KEY);

        pane.setComponentPopupMenu(MenuBuilder.buildPopup("Editing", popup));
    }

    private void installComponents() {
        installDefaultToolbars();
    }

    private void installListeners() {
        undoer = new UndoManager();
        pane.addPropertyChangeListener("text", this);
    }

    private JComboBox getParagraphSelector() {
        JComboBox paraSelector = pane.getParagraphSelector();
        paraSelector.setMaximumSize(new java.awt.Dimension(100, 21));
        paraSelector.setMinimumSize(new java.awt.Dimension(50, 18));
        paraSelector.setPreferredSize(new java.awt.Dimension(100, 20));
        paraSelector.setAction(getAction(PARAGRAPH_SELECTOR_KEY));
        return paraSelector;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Unistallation">

    @Override
    public void uninstallUI(JComponent c) {
        installed = false;
        uninstallLayout(c);
        uninstallListeners(c);
        uninstallDefaults(c);
        uninstallActions(c);
        uninstallModels();

        uninstallComponents(c);

        super.uninstallUI(c);

        this.editor = null;
    }

    private void uninstallListeners(JComponent c) {
        undoer = null;
    }

    private void uninstallModels() {
        fontModel = null;
        fontSizeModel = null;
        alignButtonGroup = null;
    }

    private void uninstallComponents(JComponent c) {
        for (JComponent each : COMPONENTS) {
            c.remove(each);
        }
        COMPONENTS.clear();
        c.setComponentPopupMenu(null);
        this.pane = null;
    }

    private void uninstallLayout(JComponent c) {
        c.setLayout(null);
        this.layout = null;
    }

    private void uninstallDefaults(JComponent c) {

    }

    private void uninstallActions(JComponent c) {
        actionMap.clear();
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overrides">
    @Override
    public JEditorPane getTextComponent() {
        return pane;
    }

    @Override
    public String getText() {
        return pane.getText();
    }

    @Override
    public void setText(String text) {
        pane.setDocument(pane.getEditorKit().createDefaultDocument());
        pane.setText(text);
        undoer.discardAllEdits();
    }

    @Override
    public JMenuBar generateMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file;
        JMenu edit;
        JMenu font = new JMenu("Font");

        List<Object> f = new LinkedList<Object>();
        List<Object> e = new LinkedList<Object>();

        EditorButtonModel model = editor.getButtonModel();
        if (model.showNewButton()) {
            f.add(getAction(NEW_KEY));
        }
        if (model.showOpenButton()) {
            f.add(getAction(OPEN_KEY));
        }
        if (model.showSaveButton()) {
            f.add(getAction(SAVE_KEY));
        }
        if (model.showPrintButton()) {
            if (!f.isEmpty()) {
                f.add("");//Separator
            }
            f.add(getAction(PRINT_KEY));
        }
        if (model.showExitButton()) {
            if (!f.isEmpty()) {
                f.add("");//Separator
            }
            f.add(getAction(EXIT_KEY));
        }

        e.add(getAction(UNDO_KEY));
        e.add(getAction(REDO_KEY));
        e.add("");//Separator
        e.add(getAction(CUT_KEY));
        e.add(getAction(COPY_KEY));
        e.add(getAction(PASTE_KEY));

        font.add(new JCheckBoxMenuItem(getAction(BOLD_KEY)));
        font.add(new JCheckBoxMenuItem(getAction(ITALIC_KEY)));
        font.add(new JCheckBoxMenuItem(getAction(UNDERLINE_KEY)));

        file = MenuBuilder.buildMenu(f, "File");
        edit = MenuBuilder.buildMenu(e, "Edit");

        file.setMnemonic(KeyEvent.VK_F);
        file.setMnemonic(KeyEvent.VK_E);
        file.setMnemonic(KeyEvent.VK_O);

        if (!f.isEmpty()) {
            bar.add(file);
        }
        bar.add(edit);
        bar.add(font);

        return bar;
    }

    @Override
    public JFrame generateFrame() {
        JFrame frame = new JFrame("Editor");
        frame.setIconImages(IconHelper.getImageList(Applications.ACCESSORIES_TEXT_EDITOR));
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(editor);
        frame.setJMenuBar(editor.getMenuBar());
        return frame;
    }

    @Override
    public JDialog generateDialog(Frame parent) {
        JDialog dialog = new JDialog(parent, "Editor");
        dialog.setJMenuBar(generateMenuBar());
        dialog.add(editor);
        return dialog;
    }

    @Override
    public void loadDocument(HTMLDocument doc) {
        this.pane.setDocument(doc);
        undoer.discardAllEdits();
    }

    private static final Set<String> ALL_KEYS = new HashSet<String>();
    private static final Set<String> EDITABLE = new HashSet<String>();

    static {
        ALL_KEYS.add(NEW_KEY);
        ALL_KEYS.add(OPEN_KEY);
        ALL_KEYS.add(SAVE_KEY);
        ALL_KEYS.add(PRINT_KEY);
        ALL_KEYS.add(CUT_KEY);
        ALL_KEYS.add(COPY_KEY);
        ALL_KEYS.add(PASTE_KEY);
        ALL_KEYS.add(BOLD_KEY);
        ALL_KEYS.add(ITALIC_KEY);
        ALL_KEYS.add(UNDERLINE_KEY);
        ALL_KEYS.add(UNDO_KEY);
        ALL_KEYS.add(REDO_KEY);
        ALL_KEYS.add(RIGHT_ALIGN_KEY);
        ALL_KEYS.add(CENTER_ALIGN_KEY);
        ALL_KEYS.add(LEFT_ALIGN_KEY);
        ALL_KEYS.add(FONT_FAMILY_KEY);
        ALL_KEYS.add(FONT_SIZE_KEY);
        ALL_KEYS.add(PARAGRAPH_SELECTOR_KEY);
        ALL_KEYS.add(EXIT_KEY);

        EDITABLE.addAll(ALL_KEYS);
        EDITABLE.remove(PRINT_KEY);
        EDITABLE.remove(COPY_KEY);
        EDITABLE.remove(EXIT_KEY);
    }

    private boolean editable = true;

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
        boolean enable = editable && editor.isEnabled();//If editor is disabled, don't enable any buttons
        for (String each : EDITABLE) {
            getAction(each).setEnabled(enable);
        }
        pane.setEditable(editable);
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void enableComponents(boolean enabled) {
        boolean shouldEnable = enabled && isEditable();
        for (String each : ALL_KEYS) {
            boolean notAffectedByEditable = !EDITABLE.contains(each);
            getAction(each).setEnabled((notAffectedByEditable) ? enabled : shouldEnable);
        }
        pane.setEnabled(enabled);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (pane.equals(evt.getSource()) && evt.getPropertyName().equals("text")) {
            editor.firePropertyChange("text", evt.getOldValue(), evt.getNewValue());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Actions">
    private static void invokeAction(final ActionListener a, final ActionEvent e, final JTextComponent pane) {
        pane.requestFocusInWindow();
        e.setSource(pane);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                a.actionPerformed(e);
            }
        });
    }

    private void fixActions() {
        actionMap.setParent(pane.getActionMap());

        for (String each : ACTIONS_TO_FIX) {
            actionMap.put(each, getHelper(each));
        }
        addActions();
    }

    private static final Set<String> ACTIONS_TO_FIX = new HashSet<String>();

    private static final Map<String, String> NAMES = new HashMap<String, String>();
    private static final Map<String, IconSet> ICONS = new HashMap<String, IconSet>();
    private static final Map<String, Integer> MNEMONICS = new HashMap<String, Integer>();
    private static final Map<String, Integer> ACCELERATORS = new HashMap<String, Integer>();
    private static final Map<String, String> TOOLTIPS = new HashMap<String, String>();

    static {
        ACTIONS_TO_FIX.add(CUT_KEY);
        NAMES.put(CUT_KEY, "Cut");
        ICONS.put(CUT_KEY, Actions.EDIT_CUT);
        MNEMONICS.put(CUT_KEY, KeyEvent.VK_T);
        ACCELERATORS.put(CUT_KEY, KeyEvent.VK_CUT);
        TOOLTIPS.put(CUT_KEY, "Cut (Ctrl+X)");

        ACTIONS_TO_FIX.add(COPY_KEY);
        NAMES.put(COPY_KEY, "Copy");
        ICONS.put(COPY_KEY, Actions.EDIT_COPY);
        MNEMONICS.put(COPY_KEY, KeyEvent.VK_C);
        ACCELERATORS.put(COPY_KEY, KeyEvent.VK_COPY);
        TOOLTIPS.put(COPY_KEY, "Copy (Ctrl+C)");

        ACTIONS_TO_FIX.add(PASTE_KEY);
        NAMES.put(PASTE_KEY, "Paste");
        ICONS.put(PASTE_KEY, Actions.EDIT_PASTE);
        MNEMONICS.put(PASTE_KEY, KeyEvent.VK_P);
        ACCELERATORS.put(PASTE_KEY, KeyEvent.VK_PASTE);
        TOOLTIPS.put(PASTE_KEY, "Paste (Ctrl+V)");

        ACTIONS_TO_FIX.add(UNDO_KEY);
        NAMES.put(UNDO_KEY, "Undo");
        ICONS.put(UNDO_KEY, Actions.EDIT_UNDO);
        MNEMONICS.put(UNDO_KEY, KeyEvent.VK_U);
        ACCELERATORS.put(UNDO_KEY, KeyEvent.VK_UNDO);
        TOOLTIPS.put(UNDO_KEY, "Undo (Ctrl+Z)");

        ACTIONS_TO_FIX.add(REDO_KEY);
        NAMES.put(REDO_KEY, "Redo");
        ICONS.put(REDO_KEY, Actions.EDIT_REDO);
        MNEMONICS.put(REDO_KEY, KeyEvent.VK_R);
        ACCELERATORS.put(REDO_KEY, KeyEvent.VK_Y);
        TOOLTIPS.put(REDO_KEY, "Redo (Ctrl+Y)");

        ACTIONS_TO_FIX.add(LEFT_ALIGN_KEY);
        NAMES.put(LEFT_ALIGN_KEY, "Align Left");
        ICONS.put(LEFT_ALIGN_KEY, Actions.FORMAT_JUSTIFY_LEFT);
        TOOLTIPS.put(LEFT_ALIGN_KEY, "Align Left");

        ACTIONS_TO_FIX.add(CENTER_ALIGN_KEY);
        NAMES.put(CENTER_ALIGN_KEY, "Center");
        ICONS.put(CENTER_ALIGN_KEY, Actions.FORMAT_JUSTIFY_CENTER);
        TOOLTIPS.put(CENTER_ALIGN_KEY, "Center");

        ACTIONS_TO_FIX.add(RIGHT_ALIGN_KEY);
        NAMES.put(RIGHT_ALIGN_KEY, "Align Right");
        ICONS.put(RIGHT_ALIGN_KEY, Actions.FORMAT_JUSTIFY_RIGHT);
        TOOLTIPS.put(RIGHT_ALIGN_KEY, "Align Right");

        ACTIONS_TO_FIX.add(BOLD_KEY);
        NAMES.put(BOLD_KEY, "Bold");
        ICONS.put(BOLD_KEY, Actions.FORMAT_TEXT_BOLD);
        MNEMONICS.put(BOLD_KEY, KeyEvent.VK_B);
        ACCELERATORS.put(BOLD_KEY, KeyEvent.VK_B);
        TOOLTIPS.put(BOLD_KEY, "Bold (Ctrl+B)");

        ACTIONS_TO_FIX.add(ITALIC_KEY);
        NAMES.put(ITALIC_KEY, "Italic");
        ICONS.put(ITALIC_KEY, Actions.FORMAT_TEXT_ITALIC);
        MNEMONICS.put(ITALIC_KEY, KeyEvent.VK_I);
        ACCELERATORS.put(ITALIC_KEY, KeyEvent.VK_I);
        TOOLTIPS.put(ITALIC_KEY, "Italicize (Ctrl+I)");

        ACTIONS_TO_FIX.add(UNDERLINE_KEY);
        NAMES.put(UNDERLINE_KEY, "Underline");
        ICONS.put(UNDERLINE_KEY, Actions.FORMAT_TEXT_UNDERLINE);
        MNEMONICS.put(UNDERLINE_KEY, KeyEvent.VK_U);
        ACCELERATORS.put(UNDERLINE_KEY, KeyEvent.VK_U);
        TOOLTIPS.put(UNDERLINE_KEY, "Underline (Ctrl+U)");

        ACTIONS_TO_FIX.add(PRE_KEY);
        NAMES.put(PRE_KEY, "Free Formatting");
    }

    private Action getAction(String key) {
        return actionMap.get(key);
    }

    private void addActions() {
        //File actions
        actionMap.put(NEW_KEY, new NewAction(editor));
        actionMap.put(OPEN_KEY, new OpenAction(editor));
        actionMap.put(SAVE_KEY, new SaveAction(editor));
        actionMap.put(PRINT_KEY, new PrintAction(editor));
        actionMap.put(EXIT_KEY, new ExitAction(editor));

        //Empty Misc. Actions
        actionMap.put(PARAGRAPH_SELECTOR_KEY, new EmptyAction());

        //Add font name actions
        for (String each : fontMap.keySet()) {
            String spaceless = each.replaceAll(" ", "");
            actionMap.put("font-family-" + each, new FontFamilyAction("font-family-" + spaceless, each));
        }

        //Set in editor
        editor.setActionMap(actionMap);
    }

    private static abstract class FileAction extends AbstractAction {

        protected final HTMLEditor editor;

        public FileAction(HTMLEditor editor, String name, String tooltip, int key, IconSet icon) {
            super(name);
            this.editor = editor;
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.CTRL_MASK));
            putValue(Action.MNEMONIC_KEY, key);
            putValue(Action.SHORT_DESCRIPTION, tooltip);
            putValue(Action.LARGE_ICON_KEY, IconHelper.getIcon(icon, IconSizes.MEDIUM));
            putValue(Action.SMALL_ICON, IconHelper.getIcon(icon, IconSizes.SMALL));
        }
    }

    private HelperAction getHelper(String type) {
        return new HelperAction(pane, type);
    }

    private class HelperAction extends AbstractAction implements PropertyChangeListener {

        private final JEditorPane pane;
        private final String type;

        public HelperAction(JEditorPane pane, String type) {
            super(NAMES.get(type));
            this.pane = pane;
            this.type = type;

            Action a = pane.getActionMap().get(type);
            if (a == null) {
                throw new IllegalArgumentException("Type " + type + " is not a valid action key");
            }

            IconSet icon = ICONS.get(type);
            if (ACCELERATORS.containsKey(type)) {
                KeyStroke stroke = KeyStroke.getKeyStroke(ACCELERATORS.get(type), InputEvent.CTRL_DOWN_MASK);
                putValue(Action.ACCELERATOR_KEY, stroke);
                pane.getKeymap().addActionForKeyStroke(stroke, this);
            }
            if (MNEMONICS.containsKey(type)) {
                putValue(Action.MNEMONIC_KEY, MNEMONICS.get(type));
            }
            putValue(Action.SHORT_DESCRIPTION, TOOLTIPS.get(type));

            if (icon != null) {
                putValue(Action.LARGE_ICON_KEY, IconHelper.getIcon(icon, IconSizes.MEDIUM));
                putValue(Action.SMALL_ICON, IconHelper.getIcon(icon, IconSizes.SMALL));
            }
            actionMap.put(type, this);
            a.addPropertyChangeListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            if (type.equals(CUT_KEY)) {
                pane.cut();
            } else if (type.equals(COPY_KEY)) {
                pane.copy();
            } else if (type.equals(PASTE_KEY)) {
                pane.paste();
            } else if (type.equals(UNDO_KEY) && undoer.canUndo()) {
                undoer.undo();
                updateUndoRedo();
            } else if (type.equals(REDO_KEY) && undoer.canRedo()) {
                undoer.redo();
                updateUndoRedo();
            } else {
                Action a = pane.getActionMap().get(type);
                invokeAction(a, e, pane);
            }
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if (!evt.getPropertyName().equals("enabled")) {
                return;
            }
//            firePropertyChange("enabled", evt.getOldValue(), evt.getNewValue());
        }
    }

    private static class EmptyAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
        }
    }

    private static class NewAction extends FileAction {

        public NewAction(HTMLEditor editor) {
            super(editor, "New", "New Document (Ctrl+N)", KeyEvent.VK_N, Actions.DOCUMENT_NEW);
        }

        public void actionPerformed(ActionEvent e) {
            editor.fireNewActionPerformed(new EditorEvent(editor));
        }
    }

    private static class OpenAction extends FileAction {

        public OpenAction(HTMLEditor editor) {
            super(editor, "Open", "Open Document (Ctrl+O)", KeyEvent.VK_O, Actions.DOCUMENT_OPEN);
        }

        public void actionPerformed(ActionEvent e) {
            editor.fireOpenActionPerformed(new EditorEvent(editor));
        }
    }

    private static class SaveAction extends FileAction {

        public SaveAction(HTMLEditor editor) {
            super(editor, "Save", "Save Changes (Ctrl+S)", KeyEvent.VK_S, Devices.MEDIA_FLOPPY);
        }

        public void actionPerformed(ActionEvent e) {
            editor.fireSaveActionPerformed(new EditorEvent(editor));
        }
    }

    private static class PrintAction extends FileAction {

        public PrintAction(HTMLEditor editor) {
            super(editor, "Print", "Print (Ctrl+P)", KeyEvent.VK_P, Devices.PRINTER);
        }

        public void actionPerformed(ActionEvent e) {
            editor.firePrintActionPerformed(new EditorEvent(editor));
        }
    }

    private static class ExitAction extends FileAction {

        public ExitAction(HTMLEditor editor) {
            super(editor, "Exit", "Exit", -1, Emblems.EMBLEM_UNREADABLE);
        }

        public void actionPerformed(ActionEvent e) {
            editor.exit();
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse">
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent evt) {
        RootPaneContainer root = (RootPaneContainer) editor.getTopLevelAncestor();
        root.getGlassPane().setCursor(TEXT_CURSOR);
        root.getGlassPane().setVisible(true);
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        RootPaneContainer root = (RootPaneContainer) editor.getTopLevelAncestor();
        root.getGlassPane().setCursor(DEFAULT_CURSOR);
        root.getGlassPane().setVisible(false);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tool Bars">
    private void installDefaultToolbars() {
        final List<Object> file = new LinkedList<Object>();
        final List<Object> font = new LinkedList<Object>();

        EditorButtonModel model = editor.getButtonModel();
        boolean fileButton = model.showNewButton() || model.showOpenButton() || model.showSaveButton();
        if (model.showNewButton()) {
            file.add(actionMap.get(NEW_KEY));
        }
        if (model.showOpenButton()) {
            file.add(actionMap.get(OPEN_KEY));
        }
        if (model.showSaveButton()) {
            file.add(actionMap.get(SAVE_KEY));
        }
        if (fileButton) {
            file.add("");
        }
        if (model.showPrintButton()) {
            file.add(actionMap.get(PRINT_KEY));
            file.add("");
        }
        file.add(actionMap.get(CUT_KEY));
        file.add(actionMap.get(COPY_KEY));
        file.add(actionMap.get(PASTE_KEY));

        file.add("");
        file.add(actionMap.get(UNDO_KEY));
        file.add(actionMap.get(REDO_KEY));

        JToolBar fileBar = MenuBuilder.buildToolBar(file, "File");
        editor.add(fileBar, new CC().dockNorth());
        COMPONENTS.add(fileBar);

        font.add(getParagraphSelector());
        font.add(buildFontComboBox());
        font.add(buildFontSizeBox());
        font.add("");
        font.add(getToggleButtonFor(BOLD_KEY));
        font.add(getToggleButtonFor(ITALIC_KEY));
        font.add(getToggleButtonFor(UNDERLINE_KEY));
        font.add("");

        alignButtonGroup = new ButtonGroup();
        JToggleButton b = getToggleButtonFor(LEFT_ALIGN_KEY);
        alignButtonGroup.add(b);
        font.add(b);

        b = getToggleButtonFor(CENTER_ALIGN_KEY);
        alignButtonGroup.add(b);
        font.add(b);

        b = getToggleButtonFor(RIGHT_ALIGN_KEY);
        alignButtonGroup.add(b);
        font.add(b);

        JToolBar fontBar = MenuBuilder.buildToolBar(font, "Font");

        String pre = System.getProperty("htmleditor.showPreButton");
        if (pre != null && pre.equals("true")) {
            fontBar.add(getHelper("InsertPre"));
        }
        editor.add(fontBar, new CC().dockNorth());
        COMPONENTS.add(fontBar);
    }

    private JToggleButton getToggleButtonFor(String key) {
        JToggleButton result = new JToggleButton(getAction(key));
        result.setHideActionText(true);
        result.getAccessibleContext().setAccessibleName(NAMES.get(key));
        return result;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Fonts">
    private static class EditorFontFamilyAction extends AbstractAction {

        private final HTMLEditor editor;
        private final ComboBoxModel model;

        public EditorFontFamilyAction(HTMLEditor editor, ComboBoxModel model) {
            this.editor = editor;
            this.model = model;
        }

        public void actionPerformed(ActionEvent e) {
            Object o = model.getSelectedItem();
            if (o == null) {
                return;
            }
            String fontName = o.toString();
            Action a = editor.getActionMap().get("font-family-" + fontName);
            if (a == null) {
                LOG.warn("Could not find action for font " + fontName);
                return;
            }
            ActionEvent e1 = new ActionEvent(editor.getTextArea(), e.getID(), fontName);
            invokeAction(a, e1, editor.getTextArea());
        }
    }
    private static final List<String> fontSizes = new LinkedList<String>();
    private static final Map<String, String> fontSizeMap = new HashMap<String, String>();

    static {
        fontSizes.add("8");
        fontSizes.add("10");
        fontSizes.add("12");
        fontSizes.add("14");
        fontSizes.add("16");
        fontSizes.add("24");
        fontSizes.add("36");

        for (String each : fontSizes) {
            fontSizeMap.put(each + "pt", each);
        }
        fontSizeMap.put("1", "8");
        fontSizeMap.put("2", "10");
        fontSizeMap.put("3", "12");
        fontSizeMap.put("4", "14");
        fontSizeMap.put("5", "16");
        fontSizeMap.put("6", "24");
        fontSizeMap.put("7", "36");
    }

    private Component buildFontSizeBox() {
        JComboBox result = new JComboBox();
        fontSizeModel.addAll(fontSizes);
        result.setModel(fontSizeModel);
        Action a = new EditorFontSizeAction(editor, result);
        actionMap.put(FONT_SIZE_KEY, a);
        result.setAction(a);

        result.setMaximumSize(new java.awt.Dimension(40, 21));
        result.setMinimumSize(new java.awt.Dimension(35, 18));
        result.setPreferredSize(new java.awt.Dimension(37, 21));
        return result;
    }

    private static class EditorFontSizeAction extends AbstractAction {

        private final HTMLEditor editor;
        private final JComboBox box;

        public EditorFontSizeAction(HTMLEditor editor, JComboBox box) {
            this.editor = editor;
            this.box = box;
        }

        public void actionPerformed(final ActionEvent e) {
            Object o = box.getSelectedItem();
            if (o == null) {
                return;
            }
            String size = o.toString();
            String actionName = "font-size-" + size;
            Action a = editor.getTextArea().getActionMap().get(actionName);
            if (a == null) {
                a = new FontSizeAction(actionName, Float.valueOf(size).intValue());
                editor.getActionMap().put(actionName, a);
            }
            invokeAction(a, e, editor.getTextArea());
        }
    }
    private static Map<String, Font> fontMap = new HashMap<String, Font>();

    static {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String each : fonts) {
            Font f = new Font(each, Font.PLAIN, 14);
            if (!fontMap.containsKey(each)) {
                fontMap.put(each, f);
            }
        }
    }

    private JComponent buildFontComboBox() {
        JComboBox result = new JComboBox();

        //Build Model
        List<String> sorted = new LinkedList(fontMap.keySet());
        Collections.sort(sorted);
        fontModel.addAll(sorted);
        result.setModel(fontModel);

        result.setRenderer(new FontBoxRenderer(fontModel));

        Action a = new EditorFontFamilyAction(editor, fontModel);
        actionMap.put(FONT_FAMILY_KEY, a);
        
        result.setAction(a);
        result.setMaximumSize(new java.awt.Dimension(200, 21));
        result.setMinimumSize(new java.awt.Dimension(100, 18));
        result.setPreferredSize(new java.awt.Dimension(150, 20));

        return result;
    }

    private static class FontBoxRenderer extends JLabel implements ListCellRenderer {

        private final MutableListComboBoxModel<String> model;

        public FontBoxRenderer(MutableListComboBoxModel<String> model) {
            this.model = model;
        }

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {


            String str = (String) value;
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            this.setText(str);
            this.setToolTipText(str);
            try {
                Font font = fontMap.get(str);
                this.setFont(font);
            } catch (Exception ex) {
                model.removeElement(value);
                return new JLabel();
            }

            return this;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Toolbar Synchronization">
    
    private class CaretSelectionListener implements CaretListener {

        public void caretUpdate(CaretEvent e) {
            boolean cutAndCopy = e.getDot() != e.getMark();
            getAction(CUT_KEY).setEnabled(cutAndCopy);
            getAction(COPY_KEY).setEnabled(cutAndCopy);
            updateTextSettings();
        }
    }

    private class EditListener implements UndoableEditListener {

        public void undoableEditHappened(UndoableEditEvent e) {
            undoer.addEdit(e.getEdit());
            updateUndoRedo();
        }
    }

    private void updateUndoRedo() {
        if (!editable || !editor.isEnabled()) {
            return;
        }
        getAction(UNDO_KEY).setEnabled(undoer.canUndo());
        getAction(REDO_KEY).setEnabled(undoer.canRedo());
    }
    //Use later
    private ExecutorService UPDATE_EXECUTOR = Executors.newSingleThreadExecutor();

    //TODO: make it run off EDT
    private void updateTextSettings() {
        UPDATE_EXECUTOR.submit(new FontUpdater());
    }

    private void select(String key, boolean value) {
        getAction(key).putValue(Action.SELECTED_KEY, value);
    }

    private static String getFontName(AttributeSet attr) {
        return getStringValue(attr, CSS.Attribute.FONT_FAMILY, "Times New Roman");
    }

    private static String getFontSize(AttributeSet attr) {
        return getStringValue(attr, CSS.Attribute.FONT_SIZE, "4");
    }

    private static String getAlignment(AttributeSet attr) {
        return getStringValue(attr, CSS.Attribute.TEXT_ALIGN, "left");
    }

    private static String getStringValue(AttributeSet attr, Object key, String defaultValue) {
        Object obj = attr.getAttribute(key);
        return (obj != null) ? obj.toString() : defaultValue;
    }

    private static boolean isBold(AttributeSet attr) {
        return getBooleanValue(attr, CSS.Attribute.FONT_WEIGHT, "bold");
    }

    private static boolean isItalic(AttributeSet attr) {
        return getBooleanValue(attr, CSS.Attribute.FONT_STYLE, "italic");
    }

    private static boolean isUnderlined(AttributeSet attr) {
        return getBooleanValue(attr, CSS.Attribute.TEXT_DECORATION, "underline");
    }

    private static boolean getBooleanValue(AttributeSet attr, Object key, String search) {
        String result = getStringValue(attr, key, null);
        return result != null && result.indexOf(search) >= 1;
    }

    private HTMLDocument getHTMLDocument() {
        return (HTMLDocument) pane.getDocument();
    }

    private class FontUpdater extends BYUTask {

        private boolean sameFont;
        private String font;
        private boolean sameSize;
        private String size;
        private boolean bold;
        private boolean italic;
        private boolean underline;
        private boolean sameAlignment;
        private String alignment;

        @Override
        protected void doInBackground() {
            if (!installed) {
                return;
            }
            int start = pane.getSelectionStart();
            int end = pane.getSelectionEnd();

            if (start > end) {
                int oldStart = start;
                start = end;
                end = oldStart;
            }
            final HTMLDocument doc = getHTMLDocument();
            Element e = doc.getCharacterElement(start);
            AttributeSet first = e.getAttributes();

            sameFont = true;
            font = getFontName(first);
            sameSize = true;
            size = getFontSize(first);
            bold = isBold(first);
            italic = isItalic(first);
            underline = isUnderlined(first);
            sameAlignment = true;
            alignment = getAlignment(first);

            for (int i = start; i < end; i++) {
                AttributeSet atts = doc.getCharacterElement(i).getAttributes();
                sameFont = sameFont && font.equals(getFontName(atts));
                sameSize = sameSize && size.equals(getFontSize(atts));
                bold = bold && isBold(atts);
                italic = italic && isItalic(atts);
                underline = underline && isUnderlined(atts);
                sameAlignment = sameAlignment && alignment.equals(getAlignment(atts));
            }
        }

        @Override
        protected void tearDown() {
            if (!installed) {
                return;
            }
            fontModel.setSelectedItem((sameFont) ? font : null);
            String size2 = (size != null) ? fontSizeMap.get(size) : null;
            fontSizeModel.setSelectedItem((sameSize) ? size2 : null);
            select(BOLD_KEY, bold);
            select(ITALIC_KEY, italic);
            select(UNDERLINE_KEY, underline);

            if (!sameAlignment) {
                alignButtonGroup.clearSelection();
            }

            select(LEFT_ALIGN_KEY, alignment.equals("left"));
            select(CENTER_ALIGN_KEY, alignment.equals("center"));
            select(RIGHT_ALIGN_KEY, alignment.equals("right"));
        }
    }

    //</editor-fold>
}