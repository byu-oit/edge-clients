package edu.byu.edge.jdbc.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/13/2012
 */
public final class StringRowMapper implements RowMapper<String> {

	public static final StringRowMapper MAPPER = new StringRowMapper();

	private StringRowMapper() {
	}

	@Override
	public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		return rs.getString(1);
	}
}
