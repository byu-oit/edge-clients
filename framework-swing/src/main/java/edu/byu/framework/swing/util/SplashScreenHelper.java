/**
 * Name: SplashScreenHelper.java
 * Date Created: Feb 6, 2009
 */
package edu.byu.framework.swing.util;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author tylers2
 */
@Deprecated
public class SplashScreenHelper {

    private final static Logger LOG = Logger.getLogger(SplashScreenHelper.class);
    private Color foreground;
    private Color background;
    private URL imageURL;
    private final Point messagePoint;
    private static SplashScreenHelper helper;
    private String appName;
    private Rectangle progressBounds;

    private SplashScreenHelper() {
        messagePoint = new Point();
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }

    public Point getMessagePoint() {
        return messagePoint;
    }

    public void setMessageLocation(int x, int y) {
        messagePoint.x = x;
        messagePoint.y = y;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Rectangle getProgressBounds() {
	return progressBounds;
    }

    public void setProgressBounds(Rectangle progressBounds) {
	this.progressBounds = progressBounds;
    }

    public synchronized static SplashScreenHelper getHelper() {
        if (helper == null) {
            helper = new SplashScreenHelper();
        }
        return helper;
    }
}
