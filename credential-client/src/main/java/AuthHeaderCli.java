import edu.byu.auth.client.ApiKeyClient;
import edu.byu.auth.client.CredentialClient;
import edu.byu.auth.client.SharedSecretCredentialResolver;
import edu.byu.auth.client.impl.ApiKeyClientImpl;
import edu.byu.auth.client.impl.WsSessionClientImpl;
import edu.byu.auth.client.impl.PasswordFileCredentialResolver;
import edu.byu.auth.client.impl.SharedSecretFileCredentialResolver;

import java.io.File;

/**
 * Created by wct5 on 12/3/14.
 */
public class AuthHeaderCli {
	public static void main(String[] args) {
		if (args.length < 3 || args.length > 5) {
			System.out.println("Usage: AuthHeaderCli <credential type p or s> <credential file> <key file> [actor [actorIdType]]");
			return;
		}
		final char type = args[0].toLowerCase().charAt(0);
		final CredentialClient client = getClient(type, new File(args[1]), new File(args[2]));
		if (client == null) {
			System.out.println("Credential or key file does not exist.");
		} else if (args.length == 3) {
			System.out.print(client.obtainAuthorizationHeaderString());
		} else if (args.length == 4) {
			System.out.print(client.obtainAuthorizationHeaderString(args[3]));
		} else if (args.length == 5) {
			System.out.print(client.obtainAuthorizationHeaderString(args[3], args[4]));
		}
		System.out.flush();
	}

	private static CredentialClient getClient(final char type, final File credFile, final File keyFile) {
		if (!credFile.exists() || !keyFile.exists()) return null;
		switch (type) {
			case 's':
				return apiClient(credFile, keyFile);
			case 'p':
				return passwordClient(credFile, keyFile);
			default:
				return null;
		}
	}

	private static SharedSecretCredentialResolver sharedSecretCredResolver(final File credFile, final File keyFile) {
		final SharedSecretFileCredentialResolver credentialResolver = new SharedSecretFileCredentialResolver();
		credentialResolver.setCredentialFile(credFile);
		credentialResolver.setKeyFile(keyFile);
		try {
			credentialResolver.afterPropertiesSet();
		} catch (Exception e) {
		}
		return credentialResolver;
	}

	private static CredentialClient apiClient(final File credFile, final File keyFile) {
		final ApiKeyClientImpl apiKeyClient = new ApiKeyClientImpl(5000, 5000);
		apiKeyClient.setResolver(sharedSecretCredResolver(credFile, keyFile));
		try {
			apiKeyClient.afterPropertiesSet();
		} catch (Exception e) {
		}
		return apiKeyClient;
	}

	private static PasswordFileCredentialResolver passwordCredResolver(final File credFile, final File keyFile) {
		final PasswordFileCredentialResolver credentialResolver = new PasswordFileCredentialResolver();
		credentialResolver.setCredentialFile(credFile);
		credentialResolver.setKeyFile(keyFile);
		try {
			credentialResolver.afterPropertiesSet();
		} catch (Exception e) {
		}
		return credentialResolver;
	}

	private static CredentialClient passwordClient(final File credFile, final File keyFile) {
		final WsSessionClientImpl client = new WsSessionClientImpl(5000, 5000);
		client.setResolver(passwordCredResolver(credFile, keyFile));
		try {
			client.afterPropertiesSet();
		} catch (Exception e) {
		}
		return client;
	}
}
