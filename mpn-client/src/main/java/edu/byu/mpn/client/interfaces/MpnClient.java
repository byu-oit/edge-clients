package edu.byu.mpn.client.interfaces;

import edu.byu.mpn.domain.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {

	/**
	 * Sends notification to iPhones through APN apis
	 *
	 * @param devices      List of devices to notify
	 * @param notification Notification to send
	 * @return True if successful, false if not
	 */
	boolean pushAppleNotifications(List<Device> devices, AppleNotificationWrapper notification);

	/**
	 * Send notification to Android devices through Google
	 *
	 * @param notification Notification to send, along with list of devices (registrationIds from devices) to send it to
	 * @return The response from Google
	 */
	GoogleResponse pushAndroidNotifications(AndroidNotificationWrapper notification);
}
