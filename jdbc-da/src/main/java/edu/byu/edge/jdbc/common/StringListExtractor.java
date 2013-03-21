package edu.byu.edge.jdbc.common;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 03/21/2013
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 03/21/2013
 */
public class StringListExtractor implements ResultSetExtractor<List<String>> {

	public static final StringListExtractor EXTRACTOR = new StringListExtractor();

	protected final StringRowMapper mapper = StringRowMapper.MAPPER;

	private StringListExtractor() {
	}

	@Override
	public List<String> extractData(final ResultSet rs) throws SQLException, DataAccessException {
		final List<String> result = new LinkedList<String>();
		int i = 0;
		while (rs.next()) {
			result.add(mapper.mapRow(rs, ++i));
		}
		return result;
	}
}
