package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.HoldLookup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/25/12
 * Time: 10:09 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class HoldLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(HoldLookupUnitTest.class);

	@Autowired
	@Qualifier("holdLookup")
	private HoldLookup holdLookup;

	@Test
	public void testHold(){
		boolean hasHold = holdLookup.hasHold("844231122", "CEE", "7");
		assertTrue("Doesn't have a hold", hasHold);
		hasHold = holdLookup.hasHold("141225662", "STANDARDS HOLD", "BAN-PART", "BAN-FULL");
		assertTrue("Doesn't have a hold", hasHold);
	}

}
