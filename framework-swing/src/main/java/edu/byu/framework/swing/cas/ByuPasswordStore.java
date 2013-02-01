/*
 * Filename: PasswordSaver
 * Created: Dec 10, 2008
 */
package edu.byu.framework.swing.cas;

import edu.byu.crypto.Crypto;
import edu.byu.framework.swing.UserPrefs;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.auth.PasswordStore;
import org.springframework.stereotype.Repository;

/**
 * Stores a username/password combination for use with ByuBootStrapper login
 * @author tylers2
 */
@Repository
public class ByuPasswordStore extends PasswordStore {

    /**
     * Username property name
     */
    public final static String USERNAME = "byu.netId";
    /**
     * Password property name
     */
    public final static String PASSWORD = "byu.password";
    private final static byte[] cryptographicKey = "this is a test".getBytes();
    private final static Logger LOG = Logger.getLogger(ByuPasswordStore.class);

    public ByuPasswordStore() {
        super();
    }

    private String encryptPassword(String clearText) {
        if (cryptographicKey.length > 0) {
            byte[] cipherText = Crypto.encrypt(cryptographicKey, clearText.getBytes());
            byte[] encodedCipherText = Crypto.encodeBase64(cipherText);
            return Crypto.bytesToString(encodedCipherText);
        } else {
            throw new IllegalStateException("MISSING_CRYPTOGRAPHIC_KEY: Cryptograpic key was empty.");
        }
    }

    private String decryptPassword(String cipherText) {
        if (cryptographicKey.length > 0) {
            byte[] plainText = Crypto.decrypt(cryptographicKey, Crypto.decodeBase64(cipherText.getBytes()));
            return Crypto.bytesToString(plainText);
        } else {
            throw new IllegalStateException("MISSING_CRYPTOGRAPHIC_KEY: Cryptograpic key was empty.");
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean set(String netId, String server, char[] password) {
        LOG.debug("setting password: " + netId);
        String encryptedPassword = encryptPassword(new String(password));
        UserPrefs.setPreference(PASSWORD, encryptedPassword);
        UserPrefs.setPreference(USERNAME, netId);
        return true;
    }

    public String getSavedUsername() {
        return UserPrefs.getPreference(USERNAME);
    }

    public char[] getSavedPassword() {
        return get(getSavedUsername(), null);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public char[] get(String netId, String server) {
        if (netId == null) {
            return null;
        }
        LOG.debug("getting password: " + netId);
        String password = UserPrefs.getPreference(PASSWORD);
        if (password == null || password.trim().length() == 0) {
            return null;
        }
        return decryptPassword(password).toCharArray();
    }

    public void clearPassword(String netId) {
        UserPrefs.setPreference(PASSWORD, "");
    }

    @Override
    public void removeUserPassword(String netId) {
        clearPassword(netId);
    }
}
