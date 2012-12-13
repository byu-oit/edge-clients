package edu.byu.edge.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/12/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/12/2012
 */
public class ObjectResultSetExtractor<T> implements ResultSetExtractor<T> {

	protected final RowMapper<T> mapper;

	public ObjectResultSetExtractor(final RowMapper<T> mapper) {
		this.mapper = mapper;
	}

	@Override
	public T extractData(final ResultSet rs) throws SQLException, DataAccessException {
		if (rs.next()) {
			return mapper.mapRow(rs, 0);
		} else {
			return null;
		}
	}
}
