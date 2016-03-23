package edu.byu.auth.util;

import edu.byu.auth.domain.Credential;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by wct5 on 3/23/16.
 */
public final class CredentialUtil {

	private CredentialUtil() {
	}

	public static Credential createCredential(final String personId, final String wsId, final String sharedSecret, final String expDate) {
		final Credential credential = new Credential();
		credential.setPersonId(personId);
		credential.setWsId(wsId);
		credential.setSharedSecret(sharedSecret);
		credential.setExpirationDate(parseDate(expDate));
		return credential;
	}

	private static Calendar parseDate(final String date) {
		final Calendar cal = new GregorianCalendar();
		final String[] split = (date + "-0-0-0-0").split("-");
		cal.set(pi(split[0]), pi(split[1]) - 1, pi(split[2]), pi(split[3]), pi(split[4]), pi(split[5]));
		cal.set(Calendar.MILLISECOND, pi(split[6]));
		return cal;
	}

	private static int pi(final String s) {
		return Integer.parseInt(s);
	}
}
