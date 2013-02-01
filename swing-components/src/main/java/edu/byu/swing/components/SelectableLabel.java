package edu.byu.swing.components;

import java.awt.Graphics;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * @version 1.0.0
 * @author jmooreoa
 */
public class SelectableLabel extends JTextField {

    public SelectableLabel() {
	super.setEditable(false);
	super.setBorder(null);
	super.setOpaque(false);
    }

    @Override
    public void setVisible(boolean aFlag) {
	super.setVisible(aFlag);
    }

    public SelectableLabel(String text) {
	this();
	this.setText(text);
    }

    @Override
    public void setEditable(boolean b) {
    }

    @Override
    public void setBorder(Border border) {
    }

    @Override
    public void paint(Graphics g) {
	UIDefaults d = UIManager.getLookAndFeelDefaults();
	this.setBackground(d.getColor("Label.background"));
	this.setDisabledTextColor(d.getColor("Label.disabledForeground"));
	this.setFont(d.getFont("Label.font"));
	this.setForeground(d.getColor("Label.foreground"));
	super.paint(g);
    }
}
