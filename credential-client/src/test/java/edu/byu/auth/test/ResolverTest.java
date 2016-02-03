package edu.byu.auth.test;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import edu.byu.auth.client.impl.PasswordFileCredentialResolver;
import edu.byu.auth.client.impl.SharedSecretFileCredentialResolver;
import edu.byu.auth.domain.Credential;
import edu.byu.crypto.Crypto;
import edu.byu.hash.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/04/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/04/2014
 */
@RunWith(JUnit4.class)
public class ResolverTest {

	private static final Logger LOG = LogManager.getLogger(ResolverTest.class);

	private void prepFiles(final boolean encrypted) throws Exception {
		keyfile = File.createTempFile("credential-test-", ".key");
		ssfile = File.createTempFile("credential-test-api-", ".properties");
		upfile = File.createTempFile("credential-test-up-", ".properties");
		keyfile.deleteOnExit();
		ssfile.deleteOnExit();
		upfile.deleteOnExit();
		final ByteSink byteSink = Files.asByteSink(keyfile);
		byteSink.write(keybytes);
		final Properties ssprops = new Properties();
		ssprops.setProperty(SharedSecretFileCredentialResolver.WSID_KEY, wsid);
		ssprops.setProperty(SharedSecretFileCredentialResolver.EXPIRE_DATE_KEY, expDateStr);
		ssprops.setProperty(SharedSecretFileCredentialResolver.PERSON_ID_KEY, personId);
		ssprops.setProperty(SharedSecretFileCredentialResolver.SHARED_SECRET_KEY, getValue(encrypted, sharedSecret));
		ssprops.store(new FileWriter(ssfile, false), "");
		final Properties upprops = new Properties();
		upprops.setProperty(PasswordFileCredentialResolver.USERNAME_KEY, username);
		upprops.setProperty(PasswordFileCredentialResolver.PASSWORD_KEY, getValue(encrypted, password));
		upprops.store(new FileWriter(upfile, false), "");
	}

	private String getValue(final boolean encrypted, final String secret) {
		return encrypted ? Base64.encode(Crypto.encrypt(keybytes, secret.getBytes()), false) : secret;
	}

	@Test
	public void testSharedSecretResolver() throws Exception {
		prepFiles(true);
		final SharedSecretFileCredentialResolver r = new SharedSecretFileCredentialResolver();
		r.setKeyFile(keyfile);
		r.setCredentialFile(ssfile);
		r.afterPropertiesSet();
		final Credential c = r.getApiKeyCredential();
		Assert.assertNotNull(c);
		Assert.assertEquals(c.getWsId(), wsid);
		Assert.assertEquals(c.getPersonId(), personId);
		Assert.assertEquals(c.getSharedSecret(), sharedSecret);
		Assert.assertEquals(c.getExpirationDate(), expDate);
	}

	@Test
	public void testPasswordResolver() throws Exception {
		prepFiles(true);
		final PasswordFileCredentialResolver r = new PasswordFileCredentialResolver();
		r.setKeyFile(keyfile);
		r.setCredentialFile(upfile);
		r.afterPropertiesSet();
		Assert.assertEquals(r.getUsername(), username);
		Assert.assertEquals(r.getPassword(), password);
	}

	@Test
	public void testSharedSecretResolverUnEnc() throws Exception {
		prepFiles(false);
		final SharedSecretFileCredentialResolver r = new SharedSecretFileCredentialResolver();
		r.setKeyFile(keyfile);
		r.setCredentialFile(ssfile);
		r.setSkipEncryption(true);
		r.afterPropertiesSet();
		final Credential c = r.getApiKeyCredential();
		Assert.assertNotNull(c);
		Assert.assertEquals(c.getWsId(), wsid);
		Assert.assertEquals(c.getPersonId(), personId);
		Assert.assertEquals(c.getSharedSecret(), sharedSecret);
		Assert.assertEquals(c.getExpirationDate(), expDate);
	}

	@Test
	public void testPasswordResolverUnEnc() throws Exception {
		prepFiles(false);
		final PasswordFileCredentialResolver r = new PasswordFileCredentialResolver();
		r.setKeyFile(keyfile);
		r.setCredentialFile(upfile);
		r.setSkipEncryption(true);
		r.afterPropertiesSet();
		Assert.assertEquals(r.getUsername(), username);
		Assert.assertEquals(r.getPassword(), password);
	}

	private static File keyfile, ssfile, upfile;
	private static final String wsid = "thekey";
	private static final String sharedSecret = "sharedSecret";
	private static final String expDateStr = "2999-12-31-23-59-59-999";
	private static final String personId = "999299992";
	private static final String username = "netid";
	private static final String password = "password!@#$%12388-_yes";
	private static final Calendar expDate;
	private static final int[] KEY_INTS = new int[] {0xfedcba98, 0x76543210, 0x8d88ea75, 0xfeedbac2, 0x77777777, 0x900df00d, 0x7ee7f00f, 0x7a5e69f0};
	private static final int KEY_BYTES = KEY_INTS.length * 4;
	private static final byte[] keybytes;

	static {
		expDate = new GregorianCalendar();
		expDate.set(2999, Calendar.DECEMBER, 31, 23, 59, 59);
		expDate.set(Calendar.MILLISECOND, 999);
		keybytes = new byte[KEY_BYTES];
		for (int i = 0; i < KEY_INTS.length; i++) {
			keybytes[4 * i + 3] = (byte) (KEY_INTS[i]  & 0xff);
			keybytes[4 * i + 2] = (byte) ((KEY_INTS[i] >> 8)  & 0xff);
			keybytes[4 * i + 1] = (byte) ((KEY_INTS[i] >> 16)  & 0xff);
			keybytes[4 * i + 0] = (byte) ((KEY_INTS[i] >> 24)  & 0xff);
		}
	}

}
