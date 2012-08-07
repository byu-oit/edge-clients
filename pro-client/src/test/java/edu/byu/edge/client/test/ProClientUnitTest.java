package edu.byu.edge.client.test;

import edu.byu.edge.client.pro.PersonSummaryClient;
import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 5/24/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:edge/personSummary/personSummary-context.xml"
})
public class ProClientUnitTest extends BaseTest {
	private static final Logger LOG = Logger.getLogger(ProClientUnitTest.class);

	@Autowired
	private PersonSummaryClient personSummaryClient;

	@Test
	public void testClient() throws Exception {
		setActor("wct5", "903201972", "Wyatt C Taylor", "739696648", "wct5@byu.edu");
		assertNotNull("need client", personSummaryClient);
		final PersonSummaryServiceType ps = personSummaryClient.getSummaryByNetId("wct5");
		LOG.debug(ps);
		personSummaryClient.getSummaryByNetId("wct5");
		personSummaryClient.getSummaryByNetId("wct5");
		final PersonSummaryServiceType ps2 = personSummaryClient.getSummaryByNetId("bhumdee");
		assertNotNull("should have second result", ps2);
		assertFalse("the values should be different", ps2.getResponse().getIdentifiers().getNetId().equals(ps.getResponse().getIdentifiers().getNetId()));
	}
}
