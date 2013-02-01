/**
 * Name: SubstanceSkinMenu.java
 * Date Created: Feb 26, 2009
 */
package edu.byu.swing.components;

import edu.byu.framework.swing.UserPrefs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SkinInfo;

/**
 * Menu for selecting a Substance Look-and-feel.  Also displays which look and feel
 * is currently selected.
 * @author tylers2
 * @version 1.0.1
 * @since 1.0.0
 */
public class SubstanceSkinMenu extends JMenu {

    private static final long serialVersionUID = 2861933418182646867L;
    private final static Map<String, JMenuItem> ITEMS = new HashMap<String, JMenuItem>();
    private static final Map<String, SkinInfo> SKINS = SubstanceLookAndFeel.getAllSkins();

    static {
        buildMenu();
    }

    /**
     * Creates a new SubstanceSkinMenu
     */
    public SubstanceSkinMenu() {
        super("Themes");
        for (JMenuItem item : getMenuItems()) {
            add(item);
        }
        this.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                String currentName = SubstanceLookAndFeel.getCurrentSkin().getDisplayName();
                for (String name : ITEMS.keySet()) {
                    if (currentName.equals(name)) {
                        ITEMS.get(name).setSelected(true);
                    }
                }
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
    }

    /**
     *
     * @return all menu items
     */
    public static Collection<JMenuItem> getMenuItems() {
        List<JMenuItem> result = new ArrayList<JMenuItem>(ITEMS.values());
        Collections.sort(result, new Comparator<JMenuItem>() {

            public int compare(JMenuItem o1, JMenuItem o2) {
                return o1.getText().compareToIgnoreCase(o2.getText());
            }
        });
        return result;
    }

    private static void buildMenu() {
        ButtonGroup group = new ButtonGroup();
        for (String key : SKINS.keySet()) {
            final SkinInfo skinInfo = SKINS.get(key);
            JRadioButtonMenuItem miLaf = new JRadioButtonMenuItem(skinInfo.getDisplayName());
            group.add(miLaf);

            miLaf.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String className = skinInfo.getClassName();
                    UserPrefs.setPreference(UserPrefs.PROPERTY_SUBSTANCE_SKIN, className);
                    SubstanceLookAndFeel.setSkin(className);
                }
            });
            ITEMS.put(skinInfo.getDisplayName(), miLaf);
        }
    }
}
