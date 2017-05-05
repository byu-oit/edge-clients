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
	protected boolean skipEncryption = false;
	protected boolean required = true;
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

	public void setSkipEncryption(final boolean skipEncryption) {
		this.skipEncryption = skipEncryption;
	}

	public void setRequired(final boolean required) {
		this.required = required;
	}

	protected abstract void myAfterPropertiesSet();

	@Override
	public final void afterPropertiesSet() throws Exception {
		try {
			Assert.isTrue(validateFile(credentialFile), "A valid credential file is required.");
			if (!skipEncryption) readKeyFile();
			readCredFile();
			myAfterPropertiesSet();
		} catch (final Exception e) {
			if (required) throw e;
		}
	}

	protected String decrypt(final String enc) {
		return skipEncryption ? enc : Crypto.bytesToString(Crypto.decrypt(enckey, Base64.decode(enc)));
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

}
