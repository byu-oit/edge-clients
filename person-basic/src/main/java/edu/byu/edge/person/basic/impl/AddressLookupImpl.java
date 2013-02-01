package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.Address;
import edu.byu.edge.person.basic.AddressLookup;
import edu.byu.edge.person.basic.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 12:18 PM
 */
public class AddressLookupImpl implements AddressLookup {

	private final JdbcTemplate jdbcTemplate;
	public static final String PERSON_ID_COL = "person_id";
	public static final String ADDRESS_TYPE_COL = "address_type";
	public static final String LINE_1 = "address_line_1";
	public static final String LINE_2 = "address_line_2";
	public static final String LINE_3 = "address_line_3";
	public static final String LINE_4 = "address_line_4";
	public static final String COUNTRY_CODE_COL = "country_code";
	public static final String CITY_COL = "city";
	public static final String STATE_CODE_COL = "state_code";
	public static final String POSTAL_CODE_COL = "postal_code";
	public static final String CONTACT_STATUS_COL = "contact_status";
	public static final String UNLISTED_COL = "unlisted";
	public static final String CAMPUS_ADDRESS_COL = "campus_address_f";

	@Autowired
	public AddressLookupImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Address> getAddressByPersonId(String personId) {
		return jdbcTemplate.query(ADDRESS_LOOKUP, new AddressRowMapper(), personId);
	}

	@Override
	public Address getAddressByPersonIdAndType(String personId, AddressType addressType) {
		final Object[] objects = new Object[2];
		objects[0] = personId;
		objects[1] = addressType.toString();
		try{
			return jdbcTemplate.queryForObject(ADDRESS_TYPE_LOOKUP, new AddressRowMapper(), objects);
		} catch (EmptyResultDataAccessException e){
			return new Address();
		}
	}

	private class AddressRowMapper implements RowMapper<Address> {

		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Address(
					rs.getString(PERSON_ID_COL),
					rs.getString(ADDRESS_TYPE_COL),
					rs.getString(LINE_1),
					rs.getString(LINE_2),
					rs.getString(LINE_3),
					rs.getString(LINE_4),
					rs.getString(COUNTRY_CODE_COL),
					rs.getString(CITY_COL),
					rs.getString(STATE_CODE_COL),
					rs.getString(POSTAL_CODE_COL),
					rs.getString(CONTACT_STATUS_COL),
					rs.getString(UNLISTED_COL),
					rs.getString(CAMPUS_ADDRESS_COL)
			);
		}
	}

	public static final String ADDRESS_LOOKUP = "select " + PERSON_ID_COL + ", " + ADDRESS_TYPE_COL + ", " + LINE_1 + ", " +
			"" + LINE_2 + ", " + LINE_3 + ", " + LINE_4 + ", " + COUNTRY_CODE_COL + ", " + CITY_COL + ", " + STATE_CODE_COL + ", " +
			"" + POSTAL_CODE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " + CAMPUS_ADDRESS_COL + " " +
			"from address where person_id = ?";

	public static final String ADDRESS_TYPE_LOOKUP = "select " + PERSON_ID_COL + ", " + ADDRESS_TYPE_COL + ", " + LINE_1 + ", " +
			"" + LINE_2 + ", " + LINE_3 + ", " + LINE_4 + ", " + COUNTRY_CODE_COL + ", " + CITY_COL + ", " + STATE_CODE_COL + ", " +
			"" + POSTAL_CODE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " + CAMPUS_ADDRESS_COL + " " +
			"from address where person_id = ? and " + ADDRESS_TYPE_COL + " = ?";
}
