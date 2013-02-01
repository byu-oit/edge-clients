/**
 * Name: UserPrefs.java
 * Date Created: Feb 26, 2009
 */
package edu.byu.framework.swing;

import edu.byu.framework.swing.exceptions.ExceptionInternalHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.OfficeSilver2007Skin;

/**
 *
 * @author tylers2
 */
public class UserPrefs {

    private final static Logger LOG = Logger.getLogger(UserPrefs.class);
    private final static Properties properties;
    private final static String fileName;
    public final static String PROPERTY_SUBSTANCE_SKIN = "substanceskin";

    static {
        fileName = System.getProperty("user.home") + File.separator + ".byu_swing_prefs";
        Properties result = null;
		try {
            result = setup();
        } catch (Throwable t) {
			LOG.warn("Unable to load user preferences", t);
			if (result == null) {
				result = new Properties();
			}
        } finally {
			properties = result;
		}
    }

    private static Properties setup() throws IOException, FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            return new Properties();
        }

        Properties props = new Properties();
        props.load(new FileInputStream(file));
        return props;
    }

    public static String getPreference(String key) {
        return properties.getProperty(key);
    }

    public static void setPreference(String key, String value) {
        properties.setProperty(key, value);
        try {
            savePrefrences();
        } catch (Throwable t) {
            ExceptionInternalHandler.doHandle(t);
        }
    }

    private static void savePrefrences() throws Exception {
        LOG.debug("saving preferences");

        FileOutputStream fos = new FileOutputStream(fileName);
        properties.store(fos, "BYU Swing Prefrence Save");
        fos.close();
    }

    public static void setLAF() {
        try {
            String skinClassName = UserPrefs.getPreference(UserPrefs.PROPERTY_SUBSTANCE_SKIN);
            if (skinClassName == null) {
                skinClassName = OfficeSilver2007Skin.class.getName();
            }
            SubstanceLookAndFeel.setSkin(skinClassName);
        } catch (Throwable t) {
            LOG.error("Unable to set LAF", t);
            System.exit(-1);
        }
    }
}
