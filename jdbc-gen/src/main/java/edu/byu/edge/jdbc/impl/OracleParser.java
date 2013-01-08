package edu.byu.edge.jdbc.impl;

import edu.byu.edge.jdbc.JdbcGen;
import edu.byu.edge.jdbc.Parser;
import edu.byu.edge.jdbc.domain.Column;
import edu.byu.edge.jdbc.domain.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/03/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/03/2012
 */
public class OracleParser implements Parser {

	public static final String DRIVER_CLASS_NAME = "oracle.jdbc.OracleDriver";

	private final NamedParameterJdbcOperations jdbc;
	private final TableRowMapper tableRowMapper;
	private final ColumnRowMapper columnRowMapper;

	public OracleParser(final NamedParameterJdbcOperations jdbc) {
		this.jdbc = jdbc;
		this.tableRowMapper = new TableRowMapper();
		this.columnRowMapper = new ColumnRowMapper();
		JdbcGen.setParser(this);
	}

	@Override
	public List<Table> getTables(final String schema) {
		final HashMap<String, Object> paramMap = new HashMap<String, Object>(2, .9999f);
		paramMap.put("schema", schema);
		return jdbc.query("select OWNER, TABLE_NAME from ALL_TABLES where OWNER = :schema", paramMap, tableRowMapper);
	}

	@Override
	public List<Column> getColumnsForTable(final Table t) {
		final HashMap<String, Object> map = new HashMap<String, Object>(3, .9999f);
		map.put("schema", t.getSchema());
		map.put("table", t.getTableName());
		return jdbc.query("select COLUMN_NAME, COLUMN_ID, NULLABLE, DATA_TYPE, CHAR_COL_DECL_LENGTH, DATA_PRECISION, DATA_SCALE from ALL_TAB_COLUMNS where owner = :schema and TABLE_NAME = :table", map, columnRowMapper);
	}

	@Override
	public List<Table> getColumnsForTables(final List<Table> tables) {
		for (final Table t : tables) {
			t.setColumns(getColumnsForTable(t));
		}
		return tables;
	}

	@Override
	public String getSchemaNameMessage() {
		return "Please enter schema. By default, in Oracle, schema names are in all upper-case letters.";
	}

	private static final class TableRowMapper implements RowMapper<Table> {
		@Override
		public Table mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new Table(
					rs.getString("OWNER"),
					rs.getString("TABLE_NAME"),
					""
			);
		}
	}

	private static final class ColumnRowMapper implements RowMapper<Column> {

		private static final Pattern NUM = Pattern.compile("^\\d+$");
		private static final Pattern SCI_NUM = Pattern.compile("^\\d{1}(\\.\\d+E\\-?\\d+)?");
		private static final Pattern BOOL = Pattern.compile("^[Yy1Tt]{1}.*$");

		private static int getInt(final ResultSet rs, final String colName) throws SQLException {
			final String s = rs.getString(colName);
			if (s == null || !(NUM.matcher(s).matches() || SCI_NUM.matcher(s).matches())) return 0;
			return rs.getInt(colName);
		}

		private static long getLong(final ResultSet rs, final String colName) throws SQLException {
			final String s = rs.getString(colName);
			if (s == null || !(NUM.matcher(s).matches() || SCI_NUM.matcher(s).matches())) return 0L;
			return rs.getLong(colName);
		}

		private static boolean getBool(final ResultSet rs, final String colName) throws SQLException {
			return BOOL.matcher(rs.getString(colName)).matches();
		}

		@Override
		public Column mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new Column(
					rs.getString("COLUMN_NAME"),
					getInt(rs, "COLUMN_ID"),
					getBool(rs, "NULLABLE"),
					rs.getString("DATA_TYPE"),
					getLong(rs, "CHAR_COL_DECL_LENGTH"),
					getLong(rs, "DATA_PRECISION"),
					getLong(rs, "DATA_SCALE"),
					rs.getString("DATA_TYPE"),
					""
			);
		}
	}
}
