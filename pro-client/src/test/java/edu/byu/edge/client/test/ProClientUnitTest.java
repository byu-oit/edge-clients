package edu.byu.edge.client.test;

import edu.byu.edge.client.pro.ProClient;
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
		"classpath:test-context.xml"
})
public class ProClientUnitTest {
	private static final Logger LOG = Logger.getLogger(ProClientUnitTest.class);

	@Autowired
	private ProClient proClient;

	@Test
	public void testClient() throws Exception {
		assertNotNull("need client", proClient);
		final String data = proClient.getData();
		LOG.debug(data);
	}
}
