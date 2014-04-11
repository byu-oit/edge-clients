package edu.byu.edge.person.basic.impl;

import edu.byu.edge.jdbc.ListResultSetExtractor;
import edu.byu.edge.jdbc.ObjectResultSetExtractor;
import edu.byu.edge.person.basic.AddressLookup;
import edu.byu.edge.person.basic.domain.Address;
import edu.byu.edge.person.basic.domain.AddressType;
import edu.byu.edge.person.basic.domain.BasicPerson;
import edu.byu.edge.person.basic.impl.BasicPersonLookupImpl.BasicPersonRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 12:18 PM
 */
public class AddressLookupImpl implements AddressLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
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
	
	private static final AddressRowMapper ADDRESS_ROW_MAPPER = new AddressRowMapper();
	private static final ObjectResultSetExtractor<Address> ADDRESS_EXTRACTOR = new ObjectResultSetExtractor<Address>(ADDRESS_ROW_MAPPER);
	private static final ListResultSetExtractor<Address> ADDRESSES_EXTRACTOR = new ListResultSetExtractor<Address>(ADDRESS_ROW_MAPPER);

	public AddressLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public AddressLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Address> getAddressByPersonId(String personId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		return jdbcTemplate.query(ADDRESS_LOOKUP, paramMap, ADDRESSES_EXTRACTOR);
	}

	@Override
	public Address getAddressByPersonIdAndType(String personId, AddressType addressType) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		paramMap.put("addressType", addressType.toString());
		return jdbcTemplate.query(ADDRESS_TYPE_LOOKUP, paramMap, ADDRESS_EXTRACTOR);
	}

	private static class AddressRowMapper implements RowMapper<Address> {

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
			"from address where person_id = :personId";

	public static final String ADDRESS_TYPE_LOOKUP = "select " + PERSON_ID_COL + ", " + ADDRESS_TYPE_COL + ", " + LINE_1 + ", " +
			"" + LINE_2 + ", " + LINE_3 + ", " + LINE_4 + ", " + COUNTRY_CODE_COL + ", " + CITY_COL + ", " + STATE_CODE_COL + ", " +
			"" + POSTAL_CODE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " + CAMPUS_ADDRESS_COL + " " +
			"from address where person_id = :personId and " + ADDRESS_TYPE_COL + " = :addressType";
}
