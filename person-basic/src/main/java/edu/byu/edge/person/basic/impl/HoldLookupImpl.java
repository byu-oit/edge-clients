package edu.byu.edge.person.basic.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.byu.edge.jdbc.common.IntegerExtractor;
import edu.byu.edge.person.basic.HoldLookup;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/25/12
 * Time: 9:46 AM
 */
public class HoldLookupImpl implements HoldLookup {

	private final NamedParameterJdbcOperations jdbcTemplate;
	private static final String fLAG_HOLD_SQL = "select count(*) from stdreg.std_flags_holds where person_id = :personId AND flag_name = :flagName AND flag_value in (:flags) AND date_removed is null and expired_date > sysdate";

	public HoldLookupImpl(JdbcTemplate jdbcTemplate){
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public HoldLookupImpl(NamedParameterJdbcOperations jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean hasHold(String personId, String flagName, String... flagValue) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("personId", personId);
		paramMap.put("flagName", flagName);
		paramMap.put("flags", Arrays.asList(flagValue));
		return jdbcTemplate.query(fLAG_HOLD_SQL, paramMap, IntegerExtractor.EXTRACTOR) > 0;
	}
}
