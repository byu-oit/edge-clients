package edu.byu.edge.jdbc.domain;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/03/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/03/2012
 */
public class Column {
	protected String columnName;
	protected int position;
	protected boolean nullable;
	protected String dataType;
	protected long maxCharLength;
	protected long numPrec;
	protected long numScale;
	protected String colType;
	protected String key;

	public Column() {
	}

	public Column(final String columnName,
			final int position,
			final boolean nullable,
			final String dataType,
			final long maxCharLength, final long numPrec, final long numScale, final String colType, final String key) {
		this.columnName = columnName;
		this.position = position;
		this.nullable = nullable;
		this.dataType = dataType;
		this.maxCharLength = maxCharLength;
		this.numPrec = numPrec;
		this.numScale = numScale;
		this.colType = colType;
		this.key = key;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public boolean getNullable() {
		return nullable;
	}

	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(final String dataType) {
		this.dataType = dataType;
	}

	public long getMaxCharLength() {
		return maxCharLength;
	}

	public void setMaxCharLength(final int maxCharLength) {
		this.maxCharLength = maxCharLength;
	}

	public long getNumPrec() {
		return numPrec;
	}

	public void setNumPrec(final int numPrec) {
		this.numPrec = numPrec;
	}

	public long getNumScale() {
		return numScale;
	}

	public void setNumScale(final int numScale) {
		this.numScale = numScale;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(final String colType) {
		this.colType = colType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Column column = (Column) o;

		if (position != column.position) return false;
		if (!columnName.equals(column.columnName)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = columnName.hashCode();
		result = 31 * result + position;
		return result;
	}

	@Override
	public String toString() {
		return "Column{" +
				"columnName='" + columnName + '\'' +
				", position=" + position +
				", nullable=" + nullable +
				", dataType='" + dataType + '\'' +
				", maxCharLength=" + maxCharLength +
				", numPrec=" + numPrec +
				", numScale=" + numScale +
				", colType='" + colType + '\'' +
				", key='" + key + '\'' +
				'}';
	}
}
