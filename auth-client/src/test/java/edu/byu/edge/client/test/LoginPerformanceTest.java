package edu.byu.edge.client.test;

import edu.byu.edge.client.auth.ByuAuthClient;
import edu.byu.edge.client.auth.impl.ByuAuthClientImpl;
import edu.byu.edge.client.domain.WsSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/26/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/26/2012
 */
//@RunWith(JUnit4.class)
public class LoginPerformanceTest {
	private static final Logger LOG = Logger.getLogger(LoginPerformanceTest.class);

//	@Test
	public void testPerformance() {
		final int numLogins = 25;
		LOG.debug("beginning performance test");
		final ByuAuthClient client = new ByuAuthClientImpl();
		final WsSession[] sesslist = new WsSession[numLogins];
		final int[] timers = new int[numLogins];
		LOG.debug("have client");
		for (int i = 0; i < numLogins; i++) {
			final long a = System.currentTimeMillis();
			final WsSession session = client.login("bhumdee", "I am the b3st @ 100% & more", 1);
			final long b = System.currentTimeMillis();
			sesslist[i] = session;
			timers[i] = (int) (b - a);
		}
		for (int i = 0; i < numLogins; i++) {
			final WsSession s = sesslist[i];
			if (s != null) {
				LOG.debug("logging out " + i + ": " + s);
				client.logout(s);
			}
		}
		for (int i = 0; i < numLogins; i++) {
			LOG.debug("time for " + i + ": " + timers[i]);
		}
		LOG.debug("max time is: " + findMax(timers));
	}

	private static int findMax(int... vals) {
		if (vals == null || vals.length == 0) return Integer.MIN_VALUE;
		int max = vals[0];
		for (int i = 1; i < vals.length; i++) {
			max = max < vals[i] ? vals[i] : max;
		}
		return max;
	}
}
