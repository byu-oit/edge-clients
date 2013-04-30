package edu.byu.edge.person.basic.impl;

import edu.byu.edge.person.basic.GroupLookup;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/30/13
 * Time: 10:06 AM
 */
public class GroupLookupImpl implements GroupLookup{

	private static final Logger LOG = Logger.getLogger(GroupLookupImpl.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GroupLookupImpl(
			JdbcTemplate jdbcTemplate
	) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Cacheable(value = "cesGroClientCache")
	@Override
	public List<String> getAllGroupIds() {
		return jdbcTemplate.queryForList("select GROUP_ID from GRO.GROUPS order by GROUP_ID", String.class);
	}

	@Cacheable(key = "#personId", value = "cesGroClientCache")
	@Override
	public List<String> getGroupsForPerson(final String personId) {
		return jdbcTemplate.queryForList("select GROUP_ID from GRO.PERSON_GROUP where PERSON_ID = ? order by GROUP_ID", String.class, personId);
	}

	@Cacheable(value = "cesGroClientCache")
	@Override
	public boolean isMember(final String personId, final String groupId) {
		return  0 < jdbcTemplate.queryForInt("select count(*) from GRO.PERSON_GROUP where PERSON_ID = ? and GROUP_ID = ?", personId, groupId);
	}
}
