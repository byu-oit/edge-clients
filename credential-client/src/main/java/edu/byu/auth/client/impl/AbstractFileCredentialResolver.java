package edu.byu.auth.client.impl;

import edu.byu.crypto.Crypto;
import edu.byu.hash.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/03/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/03/2014
 */
public abstract class AbstractFileCredentialResolver implements InitializingBean {

	protected File credentialFile;
	protected File keyFile;
	protected Map<String, String> props;
	private byte[] enckey;

	protected AbstractFileCredentialResolver() {
	}

	public void setCredentialFile(final File credentialFile) {
		this.credentialFile = credentialFile;
	}

	public void setKeyFile(final File keyFile) {
		this.keyFile = keyFile;
	}

	protected abstract void myAfterPropertiesSet();

	@Override
	public final void afterPropertiesSet() throws Exception {
		Assert.isTrue(validateFile(credentialFile), "A valid credential file is required.");
		readKeyFile();
		readCredFile();
		myAfterPropertiesSet();
	}

	protected String decrypt(final String enc) {
		return Crypto.bytesToString(Crypto.decrypt(enckey, Base64.decodeToBytes(enc)));
	}

	private boolean validateFile(final File f) {
		return f != null && f.exists() && f.isFile() && f.canRead() && f.length() > 0;
	}

	private void readKeyFile() {
		if (validateFile(keyFile)) {
			this.enckey = Crypto.readKeyFile(keyFile.getAbsolutePath());
		} else {
			this.enckey = Crypto.readUserKeyFile(true);
		}
	}

	private void readCredFile() throws IOException {
		final Properties p = new Properties();
		p.load(new FileReader(credentialFile));
		this.props = new HashMap<String, String>((int) ((1 + p.size()) / .9999f + 1), .9999f);
		for (final String s : p.stringPropertyNames()) {
			this.props.put(s, p.getProperty(s, ""));
		}
	}

	/*
	private static final int BITES_OF_PEPPER = 16;
	private static final int[] PEPPER_PLANT = new int[] {0xfedcba98, 0x76543210, 0x8d88ea75, 0xfeedbac2};
	private final byte[] pepper = createPepper();

	private byte[] createPepper() {
		final byte[] b = new byte[BITES_OF_PEPPER];
		for (int i = 0; i < PEPPER_PLANT.length; i++) {
			b[4 * i + 3] = (byte) (PEPPER_PLANT[i]  & 0xff);
			b[4 * i + 2] = (byte) ((PEPPER_PLANT[i] >> 8)  & 0xff);
			b[4 * i + 1] = (byte) ((PEPPER_PLANT[i] >> 16)  & 0xff);
			b[4 * i + 0] = (byte) ((PEPPER_PLANT[i] >> 24)  & 0xff);
		}
		return b;
	}
	/* */
}
