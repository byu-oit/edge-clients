package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.BasicPerson;
import edu.byu.edge.person.basic.BasicPersonLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/24/12
 * Time: 3:43 PM
 */
public class BasicPersonLookupImpl implements BasicPersonLookup {

	private final JdbcTemplate cesTemplate;
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

	@Autowired
	public BasicPersonLookupImpl(JdbcTemplate jdbcTemplate) {
		this.cesTemplate = jdbcTemplate;
	}

	@Override
	public BasicPerson getPersonByPersonId(final String personId) {
		return cesTemplate.queryForObject(BASIC_LOOKUP_SQL, new BasicPersonRowMapper(), personId);
	}

	@Override
	public List<BasicPerson> getPersonsByListPersonIds(final List<String> personIds) {
		List<BasicPerson> returningList = new LinkedList<BasicPerson>();
		List<String> persons = new LinkedList<String>();
		int begin = 0;
		for (int end = 0; personIds.size() != end; end++, begin++) {
			persons.add(personIds.get(end));
			if (begin == 4) {
				returningList.addAll(getFivePersonQuery(persons));
				begin = -1;
				persons.clear();
			}
		}
		if (begin != 0) {
			returningList.addAll(getFivePersonQuery(persons));
		}
		return returningList;
	}

	@Override
	public BasicPerson getPersonByNetId(final String netId) {
		return cesTemplate.queryForObject(BASIC_NET_ID_LOOKUP_SQL, new BasicPersonRowMapper(), netId);
	}

	@Override
	public BasicPerson getPersonByByuId(String byuId) {
		return cesTemplate.queryForObject(BASIC_BYU_ID_LOOKUP_SQL, new BasicPersonRowMapper(), byuId);
	}

	private List<BasicPerson> getFivePersonQuery(List<String> personIds) {
		final Object[] objects = new Object[5];
		int size = personIds.size();
		for (int i = 0; i <= 4; i++) {
			if (size != 0) {
				size--;
			}
			objects[i] = personIds.get(size);
		}
		return cesTemplate.query(MULTIPLE_LOOKUP_SQL, new BasicPersonRowMapper(), objects);
	}

	private class BasicPersonRowMapper implements RowMapper<BasicPerson> {

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
					rs.getString(RELIGION_CODE));
		}
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
			"p.religion_code as " + RELIGION_CODE + " " +
			"from pro.person p " +
			"where p.person_id=?";

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
			"p.religion_code as " + RELIGION_CODE + " " +
			"from pro.person p " +
			"where p.byu_id=?";

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
			"p.religion_code as " + RELIGION_CODE + " " +
			"from pro.person p " +
			"where p.net_id=?";

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
			"p.religion_code as " + RELIGION_CODE + " " +
			"from pro.person p " +
			"where p.person_id in (?, ?, ?, ?, ?)";
}
