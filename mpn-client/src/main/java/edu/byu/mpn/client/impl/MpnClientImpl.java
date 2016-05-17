package edu.byu.mpn.client.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.Device;
import edu.byu.mpn.helpers.AmazonResponse;
import edu.byu.mpn.helpers.AndroidNotificationWrapper;
import edu.byu.mpn.helpers.AppleNotificationWrapper;
import edu.byu.mpn.helpers.GoogleResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwoodfie on 4/25/16.
 */
public class MpnClientImpl implements MpnClient {
	private static final Logger LOG = LogManager.getLogger(MpnClientImpl.class);

	private String googleApiUrl;
	private String googleApiKey;
	private AmazonSNSClient snsClient;

	@Autowired
	public void setGoogleApiUrl(String googleApiUrl) {
		this.googleApiUrl = googleApiUrl;
	}

	@Autowired
	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	@Autowired
	public void setSnsClient(AmazonSNSClient snsClient) {
		this.snsClient = snsClient;
		snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
	}

	public AmazonResponse pushAppleNotifications(AppleNotificationWrapper notification) {
		LOG.info("pushAppleNotifications");

		int success = 0;
		int failure = 0;

		List<String> targetArns = notification.getTargetArns();
		for (String targetArn : targetArns) {
			LOG.info(publishNotification(notification.getAps().getBody(), targetArn));
			if (isEndpointEnabled(targetArn)) { // TODO: 5/17/16 Check to make sure it's not a topicArn before doing this if statement 
				success++;
			} else {
				failure++;
			}
		}
		return new AmazonResponse(success, failure);
	}

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

			InputStream input = new BufferedInputStream(connection.getInputStream());
			GoogleResponse googleResponse = gson.fromJson(IOUtils.toString(input, "UTF-8"), GoogleResponse.class);
			LOG.info(googleResponse);
			return googleResponse;
		} catch (IOException e) {
			LOG.error("Error connecting to Google Services", e);
			return null;
		}
	}

	public CreatePlatformEndpointResult createPlatformEndpoint(Device device, String platformApplicationArn) throws Exception {
		return snsClient.createPlatformEndpoint(new CreatePlatformEndpointRequest().withToken(device.getToken())
		                                                                           .withPlatformApplicationArn(platformApplicationArn)
		                                                                           .withCustomUserData(getUserData(device)));
	}

	public void updatePlatformEndpoint(Device device) {
		SetEndpointAttributesRequest request = new SetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn());
		request.addAttributesEntry("Token", device.getToken());
		request.addAttributesEntry("Enabled", "true");
		request.addAttributesEntry("User Data", getUserData(device));
		snsClient.setEndpointAttributes(request);
	}

	public boolean isEndpointEnabled(String endpointArn) {
		String enabled = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn))
		                          .getAttributes()
		                          .get("Enabled");
		return "true".equals(enabled);
	}

	public SubscribeResult subscribeDevice(String endpoint, String topicArn) {
		return snsClient.subscribe(new SubscribeRequest().withTopicArn(topicArn).withEndpoint(endpoint).withProtocol("application"));
	}

	public void unsubscribeDevice(String endpoint) {
		snsClient.unsubscribe(new UnsubscribeRequest().withSubscriptionArn(endpoint));
	}

	public PublishResult publishNotification(String message, String targetArn) {
		return snsClient.publish(new PublishRequest().withMessage(message).withTargetArn(targetArn));
	}

	private String getUserData(Device device) {
		if (device.getPersonId() != null && !device.getPersonId().isEmpty()) {
			return String.format("%s - %s", device.getPersonId(), device.getDeviceName());
		} else {
			return null;
		}
	}
}
