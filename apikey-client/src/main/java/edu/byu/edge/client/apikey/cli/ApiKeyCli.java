package edu.byu.edge.client.apikey.cli;

import edu.byu.edge.client.apikey.da.SharedSecretDao;
import edu.byu.edge.client.apikey.domain.SharedSecret;
import edu.byu.edge.client.auth.ByuAuthClient;
import edu.byu.edge.client.auth.impl.ByuAuthClientImpl;
import edu.byu.edge.client.domain.Nonce;
import edu.byu.edge.client.domain.WsSession;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Console;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 11/14/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 11/14/2012
 */
public final class ApiKeyCli {
	private static final Console CONSOLE = System.console();
	private static final PrintWriter OUT = CONSOLE.writer();

	public static void main(String[] args) {
		try {
			createObjects();
		} catch (final Throwable t) {
			OUT.println("An error occurred during initialization.");
			t.printStackTrace(OUT);
			return;
		}

		try {
			SharedSecret ss;
			do {
				final List<SharedSecret> keys = getKeys();
				OUT.println();
				OUT.println("Welcome to the command-line interface for the BYU API Key client.");
				OUT.println("Please select an ApiKey to use. You may enter the line number or the person-id.");
				OUT.println();
				OUT.println(" 0: Quit");
				final Map<String, SharedSecret> map = new LinkedHashMap<String, SharedSecret>(2 * keys.size() + 1, .9999f);
				int i = 1;
				for (final SharedSecret k : keys) {
					OUT.println(String.format("%2d: %s - %s (%s)", i, k.getPersonId(), k.getName(), k.getExpirationDate()));
					map.put(String.valueOf(i), k);
					map.put(k.getPersonId(), k);
					i++;
				}
				OUT.println();
				final String val = readString();
				if ("".equals(val) || "0".equals(val)) break;
				if (!map.containsKey(val)) {
					OUT.println("Invalid selection. Exiting.");
					break;
				}
				ss = fillOutKey(map.get(val));
			} while (new ApiKeyCli(ss).loop());
		} catch (final Throwable t) {
			OUT.println("An error occurred. Exiting.");
			t.printStackTrace(OUT);
		}
		closeObjects();
	}

	private static volatile BeanFactory ctx;

	private static List<SharedSecret> getKeys() {
		final SharedSecretDao dao = ctx.getBean("sharedSecretDao", SharedSecretDao.class);
		return dao.findAllDepartment();
	}

	private static SharedSecret fillOutKey(final SharedSecret ss) {
		final SharedSecretDao dao = ctx.getBean("sharedSecretDao", SharedSecretDao.class);
		final SharedSecret byId = dao.findById(ss.getCredentialId());
		ss.setSharedSecretValue(byId.getSharedSecretValue());
		ss.setWsid(byId.getWsid());
		return ss;
	}

	private static void createObjects() {
		ctx = new ClassPathXmlApplicationContext("classpath:oracle-context.xml");
	}

	private static void closeObjects() {
		if (ctx != null) ((ClassPathXmlApplicationContext) ctx).close();
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
	private final SharedSecret ss;
	private final WsSession session;

	private volatile boolean keepGoing = true;

	public ApiKeyCli(final SharedSecret ss) {
		this.EXEC = new ThreadPoolExecutor(5, 5, 90L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(25));
		this.client = new ByuAuthClientImpl(5000, 1000);
		this.ss = ss;
		this.session = new WsSession();
		session.setApiKey(ss.getWsid());
		final Calendar cal = Calendar.getInstance();
		cal.setTime(ss.getExpirationDate());
		session.setExpireDate(cal);
		session.setSharedSecret(ss.getSharedSecretValue());
		session.setPersonId(ss.getPersonId());
	}

	private boolean loop() {
		displayCommands();
		while (true) {
			OUT.println("");
			OUT.println("Enter command ('help'/'h'/'?' for help):");
			switch (parseCommand(readString())) {
				case -1:
					OUT.println("");
					OUT.println("Session not created yet.");
					break;
				case 0: displayCommands();
					break;
				case 1: doInfo();
					break;
				case 2: doNonce();
					break;
				case 3: doActor();
					break;
				case 4: doLogout();
					return true;
				case 5: doLogout();
					return false;
			}
		}
	}

	private int parseCommand(final String line) {
		final boolean ns = session == null;
		if (line == null) return 0;
		if ("help".equals(line)) return 0;
		if ("h".equals(line)) return 0;
		if ("?".equals(line)) return 0;
		if (!ns && "info".equals(line)) return 1;
		if (!ns && "i".equals(line)) return 1;
		if (!ns && "nonce".equals(line)) return 2;
		if (!ns && "n".equals(line)) return 2;
		if (!ns && "actor".equals(line)) return 3;
		if (!ns && "a".equals(line)) return 3;
		if ("login".equals(line)) return 4;
		if ("l".equals(line)) return 4;
		if ("exit".equals(line)) return 5;
		if ("e".equals(line)) return 5;
		if ("quit".equals(line)) return 5;
		if ("q".equals(line)) return 5;
		if (ns) return -1;
		return 0;
	}

	private void doInfo() {
		synchronized (EXEC) {
			OUT.println("");
			OUT.format("Name   :       %s\n", ss.getName());
			OUT.format("PersonId:      %s\n", ss.getPersonId());
			OUT.format("Shared Secret: %s\n", ss.getSharedSecretValue());
			OUT.format("WsID:          %s\n", ss.getWsid());
			OUT.format("Expiration:    %s\n", ss.getExpirationDate());
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
				OUT.format("Auth Header: %s\n", head.replace("Nonce-Encoded-WsSession-Key", "Nonce-Encoded-API-Key"));
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
				OUT.format("Auth Header: %s\n", head.replace("Nonce-Encoded-WsSession-Key", "Nonce-Encoded-API-Key"));
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
			OUT.println("Logging out...");
			OUT.println("");
		}
	}

	private void displayCommands() {
		OUT.println("");
		OUT.println("Commands are:");
		OUT.println("'help' '?' 'h' for this list");
		OUT.println("'login' 'l' to logout and login (change user)");
		OUT.println("'info' 'i' to display the current session attributes.");
		OUT.println("'nonce' 'n' to generate a normal nonce - this will display the nonce and the authorization header.");
		OUT.println("'actor' 'a' to generate a nonce with actor, you will be prompted to enter the actor netId - this will display the same info as 'nonce'.");
		OUT.println("'exit' 'e' 'quit' 'q' to leave program.");
	}

}
