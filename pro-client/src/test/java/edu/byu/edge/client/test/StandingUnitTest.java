package edu.byu.edge.client.test;

import edu.byu.edge.client.pro.StandingClient;
import edu.byu.edge.client.pro.domain.standing.RecStdAcadStandingServiceType;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 8/7/12
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 8/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:edge/academicStanding/standing-context.xml"
})
@DirtiesContext
public class StandingUnitTest extends BaseTest {
	private static final Logger LOG = Logger.getLogger(StandingUnitTest.class);

	@Autowired
	private StandingClient client;

	@Test
	public void testClient() {
		setActor("wct5", "903201972", "Wyatt C Taylor", "739696648", "wct5@byu.edu");
		assertNotNull("Need client", client);
		final RecStdAcadStandingServiceType standing = client.getStandingByPersonId("903201972");
		LOG.debug(standing);
	}
}
