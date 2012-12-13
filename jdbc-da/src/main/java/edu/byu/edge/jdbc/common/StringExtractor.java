package edu.byu.edge.jdbc.common;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
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
public class StringExtractor implements ResultSetExtractor<String> {

	/** Returns a null value when none exists. */
	public static final StringExtractor NULL_EXTRACTOR = new StringExtractor(true);

	/** Returns an empty string when no value exists */
	public static final StringExtractor EMPTY_EXTRACTOR = new StringExtractor(false);

	private final RowMapper<String> mapper = StringRowMapper.MAPPER;

	private final boolean returnNull;

	private StringExtractor(final boolean returnNull) {
		this.returnNull = returnNull;
	}

	@Override
	public String extractData(final ResultSet rs) throws SQLException, DataAccessException {
		final String s = doExtract(rs);
		return s == null && !returnNull ? "" : s;
	}

	private String doExtract(final ResultSet rs) throws SQLException {
		if (rs.next())
			return mapper.mapRow(rs, 1);
		else
			return null;
	}
}
