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

	public OracleParser(final NamedParameterJdbcOperations jdbc) {
		this.jdbc = jdbc;
		this.tableRowMapper = new TableRowMapper();
		JdbcGen.setParser(this);
	}

	@Override
	public List<Table> getTables(final String schema) {
		final HashMap<String, Object> paramMap = new HashMap<String, Object>(2, .9999f);
		paramMap.put("schema", schema);
		return jdbc.query("select * from DBA_TABLES where TABLE_SCHEMA = :schema", paramMap, tableRowMapper);
	}

	@Override
	public List<Column> getColumnsForTable(final Table t) {
		return null;
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
}
