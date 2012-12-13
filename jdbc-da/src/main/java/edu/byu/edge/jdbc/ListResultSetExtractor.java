package edu.byu.edge.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/12/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/12/2012
 */
public class ListResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

	protected final RowMapper<T> mapper;

	public ListResultSetExtractor(final RowMapper<T> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<T> extractData(final ResultSet rs) throws SQLException, DataAccessException {
		final List<T> list = new LinkedList<T>();
		int i = 0;
		while (rs.next()) {
			list.add(mapper.mapRow(rs, i++));
		}
		return list;
	}
}
