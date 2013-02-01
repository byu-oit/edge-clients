package edu.byu.swing.components;

// <editor-fold defaultstate="collapsed" desc="Imports">
import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import edu.byu.framework.swing.util.DialogUtil;
import edu.byu.swing.utilities.MenuBuilder;
import edu.byu.swing.icons.*;
import static edu.byu.swing.icons.IconHelper.getIcon;
import edu.byu.swing.models.ListComboBoxModel;
import edu.byu.swing.utilities.GenericPopupMouseListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.swing.text.html.CSS;
import javax.swing.text.html.CSS.Attribute;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

import org.apache.log4j.Logger;

import org.w3c.tidy.Tidy;

import org.xhtmlrenderer.pdf.ITextRenderer;
//</editor-fold>

/**
 * Creates an HTML-based text editor using <code>JTextPane</code>.
 *
 * <p>The following buttons are considered "Hideable":
 * <ul><li>New</li>
 * <li>Open</li>
 * <li>Save</li>
 * <li>Print</li>
 * <li>Exit (Frame/Dialog only)</li></ul>
 *
 * <p>If any of these buttons (except print) are visible and have not been assigned
 * at least one {@code ActionListener}, an {@code IllegalStateException} will be thrown
 * when they are pressed.
 *
 * @author  jmooreoa
 * @version 1.1.0
 * @since 1.0.0
 * @deprecated use {@link edu.byu.swing.components.v2.HTMLEditor} instead
 * @see edu.byu.swing.components.v2.HTMLEditor
 */
@Deprecated
public class HTMLEditor extends JComponent /*JTextComponent*/ {

    //<editor-fold defaultstate="collapsed" desc="Variable Declaration">
    private static final long serialVersionUID = -1286497085557749321L;
    /**
     * Editor kit used by TextPane
     */
    protected HTMLEditorKit editor;
    /**
     * Document used by TextPane
     */
    protected HTMLDocument document;
    /**
     * Actions extracted from editor kit
     */
    protected HashMap<String, Action> kitActions;
    /**
     * Actions used by buttons
     */
    protected Map<EditorButtons, EditorAction> actions;
    /**
     * Fonts used by editor.  By default, is loaded with all available system fonts
     */
    protected Map<String, Font> fontMap = new HashMap<String, Font>();
    private Map<String, Integer> fontSizeMap = initSizeMap();//CSS .em size, px size
    private List<Attribute> attributes = initAttributeList();
    private UndoManager undoer;
    private JPopupMenu popup;
    private ToolBarPanel toolBarPanel;
    private boolean frameGenerated = false;
    private JFrame frame;
    private boolean editable;
    /**
     * Hideable buttons that have been enabled.
     */
    protected final Set<EditorButtons> enabledButtons;
    private List<ActionListener> newActionListeners = new ArrayList<ActionListener>(1);
    private List<ActionListener> openActionListeners = new ArrayList<ActionListener>(1);
    private List<ActionListener> saveActionListeners = new ArrayList<ActionListener>(1);
    private List<ActionListener> exitActionListeners = new ArrayList<ActionListener>(1);
    private Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private Cursor TEXT_CURSOR = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    private ListComboBoxModel<String> fontBoxModel = new ListComboBoxModel<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    /**
     * Log
     */
    protected final Logger LOG = Logger.getLogger(getClass());
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private JComboBox fontNameBox;
    private JComboBox fontSizeBox;
    private JToolBar fontBar,  optionsBar;
    private JTextPane textPane = new JTextPane();
    /**
     * Set of all hideable buttons, for use in constructor
     */
    public static final Set<EditorButtons> ALL_BUTTONS = EnumSet.of(
            EditorButtons.NEW,
            EditorButtons.OPEN,
            EditorButtons.SAVE,
            EditorButtons.PRINT,
            EditorButtons.EXIT);
    /**
     * Set representing no hideable buttons, for use in constructor
     */
    public static final Set<EditorButtons> NO_BUTTONS = EnumSet.noneOf(EditorButtons.class);

    public static final String PROP_TEXT = "text";

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors/Initialization">
    /**
     * Creates HTMLEditor with all hideable buttons
     */
    public HTMLEditor() {
        this(ALL_BUTTONS);
    }

    /**
     * Creates HTMLEditor with selected hideable buttons.
     *
     * @param enabledButtons
     */
    public HTMLEditor(Set<EditorButtons> enabledButtons) {
        //super(new BorderLayout());
        this.enabledButtons = enabledButtons;
        
//        try {

            super.setLayout(new BorderLayout());
//            this.enabledButtons = enabledButtons;
            editor = new HTMLEditorKit();
            textPane.setEditorKit(editor);

            initActions();
            initTextPane();
            document = (HTMLDocument) textPane.getStyledDocument();
            undoer = new UndoManager();
            undoer.setLimit(50);
            textPane.getStyledDocument().addUndoableEditListener(new EditListener());
            initFonts();
            initPopupMenu();
            initToolbars();
            saveActionListeners.add(0, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    textPane.setText(replaceTabs(textPane.getText()));
                }
            });
//        } catch (Throwable t) {
            String fileName = System.getProperty("user.home") + File.separator + "netbeans_htmleditor.log";
//            try {
//                PrintWriter writer = new PrintWriter(fileName);
//                t.printStackTrace(writer);
//                writer.close();
//            } catch (Throwable t2) {
//                throw new RuntimeException("Unable to write stack trace", t2);
//            }
//        }
    }

    private void initTextPane() {
        textPane.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RootPaneContainer root = (RootPaneContainer) getTopLevelAncestor();
                root.getGlassPane().setCursor(TEXT_CURSOR);
                root.getGlassPane().setVisible(true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RootPaneContainer root = (RootPaneContainer) getTopLevelAncestor();
                root.getGlassPane().setCursor(DEFAULT_CURSOR);
                root.getGlassPane().setVisible(false);
            }
        });
        textPane.addCaretListener(new javax.swing.event.CaretListener() {

            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                updateTextSettings();
            }
        });
        JScrollPane editorScroller = new JScrollPane(textPane);
        add(editorScroller, java.awt.BorderLayout.CENTER);
    }

    private void initToolbars() {
        toolBarPanel = new ToolBarPanel();

        List<Object> options = new ArrayList<Object>();
        boolean hasButton = false;
        if (enabledButtons.contains(EditorButtons.NEW)) {
            options.add(actions.get(EditorButtons.NEW));
            hasButton = true;
        }
        if (enabledButtons.contains(EditorButtons.OPEN)) {
            options.add(actions.get(EditorButtons.OPEN));
            hasButton = true;
        }
        if (enabledButtons.contains(EditorButtons.SAVE)) {
            options.add(actions.get(EditorButtons.SAVE));
            hasButton = true;
        }
        if (hasButton) {
            options.add(new Object());//Separator
        }
        if (enabledButtons.contains(EditorButtons.PRINT)) {
            options.add(actions.get(EditorButtons.PRINT));
            options.add(new Object());//Separator
        }
        options.add(actions.get(EditorButtons.CUT));
        options.add(actions.get(EditorButtons.COPY));
        options.add(actions.get(EditorButtons.PASTE));
        options.add(new Object());
        options.add(actions.get(EditorButtons.UNDO));
        options.add(actions.get(EditorButtons.REDO));

        optionsBar = MenuBuilder.buildToolBar(options, "Options");

        fontNameBox = new JComboBox(fontBoxModel);
        fontNameBox.setMaximumSize(new java.awt.Dimension(200, 21));
        fontNameBox.setMinimumSize(new java.awt.Dimension(100, 18));
        fontNameBox.setPreferredSize(new java.awt.Dimension(150, 20));
        fontNameBox.setAction(actions.get(EditorButtons.FONT_NAME));
        fontNameBox.setRenderer(new FontBoxRenderer());
        fontNameBox.setMaximumRowCount(10);

        Vector<Integer> sizes = new Vector<Integer>(fontSizeMap.values());
        java.util.Collections.sort(sizes);
        fontSizeBox = new JComboBox(new DefaultComboBoxModel(sizes));
        fontSizeBox.setAction(actions.get(EditorButtons.FONT_SIZE));
        fontSizeBox.setMaximumSize(new java.awt.Dimension(40, 21));
        fontSizeBox.setMinimumSize(new java.awt.Dimension(35, 18));
        fontSizeBox.setPreferredSize(new java.awt.Dimension(37, 21));

        JToggleButton boldButton;
        boldButton = new JToggleButton(actions.get(EditorButtons.BOLD));
        boldButton.setHideActionText(true);
        boldButton.getAccessibleContext().setAccessibleName("Bold");

        JToggleButton italicButton = new JToggleButton(actions.get(EditorButtons.ITALIC));
        italicButton.setHideActionText(true);
        italicButton.getAccessibleContext().setAccessibleName("Italic");

        JToggleButton underlineButton = new JToggleButton(actions.get(EditorButtons.UNDERLINE));
        underlineButton.setHideActionText(true);
        underlineButton.getAccessibleContext().setAccessibleName("Underline");

        ButtonGroup alignButtonGroup = new ButtonGroup();
        JToggleButton leftAlignButton = new JToggleButton(actions.get(EditorButtons.LEFT));
        alignButtonGroup.add(leftAlignButton);
        leftAlignButton.setHideActionText(true);
        leftAlignButton.getAccessibleContext().setAccessibleName("Align Left");

        JToggleButton centerAlignButton = new JToggleButton(actions.get(EditorButtons.CENTER));
        alignButtonGroup.add(centerAlignButton);
        centerAlignButton.setHideActionText(true);

        JToggleButton rightAlignButton = new JToggleButton(actions.get(EditorButtons.RIGHT));
        alignButtonGroup.add(rightAlignButton);
        rightAlignButton.setHideActionText(true);
        rightAlignButton.getAccessibleContext().setAccessibleName("Align Right");

        List<Object> font = new ArrayList<Object>();
        font.add(fontNameBox);
        font.add(fontSizeBox);
        font.add(new Object());
        font.add(boldButton);
        font.add(italicButton);
        font.add(underlineButton);
        font.add(new Object());
        font.add(leftAlignButton);
        font.add(centerAlignButton);
        font.add(rightAlignButton);

        fontBar = MenuBuilder.buildToolBar(font, "Font");

        toolBarPanel.addToolBar(optionsBar);
        toolBarPanel.addToolBar(fontBar);

        add(toolBarPanel, java.awt.BorderLayout.NORTH);
    }

    private void initActions() {
        //Get Actions from EditorKit
        Action[] acts = editor.getActions();
        kitActions = new HashMap<String, Action>();
        for (Action act : acts) {
            kitActions.put((String) act.getValue(Action.NAME), act);
        }

        //Define actions for buttons/menus
        actions = new EnumMap<EditorButtons, EditorAction>(EditorButtons.class);
        actions.put(EditorButtons.NEW, new FileAction(EditorButtons.NEW));
        actions.put(EditorButtons.OPEN, new FileAction(EditorButtons.OPEN));
        actions.put(EditorButtons.SAVE, new FileAction(EditorButtons.SAVE));
        actions.put(EditorButtons.PRINT, new PrintAction());
        actions.put(EditorButtons.EXIT, new FileAction(EditorButtons.EXIT));
        actions.put(EditorButtons.CUT, new ClipboardAction(EditorButtons.CUT));
        actions.put(EditorButtons.COPY, new ClipboardAction(EditorButtons.COPY));
        actions.put(EditorButtons.PASTE, new ClipboardAction(EditorButtons.PASTE));
        actions.put(EditorButtons.UNDO, new UndoAction());
        actions.put(EditorButtons.REDO, new RedoAction());
        actions.put(EditorButtons.BOLD, new FontStyleAction(EditorButtons.BOLD));
        actions.put(EditorButtons.ITALIC, new FontStyleAction(EditorButtons.ITALIC));
        actions.put(EditorButtons.UNDERLINE, new FontStyleAction(EditorButtons.UNDERLINE));
        actions.put(EditorButtons.LEFT, new TextAlignAction(EditorButtons.LEFT));
        actions.put(EditorButtons.CENTER, new TextAlignAction(EditorButtons.CENTER));
        actions.put(EditorButtons.RIGHT, new TextAlignAction(EditorButtons.RIGHT));
        actions.put(EditorButtons.FONT_NAME, new FontTypeAction());
        actions.put(EditorButtons.FONT_SIZE, new FontSizeAction());
    }

    private void initFonts() {
        //Add all available fonts to kitActions

        List<String> families = fontBoxModel.getData();
        for (String str : families) {
            Font f = new Font(str, Font.PLAIN, 14);
            if (!fontMap.containsKey(str)) {
                fontMap.put(str, f);
                String spaceless = str.replaceAll(" ", "");
                kitActions.put("font-family-" + str, new FontFamilyAction("font-family-" + spaceless, str));
            }
        }
        fontBoxModel.setSelectedItem("Times New Roman");
    }

    private List<Attribute> initAttributeList() {
        String[] names = new String[]{"font-family", "text-decoration", "font-weight", "font-style", "font-size", "text-align"};
        List<Attribute> toReturn = new ArrayList<Attribute>(names.length);
        for (String name : names) {
            toReturn.add(CSS.getAttribute(name));
        }
        return toReturn;
    }

    private HashMap<String, Integer> initSizeMap() {
        HashMap<String, Integer> toReturn = new HashMap<String, Integer>();//CSS .em size, px size
        toReturn.put("1", 8);
        toReturn.put("2", 10);
        toReturn.put("3", 12);
        toReturn.put("4", 14);
        toReturn.put("5", 16);
        toReturn.put("6", 24);
        toReturn.put("7", 36);
        return toReturn;
    }

    private void initPopupMenu() {
        Object[] popupBuilder = new Object[6];
        popupBuilder[0] = actions.get(EditorButtons.CUT);
        popupBuilder[1] = actions.get(EditorButtons.COPY);
        popupBuilder[2] = actions.get(EditorButtons.PASTE);
        popupBuilder[3] = "";//Separator
        popupBuilder[4] = actions.get(EditorButtons.UNDO);
        popupBuilder[5] = actions.get(EditorButtons.REDO);

        popup = MenuBuilder.buildPopup("Editing", popupBuilder);

        textPane.addMouseListener(new GenericPopupMouseListener(popup));
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Component-building methods">
    /**
     * Creates a <code>JMenuBar</code> for use with the editor, using the
     * actions used for the buttons.
     *
     * @return JMenuBar for use with editor
     */
    public JMenuBar getMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file;
        JMenu edit;
        JMenu font = new JMenu("Font");

        Object[] fileArray = new Object[7];
        Object[] editArray = new Object[6];
        boolean buttonAdded = false;
        int idx = 0;
        if (enabledButtons.contains(EditorButtons.NEW)) {
            fileArray[idx++] = actions.get(EditorButtons.NEW);
            buttonAdded = true;
        }
        if (enabledButtons.contains(EditorButtons.OPEN)) {
            fileArray[idx++] = actions.get(EditorButtons.OPEN);
            buttonAdded = true;
        }
        if (enabledButtons.contains(EditorButtons.SAVE)) {
            fileArray[idx++] = actions.get(EditorButtons.SAVE);
            buttonAdded = true;
        }
        if (enabledButtons.contains(EditorButtons.PRINT)) {
            if (buttonAdded) {
                fileArray[idx++] = ""; //separator
            }
            fileArray[idx++] = actions.get(EditorButtons.PRINT);
            buttonAdded = true;
        }
        if (enabledButtons.contains(EditorButtons.EXIT)) {
            if (buttonAdded) {
                fileArray[idx++] = "";
            }
            fileArray[idx++] = actions.get(EditorButtons.EXIT);
            buttonAdded = true;
        }

        Object[] newFile = new Object[idx];
        if (idx > 0) {
            for (int i = 0; i < idx; i++) {
                newFile[i] = fileArray[i];
            }
        }
        fileArray = newFile;

        editArray[0] = actions.get(EditorButtons.UNDO);
        editArray[1] = actions.get(EditorButtons.REDO);
        editArray[2] = "";
        editArray[3] = actions.get(EditorButtons.CUT);
        editArray[4] = actions.get(EditorButtons.COPY);
        editArray[5] = actions.get(EditorButtons.PASTE);

        font.add(new JCheckBoxMenuItem(actions.get(EditorButtons.BOLD)));
        font.add(new JCheckBoxMenuItem(actions.get(EditorButtons.ITALIC)));
        font.add(new JCheckBoxMenuItem(actions.get(EditorButtons.UNDERLINE)));

        bar = new JMenuBar();
        file = MenuBuilder.buildMenu("File", fileArray);
        edit = MenuBuilder.buildMenu("Edit", editArray);

        file.setMnemonic(KeyEvent.VK_F);
        edit.setMnemonic(KeyEvent.VK_E);
        font.setMnemonic(KeyEvent.VK_O);

        if (buttonAdded) {
            bar.add(file);
        }
        bar.add(edit);
        bar.add(font);

        return bar;
    }

    /**
     * Gets pre-built frame containing this HTMLEditor
     * @return editor frame
     */
    public JFrame getFrame() {
        if (!frameGenerated) {
            this.frame = generateFrame();
            frameGenerated = true;
        }
        return frame;
    }

    /**
     * Shows a frame containing the editor.
     * @param pos position where frame appears
     * @param dim size of frame
     */
    public void showFrame(Point pos, Dimension dim) {
        JFrame toShow = getFrame();
        toShow.setSize(dim);
        toShow.setLocation(pos);
        toShow.setVisible(true);
    }

    /**
     * Shows a frame containing the editor.
     * @param dim size of frame
     */
    public void showFrame(Dimension dim) {
        JFrame toShow = getFrame();
        toShow.setSize(dim);
        DialogUtil.centerFrame(toShow);
        toShow.setVisible(true);
        
        
        toShow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private JDialog dialog;

    /**
     * Displays a modal dialog containing this HTMLEditor
     * @param parent Parent frame
     * @param dim size of dialog
     */
    public void showDialog(final Frame parent, final Dimension dim) {
        final HTMLEditor hTMLEditor = this;
        Runnable r = new Runnable() {

            public void run() {
                dialog = new JDialog(parent, "HTML Editor", true);
                dialog.setJMenuBar(getMenuBar());
                dialog.setSize(dim);
                dialog.add(hTMLEditor);
                DialogUtil.centerDialog(parent, dialog);
                dialog.setVisible(true);
            }
        };

        runThread(r);
    }

    /**
     *
     * @return dialog containing this editor
     */
    public JDialog getDialog() {
        return dialog;
    }

    private static void runThread(final Runnable r) {
        if (EventQueue.isDispatchThread()) {
            r.run();
            return;
        }

        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    try {
                        r.run();
                    } catch (Throwable t) {
                        ExceptionInternalHandler.doHandle(t);
                    }
                }
            });
        } catch (Throwable t) {
            throw new RuntimeException("Unable to show dialog", t);
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Public Utility Methods">
    /**
     * Loads a document into the editor.
     *
     * @param doc the document to load
     */
    public void loadDocument(HTMLDocument doc) {
        textPane.setDocument(doc);
        undoer.discardAllEdits();
    }

    /**
     * Gets plain-text HTML representation of text in editor.
     *
     * @return contents of the editor
     */
    public String getText() {
        return textPane.getText();
    }

    /**
     * Sets the text in editor
     *
     * @param text new content of editor
     */
    public void setText(String text) {
        String oldText = textPane.getText();
        if (text == null || text.trim().isEmpty()) {
            text = "<html><body><p style=\"margin-top: 0\"></p></body></html>";//To prevent later formatting errors
        }
        StringBuilder sb = new StringBuilder(text);
        if (sb.indexOf("<p") == -1 && sb.indexOf("<body>") != -1) {
            sb.insert(sb.indexOf("<body>") + 6, " <p style=\"margin-top: 0\">");
            sb.insert(sb.indexOf("</body>"), "</p> ");
            text = sb.toString();
        }
//        if (text.indexOf("{\\rtf") != -1) {
//            text = convertFromRTF(text);
//        }//Parse RTF
        textPane.setText(text);
        textPane.setCaretPosition(0);
        undoer.discardAllEdits();
        updateUndoRedo();
        updateTextSettings();
        propertyChangeSupport.firePropertyChange(PROP_TEXT, oldText, text);
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
        for (EditorAction a : actions.values()) {
            if (!editable) {
                if (a instanceof PrintAction) {
                    continue;
                }
                if (a instanceof FileAction) {
                    EditorButtons button = ((FileAction) a).getButton();
                    if (button.equals(EditorButtons.OPEN) || button.equals(EditorButtons.EXIT)) {
                        continue;
                    }
                }
                if (a instanceof ClipboardAction) {
                    if (((ClipboardAction) a).getButton().equals(EditorButtons.COPY)) {
                        continue;
                    }
                }
            }
            a.setEnabled(editable);
        }
        Runnable r = new Runnable() {

            public void run() {
                textPane.setEditable(editable);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    public boolean isEditable() {
        return editable;
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Clears text in editor
     */
    public void clear() {
        textPane.setText("<html><body><p style=\"margin-top: 0\"></p></body></html>");//To avoid later formatting errors
    }

    /**
     * Gets list of all action listeners assigned to New button
     * @return all action listeners for new button
     */
    public List<ActionListener> getNewActionListeners() {
        List<ActionListener> copy = new ArrayList<ActionListener>(newActionListeners.size());
        Collections.copy(copy, newActionListeners);//Defensive Copy
        return copy;
    }

    /**
     * Gets list of all action listeners assigned to open button
     * @return all action listeners for open button
     */
    public List<ActionListener> getOpenActionListeners() {
        List<ActionListener> copy = new ArrayList<ActionListener>(openActionListeners.size());
        Collections.copy(copy, openActionListeners);//Defensive Copy
        return copy;
    }

    /**
     * Gets list of all action listeners assigned to save button
     * @return all action listeners for save button
     */
    public List<ActionListener> getSaveActionListeners() {
        List<ActionListener> copy = new ArrayList<ActionListener>(saveActionListeners.size());
        Collections.copy(copy, saveActionListeners);//Defensive Copy
        copy.remove(0);//Remove default listener from copied list
        return copy;
    }

    /**
     * Adds listener to new button
     * @param listener to add
     */
    public void addNewActionListener(ActionListener listener) {
        newActionListeners.add(listener);
    }

    /**
     * Removes listener from new button
     * @param listener to remove
     */
    public void removeNewActionListener(ActionListener listener) {
        newActionListeners.remove(listener);
    }

    /**
     * Adds listener to save button
     * @param listener to add
     */
    public void addSaveActionListener(ActionListener listener) {
        saveActionListeners.add(listener);
    }

    /**
     * Removes listener from save button
     * @param listener listener to remove
     */
    public void removeSaveActionListener(ActionListener listener) {
        saveActionListeners.remove(listener);
    }

    /**
     * Adds listener to open button
     * @param listener listener to add
     */
    public void addOpenActionListener(ActionListener listener) {
        openActionListeners.add(listener);
    }

    /**
     * Removes listener from open button
     * @param listener listener to remove
     */
    public void removeOpenActionListener(ActionListener listener) {
        openActionListeners.remove(listener);
    }

    /**
     * Adds listener to exit button (only used in frames)
     * @param listener
     */
    public void addExitActionListener(ActionListener listener) {
        exitActionListeners.add(listener);
    }

    /**
     * Removes listener from exit button (only used in frames)
     * @param listener listener to remove
     */
    public void removeExitActionListener(ActionListener listener) {
        exitActionListeners.remove(listener);
    }

    /**
     * Returns text component used for editing.
     *
     * This method is provided to facilitate the use of external decorators or utilities
     * with the editor.
     * @return text component used by editor
     */
    public JTextComponent getTextArea() {
        return textPane;
    }

    /**
     * Enables/disables editor and all contained components
     * @param enabled
     */
    @Override
    public void setEnabled(final boolean enabled) {
        for (EditorAction a : actions.values()) {
            a.setEnabled(enabled);
        }
        Runnable r = new Runnable() {

            public void run() {
                textPane.setEnabled(enabled);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
        updateUndoRedo();
        super.setEnabled(enabled);
    }

    /**
     * Adds a toolbar to the editor.
     * @param bar
     */
    public void addToolBar(JToolBar bar) {
        toolBarPanel.addToolBar(bar);
    }

    /**
     * Removes the toolbar at the specified index
     * @param index
     * @return {@code true} if the editor changed
     */
    public boolean removeToolBar(int index) {
        return toolBarPanel.removeToolBar(index);
    }

    /**
     * Removes the specified toolbar
     * @param bar toolbar to remove
     * @return {@code true} if the editor changed
     */
    public boolean removeToolBar(JToolBar bar) {
        return toolBarPanel.removeToolBar(bar);
    }

    /**
     * @return count of currently installed toolbars (including default toolbars)
     */
    public int getToolBarCount() {
        return toolBarPanel.getToolBarCount();
    }

    /**
     * Adds toolbar at specified index
     * @param bar toolbar to add
     * @param index
     */
    public void addToolBar(JToolBar bar, int index) {
        toolBarPanel.addToolBar(bar, index);
    }

    /**
     * Parses RTF into HTML.
     * Removes most RTF tags from a string and replace them with
     * the appropriate HTML tags
     * @param toConvert text to convert
     * @return converted text
     */
    public static String convertFromRTF(String toConvert) {
        toConvert = toConvert.replaceAll("<", "[");
        toConvert = toConvert.replaceAll(">", "]");
        String[] split = toConvert.split("\\\\viewkind", 2);
        if (split.length > 1) {
            split = split[1].split("\\\\f0", 2);
            if (split.length > 1) {
                toConvert = split[1];
            }
        }

        toConvert = "<html><head></head><body><p>" + toConvert;
        String[] splitPar = toConvert.split("\\\\pard");
        if (splitPar.length > 1) {
            String fixed = "";
            int start = 1;
            if (toConvert.indexOf("\\\\pard") == 0) {
                start = 0;
            }
            for (int i = start; i < splitPar.length; i++) {
                String str = splitPar[i];
                if (str.startsWith("\\\\qc")) {
                    fixed = fixed + "<p align=\"center\">" + str.replace("\\\\qc ", "");
                } else if (str.startsWith("\\\\qr")) {
                    fixed = fixed + "<p align=\"right\">" + str.replace("\\\\qr ", "");
                } else {
                    fixed = "<p>" + str;
                }
            }
            toConvert = fixed;
        }// end pard

        toConvert = toConvert.replaceAll("\\\\par", "</p>\n<p>");

        toConvert = toConvert.replaceAll("\\\\b0", "</b>");
        toConvert = toConvert.replaceAll("\\\\b ", "<b>");
        toConvert = toConvert.replaceAll("\\\\i0", "</i>");
        toConvert = toConvert.replaceAll("\\\\i ", "<i>");
        toConvert = toConvert.replaceAll("\\\\ulnone", "</u>");
        toConvert = toConvert.replaceAll("\\\\ul ", "<u>");

        String[] fsSplit = toConvert.split("\\\\fs");
        if (fsSplit.length > 1) {
            String fixed = "";
            int start = 1;
            if (toConvert.startsWith("\\\\fs")) {
                start = 0;
            }
            for (int i = start; i < fsSplit.length; i++) {
                String str = fsSplit[i];

                fixed = fixed + str.substring(2);
            }
            toConvert = fixed;
        }
        toConvert = toConvert.trim();
        toConvert = toConvert.substring(0, toConvert.lastIndexOf("}")) + "</body></html>";
        return toConvert;
    }

    private JTextPane getTextPane() {
        if (textPane == null) {
            textPane = new JTextPane();
        }
        return textPane;
    }

    /*    @Override
    public void addCaretListener(CaretListener listener) {
    getTextPane().addCaretListener(listener);
    }

    @Override
    public void addInputMethodListener(InputMethodListener l) {
    getTextPane().addInputMethodListener(l);
    }

    @Override
    public void copy() {
    getTextPane().copy();
    }

    @Override
    public void cut() {
    getTextPane().cut();
    }

    @Override
    public Action[] getActions() {
    return getTextPane().getActions();
    }

    @Override
    public Caret getCaret() {
    return getTextPane().getCaret();
    }

    @Override
    public Color getCaretColor() {
    return getTextPane().getCaretColor();
    }

    @Override
    public CaretListener[] getCaretListeners() {
    return getTextPane().getCaretListeners();
    }

    @Override
    public int getCaretPosition() {
    return getTextPane().getCaretPosition();
    }

    @Override
    public Color getDisabledTextColor() {
    return getTextPane().getDisabledTextColor();
    }

    @Override
    public Document getDocument() {
    return getTextPane().getDocument();
    }

    @Override
    public boolean getDragEnabled() {
    return getTextPane().getDragEnabled();
    }

    @Override
    public char getFocusAccelerator() {
    return getTextPane().getFocusAccelerator();
    }

    @Override
    public Highlighter getHighlighter() {
    return getTextPane().getHighlighter();
    }

    @Override
    public InputMethodRequests getInputMethodRequests() {
    return getTextPane().getInputMethodRequests();
    }

    @Override
    public Keymap getKeymap() {
    return getTextPane().getKeymap();
    }

    @Override
    public Insets getMargin() {
    return getTextPane().getMargin();
    }

    @Override
    public Printable getPrintable(MessageFormat headerFormat, MessageFormat footerFormat) {
    return getTextPane().getPrintable(headerFormat, footerFormat);
    }

    @Override
    public String getSelectedText() {
    return getTextPane().getSelectedText();
    }

    @Override
    public Color getSelectedTextColor() {
    return getTextPane().getSelectedTextColor();
    }

    @Override
    public Color getSelectionColor() {
    return getTextPane().getSelectionColor();
    }

    @Override
    public int getSelectionEnd() {
    return getTextPane().getSelectionEnd();
    }

    @Override
    public int getSelectionStart() {
    return getTextPane().getSelectionStart();
    }

    @Override
    public String getText(int offs, int len) throws BadLocationException {
    return getTextPane().getText(offs, len);
    }

    @Override
    public boolean isEditable() {
    return getTextPane().isEditable();
    }

    @Override
    public Rectangle modelToView(int pos) throws BadLocationException {
    return getTextPane().modelToView(pos);
    }

    @Override
    public void moveCaretPosition(int pos) {
    getTextPane().moveCaretPosition(pos);
    }

    @Override
    public void paste() {
    getTextPane().paste();
    }

    @Override
    public boolean print() throws PrinterException {
    return getTextPane().print();
    }

    @Override
    public boolean print(MessageFormat headerFormat, MessageFormat footerFormat) throws PrinterException {
    return getTextPane().print(headerFormat, footerFormat);
    }

    @Override
    public boolean print(MessageFormat headerFormat, MessageFormat footerFormat, boolean showPrintDialog, PrintService service, PrintRequestAttributeSet attributes, boolean interactive) throws PrinterException {
    return getTextPane().print(headerFormat, footerFormat, showPrintDialog, service, attributes, interactive);
    }

    @Override
    public void read(Reader in, Object desc) throws IOException {
    getTextPane().read(in, desc);
    }

    @Override
    public void removeCaretListener(CaretListener listener) {
    getTextPane().removeCaretListener(listener);
    }

    @Override
    public void replaceSelection(String content) {
    getTextPane().replaceSelection(content);
    }

    @Override
    public void select(int selectionStart, int selectionEnd) {
    getTextPane().select(selectionStart, selectionEnd);
    }

    @Override
    public void selectAll() {
    getTextPane().selectAll();
    }

    @Override
    public void setCaret(Caret c) {
    getTextPane().setCaret(c);
    }

    @Override
    public void setCaretColor(Color c) {
    getTextPane().setCaretColor(c);
    }

    @Override
    public void setCaretPosition(int position) {
    getTextPane().setCaretPosition(position);
    }

    @Override
    public void setDisabledTextColor(Color c) {
    getTextPane().setDisabledTextColor(c);
    }

    @Override
    public void setDocument(Document doc) {
    getTextPane().setDocument(doc);
    }

    @Override
    public void setDragEnabled(boolean b) {
    getTextPane().setDragEnabled(b);
    }

    @Override
    public void setEditable(boolean b) {
    getTextPane().setEditable(b);
    }

    @Override
    public void setFocusAccelerator(char aKey) {
    getTextPane().setFocusAccelerator(aKey);
    }

    @Override
    public void setHighlighter(Highlighter h) {
    getTextPane().setHighlighter(h);
    }

    @Override
    public void setKeymap(Keymap map) {
    getTextPane().setKeymap(map);
    }

    @Override
    public void setMargin(Insets m) {
    getTextPane().setMargin(m);
    }

    @Override
    public void setNavigationFilter(NavigationFilter filter) {
    getTextPane().setNavigationFilter(filter);
    }

    @Override
    public void setSelectedTextColor(Color c) {
    getTextPane().setSelectedTextColor(c);
    }

    @Override
    public void setSelectionColor(Color c) {
    getTextPane().setSelectionColor(c);
    }

    @Override
    public void setSelectionEnd(int selectionEnd) {
    getTextPane().setSelectionEnd(selectionEnd);
    }

    @Override
    public void setSelectionStart(int selectionStart) {
    getTextPane().setSelectionStart(selectionStart);
    }

    @Override
    public int viewToModel(Point pt) {
    return getTextPane().viewToModel(pt);
    }

    @Override
    public void write(Writer out) throws IOException {
    getTextPane().write(out);
    }*/
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Utility Methods">
    private void updateTextSettings() {//TODO:Fix selection problems: underline, etc.
        Runnable r = new Runnable() {

            public void run() {
                int pos = textPane.getCaretPosition() - 1;
                if (pos < document.getStartPosition().getOffset()) {
                    pos += 1;
                }
                AttributeSet attSet = document.getCharacterElement(pos).getAttributes();

                for (Attribute a : attributes) {
                    String name = a.toString();
                    String defaultVal = a.getDefaultValue();
                    Object valObject = attSet.getAttribute(a);
                    if (valObject == null || valObject.toString().equals(defaultVal)) {
                        if (name.equals("font-family")) {
                            fontNameBox.setSelectedItem("Times New Roman");
                        } else if (name.equals("text-decoration")) {
                            actions.get(EditorButtons.UNDERLINE).setSelected(false);
                        } else if (name.equals("font-weight")) {
                            actions.get(EditorButtons.BOLD).setSelected(false);
                        } else if (name.equals("font-style")) {
                            actions.get(EditorButtons.ITALIC).setSelected(false);
                        } else if (name.equals("font-size")) {
                            fontSizeBox.setSelectedItem(3);
                        } else if (name.equals("text-align")) {
                            actions.get(EditorButtons.LEFT).setSelected(true);
                        }
                        continue;
                    }
                    String value = valObject.toString();
                    if (name.equals("font-family")) {
                        fontNameBox.setSelectedItem(value);
                    } else if (name.equals("text-decoration")) {
                        actions.get(EditorButtons.UNDERLINE).setSelected(true);
                    } else if (name.equals("font-weight")) {
                        actions.get(EditorButtons.BOLD).setSelected(true);
                    } else if (name.equals("font-style")) {
                        if (value.equals("italic")) {
                            actions.get(EditorButtons.ITALIC).setSelected(true);
                        } else {
                            actions.get(EditorButtons.ITALIC).setSelected(false);
                        }
                    } else if (name.equals("font-size")) {
                        fontSizeBox.setSelectedItem(fontSizeMap.get(value));
                    } else if (name.equals("text-align")) {
                        if (value.equals("center")) {
                            actions.get(EditorButtons.CENTER).setSelected(true);
                        } else if (value.equals("right")) {
                            actions.get(EditorButtons.RIGHT).setSelected(true);
                        } else {//Must be left
                            actions.get(EditorButtons.LEFT).setSelected(true);
                        }
                    }
                }
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    private JFrame generateFrame() {
        JFrame newFrame = new JFrame("HTML Editor");
        newFrame.setIconImage(IconHelper.getImage(Applications.ACCESSORIES_TEXT_EDITOR, IconSizes.SMALL));
        newFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        newFrame.add(this);
        newFrame.setJMenuBar(getMenuBar());
        return newFrame;
    }

    private void updateUndoRedo() {
        Runnable r = new Runnable() {

            public void run() {
                if (isEnabled()) {
                    actions.get(EditorButtons.REDO).setEnabled(undoer.canRedo());
                    actions.get(EditorButtons.UNDO).setEnabled(undoer.canUndo());
                } else {
                    actions.get(EditorButtons.REDO).setEnabled(false);
                    actions.get(EditorButtons.UNDO).setEnabled(false);
                }
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
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
                toAppend = toAppend + "&emsp;&emsp;&emsp;";
            }
            toReturn = toReturn + toAppend;
        }
        return toReturn;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Internal Classes">
    /**
     * Represents default buttons/components used by HTMLEditor.
     * {@code NEW, OPEN, SAVE, PRINT}, and {@code EXIT} are optional.  Whether they
     * are displayed is set in the HTMLEditor constructor
     * @see HTMLEditor(Set<EditorButtons>)
     */
    public enum EditorButtons {

        NEW("New", true), OPEN("Open", true), SAVE("Save", true), PRINT("Print", true),
        /**
         * Exit button, used only in dialogs and frames
         */
        EXIT("Exit", true),
        CUT("Cut"), COPY("Copy"), PASTE("Paste"), UNDO("Undo"), REDO("Redo"), BOLD("Bold"),
        ITALIC("Italic"), UNDERLINE("Underline"), LEFT("Left"), CENTER("Center"),
        RIGHT("Right"), FONT_SIZE("Font Size"), FONT_NAME("Font Name");
        private final boolean optional;
        private final String actionCommand;
        private final String name;

        private EditorButtons(String name) {
            this(name, false);
        }

        private EditorButtons(String name, boolean isHideable) {
            this.name = name;
            this.optional = isHideable;
            actionCommand = this.toString();
        }

        /**
         *
         * @return if this button is optional
         */
        public boolean isOptional() {
            return optional;
        }

        /**
         * @return a default actionCommand for button
         */
        public String getActionCommand() {
            return actionCommand;
        }

        /**
         * Name of this button
         * @return
         */
        public String getName() {
            return name;
        }
    }

    private class EditListener implements UndoableEditListener {

        public void undoableEditHappened(UndoableEditEvent e) {
            undoer.addEdit(e.getEdit());
            updateUndoRedo();
        }
    }

    private class FontBoxRenderer extends JLabel implements ListCellRenderer {

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
                fontNameBox.removeItem(str);
                return new JLabel();
            }

            return this;
        }
    }

    /**
     * Represents an action to be used by buttons in the editor
     */
    protected abstract class EditorAction extends AbstractAction {

        /**
         * Basic constructor
         * @param name name to be displayed
         */
        public EditorAction(String name) {
            super(name);
        }

        /**
         * Advanced constructor
         * @param name displayed button text
         * @param tooltip tooltip text
         * @param mnemonic mnemonic for use in menus, etc.
         * @param accelerator accelerator key (Ctrl mask is added later)
         * @param iconName name of icon (from edu.byu.swing.icons)
         */
        public EditorAction(String name, String tooltip, int mnemonic, int accelerator, IconSet icon) {
            super(name, getIcon(icon, IconSizes.MEDIUM));
            putValue(ACTION_COMMAND_KEY, name.toLowerCase());
            if (accelerator >= 0) {
                putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator, InputEvent.CTRL_MASK));
            }
            if (mnemonic >= 0) {
                putValue(MNEMONIC_KEY, mnemonic);
            }
            putValue(SHORT_DESCRIPTION, tooltip);
            putValue(SMALL_ICON, getIcon(icon, IconSizes.SMALL, name));
        }

        /**
         * Selects/deselects button
         * @param isSelected
         */
        public void setSelected(boolean isSelected) {
            putValue(SELECTED_KEY, isSelected);
        }

        public abstract void actionPerformed(ActionEvent e);
    }

    private class FileAction extends EditorAction {

        private final EditorButtons BUTTON;
        private final List<ActionListener> listeners;
        private final int DEFAULT_LISTENER_COUNT;

        public FileAction(EditorButtons button) {
            super(button.getName());
            this.BUTTON = button;
            String ttip;
            int key;
            IconSet icon;
            switch (button) {
                case NEW:
                    ttip = "New Document (Ctrl+N)";
                    key = KeyEvent.VK_N;
                    icon = Actions.DOCUMENT_NEW;
                    listeners = newActionListeners;
                    DEFAULT_LISTENER_COUNT = 0;
                    break;
                case OPEN:
                    ttip = "Open Document (Ctrl+O)";
                    key = KeyEvent.VK_O;
                    icon = Actions.DOCUMENT_OPEN;
                    listeners = openActionListeners;
                    DEFAULT_LISTENER_COUNT = 0;
                    break;
                case SAVE:
                    ttip = "Save Changes (Ctrl+S)";
                    key = KeyEvent.VK_S;
                    icon = Devices.MEDIA_FLOPPY;
                    listeners = saveActionListeners;
                    DEFAULT_LISTENER_COUNT = 1;
                    break;
                case EXIT:
                    ttip = "Exit Editor";
                    key = -1;
                    icon = Emblems.EMBLEM_UNREADABLE;
                    listeners = exitActionListeners;
                    DEFAULT_LISTENER_COUNT = 0;
                    break;
                default:
                    throw new IllegalArgumentException(button.toString() + " is not a valid file button Type");
            }
            putValue(ACTION_COMMAND_KEY, button.getActionCommand());
            if (key != -1) {
                putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.CTRL_MASK));
                putValue(MNEMONIC_KEY, key);
            }
            putValue(SHORT_DESCRIPTION, ttip);
            putValue(LARGE_ICON_KEY, getIcon(icon, IconSizes.MEDIUM, (String) super.getValue(NAME)));
            putValue(SMALL_ICON, getIcon(icon, IconSizes.SMALL, (String) super.getValue(NAME)));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (listeners.size() <= DEFAULT_LISTENER_COUNT) {
                throw new IllegalStateException("No listeners defined for " + BUTTON.getName());
            }
            final ActionEvent ae = new ActionEvent(this, 0, BUTTON.getActionCommand());

            for (ActionListener l : listeners) {
                l.actionPerformed(ae);
            }
        }

        public EditorButtons getButton() {
            return BUTTON;
        }
    }

    private class PrintAction extends EditorAction {

        private static final int KEY = KeyEvent.VK_P;

        public PrintAction() {
            super("Print", "Print (Ctrl+P)", KEY, KEY, Actions.DOCUMENT_PRINT);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String input = getText();
            Tidy t = new Tidy();
            t.setXHTML(true);
            t.setDropEmptyParas(false);
            t.setDocType("strict");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            t.parse(new ByteArrayInputStream(input.getBytes()), output);

            try {
                File f = File.createTempFile("swingprint", ".pdf");
                f.deleteOnExit();
                OutputStream os = new FileOutputStream(f);
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(output.toString());
                renderer.layout();
                renderer.createPDF(os, true);
                os.close();
                Desktop.getDesktop().open(f);
            } catch (Exception ex) {
                LOG.error("Could not ouptut to PDF", ex);
            }
        }
    }

    private class ClipboardAction extends EditorAction {

        private final EditorButtons button;
        private final String command;

        public ClipboardAction(EditorButtons button) {
            super(button.getName());
            this.button = button;
            String ttip;
            int mnem;
            int accel;
            IconSet icon;
            switch (button) {
                case CUT:
                    ttip = "Cut (Ctrl+X)";
                    mnem = KeyEvent.VK_T;
                    accel = KeyEvent.VK_CUT;
                    icon = Actions.EDIT_CUT;
                    command = "cut-to";
                    break;
                case COPY:
                    ttip = "Copy (Ctrl+C)";
                    mnem = KeyEvent.VK_C;
                    accel = KeyEvent.VK_COPY;
                    icon = Actions.EDIT_COPY;
                    command = "copy-to";
                    break;
                case PASTE:
                    ttip = "Paste (Ctrl+V)";
                    accel = KeyEvent.VK_PASTE;
                    mnem = KeyEvent.VK_P;
                    icon = Actions.EDIT_PASTE;
                    command = "paste-from";
                    break;
                default:
                    throw new IllegalArgumentException(button.toString() + " is not a valid clipboard button type");
            }
            putValue(ACTION_COMMAND_KEY, button.getActionCommand());
            putValue(MNEMONIC_KEY, mnem);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accel, InputEvent.CTRL_MASK));
            putValue(SHORT_DESCRIPTION, ttip);
            putValue(LARGE_ICON_KEY, getIcon(icon, IconSizes.MEDIUM, (String) super.getValue(NAME)));
            putValue(SMALL_ICON, getIcon(icon, IconSizes.SMALL, (String) super.getValue(NAME)));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            kitActions.get(command + "-clipboard").actionPerformed(e);
            textPane.requestFocus();
        }

        public EditorButtons getButton() {
            return button;
        }
    }

    private class UndoAction extends EditorAction {

        public UndoAction() {
            super("Undo", "Undo (Ctrl+Z)", KeyEvent.VK_U, KeyEvent.VK_UNDO, Actions.EDIT_UNDO);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (undoer.canUndo()) {
                undoer.undo();
            }
            updateUndoRedo();
            textPane.requestFocus();
        }
    }

    private class RedoAction extends EditorAction {

        public RedoAction() {
            super("Redo", "Redo", KeyEvent.VK_R, KeyEvent.VK_Y, Actions.EDIT_REDO);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (undoer.canRedo()) {
                undoer.redo();
            }
            updateUndoRedo();
            textPane.requestFocus();
        }
    }

    private class FontStyleAction extends EditorAction {

        private final EditorButtons button;
        private final String command;

        public FontStyleAction(EditorButtons button) {
            super(button.getName());
            this.button = button;
            String ttip;
            int key;
            IconSet icon;
            switch (button) {
                case BOLD:
                    ttip = "<html><b>Bold</b> (Ctrl+B)";
                    key = KeyEvent.VK_B;
                    icon = Actions.FORMAT_TEXT_BOLD;
                    command = "bold";
                    break;
                case ITALIC:
                    ttip = "<html><i>Italic</i> (Ctrl+I)";
                    key = KeyEvent.VK_I;
                    icon = Actions.FORMAT_TEXT_ITALIC;
                    command = "italic";
                    break;
                case UNDERLINE:
                    ttip = "<html><u>Underline</u> (Ctrl+U)";
                    key = KeyEvent.VK_U;
                    icon = Actions.FORMAT_TEXT_UNDERLINE;
                    command = "underline";
                    break;
                default:
                    throw new IllegalArgumentException(button.toString() + " is not a valid font style button type");
            }
            putValue(ACTION_COMMAND_KEY, button.getActionCommand());
            putValue(MNEMONIC_KEY, key);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, InputEvent.CTRL_MASK));
            putValue(SHORT_DESCRIPTION, ttip);
            putValue(LARGE_ICON_KEY, getIcon(icon, IconSizes.MEDIUM, (String) super.getValue(NAME)));
            putValue(SMALL_ICON, getIcon(icon, IconSizes.SMALL, (String) super.getValue(NAME)));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            kitActions.get("font-" + command).actionPerformed(e);
            boolean select = textPane.getSelectionStart() == textPane.getSelectionEnd();
            putValue(SELECTED_KEY, select);
            textPane.requestFocus();
        }
    }

    private class FontTypeAction extends EditorAction {

        public FontTypeAction() {
            super("Font Family", "Fonts", -1, -1, Status.IMAGE_MISSING);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String value = "font-family-" + fontNameBox.getSelectedItem().toString();
            kitActions.get(value).actionPerformed(e);
            textPane.requestFocus();
        }
    }

    private class FontSizeAction extends EditorAction {

        public FontSizeAction() {
            super("Font Size", "Font Size", -1, -1, Status.IMAGE_MISSING);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object selected = fontSizeBox.getSelectedItem();
            String value = "font-size-" + ((selected != null) ? selected.toString() : "12");
            kitActions.get(value).actionPerformed(e);
            textPane.requestFocus();
        }
    }

    private class TextAlignAction extends EditorAction {

        private final EditorButtons button;
        private final String command;

        public TextAlignAction(EditorButtons button) {
            super(button.getName());
            this.button = button;
            String ttip;
            IconSet icon;
            switch (button) {
                case LEFT:
                    ttip = "Align Left";
                    icon = Actions.FORMAT_JUSTIFY_LEFT;
                    command = "left";
                    break;
                case CENTER:
                    ttip = "Center";
                    icon = Actions.FORMAT_JUSTIFY_CENTER;
                    command = "center";
                    break;
                case RIGHT:
                    ttip = "Align Right";
                    icon = Actions.FORMAT_JUSTIFY_RIGHT;
                    command = "right";
                    break;
                default:
                    throw new IllegalArgumentException(button.toString() + " is not a valid alignment button type");
            }
            putValue(ACTION_COMMAND_KEY, button.getActionCommand());
            putValue(SHORT_DESCRIPTION, ttip);
            putValue(LARGE_ICON_KEY, getIcon(icon, IconSizes.MEDIUM, (String) super.getValue(NAME)));
            putValue(SMALL_ICON, getIcon(icon, IconSizes.SMALL, (String) super.getValue(NAME)));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            kitActions.get(command + "-justify").actionPerformed(e);
            textPane.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HTMLEditor e = new HTMLEditor();
                e.showFrame(new Dimension(800,600));
            }
        });
    }
    //</editor-fold>
}