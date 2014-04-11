package edu.byu.edge.person.basic.impl;

import edu.byu.edge.jdbc.ListResultSetExtractor;
import edu.byu.edge.person.basic.PhoneLookup;
import edu.byu.edge.person.basic.domain.PhoneInformation;
import edu.byu.edge.person.basic.domain.PhoneType;

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
 * Time: 10:13 AM
 */
public class PhoneLookupImpl implements PhoneLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
	private static final PhoneRowMapper PHONE_ROW_MAPPER = new PhoneRowMapper();
	private static final ListResultSetExtractor<PhoneInformation> PHONE_EXTRACTOR = new ListResultSetExtractor<PhoneInformation>(PHONE_ROW_MAPPER);
	
	public static final String PERSON_ID_COL = "person_id";
	public static final String PHONE_TYPE_COL = "phone_type";
	public static final String PHONE_NUMBER_COL = "phone_number";
	public static final String TYPE_OF_DEVICE_COL = "type_of_device";
	public static final String COUNTRY_CODE_COL = "country_code";
	public static final String MOBILE_COL = "mobile";
	public static final String CONTACT_STATUS_COL = "contact_status";
	public static final String UNLISTED_COL = "unlisted";
	public static final String PRIMARY_F_COL = "primary_f";

	public PhoneLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public PhoneLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PhoneInformation> getPhoneInformationByPersonId(String personId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		return jdbcTemplate.query(PHONE_LOOKUP_SQL, paramMap, PHONE_EXTRACTOR);
	}

	@Override
	public List<PhoneInformation> getPhoneInformationByPersonIdAndType(String personId, PhoneType phoneType) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		paramMap.put("phoneType", phoneType.toString());
		return jdbcTemplate.query(PHONE_LOOKUP_TYPE_SQL, paramMap, PHONE_EXTRACTOR);
	}

	private static class PhoneRowMapper implements RowMapper<PhoneInformation> {

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
			PRIMARY_F_COL + " from phone where person_id = :personId";

	private static final String PHONE_LOOKUP_TYPE_SQL = "select " + PERSON_ID_COL + ", " + PHONE_TYPE_COL + ", " + PHONE_NUMBER_COL + ", " +
			TYPE_OF_DEVICE_COL + ", " + COUNTRY_CODE_COL + ", " + MOBILE_COL + ", " + CONTACT_STATUS_COL + ", " + UNLISTED_COL + ", " +
			PRIMARY_F_COL + " from phone where person_id = :personId and " + PHONE_TYPE_COL + " = :phoneType";
}
