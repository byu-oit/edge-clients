package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.EmailAddressLookup;
import edu.byu.edge.person.basic.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/26/12
 * Time: 1:14 PM
 */
public class EmailAddressLookupImpl implements EmailAddressLookup {

	private final JdbcTemplate jdbcTemplate;
	public static final String PERSON_ID = "person_id";
	public static final String EMAIL_ADDRESS = "email_address";

	@Autowired
	public EmailAddressLookupImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Email getByPersonId(String personId) {
		try {
			return jdbcTemplate.queryForObject(EMAIL_LOOKUP, new EmailRowMapper(), personId);
		} catch (EmptyResultDataAccessException e) {
			return new Email();
		}
	}

	private class EmailRowMapper implements RowMapper<Email> {

		@Override
		public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Email(rs.getString(PERSON_ID), rs.getString(EMAIL_ADDRESS));
		}
	}

	public static final String EMAIL_LOOKUP = "select " + PERSON_ID + ", " + EMAIL_ADDRESS + " from email_address where person_id = ?";
}
