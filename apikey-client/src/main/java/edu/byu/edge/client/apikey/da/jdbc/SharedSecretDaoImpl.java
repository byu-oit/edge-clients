package edu.byu.edge.client.apikey.da.jdbc;

import edu.byu.edge.client.apikey.da.SharedSecretDao;
import edu.byu.edge.client.apikey.domain.SharedSecret;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/14/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/14/2012
 */
public class SharedSecretDaoImpl implements SharedSecretDao {

	private static final Logger LOG = Logger.getLogger(SharedSecretDaoImpl.class);

	protected final NamedParameterJdbcTemplate jdbc;
	protected final RowMapper<SharedSecret> deptRowMapper;
	protected final RowMapper<SharedSecret> ssRowMapper;

	private static final String SQL_BY_ID = "select c.*, s.* from CREDENTIAL c join SHARED_SECRET_CREDENTIAL s on c.CREDENTIAL_ID = s.CREDENTIAL_ID where c.CREDENTIAL_ID = :id";
	private static final String SQL_BY_PERSONID = "select c.*, s.* from CREDENTIAL c join SHARED_SECRET_CREDENTIAL s on c.CREDENTIAL_ID = s.CREDENTIAL_ID where c.PERSON_ID = :id";
	private static final String SQL_ALL_DEPTS = "select s.*, p.SORT_NAME from CREDENTIAL s join PERSON_LNK p on s.PERSON_ID = p.PERSON_ID and p.ORGANIZATION_F = 'Y' where s.TYPE = 'SHARED SECRET' and s.EXPIRATION_DATE > sysdate order by upper(p.SORT_NAME)";
	private static final Map<String, ?> EMPTY = new HashMap<String, Object>(0);

	public SharedSecretDaoImpl(final NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
		deptRowMapper = new DeptRowMapper();
		ssRowMapper = new SharedSecretRowMapper();
	}

	@Override
	public SharedSecret findById(final int credentialId) {
		final Map<String, Integer> map = new HashMap<String, Integer>(2, .9999f);
		map.put("id", credentialId);
		return jdbc.queryForObject(SQL_BY_ID, map, ssRowMapper);
	}

	@Override
	public List<SharedSecret> findByPersonId(final String personId) {
		final Map<String, String> map = new HashMap<String, String>(2, .9999f);
		map.put("id", personId);
		return jdbc.query(SQL_BY_PERSONID, map, ssRowMapper);
	}

	@Override
	public List<SharedSecret> findAllDepartment() {
		return jdbc.query(SQL_ALL_DEPTS, EMPTY, deptRowMapper);
	}

	private static final class SharedSecretRowMapper implements RowMapper<SharedSecret> {
		@Override
		public SharedSecret mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new SharedSecret(
					rs.getInt("CREDENTIAL_ID"),
					rs.getString("PERSON_ID"),
					rs.getTimestamp("EXPIRATION_DATE"),
					rs.getString("SHARED_SECRET_VALUE"),
					rs.getString("WS_ID"),
					"");
		}
	}

	private static final class DeptRowMapper implements RowMapper<SharedSecret> {
		@Override
		public SharedSecret mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new SharedSecret(
					rs.getInt("CREDENTIAL_ID"),
					rs.getString("PERSON_ID"),
					rs.getTimestamp("EXPIRATION_DATE"),
					"", "",
					rs.getString("SORT_NAME"));
		}
	}
}
