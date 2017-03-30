package edu.byu.edge.coreIdentity.test;

import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;
import edu.byu.edge.coreIdentity.client.impl.CoreIdentityClientImpl;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Scott Hutchings on 3/30/2017.
 * edge-clients
 */
@RunWith(JUnit4.class)
public class CoreIdentityClientUnitTest {
	private static final Logger LOG = LogManager.getLogger(CoreIdentityClientUnitTest.class);
	private static CoreIdentityClient coreIdentityClient;

	@BeforeClass
	public static void setup() throws IOException {
		Properties properties = new Properties();

		final FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.home") + File.separator +
						"basic-oauth-tester.properties");
		properties.load(inputStream);
		Wso2Credentials wso2Credentials = new Wso2Credentials(properties.getProperty("client_id"),properties.getProperty("client_secret"));
		ClientCredentialOauthTokenProvider tokenProvider = new ClientCredentialOauthTokenProvider(wso2Credentials);
		TokenHeaderProvider tokenHeaderProvider = new ClientCredentialsTokenHeaderProvider(tokenProvider);
		coreIdentityClient = new CoreIdentityClientImpl(tokenHeaderProvider);
	}

	@Test
	public void testCoreIdentityClient() throws IOException, RestHttpException {
		final CoreIdentity testPerson = coreIdentityClient.getCoreIdentityByNetId("endor2");
		Assert.assertNotNull(testPerson);
		Assert.assertNotNull(testPerson.getPersonId());
		LOG.debug(testPerson);
	}
}
