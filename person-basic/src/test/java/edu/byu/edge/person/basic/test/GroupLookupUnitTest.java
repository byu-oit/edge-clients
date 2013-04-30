package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.GroupLookup;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/30/13
 * Time: 10:07 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class GroupLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(GroupLookupUnitTest.class);

	@Autowired
	@Qualifier("groupLookup")
	private GroupLookup groupLookup;
}
