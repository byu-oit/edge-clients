package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.CodeStateLookup;
import edu.byu.edge.person.basic.domain.CodeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 8:58 PM
 */
public class CodeStateLookupImpl implements CodeStateLookup {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CodeStateLookupImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CodeState> getStateByCountry(String country) {
		return jdbcTemplate.query(CODE_STATE_SQL, new CodeStateMapper(), country);
	}

	private class CodeStateMapper implements RowMapper<CodeState> {

		@Override
		public CodeState mapRow(ResultSet rs, int rowNum) throws SQLException {
			CodeState codeState = new CodeState();
			codeState.setCountryCode(rs.getString("COUNTRY_CODE"));
			codeState.setStateCode(rs.getString("STATE_CODE"));
			codeState.setStateName(rs.getString("STATE_NAME"));
			return codeState;
		}
	}

	public static final String CODE_STATE_SQL = "select * from pro.code_state where country_code = ? order by state";
}
