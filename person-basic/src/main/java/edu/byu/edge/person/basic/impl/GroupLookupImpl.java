package edu.byu.edge.person.basic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.byu.edge.jdbc.common.IntegerExtractor;
import edu.byu.edge.jdbc.common.StringListExtractor;
import edu.byu.edge.person.basic.GroupLookup;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/30/13
 * Time: 10:06 AM
 */
public class GroupLookupImpl implements GroupLookup{

	private static final Logger LOG = Logger.getLogger(GroupLookupImpl.class);

	private final NamedParameterJdbcOperations jdbcTemplate;

	public GroupLookupImpl(JdbcTemplate jdbcTemplate) {
		this(new NamedParameterJdbcTemplate(jdbcTemplate));
	}
	
	public GroupLookupImpl(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Cacheable(value = "cesGroClientCache")
	@Override
	public List<String> getAllGroupIds() {
		return jdbcTemplate.query("select GROUP_ID from GRO.GROUPS order by GROUP_ID", new HashMap<String, String>(), StringListExtractor.EXTRACTOR);
	}

	@Cacheable(key = "#personId", value = "cesGroClientCache")
	@Override
	public List<String> getGroupsForPerson(final String personId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		return jdbcTemplate.query("select GROUP_ID from GRO.PERSON_GROUP where PERSON_ID = :personId order by GROUP_ID", paramMap, StringListExtractor.EXTRACTOR);
	}

	@Cacheable(value = "cesGroClientCache")
	@Override
	public boolean isMember(final String personId, final String groupId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("personId", personId);
		paramMap.put("groupId", groupId);
		return 0 < jdbcTemplate.query("select count(*) from GRO.PERSON_GROUP where PERSON_ID = :personId and GROUP_ID = :groupId", paramMap, IntegerExtractor.EXTRACTOR);
	}
}
