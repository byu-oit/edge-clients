package edu.byu.edge.person.basic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.byu.edge.jdbc.ListResultSetExtractor;
import edu.byu.edge.person.basic.CodeStateLookup;
import edu.byu.edge.person.basic.domain.CodeState;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 8:58 PM
 */
public class CodeStateLookupImpl implements CodeStateLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
	private static final CodeStateMapper CODE_STATE_ROW_MAPPER = new CodeStateMapper();
	private static final ListResultSetExtractor<CodeState> CODE_STATES_EXTRACTOR = new ListResultSetExtractor<CodeState>(CODE_STATE_ROW_MAPPER);

	public CodeStateLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public CodeStateLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CodeState> getStateByCountry(String country) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("countryCode", country);
		return jdbcTemplate.query(CODE_STATE_SQL, paramMap, CODE_STATES_EXTRACTOR);
	}

	private static class CodeStateMapper implements RowMapper<CodeState> {

		@Override
		public CodeState mapRow(ResultSet rs, int rowNum) throws SQLException {
			CodeState codeState = new CodeState();
			codeState.setCountryCode(rs.getString("COUNTRY_CODE"));
			codeState.setStateCode(rs.getString("STATE_CODE"));
			codeState.setStateName(rs.getString("STATE"));
			return codeState;
		}
	}

	public static final String CODE_STATE_SQL = "select * from pro.code_state where country_code = :countryCode order by state";
}
