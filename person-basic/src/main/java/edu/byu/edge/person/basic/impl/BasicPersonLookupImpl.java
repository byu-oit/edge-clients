package edu.byu.edge.person.basic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.byu.edge.jdbc.ListResultSetExtractor;
import edu.byu.edge.jdbc.ObjectResultSetExtractor;
import edu.byu.edge.person.basic.BasicPersonLookup;
import edu.byu.edge.person.basic.domain.BasicPerson;
import edu.byu.util.QueryUtils;
import edu.byu.util.SelectableQueryExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/24/12
 * Time: 3:43 PM
 */
public class BasicPersonLookupImpl implements BasicPersonLookup {

	private static final BasicPersonRowMapper BASIC_PERSON_ROW_MAPPER = new BasicPersonRowMapper();
	private static final ObjectResultSetExtractor<BasicPerson> BASIC_PERSON_EXTRACTOR = new ObjectResultSetExtractor<BasicPerson>(BASIC_PERSON_ROW_MAPPER);
	private static final ListResultSetExtractor<BasicPerson> BASIC_PERSONS_EXTRACTOR = new ListResultSetExtractor<BasicPerson>(BASIC_PERSON_ROW_MAPPER);
	private final SelectableQueryExecutor<String, BasicPerson> personsByPersonIdsQueryExecutor;

	protected final static Logger LOG = Logger.getLogger(BasicPersonLookupImpl.class);

	private final NamedParameterJdbcOperations cesTemplate;
	private static final String PERSON_ID_COL = "person_id";
	private static final String NET_ID_COL = "net_id";
	private static final String PREFERRED_NAME_COL = "preferred_name";
	private static final String SURNAME_COL = "surname";
	private static final String REST_OF_NAME_COL = "rest_of_name";
	private static final String BYU_ID_COL = "byu_id";
	private static final String BIRTH_DATE_COL = "birth_date";
	private static final String GENDER = "gender";
	private static final String ORGANIZATION_F = "organization_f";
	private static final String RELIGION_CODE = "religion_code";
	public static final String SSN = "ssn";

	public BasicPersonLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}

	public BasicPersonLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.cesTemplate = jdbcTemplate;
		this.personsByPersonIdsQueryExecutor =  new PersonsByPersonIdsQueryExecutor(cesTemplate);
	}

	@Override
	public BasicPerson getPersonByPersonId(final String personId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		return cesTemplate.query(BASIC_LOOKUP_SQL, paramMap, BASIC_PERSON_EXTRACTOR);
	}
	
	@Override
	public List<BasicPerson> getPersonsByListPersonIds(final List<String> personIds) {
		return QueryUtils.doQueryWithParameterList(personIds, personsByPersonIdsQueryExecutor);
	}

	@Override
	public List<BasicPerson> searchBy(String searchParam){
		List<BasicPerson> returningList = new LinkedList<BasicPerson>();
		Map<String, String> paramMap = new HashMap<String, String>();
		if(Character.isDigit(searchParam.charAt(0))) {
			searchParam = searchParam.replace("-", "");
			paramMap.put("byuIdSearch", addPercentToString(searchParam));
			return cesTemplate.query(SEARCH_BYU_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
		} else if (searchParam.startsWith("=")) {//Take out the =
			paramMap.put("personIdSearch", addPercentToString(searchParam.substring(1)));
			return cesTemplate.query(SEARCH_FOR_PERSON_ID, paramMap, BASIC_PERSONS_EXTRACTOR);
		} else if (searchParam.matches(".*\\d.*")) {//Looking for netId
			paramMap.put("netIdSearch", addPercentToString(searchParam));
			return cesTemplate.query(SEARCH_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
		} else if (searchParam.contains(",")) {
			paramMap.put("sortNameSearch", addPercentToString(searchParam));
			return cesTemplate.query(SEARCH_SORT_NAME_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
		} else {
			String[] tokens = searchParam.split(" ");
			if(tokens.length == 1){
				paramMap.put("sortNameSearch", addPercentToString(tokens[0]));
				paramMap.put("netIdSearch", addPercentToString(tokens[0]));
				paramMap.put("restOfNameSearch", addPercentToString(tokens[0]));
				paramMap.put("surNameSearch", addPercentToString(tokens[0]));
				return cesTemplate.query(SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
			} else if (tokens.length == 2){
				paramMap.put("sortNameSearch", "");
				paramMap.put("netIdSearch", "");
				paramMap.put("restOfNameSearch", addPercentToString(tokens[0]));
				paramMap.put("surNameSearch", addPercentToString(tokens[1]));
				return cesTemplate.query(SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
			} else if (tokens.length == 3) {
				//Search for last name
				paramMap.put("sortNameSearch", "");
				paramMap.put("netIdSearch", "");
				paramMap.put("restOfNameSearch", addPercentToString(tokens[0]));
				paramMap.put("surNameSearch", addPercentToString(tokens[1] + " " + tokens[2]));
				returningList.addAll(cesTemplate.query(SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR));
				
				//Search for middle name
				paramMap.put("restOfNameSearch", addPercentToString(tokens[0] + " " + tokens[1]));
				paramMap.put("surNameSearch", addPercentToString(tokens[2]));
				returningList.addAll(cesTemplate.query(SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR));
				return returningList;
			} else {
				paramMap.put("sortNameSearch", "");
				paramMap.put("netIdSearch", "");
				paramMap.put("restOfNameSearch", addPercentToString(tokens[0]));
				paramMap.put("surNameSearch", addPercentToString(tokens[1]));
				return cesTemplate.query(SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSONS_EXTRACTOR);
			}
		}
	}

	@Override
	public BasicPerson getPersonByNetId(final String netId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("netId", netId);
		return cesTemplate.query(BASIC_NET_ID_LOOKUP_SQL, paramMap, BASIC_PERSON_EXTRACTOR);
	}

	@Override
	public BasicPerson getPersonByByuId(String byuId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("byuId", byuId);
		return cesTemplate.query(BASIC_BYU_ID_LOOKUP_SQL, paramMap, BASIC_PERSON_EXTRACTOR);
	}

	@Override
	public BasicPerson getPersonBySsn(final String ssn) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("ssn", ssn);
		return cesTemplate.query(BASIC_SSN_LOOKUP_SQL, paramMap, BASIC_PERSON_EXTRACTOR);
	}

	public static class BasicPersonRowMapper implements RowMapper<BasicPerson> {

		@Override
		public BasicPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new BasicPerson(
					rs.getString(PERSON_ID_COL),
					rs.getString(NET_ID_COL),
					rs.getString(REST_OF_NAME_COL),
					rs.getString(PREFERRED_NAME_COL),
					rs.getString(SURNAME_COL),
					rs.getString(BYU_ID_COL),
					rs.getDate(BIRTH_DATE_COL),
					rs.getString(GENDER),
					rs.getString(ORGANIZATION_F),
					rs.getString(RELIGION_CODE),
					rs.getString(SSN));
		}
	}
	
	private static class PersonsByPersonIdsQueryExecutor implements SelectableQueryExecutor<String, BasicPerson> {
		
		public PersonsByPersonIdsQueryExecutor(NamedParameterJdbcOperations jdbcTemplate) {
			super();
			this.jdbcTemplate = jdbcTemplate;
		}

		private NamedParameterJdbcOperations jdbcTemplate;
		
		@Override
		public List<BasicPerson> doQuery(final List<String> personIds) {
			if(personIds == null || personIds.isEmpty()) {
				return new ArrayList<BasicPerson>();
			}
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			paramMap.put("personIds", personIds);
			return jdbcTemplate.query(MULTIPLE_LOOKUP_SQL, paramMap, new ListResultSetExtractor<BasicPerson>(BASIC_PERSON_ROW_MAPPER));
		}
	}

	private String addPercentToString(String searchParm){
		return "%" + searchParm + "%";
	}

	private static final String BASIC_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.person_id = :personId";

	private static final String SEARCH_FOR_PERSON_ID = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.person_id like :personIdSearch";

	private static final String BASIC_BYU_ID_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.byu_id=:byuId";

	private static final String SEARCH_BYU_ID_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.byu_id like :byuIdSearch";

	private static final String BASIC_NET_ID_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.net_id=:netId";

	private static final String SEARCH_NET_ID_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where upper(p.net_id) like upper(:netIdSearch) " +
			"and net_id != ' ' " +
			"order by p.net_id";

	private static final String SEARCH_SORT_NAME_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where upper(p.sort_name) like upper(:sortNameSearch) " +
			"and net_id != ' ' " +
			"order by p.net_id";

	private static final String SEARCH_SORT_NAME_OR_NET_ID_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where (upper(p.sort_name) like upper(:sortNameSearch) or upper(net_id) like upper(:netIdSearch) or (upper(p.rest_of_name) like upper(:restOfNameSearch) and upper(surname) like upper(:surNameSearch))) " +
			"and net_id != ' ' " +
			"order by net_id ";

	private static final String BASIC_SSN_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.ssn=:ssn";

	private static final String MULTIPLE_LOOKUP_SQL = "select " +
			"p.person_id as " + PERSON_ID_COL + ", " +
			"p.net_id as " + NET_ID_COL + ", " +
			"p.rest_of_name as " + REST_OF_NAME_COL + ", " +
			"p.preferred_first_name as " + PREFERRED_NAME_COL + ", " +
			"p.surname as " + SURNAME_COL + ", " +
			"p.byu_id as " + BYU_ID_COL + ", " +
			"p.date_of_birth as " + BIRTH_DATE_COL + ", " +
			"p.gender as " + GENDER + "," +
			"p.organization_f as " + ORGANIZATION_F + ", " +
			"p.religion_code as " + RELIGION_CODE + ", " +
			"p.ssn as " + SSN + " " +
			"from pro.person p " +
			"where p.person_id in (:personIds)";
}
