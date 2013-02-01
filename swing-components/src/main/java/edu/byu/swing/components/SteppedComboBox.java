package edu.byu.swing.components;

import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import edu.byu.swing.models.MutableListComboBoxModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import org.jvnet.substance.SubstanceComboBoxUI;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.utils.combo.SubstanceComboPopup;

/**
 * Combo box providing "Stepped-out" UI.  In effect, if the content of the combo
 * box is wider than the combo box, the popup will be widened to accomodate the
 * contents. <p>
 * This component is especially useful in tables.
 * @param <T> type of objects stored in combo box
 * @author jmooreoa
 * @version 1.0.1
 * @since 1.0.0
 * @see MutableListComboBoxModel
 */
public class SteppedComboBox<T> extends JComboBox {

    private static final long serialVersionUID = 7975315634777916028L;
    protected int popupWidth;
    private final MutableListComboBoxModel<T> model = new MutableListComboBoxModel<T>();

    /**
     * Creates an empty SteppedComboBox
     */
    @SuppressWarnings("unchecked")
    public SteppedComboBox() {
        super();
        setupUI(Collections.EMPTY_LIST);
    }

    /**
     * Creates a new SteppedComboBox
     * @param items items to be displayed
     */
    public SteppedComboBox(List<? extends T> items) {
        super();
        setupUI(items);
    }

    /**
     * Creates a new SteppedComboBox
     * @param items items to be displayed
     */
    public SteppedComboBox(Vector<? extends T> items) {
        super();
        setupUI(items);
    }

    /**
     * Creates a new SteppedComboBox
     * @param items items to be displayed
     */
    public SteppedComboBox(T[] items) {
        super();
        setupUI(Arrays.asList(items));
    }

    /**
     * Gets the selected item from the combo box, cast to the proper type
     * @return selected object
     */
    public T getTypedSelectedItem() {
        return (T) model.getSelectedItem();
    }

    private void setupUI(Collection<? extends T> items) {
        try {
            setUI(getProperUI());
            Dimension min = getMinimumSize();
            Dimension pref = getPreferredSize();
            Dimension max = getMaximumSize();
            popupWidth = pref.width;
            super.setModel(model);
            model.addAll(items);
            setMinimumSize(min);
            setPreferredSize(pref);
            setMaximumSize(max);
        } catch (Throwable t) {
            String fileName = System.getProperty("user.home") + File.separator + "netbeans_steppedcombobox.log";
            try {
                PrintWriter writer = new PrintWriter(fileName);
                t.printStackTrace(writer);
                writer.close();
            } catch (Throwable t2) {
                throw new RuntimeException("Unable to write stack trace", t2);
            }
        }
    }

    @Override
    public void updateUI() {
        setUI(getProperUI());

        ListCellRenderer renderer = getRenderer();
        if (renderer instanceof Component) {
            SwingUtilities.updateComponentTreeUI((Component)renderer);
        }
    }

    private ComboBoxUI getProperUI() {
        LookAndFeel laf = UIManager.getLookAndFeel();
        if (laf instanceof SubstanceLookAndFeel) {
            return new SubstanceSteppedComboBoxUI();
        } else if (laf instanceof WindowsLookAndFeel) {
            return new WindowsSteppedComboBoxUI();
        } else if (laf instanceof MotifLookAndFeel) {
            return new MotifSteppedComboBoxUI();
        } else {
            return new MetalSteppedComboBoxUI();
        }
    }

//    /**
//     * {@inheritDoc }
//     */
//    @Override
//    public void setModel(ComboBoxModel aModel) {
//        if (model != null) {
//            throw new IllegalStateException("model has already been set");
//        }
//        if (!(aModel instanceof MutableListComboBoxModel)) {
//            throw new IllegalArgumentException("Model is not valid type");
//        }
//        super.setModel(aModel);
//    }

    /**
     *
     * @return model used by this combo box
     */
    public MutableListComboBoxModel<T> getComboBoxModel() {
        return model;
    }

    /**
     * Sets options available in combo box
     * @param options new options
     */
    public void setOptions(List<? extends T> options) {
        model.clear();
        model.addAll(options);
    }

    /**
     * Sets options available in combo box
     * @param options new options
     */
    public void setOptions(T[] options) {
        setOptions(Arrays.asList(options));
    }

    /**
     * Sets the popup width, overriding the default width
     * @param width desired width of popup
     */
    public void setPopupWidth(int width) {
        popupWidth = width;
    }

    /**
     * Clears the selection
     */
    public void clearSelection() {
        super.setSelectedItem(null);
    }

    /**
     * Gets popup size
     * @return size of popup
     */
    public Dimension getPopupSize() {
        Dimension size = getPreferredSize();
        if (popupWidth < size.width) {
            popupWidth = size.width;
        }
        return new Dimension(popupWidth, size.height);
    }

    private class SubstanceSteppedComboBoxUI extends SubstanceComboBoxUI {

        @Override
        protected ComboPopup createPopup() {
            SubstanceComboPopup popup = new SubstanceComboPopup(comboBox) {

                @Override
                public void show() {
                    Dimension popupSize = ((SteppedComboBox) comboBox).getPopupSize();
                    popupSize.setSize(popupSize.width,
                            getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                    Rectangle popupBounds = new Rectangle(0, comboBox.getBounds().height, popupSize.width, popupSize.height);
                    scroller.setMaximumSize(popupBounds.getSize());
                    scroller.setPreferredSize(popupBounds.getSize());
                    scroller.setMinimumSize(popupBounds.getSize());
                    list.invalidate();
                    setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
                    show(comboBox, popupBounds.x, popupBounds.y);
                }
            };
            popup.getAccessibleContext().setAccessibleParent(comboBox);
            return popup;
        }
    }

    private class WindowsSteppedComboBoxUI extends WindowsComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
            return getBasicPopup(comboBox);
        }
    }

    private class MetalSteppedComboBoxUI extends MetalComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
            return getBasicPopup(comboBox);
        }
    }
    
    private class MotifSteppedComboBoxUI extends MotifComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
        BasicComboPopup popup = new MotifComboPopup(comboBox) {

                @Override
                public void show() {
                    Dimension popupSize = SteppedComboBox.this.getPopupSize();
                    popupSize.setSize(popupSize.width,
                            getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                    Rectangle popupBounds = new Rectangle(0, comboBox.getBounds().height, popupSize.width, popupSize.height);
                    scroller.setMaximumSize(popupBounds.getSize());
                    scroller.setPreferredSize(popupBounds.getSize());
                    scroller.setMinimumSize(popupBounds.getSize());
                    list.invalidate();
                    int selectedIndex = comboBox.getSelectedIndex();
                    if (selectedIndex == -1) {
                        list.clearSelection();
                    } else {
                        list.setSelectedIndex(selectedIndex);
                    }
                    list.ensureIndexIsVisible(list.getSelectedIndex());
                    setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
                    show(comboBox, popupBounds.x, popupBounds.y);
                }
            };
            popup.getAccessibleContext().setAccessibleParent(comboBox);
            return popup;
        };
    }

    private ComboPopup getBasicPopup(JComboBox comboBox) {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {

                @Override
                public void show() {
                    Dimension popupSize = SteppedComboBox.this.getPopupSize();
                    popupSize.setSize(popupSize.width,
                            getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                    Rectangle popupBounds = new Rectangle(0, comboBox.getBounds().height, popupSize.width, popupSize.height);
                    scroller.setMaximumSize(popupBounds.getSize());
                    scroller.setPreferredSize(popupBounds.getSize());
                    scroller.setMinimumSize(popupBounds.getSize());
                    list.invalidate();
                    int selectedIndex = comboBox.getSelectedIndex();
                    if (selectedIndex == -1) {
                        list.clearSelection();
                    } else {
                        list.setSelectedIndex(selectedIndex);
                    }
                    list.ensureIndexIsVisible(list.getSelectedIndex());
                    setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
                    show(comboBox, popupBounds.x, popupBounds.y);
                }
            };
            popup.getAccessibleContext().setAccessibleParent(comboBox);
            return popup;
    }
}
