/*
 * SplashScreenForm.java
 *
 * Created on Feb 18, 2009, 12:49:07 PM
 */
package edu.byu.framework.swing.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SplashScreen;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Frame for displaying a splash screen.
 * @author tylers2
 */
public abstract class SplashScreenForm extends java.awt.Frame {

    private static final long serialVersionUID = 9182146654777077058L;
    private BufferedImage image;
    private final static Logger LOG = Logger.getLogger(SplashScreenForm.class);
    private String message;
    private Color fg;
    private Point messagePoint;
    private SplashScreen splashScreen;
    private boolean isSetup = false;
    private static int percent;
    private Rectangle progressBounds;

    public static int getPercent() {
	return percent;
    }

    public static void setPercent(int percent) {
	SplashScreenForm.percent = percent;
    }

    public Rectangle getProgressBounds() {
	return progressBounds;
    }

    public void setProgressBounds(Rectangle progressBounds) {
	this.progressBounds = progressBounds;
    }

    

    /** Creates new  SplashScreenForm
     */
    public SplashScreenForm() {
        super();
        initComponents();
    }

    /**
     * Sets up form
     * @param splashScreen splashscreen, if provided by application jar
     * @param url splash image location
     * @param fg forground color
     * @param messagePoint center point of log message
     */
    public void setup(SplashScreen splashScreen, URL url, Color fg, Point messagePoint) {
        LOG.debug("setting up...");
        this.fg = fg;
        this.messagePoint = messagePoint;
        this.splashScreen = splashScreen;

        if (url == null) {
            throw new IllegalArgumentException("No image url specified");
        }

        LOG.debug("loading image");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(url);
        } catch (Throwable t) {
            LOG.debug("Unable to load image", t);
        }
        image = bufferedImage;

        LOG.debug("bounds init");
        if (splashScreen == null) {
            int width = image.getWidth();
            int height = image.getHeight();
            Rectangle bounds = new Rectangle(0, 0, width, height);
            LOG.debug("setting bounds: " + bounds);
            setBounds(bounds);
            DialogUtil.centerFrame(this);
        } else {
            Rectangle bounds = splashScreen.getBounds();
            LOG.debug("setting bounds: " + bounds);
            setBounds(bounds);
        }

        LOG.debug("image: " + image);

        BYUAppenderListener listener = new BYUAppenderListener() {

            public void log(LoggingEvent loggingEvent) {
                if (!loggingEvent.getLevel().equals(Level.INFO)) {
                    return;
                }

                SplashScreenForm.this.message = loggingEvent.getMessage().toString();
                SplashScreenForm.this.repaint();
            }
        };

        BYUAppender.addListener(listener);
        isSetup = true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setVisible(boolean b) {
        if (b && splashScreen != null) {
            splashScreen.close();
            splashScreen = null;
        }
        super.setVisible(b);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void paint(Graphics g) {
        if (!isSetup) {
            throw new IllegalStateException("setup has not been run!");
        }
        int width = getSize().width;
        int height = getSize().height;
        Image bufferImage = createImage(width, height);
        Graphics buffer = bufferImage.getGraphics();
        Graphics2D buffer2D = (Graphics2D) buffer;
        if (image == null) {
            buffer.clearRect(0, 0, getSize().width, getSize().height);
            return;
        }

        buffer.drawImage(image, 0, 0, this);//getSize().width, getSize().height, this);

        if (message != null) {
            buffer.setColor(fg);
            DrawingUtil.drawCenteredString(buffer2D, message, buffer2D.getFont(), messagePoint.x, messagePoint.y);
//            Point startPoint = DrawingUtil.getCenteringStartPoint(buffer2D, message, buffer2D.getFont(), messagePoint);
//            buffer.drawString(message, startPoint.x, startPoint.y);
        }
	if (progressBounds != null) {
	    buffer2D.setColor(fg);
	    buffer2D.drawRect(progressBounds.x, progressBounds.y, progressBounds.width, progressBounds.height);
	    buffer2D.fillRect(progressBounds.x, progressBounds.y, (int) (progressBounds.width * ((float) percent / 100)), progressBounds.height);
	}
        g.drawImage(bufferImage, 0, 0, this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setResizable(false);
        setUndecorated(true);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
