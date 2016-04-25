package edu.byu.mpn.client.interfaces;

import edu.byu.mpn.domain.Device;
import edu.byu.mpn.domain.Notification;

import java.io.IOException;

/**
 * Created by cwoodfie on 4/25/16.
 */
public interface MpnClient {
	/**
	 * Pushes notification passed in to the device passed in
	 * @param device Device to notify
	 * @param notification Notification to send
	 * @return True if notification was sent successfully, false if not
	 */
	boolean pushNotification(Device device, Notification notification) throws IOException;
}
