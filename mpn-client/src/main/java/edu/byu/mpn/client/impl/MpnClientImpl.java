package edu.byu.mpn.client.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
	public boolean pushAppleNotifications(List<Device> devices, AppleNotificationWrapper notification) {
		LOG.info("pushAppleNotifications");
		return true;
	}

	@Override
	public GoogleResponse pushAndroidNotifications(AndroidNotificationWrapper notification) {
		LOG.info("pushAndroidNotifications");

		try {
			final URL url = new URL(googleApiUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", "key=" + googleApiKey);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			OutputStream outputStream = connection.getOutputStream();

			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			outputStream.write(gson.toJson(notification).getBytes());
			outputStream.close();

			BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
			return gson.fromJson(IOUtils.toString(input, "UTF-8"), GoogleResponse.class);
		} catch (IOException e) {
			LOG.error("Error connecting to Google Services", e);
			return null;
		}
	}
}
