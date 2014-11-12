package edu.byu.auth.test;

import com.sun.jersey.api.client.filter.LoggingFilter;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.auth.client.PasswordCredentialResolver;
import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.client.WsSessionClient;
import edu.byu.auth.client.impl.ApiKeyClientImpl;
import edu.byu.auth.client.impl.PasswordFileCredentialResolver;
import edu.byu.auth.client.impl.SharedSecretFileCredentialResolver;
import edu.byu.auth.client.impl.WsSessionClientImpl;
import edu.byu.auth.domain.Nonce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/04/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/04/2014
 */
@RunWith(JUnit4.class)
public class ClientTest {

	private static final Logger LOG = LogManager.getLogger(ClientTest.class);

	private static final String USER_HOME = System.getProperty("user.home");
	private static final File API_KEY_FILE = new File(USER_HOME, ".cred-client-key");
	private static final File CRED_FILE = new File(USER_HOME, ".apikey");
	private static final File USER_FILE = new File(USER_HOME, ".user-pass");

	@Test
	public void testApiClient() {
		if (!API_KEY_FILE.exists() || !API_KEY_FILE.canRead() || API_KEY_FILE.length() == 0) {
			LOG.warn("Missing required files to execute this test.");
			return;
		}
		if (!CRED_FILE.exists() || !CRED_FILE.canRead() || CRED_FILE.length() == 0) {
			LOG.warn("Missing required files to execute this test.");
			return;
		}
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApiSpringConfig.class);
		final ApiKeyClient client = ctx.getBean(ApiKeyClient.class);
		Assert.assertNotNull(client);
		final Nonce nonce = client.obtainNonce();
		Assert.assertNotNull(nonce);
	}

	@Test
	public void testUserClient() {
		if (!USER_FILE.exists() || !USER_FILE.canRead() || USER_FILE.length() == 0) {
			LOG.warn("Missing required files to execute this test.");
			return;
		}
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(UserSpringConfig.class);
		final WsSessionClient client = ctx.getBean(WsSessionClient.class);
		Assert.assertNotNull(client);
		final Nonce n1 = client.obtainNonce();
		Assert.assertNotNull(n1);
		client.renew();
		final Nonce n2 = client.obtainNonce();
		Assert.assertNotEquals(n1.getApiKey(), n2.getApiKey());
		client.logout();
	}

	@Configuration
	public static class ApiSpringConfig {

		@Bean
		public SharedSecretCredentialResolver resolver() {
			final SharedSecretFileCredentialResolver r = new SharedSecretFileCredentialResolver();
			r.setKeyFile(API_KEY_FILE);
			r.setCredentialFile(CRED_FILE);
			return r;
		}

		@Bean
		public ApiKeyClient client() {
			final ApiKeyClientImpl c = new ApiKeyClientImpl(5000, 5000);
			c.setResolver(resolver());
			return c;
		}
	}

	@Configuration
	public static class UserSpringConfig {

		@Bean
		public PasswordCredentialResolver resolver() {
			final PasswordFileCredentialResolver r = new PasswordFileCredentialResolver();
			r.setCredentialFile(USER_FILE);
			return r;
		}

		@Bean
		public WsSessionClient client() {
			final WsSessionClientImpl c = new WsSessionClientImpl(5000, 5000);
			c.setResolver(resolver());
			c.setAutoRenew(false);
			c.setSessionDuration(5);
			return c;
		}
	}

}
