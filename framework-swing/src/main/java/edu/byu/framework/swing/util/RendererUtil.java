package edu.byu.framework.swing.util;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * @version 1.0.0
 * @author jmooreoa
 */
public final class RendererUtil {
	private RendererUtil() {}

	public static ListCellRenderer getDefaultListRenderer() {
		return (ListCellRenderer) UIManager.getLookAndFeelDefaults().get("List.cellRenderer");
	}
}
