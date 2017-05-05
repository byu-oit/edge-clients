package edu.byu.edge.academic.client.test;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.edge.academic.client.CreditHourClient;
import edu.byu.edge.academic.client.ServiceException;
import edu.byu.edge.academic.client.impl.CreditHourClientImpl;
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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Scott Hutchings on 3/31/2017.
 * edge-clients
 */
@RunWith(JUnit4.class)
public class CreditHourClientUnitTest {
	private static final Logger LOG = LogManager.getLogger(CreditHourClientUnitTest.class);

	private static CreditHourClient creditHourClient;
	private static String personId;

	@BeforeClass
	public static void setup() throws IOException {
		final Properties properties = new Properties();
		final FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.home") + File.separator +
						"cred" + File.separator + "oauth-tester.cred");
		properties.load(inputStream);
		Wso2Credentials wso2Credentials = new Wso2Credentials(properties.getProperty("stage.client_id"),properties.getProperty("stage.client_secret"));
		ClientCredentialOauthTokenProvider tokenProvider = new ClientCredentialOauthTokenProvider(wso2Credentials);
		TokenHeaderProvider tokenHeaderProvider = new ClientCredentialsTokenHeaderProvider(tokenProvider);
		creditHourClient = new CreditHourClientImpl(tokenHeaderProvider);
		personId = properties.getProperty("person_id");
	}

	@Test
	public void testCreditHourClient(){
		try {
			final double creditHours = creditHourClient.getCreditHoursByPersonIdAndYearTerm(personId, "20171");
			Assert.assertNotNull(creditHours);
			LOG.debug(creditHours);
		} catch (ServiceException e) {
			LOG.error(e);
			Assert.fail("An exception occurred trying the creditHours service");
		}
	}
}
