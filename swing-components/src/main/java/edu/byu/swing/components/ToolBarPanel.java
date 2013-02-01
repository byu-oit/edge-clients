package edu.byu.swing.components;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * Container to display {@code JToolBar} components.
 *
 * @author jmooreoa
 * @see JPanel, JToolBar
 * @version 1.0.0
 * @since 1.0.0
 */
public class ToolBarPanel extends JPanel{
    private int toolBarCount = 0;
    private Map<JToolBar, WrapperPanel> panelMap = new HashMap<JToolBar, WrapperPanel>();
    private boolean isHorizontal;

    /**
     * Creates a horizontal {@code ToolBarPanel}
     */
    public ToolBarPanel() {
        this(SwingConstants.HORIZONTAL);
    }

    /**
     * Creates a {@code ToolBarPanel} with given alignment.
     *
     * <p>Alignment must be either {@code SwingConstants.HORIZONTAL} or
     * {@code SwingConstants.VERTICAL}.
     * @param alignment toolbar alignment
     * @see SwingConstants
     * @throws IllegalArgumentException if alignment is neither {@code SwingConstants.HORIZONTAL} nor
     * {@code SwingConstants.VERTICAL}
     */
    public ToolBarPanel(int alignment) {
        if (alignment == SwingConstants.VERTICAL) {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            isHorizontal = false;
        } else if (alignment == SwingConstants.HORIZONTAL) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            isHorizontal = true;
        } else {
            throw new IllegalArgumentException(
                    "Invalid Alignment: " +
                    "Must be either SwingConstants.VERTICAL " +
                    "or SwingConstants.HORIZONTAL");
        }
    }
    /**
     * Adds a {@code JToolBar} to this panel
     * @param bar bar to add
     */
    public void addToolBar(JToolBar bar) {
        WrapperPanel wrapper = new WrapperPanel(bar);
        panelMap.put(bar, wrapper);
        this.add(wrapper);
        toolBarCount++;
    }

    /**
     * Removes specified {@code JToolBar} from the panel
     * @param bar toolbar to remove
     * @return {@code true} if panel changed
     */
    public boolean removeToolBar(JToolBar bar) {
        JPanel panel = panelMap.get(bar);
        if (panel == null) {
            return false;
        }
        panel.remove(bar);
        remove(panel);
        panelMap.remove(bar);
        toolBarCount--;
        return true;
    }

    /**
     * Adds tool bar at the specified index
     * @param bar
     * @param index
     */
    public void addToolBar(JToolBar bar, int index) {
        WrapperPanel wrapper = new WrapperPanel(bar);
        panelMap.put(bar, wrapper);
        this.add(wrapper, index);
        toolBarCount++;
    }

    /**
     * Removes toolbar at specified index
     * @param index
     * @return {@code true} if the panel changed
     */
    public boolean removeToolBar(int index) {
        WrapperPanel panel = (WrapperPanel) this.getComponent(index);
        if (panel == null) {
            return false;
        }
        panel.remove(index);
        remove(panel);
        panelMap.remove(panel.getWrappedBar());
        toolBarCount--;
        return true;
    }

    /**
     * Returns count of toolbars in this panel
     * @return number of toolbars
     */
    public int getToolBarCount() {
        return toolBarCount;
    }

    /**
     * Returns toolbar alignment: either {@code SwingConstants.HORIZONTAL} or
     * {@code SwingConstants.VERTICAL}.
     * @return toolbar alignment
     */
    public int getAlignment() {
        return isHorizontal ? SwingConstants.HORIZONTAL : SwingConstants.VERTICAL;
    }

    private class WrapperPanel extends JPanel {
        private final JToolBar wrappedBar;

        public WrapperPanel(JToolBar wrappedBar) {
            super(new BorderLayout());
            this.wrappedBar = wrappedBar;
            super.add(wrappedBar, isHorizontal ? BorderLayout.NORTH : BorderLayout.WEST);
        }

        public JToolBar getWrappedBar() {
            return wrappedBar;
        }
    }
}
