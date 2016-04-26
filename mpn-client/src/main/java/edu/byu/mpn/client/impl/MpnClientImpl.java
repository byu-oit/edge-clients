package edu.byu.mpn.client.impl;

import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.Device;
import edu.byu.mpn.domain.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by cwoodfie on 4/25/16.
 */
public class MpnClientImpl implements MpnClient {
	private static final Logger LOG = LogManager.getLogger(MpnClientImpl.class);

	private String googleApiUrl;
	private String googleApiKey;

	@Autowired
	public void setGoogleApiUrl(String googleApiUrl) {
		this.googleApiUrl = googleApiUrl;
	}

	@Autowired
	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	@Override
	public boolean pushAppleNotifications(List<Device> devices, Notification notification) throws IOException {
		LOG.info("pushAppleNotifications");
		return true;
	}

	@Override
	public boolean pushAndroidNotifications(List<String> registrationIds, Notification notification) throws IOException {
		LOG.info("pushAndroidNotifications");
		return true;
//		try {
//			final URL url = new URL(googleApiUrl);
//			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Accept", "application/json");
//			connection.setRequestProperty("Authorization", "key=" + googleApiKey);
//			connection.setRequestProperty("Content-Type", "application/json");
//		} catch (IOException e) {
//			LOG.error("Error connecting to Google Services", e);
//		}
	}
}
