package edu.byu.mpn.client.interfaces;

import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import edu.byu.mpn.client.helpers.GenericNotification;

import java.util.List;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {

	/**
	 * Sends notification to all registered mobile devices through AWS SNS topic
	 *
	 * @param title    Title of the notification to send. Shouldn't be very important. Only seen on Android devices
	 * @param message  Message of the notification to send
	 * @param topicArn The topicArn to send it to
	 * @return A PublishResult from Amazon
	 */
	PublishResult pushNotificationToTopic(String title, String message, String topicArn);

	/**
	 * Sends notification to a list of mobile devices through AWS SNS
	 *
	 * @param title        Title of the notification to send. Shouldn't be very important. Only seen on Android devices
	 * @param message      Message of the notification to send
	 * @param endpointArns A list of devices (endpointArns) to send it to
	 * @return False if any of the endpoints passed in were disabled, true if all were enabled at the time of sending the notification
	 */
	boolean pushNotifications(String title, String message, List<String> endpointArns);

	/**
	 * Creates endpoint with Amazon WS Simple Notification Service.
	 *
	 * @param token                  Token of the device to create
	 * @param platformApplicationArn The arn of the application (on AWS) to create an endpoint for
	 * @return Returns a CreatePlatformEndpointResult from Amazon, with the EndpointArn to be able to publish messages to this endpoint
	 * @throws InvalidParameterException if the token of the device is invalid or if it is already associated with a different endpoint
	 */
	CreatePlatformEndpointResult createPlatformEndpoint(String token, String platformApplicationArn) throws InvalidParameterException;

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
	 * @param token       Token of the device to update
	 * @param endpointArn EndpointArn of the device to update
	 * @throws InvalidParameterException if the token of the device is invalid or if it is already associated with a different endpoint
	 */
	void updatePlatformEndpoint(String token, String endpointArn) throws InvalidParameterException;

	/**
	 * Subscribe a device to a topic, and set the user data of the endpoint to contain the deviceName and the personId of the device
	 *
	 * @param endpointArn The endpointArn of the device to subscribe
	 * @param topicArn    The topic to subscribe it to
	 * @param personId    The personId the device is registered to
	 * @param deviceName  The name of the device
	 * @return A SubscribeResult from Amazon
	 */
	SubscribeResult subscribeDevice(String endpointArn, String topicArn, String personId, String deviceName);

	/**
	 * Unsubscribes a device (using the subscriptionArn stored in the device), and removes any user data associated with the device endpoint
	 *
	 * @param endpointArn     The endpointArn of the device to unsubscribe
	 * @param subscriptionArn The subscriptionArn of the subscription to be cancelled (associated with the device)
	 */
	void unsubscribeDevice(String endpointArn, String subscriptionArn);

	/**
	 * Send a notification to a device or a topic
	 *
	 * @param notification A generic notification including a default message, a iOS specific payload, and an Android specific payload
	 * @param endpoint     The endpoint you want to send it to, either a topic endpoint or a device endpoint
	 * @return A PublishResult from Amazon
	 */
	PublishResult publishNotification(GenericNotification notification, String endpoint);
}
