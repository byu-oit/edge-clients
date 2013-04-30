package edu.byu.edge.person.basic;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/30/13
 * Time: 10:06 AM
 */
public interface GroupLookup {
	@Cacheable(value = "cesGroClientCache")
	List<String> getAllGroupIds();

	@Cacheable(key = "#personId", value = "cesGroClientCache")
	List<String> getGroupsForPerson(String personId);

	@Cacheable(value = "cesGroClientCache")
	boolean isMember(String personId, String groupId);
}
