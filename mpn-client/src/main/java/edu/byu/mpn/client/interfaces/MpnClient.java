package edu.byu.mpn.client.interfaces;

import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import edu.byu.mpn.domain.Device;
import edu.byu.mpn.helpers.*;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {

	/**
	 * Sends notification to iPhones through APN apis
	 *
	 * @param notification Notification to send, along with list of devices to send it to. If list is empty, notification is sent to all registered devices
	 * @return An object containing the number of failures and successes. Null if notification was sent to a topic
	 */
	AmazonResponse pushAppleNotifications(AppleNotificationWrapper notification);

	/**
	 * Send notification to Android devices through Google
	 *
	 * @param notification Notification to send, along with list of devices (registrationIds from devices) to send it to
	 * @return The response from Google
	 */
	GoogleResponse pushAndroidNotifications(AndroidNotificationWrapper notification);

	/**
	 * Creates endpoint with Amazon WS Simple Notification Service
	 *
	 * @param device                 The device to create an endpoint for
	 * @param platformApplicationArn The arn of the application (on AWS) to create an endpoint for
	 * @return Returns the EndpointARN to be able to publish messages to this endpoint
	 * @throws Exception if the token of the device is invalid
	 */
	CreatePlatformEndpointResult createPlatformEndpoint(Device device, String platformApplicationArn) throws Exception;

	/**
	 * Checks if a device endpoint is currently enabled to receive notifications
	 *
	 * @param endpointArn The endpointArn of the device to check
	 * @return True if the endpoint is enabled, false if it is not
	 */
	boolean isEndpointEnabled(String endpointArn);

	/**
	 * Updates an endpoint if token was changed or re-registered
	 *
	 * @param device The device that has been updated
	 */
	void updatePlatformEndpoint(Device device);

	/**
	 * Subscribe a device to the emergency notification topic
	 *
	 * @param endpoint The endpoint of the device that is being subscribed
	 */
	SubscribeResult subscribeDevice(String endpoint, String topicArn);

	/**
	 * Unsubscribes a device
	 *
	 * @param subscription The subscription arn that should be cancelled
	 */
	void unsubscribeDevice(String subscription);

	/**
	 * Send a notification to a device or a topic
	 *
	 * @param message  The message you want to send in the notification
	 * @param endpoint The endpoint you want to send it to, either a topic endpoint or a device endpoint
	 */
	PublishResult publishNotification(String message, String endpoint);
}
