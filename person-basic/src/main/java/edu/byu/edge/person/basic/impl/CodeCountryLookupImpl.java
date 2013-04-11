package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.CodeCountryLookup;
import edu.byu.edge.person.basic.domain.CodeCountry;
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
 * Time: 8:32 PM
 */
public class CodeCountryLookupImpl implements CodeCountryLookup {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CodeCountryLookupImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CodeCountry> getAllCodeCountryCodes() {
		return jdbcTemplate.query(CODE_COUNTRY_SQL, new CodeCountryMapper());
	}

	private class CodeCountryMapper implements RowMapper<CodeCountry> {

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
}
