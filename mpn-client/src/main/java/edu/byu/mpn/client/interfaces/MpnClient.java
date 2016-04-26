package edu.byu.mpn.client.interfaces;

import edu.byu.mpn.domain.Device;
import edu.byu.mpn.domain.Notification;

import java.io.IOException;
import java.util.List;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {

	/**
	 * Send notification to Apple devices through APN apis
	 *
	 * @param devices      List of apple devices to notify
	 * @param notification Notification to send
	 * @throws IOException Throws exception if something doesn't work
	 */
	boolean pushAppleNotifications(List<Device> devices, Notification notification) throws IOException;

	/**
	 * Send notification to Android devices through Google
	 *
	 * @param registrationIds List of registrationIds of the Android devices to be notified
	 * @param notification    Notification to send
	 * @throws IOException Throws exception if something doesn't work
	 */
	boolean pushAndroidNotifications(List<String> registrationIds, Notification notification) throws IOException;
}
