package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.HoldLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/25/12
 * Time: 9:46 AM
 */
public class HoldLookupImpl implements HoldLookup {

	private final JdbcTemplate cesTemplate;

	@Autowired
	public HoldLookupImpl(JdbcTemplate jdbcTemplate){
		this.cesTemplate = jdbcTemplate;
	}

	@Override
	public boolean hasHold(String personId, String flagName, String... flagValue) {
		String WHERE_SQL = "in( ";
		int x=0;
		final Object[] objects = new Object[2 + flagValue.length];
		objects[x++] = personId;
		objects[x++] = flagName;
		for(String s : flagValue){
			objects[x++] = s;
			if(x > 3){
				WHERE_SQL += ",";
			}
			WHERE_SQL += "?";
		}
		String FLAG_HOLD_SQL = "select count(*) from stdreg.std_flags_holds where person_id = ? AND flag_name = ? AND flag_value " + WHERE_SQL + ") AND date_removed is null and expired_date > sysdate";
		return cesTemplate.queryForInt(FLAG_HOLD_SQL, objects) > 0;
	}
}
