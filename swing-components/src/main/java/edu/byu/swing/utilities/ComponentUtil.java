package edu.byu.swing.utilities;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

/**
 * @version 1.0.0
 * @author jmooreoa
 */
public final class ComponentUtil {
    private ComponentUtil() {}

    public static CharSequence getNamedPathToComponent(Component c) {
        StringBuilder sb = new StringBuilder();
        if (c instanceof JScrollPane) {
            sb.append(String.format(" -> %s", ((JScrollPane) c).getViewport().getView().getName()));
        }
        while (c != null) {
            if (c.getName() != null) {
                sb.append(String.format(" -> %s", c.getName()));
            }
            c = c.getParent();
        }
        return sb;
    }

	public static void invokeAndWait(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
			return;
		}
		try {
			SwingUtilities.invokeAndWait(r);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to invoke/wait for task", e);
		}
	}

}
