package edu.byu.edge.person.basic.impl;

import edu.byu.edge.jdbc.ListResultSetExtractor;
import edu.byu.edge.person.basic.PersonAddressLookup;
import edu.byu.edge.person.basic.domain.Address;
import edu.byu.edge.person.basic.domain.BasicPerson;
import edu.byu.edge.person.basic.domain.PersonAddress;

import org.apache.log4j.Logger;
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
 * Date: 4/9/13
 * Time: 11:31 AM
 */
public class PersonAddressLookupImpl implements PersonAddressLookup {

	private static final Logger LOG = Logger.getLogger(PersonAddressLookupImpl.class);
	private final NamedParameterJdbcOperations jdbcTemplate;
	
	private static final PersonAddressHelperRowMapper PERSON_ADDRESS_ROW_MAPPER = new PersonAddressHelperRowMapper();
	private static final ListResultSetExtractor<PersonAddress> PERSON_ADDRESSES_EXTRACTOR = new ListResultSetExtractor<PersonAddress>(PERSON_ADDRESS_ROW_MAPPER);

	private static final String PERSON_SELECT = "p.PERSON_ID, p.BYU_ID, p.NET_ID, p.SSN, p.SORT_NAME, p.REST_OF_NAME, p.GENDER, p.MARITAL_STATUS";
	private static final String ADDRESS_SELECT = "a.ADDRESS_TYPE, a.ADDRESS_LINE_1, a.ADDRESS_LINE_2, a.ADDRESS_LINE_3, a.ADDRESS_LINE_4, a.CITY, a.STATE_CODE, a.POSTAL_CODE, a.COUNTRY_CODE";
	private static final String SEARCH_SELECT = "select " + PERSON_SELECT + ", " + ADDRESS_SELECT;

	private static final String SEARCH_FROM = "from PERSON p join ADDRESS a on p.PERSON_ID = a.PERSON_ID";

	private static final String SELECT_FROM = SEARCH_SELECT + " " + SEARCH_FROM;

	private static final String PERSON_WHERE_IDS = "p.BYU_ID like :name or p.SSN like :name";
	private static final String PERSON_WHERE_NAMES = "upper(p.SURNAME) like :upperName or upper(p.REST_OF_NAME) like :upperName or upper(p.SORT_NAME) like :upperName or upper(p.PREFERRED_FIRST_NAME) like :upperName";
	private static final String PERSON_WHERE_NET_ID = "p.NET_ID like :lowerName";

	private static final String SEARCH_NAME_ONLY = SELECT_FROM + " where (" + PERSON_WHERE_NAMES + " or " + PERSON_WHERE_NET_ID + ")";
	private static final String SEARCH_ID_ONLY = SELECT_FROM + " where (" + PERSON_WHERE_IDS + " or " + PERSON_WHERE_NET_ID + ")";
	private static final String SEARCH_NET_ID_ONLY = SELECT_FROM + " where (" + PERSON_WHERE_NET_ID + ")";
	private static final String SEARCH_ADDR_ONLY_BASE = SELECT_FROM + " where ";
	private static final String SSN_BASE = "select PERSON_ID from PERSON where SSN like :name";
	private static final String BYU_ID_BASE = "select PERSON_ID from PERSON where BYU_ID like :name";
	private static final String NET_ID_BASE = "select PERSON_ID from PERSON where NET_ID like :lowerName";
	private static final String SN_BASE = "select PERSON_ID from PERSON where upper(SORT_NAME) like :upperName";
	private static final String RON_BASE = "select PERSON_ID from PERSON where upper(REST_OF_NAME) like :upperName";
	private static final String SUR_BASE = "select PERSON_ID from PERSON where upper(SURNAME) like :upperName";
	private static final String PFN_BASE = "select PERSON_ID from PERSON where upper(PREFERRED_FIRST_NAME) like :upperName";
	private static final String UNION_ALL = " union all ";
	private static final String INTERSECT = " intersect ";
	private static final String NET_ID_SEARCH = NET_ID_BASE;
	private static final String NUMERIC_SEARCH = SSN_BASE + UNION_ALL + BYU_ID_BASE + UNION_ALL + NET_ID_BASE;
	private static final String NAME_SEARCH = SN_BASE + UNION_ALL + RON_BASE + UNION_ALL + SUR_BASE + UNION_ALL + PFN_BASE + UNION_ALL + NET_ID_BASE;
	private static final String WHERE = " where p.PERSON_ID in (";
	private static final String ORDER_BY = " order by upper(p.SORT_NAME)";

	public PersonAddressLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	public PersonAddressLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PersonAddress> performAddressSearch(String nameMask, String addressLine1Mask, String cityMask, String stateMask, String zipCodeMask) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("performing address search <%s> <%s> <%s> <%s> <%s>", nameMask, addressLine1Mask, cityMask, stateMask, zipCodeMask));
		}
		final String name = createQueryParamFromInput(nameMask);
		final String line1 = createQueryParamFromInput(addressLine1Mask);
		final String city = createQueryParamFromInput(cityMask);
		final String state = createQueryParamFromInput(stateMask);
		final String zip = createQueryParamFromInput(zipCodeMask);

		final Map<String, String> params = new HashMap<String, String>(9, .8f);
		params.put("lowerName", name.toLowerCase());
		params.put("name", name);
		params.put("upperName", name.toUpperCase());
		params.put("line1", line1.toUpperCase());
		params.put("city", city.toUpperCase());
		params.put("state", state.toUpperCase());
		params.put("zip", zip.toUpperCase());

		final boolean personHasNumbers = name.matches("^.*?\\d+.*?$");
		final boolean personHasLetters = name.matches("^.*?[a-zA-Z]+.*?$");
		final StringBuilder query = new StringBuilder(1024);
		boolean isFirst = true;

		if (name.equals("%")) {
			query.append(SEARCH_ADDR_ONLY_BASE);
		} else if (personHasLetters && personHasNumbers) {
			query.append(SEARCH_NET_ID_ONLY);
			isFirst = false;
		} else if (personHasLetters && !personHasNumbers) {
			query.append(SEARCH_NAME_ONLY);
			isFirst = false;
		} else if (!personHasLetters && personHasNumbers) {
			query.append(SEARCH_ID_ONLY);
			isFirst = false;
		}

		isFirst = checkAndAppendAddress(query, isFirst, line1, "ADDRESS_LINE_1", "line1");
		isFirst = checkAndAppendAddress(query, isFirst, city, "CITY", "city");
		isFirst = checkAndAppendAddress(query, isFirst, state, "STATE_CODE", "state");
		isFirst = checkAndAppendAddress(query, isFirst, zip, "POSTAL_CODE", "zip");
		query.append(" order by upper(p.SORT_NAME)");
		final String sql = query.toString();
		return jdbcTemplate.query(sql, params, PERSON_ADDRESSES_EXTRACTOR);
	}

	@Override
	public List<PersonAddress> performAddressSearchOptimized(String nameMask, String addressLine1Mask, String cityMask, String stateMask, String zipCodeMask) {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("performing address search <%s> <%s> <%s> <%s> <%s>", nameMask, addressLine1Mask, cityMask, stateMask, zipCodeMask));
		}
		final String name = createQueryParamFromInput(nameMask);
		final String line1 = createQueryParamFromInput(addressLine1Mask);
		final String city = createQueryParamFromInput(cityMask);
		final String state = createQueryParamFromInput(stateMask);
		final String zip = createQueryParamFromInput(zipCodeMask);

		final Map<String, String> params = new HashMap<String, String>(9, .8f);
		params.put("lowerName", name.toLowerCase());
		params.put("name", name);
		params.put("upperName", name.toUpperCase());
		params.put("line1", line1.toUpperCase());
		params.put("city", city.toUpperCase());
		params.put("state", state.toUpperCase());
		params.put("zip", zip.toUpperCase());

		final String secondAddressWhere = generateSecondAddressWhere(params);

		final StringBuilder query = new StringBuilder(SELECT_FROM);
		query.append(WHERE);
		query.append(generateSubQuery(params));
		query.append(secondAddressWhere);
		query.append(ORDER_BY);

		final String sql = query.toString();
		LOG.debug(sql);
		return jdbcTemplate.query(sql, params, PERSON_ADDRESSES_EXTRACTOR);
	}

	private static final class PersonAddressHelperRowMapper implements RowMapper<PersonAddress> {
		@Override
		public PersonAddress mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final BasicPerson basicPerson = new BasicPerson(rs.getString("PERSON_ID"), rs.getString("NET_ID"), rs.getString("REST_OF_NAME"), null, null, rs.getString("BYU_ID"), null, rs.getString("GENDER"), null, null, rs.getString("SSN"));
			final Address address = new Address();
			address.setAddressType(rs.getString("ADDRESS_TYPE"));
			address.setLine1(rs.getString("ADDRESS_LINE_1"));
			address.setLine2(rs.getString("ADDRESS_LINE_2"));
			address.setLine3(rs.getString("ADDRESS_LINE_3"));
			address.setLine4(rs.getString("ADDRESS_LINE_4"));
			address.setCity(rs.getString("CITY"));
			address.setStateCode(rs.getString("STATE_CODE"));
			address.setPostalCode(rs.getString("POSTAL_CODE"));
			address.setCountryCode(rs.getString("COUNTRY_CODE"));
			return new PersonAddress(basicPerson, address);
		}
	}

	private static String generateSecondAddressWhere(final Map<String, String> params) {
		return ")" + processAddressPortion(params, false);
	}

	private static boolean checkAndAppendAddress(
			final StringBuilder query, final boolean isFirst, final String fieldVal, final String fieldName, final String paramName
	) {
		if (fieldVal.equals("%")) {
			return isFirst;
		}
		if (!isFirst) {
			query.append(" and");
		}
		query.append(" upper(a.");
		query.append(fieldName);
		query.append(") like :");
		query.append(paramName);
		return false;
	}

	private static String generateAddressPortion(final Map<String, String> params) {
		final String where = processAddressPortion(params, true);
		if (where.length() == 0) {
			return "";
		}
		return "select PERSON_ID from ADDRESS where (" + where.replaceAll("a\\.", "") + ")";
	}

	private static String generateSubQuery(final Map<String, String> params) {
		final String pp = generatePersonPortion(params);
		final String ap = generateAddressPortion(params);
		if (pp.equals("") && ap.equals("")) {
			//how is this possible, it means all null parameters
			return "";
		}
		if (pp.contains("select") && ap.contains("select")) {
			return "select distinct PERSON_ID from (" + pp + ")" + INTERSECT + ap;
		}
		if (pp.contains("select")) {
			return "select distinct PERSON_ID from (" + pp + ")";
		}
		return ap;
	}

	private static String generatePersonPortion(final Map<String, String> params) {
		final String name = params.get("name");
		if (name.equals("%")) {
			return "";
		}
		final boolean personHasNumbers = name.matches("^.*?\\d+.*?$");
		final boolean personHasLetters = name.matches("^.*?[a-zA-Z]+.*?$");
		if (personHasLetters && personHasNumbers) {
			return NET_ID_SEARCH;
		}
		if (personHasLetters && ! personHasNumbers) {
			return NAME_SEARCH;
		}
		if (! personHasLetters && personHasNumbers) {
			return NUMERIC_SEARCH;
		}
		return "";
	}

	private static String processAddressPortion(final Map<String, String> params, final boolean isFirstParam) {
		boolean isFirst = isFirstParam;
		final StringBuilder where = new StringBuilder(512);
		isFirst = checkAndAppendAddress(where, isFirst, params.get("line1"), "ADDRESS_LINE_1", "line1");
		isFirst = checkAndAppendAddress(where, isFirst, params.get("city"), "CITY", "city");
		isFirst = checkAndAppendAddress(where, isFirst, params.get("state"), "STATE_CODE", "state");
		isFirst = checkAndAppendAddress(where, isFirst, params.get("zip"), "POSTAL_CODE", "zip");
		return where.toString();
	}

	private static String createQueryParamFromInput(final String nameMask) {
		if (nameMask == null) return "%";
		if (nameMask.trim().isEmpty()) return "%";
		return "%" + nameMask.trim().replaceAll("\\*", "%").replaceAll("\\?", "_") + "%";
	}
}
