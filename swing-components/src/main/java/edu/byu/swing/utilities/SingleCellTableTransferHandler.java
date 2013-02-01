package edu.byu.swing.utilities;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * User: jmooreoa
 * Date: Apr 15, 2010
 * Time: 1:31:01 PM
 */
public class SingleCellTableTransferHandler extends TransferHandler {
	protected Transferable createTransferable(JComponent c) {
		if (c instanceof JTable) {
			JTable table = (JTable) c;
			int row = table.getSelectedRow();
			int col = table.getSelectedColumn();

			if (row < 0 || col < 0) {
				return null;
			}

			TableCellRenderer renderer = table.getCellRenderer(row, col);

			Object value = table.getValueAt(row, col);
			Component comp = renderer.getTableCellRendererComponent(table, value, true, true, row, col);
			if (comp instanceof JLabel) {
				value = ((JLabel) comp).getText();
			} else if (comp instanceof JTextComponent) {
				value = ((JTextComponent) comp).getText();
			}
			return new StringSelection((value != null) ? value.toString() : null);
		}

		return null;
	}

	public int getSourceActions(JComponent c) {
		return COPY;
	}
}
