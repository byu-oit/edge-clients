package edu.byu.edge.client.auth;

import edu.byu.edge.client.auth.impl.ByuAuthClientImpl;
import edu.byu.edge.client.domain.Nonce;
import edu.byu.edge.client.domain.WsSession;

import java.io.Console;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 6/7/12
 */
public final class CliInterface {

	private static final Console CONSOLE = System.console();
	private static final PrintWriter OUT = CONSOLE.writer();

	public static void main(String[] args) {
		OUT.println("Welcome to the command-line interface for the BYU Temporary API Key client.");
		OUT.println("You will be prompted for your username and password. This should be your Route-Y Net-ID and password.");
		OUT.println("You will need to specify a timeout for your session.");
		OUT.println();
		OUT.println("Please enter your username:");
		final String user = readString();
		OUT.println("Please enter your password:");
		final String pass = readPassword();
		OUT.println("Please provide a session duration in minutes (1-480).");
		final int dur = readIntExitOnFail();
		new CliInterface(user, pass, dur).loop();
	}

	private static int readIntExitOnFail() {
		try {
			return readInt();
		} catch (final IllegalArgumentException e) {
			OUT.println("Invalid duration. Exiting.");
			System.exit(-1);
		}
		return 0;
	}

	private static String readString() {
		return CONSOLE.readLine();
	}

	private static String readPassword() {
		final char[] chars = CONSOLE.readPassword();
		final String result = new String(chars);
		for (int i = 0; i < chars.length; i++) {
			chars[0] = 0;
		}
		return result;
	}

	private static int readInt() {
		final String val = CONSOLE.readLine();
		try {
			return Integer.parseInt(val);
		} catch (final Throwable t) {
			throw new IllegalArgumentException("Please enter a valid integer.");
		}
	}

	private final ThreadPoolExecutor EXEC;
	private final ByuAuthClient client;
	private final String netId;
	private final String password;
	private final int duration;

	private volatile WsSession session;
	private volatile boolean keepGoing = true;

	private CliInterface(final String netId, final String password, final int duration) {
		this.EXEC = new ThreadPoolExecutor(5, 5, 90L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(25));
		this.client = new ByuAuthClientImpl(5000, 1000);
		this.netId = netId;
		this.password = password;
		this.duration = duration;
		EXEC.submit(new LoginTask(this));
	}

	private void loop() {
		displayCommands();
		while (true) {
			OUT.println("");
			OUT.println("Enter command ('help'/'h'/'?' for help):");
			switch (parseCommand(readString())) {
				case 0: displayCommands();
					break;
				case 1: doInfo();
					break;
				case 2: doNonce();
					break;
				case 3: doActor();
					break;
				case 4: doLogout();
					return;
			}
		}
	}

	private int parseCommand(final String line) {
		if (line == null) return 0;
		if ("help".equals(line)) return 0;
		if ("h".equals(line)) return 0;
		if ("?".equals(line)) return 0;
		if ("info".equals(line)) return 1;
		if ("i".equals(line)) return 1;
		if ("nonce".equals(line)) return 2;
		if ("n".equals(line)) return 2;
		if ("actor".equals(line)) return 3;
		if ("a".equals(line)) return 3;
		if ("exit".equals(line)) return 4;
		if ("e".equals(line)) return 4;
		if ("quit".equals(line)) return 4;
		if ("q".equals(line)) return 4;
		return 0;
	}

	private void doInfo() {
		synchronized (EXEC) {
			OUT.println("");
			OUT.format("API Key:       %s\n", session.getApiKey());
			OUT.format("Shared Secret: %s\n", session.getSharedSecret());
			OUT.format("PersonId:      %s\n", session.getPersonId());
			OUT.format("Expiration:    %s\n", session.getExpireDate().getTime());
		}
	}

	private void doNonce() {
		synchronized (EXEC) {
			try {
				final Nonce nonce = client.obtainNonce(session);
				final String hmac = client.calculateHmac(session, nonce);
				final String head = client.getHeaderValue(session, nonce, hmac);
				OUT.println("");
				OUT.format("Nonce Key:   %s\n", nonce.getNonceKey());
				OUT.format("Nonce Value: %s\n", nonce.getNonceValue());
				OUT.format("HMAC:        %s\n", hmac);
				OUT.format("Auth Header: %s\n", head);
			} catch (final Throwable t) {
				OUT.println("Error getting nonce:");
				t.printStackTrace(OUT);
			}
		}
	}

	private void doActor() {
		synchronized (EXEC) {
			try {
				OUT.println("");
				OUT.println("Enter actor NetId:");
				final String actorNetId = readString();
				if (actorNetId == null || "".equals(actorNetId.replaceAll("\\s+?", ""))) {
					OUT.println("Invalid actor NetId.");
					return;
				}
				final Nonce nonce = client.obtainNonce(session, actorNetId);
				final String hmac = client.calculateHmac(session, nonce);
				final String head = client.getHeaderValue(session, nonce, hmac);
				OUT.println("");
				OUT.format("Nonce Key:   %s\n", nonce.getNonceKey());
				OUT.format("Nonce Value: %s\n", nonce.getNonceValue());
				OUT.format("HMAC:        %s\n", hmac);
				OUT.format("Auth Header: %s\n", head);
			} catch (final Throwable t) {
				OUT.println("Error getting nonce with actor:");
				t.printStackTrace(OUT);
			}
		}
	}

	private void doLogout() {
		synchronized (EXEC) {
			keepGoing = false;
			EXEC.shutdownNow();
			client.logout(session);
		}
	}

	private void displayCommands() {
		OUT.println("");
		OUT.println("Commands are:");
		OUT.println("'help' '?' 'h' for this list");
		OUT.println("'info' 'i' to display the current session attributes.");
		OUT.println("'nonce' 'n' to generate a normal nonce - this will display the nonce and the authorization header.");
		OUT.println("'actor' 'a' to generate a nonce with actor, you will be prompted to enter the actor netId - this will display the same info as 'nonce'.");
		OUT.println("'exit' 'e' 'quit' 'q' to leave program.");
	}


	private static class LoginTask implements Runnable {

		private final CliInterface cli;

		private LoginTask(final CliInterface cli) {
			this.cli = cli;
		}

		@Override
		public void run() {
			synchronized (cli.EXEC) {
				cli.session = cli.client.login(cli.netId, cli.password, cli.duration);
				if (cli.keepGoing) cli.EXEC.submit(new RenewTask(cli));
			}
		}
	}

	private static class RenewTask implements Runnable {

		private final CliInterface cli;

		private RenewTask(final CliInterface cli) {
			this.cli = cli;
		}

		@Override
		public void run() {
			final long expTime = getExpTime();
			try {
				Thread.sleep(expTime - System.currentTimeMillis() - 5000);
			} catch (InterruptedException e) {
			}
			synchronized (cli.EXEC) {
				cli.session = cli.client.renew(cli.session, cli.duration);
				if (cli.keepGoing) cli.EXEC.submit(new RenewTask(cli));
			}
		}

		private long getExpTime() {
			synchronized (cli.EXEC) {
				final WsSession sess = cli.session;
				return sess.getExpireDate().getTimeInMillis();
			}
		}
	}
}
