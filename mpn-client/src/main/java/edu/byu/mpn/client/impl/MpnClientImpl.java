package edu.byu.mpn.client.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.google.gson.Gson;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.client.helpers.GenericNotification;
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

	public PublishResult pushNotificationToTopic(String title, String message, String topicArn) {
		return publishNotification(new GenericNotification(title, message), topicArn);
	}

	public boolean pushNotifications(String title, String message, List<String> targetArns) {
		boolean result = true;
		for (String targetArn : targetArns) {
			if (isEndpointEnabled(targetArn)) {
//				This function throws an exception if the endpoint is disabled, so we added the if instead of doing a try/catch
				publishNotification(new GenericNotification(title, message), targetArn);
			} else {
				result = false;
			}
		}
		return result;
	}

	public CreatePlatformEndpointResult createPlatformEndpoint(String token, String platformApplicationArn) throws InvalidParameterException {
		return snsClient.createPlatformEndpoint(new CreatePlatformEndpointRequest().withToken(token).withPlatformApplicationArn(platformApplicationArn));
	}

	public void updatePlatformEndpoint(String token, String endpointArn) throws InvalidParameterException {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("Token", token);
		attributes.put("Enabled", "true");
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(endpointArn).withAttributes(attributes));
	}

	public boolean isEndpointEnabled(String endpointArn) {
		String enabled = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn)).getAttributes().get("Enabled");
		return "true".equals(enabled);
	}

	public SubscribeResult subscribeDevice(String endpointArn, String topicArn, String personId, String deviceName) {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("CustomUserData", String.format("%s - %s", personId, deviceName));
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(endpointArn).withAttributes(attributes));
		return snsClient.subscribe(new SubscribeRequest().withTopicArn(topicArn).withEndpoint(endpointArn).withProtocol("application"));
	}

	public void unsubscribeDevice(String endpointArn, String subscriptionArn) {
		GetEndpointAttributesResult endpointAttributes = snsClient.getEndpointAttributes(new GetEndpointAttributesRequest().withEndpointArn(endpointArn));
		Map<String, String> attributes = endpointAttributes.getAttributes();
		attributes.put("CustomUserData", "");
		snsClient.setEndpointAttributes(new SetEndpointAttributesRequest().withEndpointArn(endpointArn).withAttributes(attributes));
		snsClient.unsubscribe(new UnsubscribeRequest().withSubscriptionArn(subscriptionArn));
	}

	public PublishResult publishNotification(GenericNotification notification, String targetArn) {
		return snsClient.publish(new PublishRequest().withMessageStructure("json").withMessage(new Gson().toJson(notification)).withTargetArn(targetArn));
	}

}
