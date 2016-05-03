package edu.byu.mpn.client.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
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

	@Override
	public void pushAppleNotifications(AppleNotificationWrapper notification) {
		LOG.info("pushAppleNotificationsToTopic");

		List<String> targetArns = notification.getTargetArns();
		for (String targetArn : targetArns) {
			publishNotification(notification.getAps().getBody(), targetArn);
		}
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

			InputStream input = new BufferedInputStream(connection.getInputStream());
			GoogleResponse googleResponse = gson.fromJson(IOUtils.toString(input, "UTF-8"), GoogleResponse.class);
			LOG.info(googleResponse);
			return googleResponse;
		} catch (IOException e) {
			LOG.error("Error connecting to Google Services", e);
			return null;
		}
	}

	public CreatePlatformEndpointResult createPlatformEndpoint(String token, String platformApplicationArn) {
		CreatePlatformEndpointRequest request = new CreatePlatformEndpointRequest().withPlatformApplicationArn(platformApplicationArn).withToken(token);
		return snsClient.createPlatformEndpoint(request);
	}

	public void updatePlatformEndpoint(Device device) {
		SetEndpointAttributesRequest request = new SetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn());
		request.addAttributesEntry("Token", device.getToken());
		request.addAttributesEntry("Enabled", "true");
		snsClient.setEndpointAttributes(request);
	}

	public SubscribeResult subscribeDevice(String endpoint, String topicArn) {
		SubscribeRequest request = new SubscribeRequest().withTopicArn(topicArn).withEndpoint(endpoint).withProtocol("application");
		return snsClient.subscribe(request);
	}

	public void unsubscribeDevice(String endpoint) {
		UnsubscribeRequest request = new UnsubscribeRequest().withSubscriptionArn(endpoint);
		snsClient.unsubscribe(request);
	}

	public PublishResult publishNotification(String message, String targetArn) {
		PublishRequest request = new PublishRequest().withMessage(message).withTargetArn(targetArn);
		return snsClient.publish(request);
	}
}
