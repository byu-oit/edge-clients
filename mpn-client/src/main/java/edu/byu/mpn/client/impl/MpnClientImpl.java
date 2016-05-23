package edu.byu.mpn.client.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.Device;
import edu.byu.mpn.helpers.GenericNotification;
import edu.byu.mpn.helpers.NotificationWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by cwoodfie on 4/25/16.
 */
public class MpnClientImpl implements MpnClient {
	private static final Logger LOG = LogManager.getLogger(MpnClientImpl.class);

	private AmazonSNSClient snsClient;

	@Autowired
	public void setSnsClient(AmazonSNSClient snsClient) {
		this.snsClient = snsClient;
		snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
	}

	public void pushNotificationToTopic(GenericNotification notification, String topicArn) {
		publishNotification(notification.getMessage(), topicArn);
	}

	public boolean pushNotifications(NotificationWrapper notification) {
		List<String> targetArns = notification.getTargetArns();
		boolean result = true;

		for (String targetArn : targetArns) {
			if (isEndpointEnabled(targetArn)) {
				try {
					publishNotification(notification.getNotification().getMessage(), targetArn);
				} catch (EndpointDisabledException e) {
					LOG.error(e.getMessage());
					result = false;
				}
			} else {
				result = false;
			}
		}
		return result;
	}

	public CreatePlatformEndpointResult createPlatformEndpoint(Device device, String platformApplicationArn) throws InvalidParameterException {
		return snsClient.createPlatformEndpoint(new CreatePlatformEndpointRequest().withToken(device.getToken()).withPlatformApplicationArn(platformApplicationArn));
	}

	public void updatePlatformEndpoint(Device device) throws InvalidParameterException {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("Token", device.getToken());
		attributes.put("Enabled", "true");
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()).withAttributes(attributes));
	}

	public boolean isEndpointEnabled(String endpointArn) {
		String enabled = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn)).getAttributes().get("Enabled");
		return "true".equals(enabled);
	}

	public SubscribeResult subscribeDevice(Device device, String topicArn) {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("CustomUserData", getUserData(device));
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()).withAttributes(attributes));
		return snsClient.subscribe(new SubscribeRequest().withTopicArn(topicArn).withEndpoint(device.getEndpointArn()).withProtocol("application"));
	}

	public void unsubscribeDevice(Device device) {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("CustomUserData", "");
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(device.getEndpointArn()).withAttributes(attributes));
		snsClient.unsubscribe(new UnsubscribeRequest().withSubscriptionArn(device.getSubscriptionArn()));
	}

	public PublishResult publishNotification(String message, String targetArn) {
		return snsClient.publish(new PublishRequest().withMessage(message).withTargetArn(targetArn));
	}

	private String getUserData(Device device) {
		return String.format("%s - %s", device.getPersonId(), device.getDeviceName());
	}
}
