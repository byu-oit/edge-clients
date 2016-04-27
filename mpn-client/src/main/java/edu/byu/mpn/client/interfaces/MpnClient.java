package edu.byu.mpn.client.interfaces;

import edu.byu.mpn.domain.AndroidNotificationWrapper;
import edu.byu.mpn.domain.AppleNotification;
import edu.byu.mpn.domain.Device;

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
	boolean pushAppleNotifications(List<Device> devices, AppleNotification notification) throws IOException;

	/**
	 * Send notification to Android devices through Google
	 *
	 * @param notification    Notification to send and device ids to send it to
	 * @throws IOException Throws exception if something doesn't work
	 */
	boolean pushAndroidNotifications(AndroidNotificationWrapper notification) throws IOException;
}
