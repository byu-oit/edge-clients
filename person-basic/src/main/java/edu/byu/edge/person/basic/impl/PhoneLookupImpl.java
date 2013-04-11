package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.PhoneLookup;
import edu.byu.edge.person.basic.domain.PhoneInformation;
import edu.byu.edge.person.basic.domain.PhoneType;
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
 * Time: 10:13 AM
 */
public class PhoneLookupImpl implements PhoneLookup {

	private final JdbcTemplate jdbcTemplate;
	public static final String PERSON_ID_COL = "person_id";
	public static final String PHONE_TYPE_COL = "phone_type";
	public static final String PHONE_NUMBER_COL = "phone_number";
	public static final String TYPE_OF_DEVICE_COL = "type_of_device";
	public static final String COUNTRY_CODE_COL = "country_code";
	public static final String MOBILE_COL = "mobile";
	public static final String CONTACT_STATUS_COL = "contact_status";
	public static final String UNLISTED_COL = "unlisted";
	public static final String PRIMARY_F_COL = "primary_f";

	@Autowired
	public PhoneLookupImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PhoneInformation> getPhoneInformationByPersonId(String personId) {
		return jdbcTemplate.query(PHONE_LOOKUP_SQL, new PhoneRowMapper(), personId);
	}

	@Override
	public PhoneInformation getPhoneInformationByPersonIdAndType(String personId, PhoneType phoneType) {
		final Object[] objects = new Object[2];
		objects[0] = personId;
		objects[1] = phoneType.toString();
		try{
			return jdbcTemplate.queryForObject(PHONE_LOOKUP_TYPE_SQL, new PhoneRowMapper(), objects);
		} catch (EmptyResultDataAccessException e){
			return new PhoneInformation();
		}
	}

	private class PhoneRowMapper implements RowMapper<PhoneInformation> {

		@Override
		public PhoneInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new PhoneInformation(
					rs.getString(PERSON_ID_COL),
					rs.getString(PHONE_TYPE_COL),
					rs.getString(PHONE_NUMBER_COL),
					rs.getString(TYPE_OF_DEVICE_COL),
					rs.getString(COUNTRY_CODE_COL),
					rs.getString(MOBILE_COL),
					rs.getString(CONTACT_STATUS_COL),
					rs.getString(UNLISTED_COL),
					rs.getString(PRIMARY_F_COL)
			);
		}
	}

	private static final String PHONE_LOOKUP_SQL = "select " + PERSON_ID_COL + ", " + PHONE_TYPE_COL + ", " + PHONE_NUMBER_COL + ", " +
			TYPE_OF_DEVICE_COL + ", " + COUNTRY_CODE_COL + ", " + MOBILE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " +
			PRIMARY_F_COL + " from phone where person_id = ?";

	private static final String PHONE_LOOKUP_TYPE_SQL = "select " + PERSON_ID_COL + ", " + PHONE_TYPE_COL + ", " + PHONE_NUMBER_COL + ", " +
			TYPE_OF_DEVICE_COL + ", " + COUNTRY_CODE_COL + ", " + MOBILE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " +
			PRIMARY_F_COL + " from phone where person_id = ? and " + PHONE_TYPE_COL + " = ?";
}
