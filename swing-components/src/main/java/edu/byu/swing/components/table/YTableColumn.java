package edu.byu.swing.components.table;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Extended implementation of TableColumn allowing constructor-level customization
 * @author olsens
 * @see TableColumn
 * @version 1.0.0
 * @since 1.0.0
 */
public class YTableColumn extends TableColumn {

    /**
     * Creates basic YTableColumn
     */
    public YTableColumn() {
            super();
    }
    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     * @param width default width
     * @param header column header
     */
    public YTableColumn(int modelIndex, int width, String header) {
        super(modelIndex, width);
        setHeaderValue(header);
    }
    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     * @param width default width
     * @param header column header
     * @param resize if column is resizeable
     */
    public YTableColumn(int modelIndex, int width, String header, boolean resize) {
        super(modelIndex, width);
        setHeaderValue(header);
        setResizable(resize);
    }
    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     * @param width default width
     */
    public YTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
    }
    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     */
    public YTableColumn(int modelIndex) {
        super(modelIndex);
    }

    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     * @param width default width
     * @param cellRenderer renderer for all cells in column
     * @param cellEditor editor for all cells in column
     */
    public YTableColumn(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
    }

    /**
     * Creates a new YTableColumn
     * @param modelIndex column in TableModel
     * @param width default width
     * @param header column header
     * @param resize is column resizeable
     * @param cellRenderer renderer for all cells in column
     * @param cellEditor editor for all cells in column
     */
    public YTableColumn(int modelIndex, int width, String header, boolean resize, TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
        setHeaderValue(header);
        setResizable(resize);
    }
}
