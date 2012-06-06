package edu.byu.edge.client.test;

import edu.byu.edge.client.auth.ByuAuthClient;
import edu.byu.edge.client.auth.impl.ByuAuthClientImpl;
import edu.byu.edge.client.domain.WsSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/6/12
 */
@RunWith(JUnit4.class)
public class ByuAuthClientIntegrationTest {
	private static final Logger LOG = Logger.getLogger(ByuAuthClientIntegrationTest.class);

	@Test
	public void testAuthClient() {
		LOG.debug("starting test: creating client");
		final ByuAuthClient client = new ByuAuthClientImpl();
		LOG.debug("logging in");
		final WsSession session = client.login("bhumdee", "I am the b3st @ 100% & more", 1);
		assertNotNull("authentication should be successful", session);
		assertNotNull("api key should exist", session.getApiKey());
		assertNotNull("shared secret should exist", session.getSharedSecret());

	}
}
