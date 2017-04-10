package edu.byu.chartblock.test;

import edu.byu.chartblock.AccountCodeValidation;
import edu.byu.chartblock.ChartBlock;
import edu.byu.chartblock.impl.AccountCodeValidationImpl;
import edu.byu.wso2.core.Wso2Credentials;
import edu.byu.wso2.core.provider.ClientCredentialOauthTokenProvider;
import edu.byu.wso2.core.provider.ClientCredentialsTokenHeaderProvider;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import edu.byu.wso2.filter.jersey.JerseyOutboundOauthTokenFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 3:32 PM
 */
@RunWith(JUnit4.class)
public class AccountCodeValidationImplTest {
	private static final Logger LOG = LogManager.getLogger(AccountCodeValidationImplTest.class);

	private static AccountCodeValidation accountCodeValidation;

	@BeforeClass
	public static void setup() throws IOException {
		final Properties properties = new Properties();
		final FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.home") + File.separator +
						"cred" + File.separator + "softdist.cred");
		properties.load(inputStream);
		final Wso2Credentials wso2Credentials = new Wso2Credentials(properties.getProperty("stage.client_id"),properties.getProperty("stage.client_secret"));
		final ClientCredentialOauthTokenProvider tokenProvider = new ClientCredentialOauthTokenProvider(wso2Credentials);
		final TokenHeaderProvider tokenHeaderProvider = new ClientCredentialsTokenHeaderProvider(tokenProvider);
		final JerseyOutboundOauthTokenFilter tokenFilter = new JerseyOutboundOauthTokenFilter(tokenHeaderProvider);
		accountCodeValidation = new AccountCodeValidationImpl(tokenFilter);
	}

	@Test
	public void testAccountCodeValidation1() throws Exception {
		assertNotNull("need client", accountCodeValidation);
		final ChartBlock account = accountCodeValidation.getAccount("11405500-6000-40391");
		assertEquals("Success", account.getStatus());
		assertNotNull(account.getOperatingUnit());
		assertNotNull(account.getAccountField());
		assertNotNull(account.getClassField());
		assertNotNull(account.getOperatingUnit().getContact());
	}

	@Test
	public void testAccountCodeValidation2() throws Exception {
		assertNotNull("need client", accountCodeValidation);
		accountCodeValidation.getAccount("11360000-6010-00000");
		LOG.debug("Done.");
	}
}
