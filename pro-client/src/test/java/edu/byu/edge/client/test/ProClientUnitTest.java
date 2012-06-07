package edu.byu.edge.client.test;

import edu.byu.edge.client.pro.PersonSummaryClient;
import edu.byu.edge.client.pro.domain.personSummary.PersonSummaryServiceType;
import edu.byu.security.userdetails.IdentityDetails;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Collections;

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
public class ProClientUnitTest {
	private static final Logger LOG = Logger.getLogger(ProClientUnitTest.class);

	@Autowired
	private PersonSummaryClient personSummaryClient;

	@Test
	public void testClient() throws Exception {
		setActor();
		assertNotNull("need client", personSummaryClient);
		final PersonSummaryServiceType ps = personSummaryClient.getSummaryByNetId("wct5");
		LOG.debug(ps);
		personSummaryClient.getSummaryByNetId("wct5");
		personSummaryClient.getSummaryByNetId("wct5");
		final PersonSummaryServiceType ps2 = personSummaryClient.getSummaryByNetId("bhumdee");
		assertNotNull("should have second result", ps2);
		assertFalse("the values should be different", ps2.getResponse().getIdentifiers().getNetId().equals(ps.getResponse().getIdentifiers().getNetId()));
	}

	private void setActor() {
		final IdentityDetails details = new IdentityDetails("903201972", "wct5", "739696648", "Wyatt C Taylor", "wct5@byu.edu", Collections.<GrantedAuthority>emptySet());
		final Authentication auth = new Authentication() {
			@Override
			public Collection<GrantedAuthority> getAuthorities() {
				return details.getAuthorities();
			}

			@Override
			public Object getCredentials() {
				return null;
			}

			@Override
			public Object getDetails() {
				return null;
			}

			@Override
			public Object getPrincipal() {
				return details;
			}

			@Override
			public boolean isAuthenticated() {
				return true;
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

			}

			@Override
			public String getName() {
				return details.getName();
			}
		};
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
