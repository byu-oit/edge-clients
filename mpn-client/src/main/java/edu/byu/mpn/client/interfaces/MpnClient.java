package edu.byu.mpn.client.interfaces;

import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import edu.byu.mpn.domain.Device;
import edu.byu.mpn.helpers.*;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {

	/**
	 * Sends notification to all registered iPhones through AWS SNS topic
	 *
	 * @param notification The notification to send
	 * @param topicArn     The topicArn to send it to
	 */
	void pushNotificationToTopic(GenericNotification notification, String topicArn);

	/**
	 * Sends notification to a list of iPhones through AWS SNS
	 *
	 * @param notification Notification to send, along with list of devices (endpointArns) to send it to
	 * @return False if any of the endpoints passed in were disabled, true if all were enabled at the time of sending the notification
	 */
	boolean pushAppleNotifications(AppleNotificationWrapper notification);

	/**
	 * Send notification to Android devices through Google
	 *
	 * @param notification Notification to send, along with list of devices (registrationIds from devices) to send it to
	 * @return The response from Google
	 */
	GoogleResponse pushAndroidNotifications(AndroidNotificationWrapper notification);

	/**
	 * Creates endpoint with Amazon WS Simple Notification Service.
	 *
	 * @param device                 The device to create an endpoint for
	 * @param platformApplicationArn The arn of the application (on AWS) to create an endpoint for
	 * @return Returns the EndpointARN to be able to publish messages to this endpoint
	 * @throws InvalidParameterException if the token of the device is invalid or if it is already associated with a different endpoint
	 */
	CreatePlatformEndpointResult createPlatformEndpoint(Device device, String platformApplicationArn) throws InvalidParameterException;

	/**
	 * Checks if a device endpoint is currently enabled to receive notifications. If a topicArn is passed into the function it will throw an exception.
	 * Don't pass in topicArns.
	 *
	 * @param endpointArn The endpointArn of the device to check
	 * @return True if the endpoint is enabled, false if it is not
	 */
	boolean isEndpointEnabled(String endpointArn);

	/**
	 * Updates an endpoint if token was changed. Sets Token to device.getToken() and Enabled to true
	 *
	 * @param device The device that has been updated
	 * @throws InvalidParameterException if the token of the device is invalid or if it is already associated with a different endpoint
	 */
	void updatePlatformEndpoint(Device device) throws InvalidParameterException;

	/**
	 * Subscribe a device to a topic, and set the user data of the endpoint to contain the deviceName and the personId of the device
	 *
	 * @param device   The device to subscribe
	 * @param topicArn The topic to subscribe it to
	 */
	SubscribeResult subscribeDevice(Device device, String topicArn);

	/**
	 * Unsubscribes a device (using the subscriptionArn stored in the device), and removes any user data associated with the device endpoint
	 *
	 * @param device The device that should be unsubscribed
	 */
	void unsubscribeDevice(Device device);

	/**
	 * Send a notification to a device or a topic
	 *
	 * @param message  The message you want to send in the notification
	 * @param endpoint The endpoint you want to send it to, either a topic endpoint or a device endpoint
	 */
	PublishResult publishNotification(String message, String endpoint);
}
