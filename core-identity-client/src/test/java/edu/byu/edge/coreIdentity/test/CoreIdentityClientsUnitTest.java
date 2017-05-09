package edu.byu.edge.coreIdentity.test;

import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.IdentityLookupClient;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.impl.CoreIdentityClientImpl;
import edu.byu.edge.coreIdentity.client.impl.IdentityLookupClientImpl;
import edu.byu.edge.coreIdentity.client.impl.MemberOfClientImpl;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import edu.byu.edge.coreIdentity.domain.IdentityLookupSummary;
import edu.byu.wso2.core.Wso2Credentials;
import edu.byu.wso2.core.provider.ClientCredentialOauthTokenProvider;
import edu.byu.wso2.core.provider.ClientCredentialsTokenHeaderProvider;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Scott Hutchings on 3/30/2017.
 * edge-clients
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class CoreIdentityClientsUnitTest {
	private static final Logger LOG = LogManager.getLogger(CoreIdentityClientsUnitTest.class);
	private static CoreIdentityClient coreIdentityClient;
	private static IdentityLookupClient identityLookupClient;
	private MemberOfClient memberOfClient;
	private static String personId;
	private static String netId;

	@Autowired
	public void setMemberOfClient(MemberOfClient memberOfClient) {
		this.memberOfClient = memberOfClient;
	}

	@BeforeClass
	public static void setup() throws IOException {
		Properties properties = new Properties();

		final FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.home") + File.separator +
						"cred" + File.separator + "oauth-tester.cred");
		properties.load(inputStream);
		Wso2Credentials wso2Credentials = new Wso2Credentials(properties.getProperty("stage.client_id"),properties.getProperty("stage.client_secret"));
		ClientCredentialOauthTokenProvider tokenProvider = new ClientCredentialOauthTokenProvider(wso2Credentials);
		TokenHeaderProvider tokenHeaderProvider = new ClientCredentialsTokenHeaderProvider(tokenProvider);
		coreIdentityClient = new CoreIdentityClientImpl(tokenHeaderProvider);
		identityLookupClient = new IdentityLookupClientImpl(tokenHeaderProvider);
//		memberOfClient = new MemberOfClientImpl(tokenHeaderProvider);
		personId = properties.getProperty("person_id");
		netId = properties.getProperty("net_id");
	}

	@Test
	public void testCoreIdentityClient() {
		try {
			final CoreIdentity testPerson = coreIdentityClient.getCoreIdentityByNetId(netId);
			Assert.assertNotNull(testPerson);
			Assert.assertNotNull(testPerson.getPersonId());
			Assert.assertEquals(personId, testPerson.getPersonId());
			LOG.debug(testPerson);
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("An exception occurred trying the coreIdentityClient service");
		}
	}

	@Test
	public void testIdentityLookupClient() {
		try {
			final List<IdentityLookupSummary> lookupSummaries = identityLookupClient.searchBy(netId);
			Assert.assertNotNull(lookupSummaries);
			LOG.debug(lookupSummaries);
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("An exception occurred trying the identityLookupClient service");
		}
	}

	@Test
	public void testMemberOfClient() {
		final boolean isMember;
		try {
			isMember = memberOfClient.isPersonMemberOfGroup(personId, "testGroup");
			LOG.debug("isMember: " + isMember);
		} catch (Exception e) {
			LOG.error(e);
			Assert.fail("An exception occurred trying the isMember service");
		}
	}
}
