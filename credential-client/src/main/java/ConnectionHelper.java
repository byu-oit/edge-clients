import com.google.common.base.Charsets;
import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import edu.byu.crypto.Crypto;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 06/17/2014
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 06/17/2014
 */
public class ConnectionHelper {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Usage: <action> <action args>");
			System.out.println("\tWhere action is:");
			System.out.println("\t'e' for encrypt");
			System.out.println("\t'd' for decrypt.");
			System.out.println("\t'g' for generate key.");
			System.out.println("");
			System.out.println("Args for encrypt and decrypt actions are a String to encrypt or decrypt.");
			System.out.println("A second arg can be included to specify the encryption key file. When not specified, the default user key file will be used.");
			System.out.println("");
			System.out.println("Args for generate key are as follows:");
			System.out.println("\tThe file to place the key (absolute/relative path)");
			System.out.println("\tAn optional number of bytes (24 <= n <= 64, def 24).");
			System.out.println();
			return;
		}
		final char action = args[0].charAt(0);
		final byte[] keyBytes = getKeyBytes(args);
		switch(action) {
			case 'e':
			case 'E':
				checkArgs(2, 3, args);
				doEncrypt(args[1], keyBytes);
				break;
			case 'd':
			case 'D':
				checkArgs(2, 3, args);
				doDecrypt(args[1], keyBytes);
				break;
			case 'g':
			case 'G':
				checkArgs(2, 3, args);
				doGenerate(args[1], getByteCount(args));
				break;
			default:
				System.out.println("Invalid action '" + action + "' specified.");
				System.exit(2);
		}
	}

	private static void checkArgs(final int min, final int max, final String[] args) {
		if (args.length < min || args.length > max) {
			System.out.println("Wrong number of arguments for action specified.");
			System.exit(1);
		}
	}

	private static byte[] getKeyBytes(final String[] args) {
		if (args.length == 3) {
			return Crypto.readKeyFile(args[2]);
		} else {
			return Crypto.readUserKeyFile(true);
		}
	}

	private static void doDecrypt(final String base, final byte[] keyBytes) {
		final byte[] db = Crypto.decrypt(keyBytes, Crypto.decodeBase64(base));
		System.out.println("Decrypted to " + db.length + " bytes.");
		System.out.println(new String(db, Charsets.UTF_8));
	}

	private static void doEncrypt(final String base, final byte[] keyBytes) {
		final byte[] db = Crypto.encrypt(keyBytes, base);
		System.out.println("Encrypted to " + db.length + " bytes.");
		System.out.println(new String(Crypto.encodeBase64(db), Charsets.UTF_8));
	}

	private static void doGenerate(final String path, final int numbytes) throws IOException {
		final byte[] bytes = new byte[numbytes];
		new SecureRandom().nextBytes(bytes);
		final ByteSink sink = Files.asByteSink(new File(path));
		sink.write(bytes);
	}

	private static int getByteCount(final String[] args) {
		if (args.length == 2) return 24;
		return Math.min(64, Math.max(24, Integer.parseInt(args[2])));
	}
}
