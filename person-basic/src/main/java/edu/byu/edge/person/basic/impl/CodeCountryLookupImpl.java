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
import edu.byu.edge.jdbc.ObjectResultSetExtractor;
import edu.byu.edge.person.basic.CodeCountryLookup;
import edu.byu.edge.person.basic.domain.CodeCountry;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 8:32 PM
 */
public class CodeCountryLookupImpl implements CodeCountryLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
	private static final CodeCountryMapper CODE_COUNTRY_ROW_MAPPER = new CodeCountryMapper();
	private static final ObjectResultSetExtractor<CodeCountry> CODE_COUNTRY_EXTRACTOR = new ObjectResultSetExtractor<CodeCountry>(CODE_COUNTRY_ROW_MAPPER);
	private static final ListResultSetExtractor<CodeCountry> CODE_COUNTRIES_EXTRACTOR = new ListResultSetExtractor<CodeCountry>(CODE_COUNTRY_ROW_MAPPER);

	public CodeCountryLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public CodeCountryLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CodeCountry> getAllCodeCountryCodes() {
		Map<String, String> paramMap = new HashMap<String, String>();
		return jdbcTemplate.query(CODE_COUNTRY_SQL, paramMap, CODE_COUNTRIES_EXTRACTOR);
	}

	@Override
	public CodeCountry getCountryByCodeId(String countryCode) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("countryCode", countryCode);
		return jdbcTemplate.query(CODE_ID_SQL, paramMap, CODE_COUNTRY_EXTRACTOR);
	}

	private static class CodeCountryMapper implements RowMapper<CodeCountry> {

		@Override
		public CodeCountry mapRow(ResultSet rs, int rowNumb) throws SQLException {
			CodeCountry cc = new CodeCountry();
			cc.setContinentCode(rs.getString("CONTINENT_CODE"));
			cc.setCountry(rs.getString("COUNTRY"));
			cc.setCountryCode(rs.getString("COUNTRY_CODE"));
			cc.setCountryPhonePrefix(rs.getString("COUNTRY_PHONE_PREFIX"));
			cc.setIsoCode(rs.getString("ISO_CODE"));
			cc.setIsoCode3(rs.getString("ISO_CODE_3"));
			return cc;
		}
	}

	public static final String CODE_COUNTRY_SQL = "select * from pro.code_country order by country";
	public static final String CODE_ID_SQL = "select * from pro.code_country where country_code = :countryCode order by country";
}
