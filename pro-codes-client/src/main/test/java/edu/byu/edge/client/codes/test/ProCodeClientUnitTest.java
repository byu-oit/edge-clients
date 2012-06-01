package edu.byu.edge.client.codes.test;

import edu.byu.edge.client.pro.codes.CodesClient;
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
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 5/31/12
 * Time: 5:37 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml"
})
public class ProCodeClientUnitTest {

	private static final Logger LOG = Logger.getLogger(ProCodeClientUnitTest.class);

	@Autowired
	private CodesClient codesClient;

	@Test
	public void testClient() throws Exception {
		setActor();
		assertNotNull("need client", codesClient);
		final String data = codesClient.getData();
		LOG.debug(data);
	}

	private void setActor() {
		final IdentityDetails details = new IdentityDetails("249262982", "thirschi", "951038980", "Tim A Hirschi", "timothy_hirschi@byu.edu", Collections.<GrantedAuthority>emptySet());
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
