package edu.byu.auth.client.impl;

import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.domain.Credential;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 09/03/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 09/03/2014
 */
public class SharedSecretFileCredentialResolver extends AbstractFileCredentialResolver implements SharedSecretCredentialResolver {

	public static final String EXPIRE_DATE_KEY = "expireDate";
	public static final String PERSON_ID_KEY = "personId";
	public static final String SHARED_SECRET_KEY = "sharedSecret";
	public static final String WSID_KEY = "wsid";

	public SharedSecretFileCredentialResolver() {
	}

	@Override
	protected void myAfterPropertiesSet() {
		Assert.isTrue(props.containsKey(EXPIRE_DATE_KEY), "Credential file does not contain the expiration date.");
		Assert.isTrue(props.containsKey(PERSON_ID_KEY), "Credential file does not contain the person id.");
		Assert.isTrue(props.containsKey(SHARED_SECRET_KEY), "Credential file does not contain a shared secret.");
		Assert.isTrue(props.containsKey(WSID_KEY), "Credential file does not contain a wsid.");
	}

	@Override
	public Credential getApiKeyCredential() {
		final Credential c = new Credential();
		c.setPersonId(props.get(PERSON_ID_KEY));

		final String[] s = props.get(EXPIRE_DATE_KEY).split("-");
		int i = 0;
		final Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.MONTH, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.DAY_OF_MONTH, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.HOUR_OF_DAY, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.MINUTE, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.SECOND, i < s.length ? Integer.parseInt(s[i++]) : 0);
		cal.set(Calendar.MILLISECOND, i < s.length ? Integer.parseInt(s[i++]) : 0);
		c.setExpirationDate(cal);
		c.setWsId(props.get(WSID_KEY));
		c.setSharedSecret(decrypt(props.get(SHARED_SECRET_KEY)));
		return c;
	}
}
