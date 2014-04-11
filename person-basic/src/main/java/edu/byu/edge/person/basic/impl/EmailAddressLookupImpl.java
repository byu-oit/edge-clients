package edu.byu.edge.person.basic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.byu.edge.jdbc.ObjectResultSetExtractor;
import edu.byu.edge.person.basic.EmailAddressLookup;
import edu.byu.edge.person.basic.domain.Email;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/26/12
 * Time: 1:14 PM
 */
public class EmailAddressLookupImpl implements EmailAddressLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
	public static final String PERSON_ID = "person_id";
	public static final String EMAIL_ADDRESS = "email_address";
	private static final EmailRowMapper EMAIL_ROW_MAPPER = new EmailRowMapper();
	private static final ObjectResultSetExtractor<Email> CODE_COUNTRY_EXTRACTOR = new ObjectResultSetExtractor<Email>(EMAIL_ROW_MAPPER);

	public EmailAddressLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public EmailAddressLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Email getByPersonId(String personId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		return jdbcTemplate.query(EMAIL_LOOKUP, paramMap, CODE_COUNTRY_EXTRACTOR);
	}

	private static class EmailRowMapper implements RowMapper<Email> {

		@Override
		public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Email(rs.getString(PERSON_ID), rs.getString(EMAIL_ADDRESS));
		}
	}

	public static final String EMAIL_LOOKUP = "select " + PERSON_ID + ", " + EMAIL_ADDRESS + " from email_address where person_id = :personId";
}
