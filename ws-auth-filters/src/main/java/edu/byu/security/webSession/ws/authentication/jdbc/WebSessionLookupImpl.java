package edu.byu.security.webSession.ws.authentication.jdbc;

import edu.byu.security.webSession.ws.authentication.WebSessionData;
import edu.byu.security.webSession.ws.authentication.WebSessionLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 1/17/13
 * Time: 10:16 AM
 */
public class WebSessionLookupImpl implements WebSessionLookup {

	private final JdbcTemplate casTemplate;
	public static final String SESSION_KEY = "session_key";
	public static final String USER_NAME = "user_name";
	public static final String INACTIVE_EXPIRE_DATE_TIME = "inactive_expire_date_time";
	public static final String EXPIRE_DATE_TIME = "expire_date_time";

	@Autowired
	public WebSessionLookupImpl(@Qualifier("casTemplate") JdbcTemplate casTemplate) {
		this.casTemplate = casTemplate;
	}

	public WebSessionData getWebSessionDataBySessionKey(final String sessionKey){
		return casTemplate.queryForObject(WEB_SESSION_SQL, new WebSessionRowMapper(), sessionKey);
	}

	private class WebSessionRowMapper implements RowMapper<WebSessionData> {

		@Override
		public WebSessionData mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new WebSessionData(
					rs.getString(SESSION_KEY),
					rs.getString(USER_NAME),
					rs.getDate(INACTIVE_EXPIRE_DATE_TIME),
					rs.getDate(EXPIRE_DATE_TIME)
			);
		}
	}

	public static final String WEB_SESSION_SQL = "select " + SESSION_KEY + ", " + USER_NAME + ", " +
			INACTIVE_EXPIRE_DATE_TIME + ", " + EXPIRE_DATE_TIME +
			" from websession.web_session where session_key = ?";
}
