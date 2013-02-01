/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.byu.framework.swing.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JDialog;
import org.apache.log4j.Logger;

/**
 *
 * @author tylers2
 */
public class DialogUtil {

    private final static Logger LOG = Logger.getLogger(DialogUtil.class);

    public static Frame getSelectedWindow() {
        for (Frame frame : Frame.getFrames()) {
            if (frame.isActive()) {
                return frame;
            }
        }
        return null;
    }

    public static void centerFrame(Frame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenBounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        Point myLocation = getCenteringPoint(screenBounds, frame.getSize());
        frame.setLocation(myLocation);
    }

    public static void centerDialog(Frame parent, JDialog dialog) {
        if (parent == null) {
            parent = getSelectedWindow();
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenBounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        LOG.debug(String.format("Screen bounds: %s", screenBounds.toString()));

        Rectangle bounds = (parent != null) ? parent.getBounds() : screenBounds;
        LOG.debug(String.format("Selected bounds: %s", bounds));

        Dimension dialogSize = (dialog.isVisible()) ? dialog.getSize() : dialog.getPreferredSize();

        Point myLocation = getCenteringPoint(bounds, dialogSize);
        LOG.debug(String.format("Initial centering location: %s", myLocation));
        while (!screenBounds.contains(myLocation)) {
            LOG.debug("Point not within screen bounds");
            if (parent != null) {
                myLocation = getCenteringPoint(screenBounds, dialogSize);
                parent = null;
            } else {
                myLocation = screenBounds.getLocation();
            }
            LOG.debug(String.format("New point: %s", myLocation));
        }
        LOG.debug(String.format("Setting location to: %s", myLocation));
        dialog.setLocation(myLocation);
    }

    private static Point getCenteringPoint(Rectangle area, Dimension size) {
        Point base = area.getLocation();
        LOG.debug(String.format("Base: %s", base));
        LOG.debug(String.format("dx = %s / 2 - %s / 2", area.getWidth(), size.getWidth()));
        int dx = (int) (area.getWidth() / 2 - size.getWidth() / 2);
        LOG.debug(String.format("dy = %s / 2 - %s / 2", area.getHeight(), size.getHeight()));
        int dy = (int) (area.getHeight() / 2 - size.getHeight() / 2);
        base.translate(dx, dy);
        LOG.debug(String.format("dx: %s - dy: %s - translated: %s", dx, dy, base));
        return base;
    }
}
