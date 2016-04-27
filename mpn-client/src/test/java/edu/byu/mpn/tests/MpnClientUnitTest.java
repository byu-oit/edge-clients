package edu.byu.mpn.tests;

import edu.byu.mpn.client.impl.MpnClientImpl;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by cwoodfie on 4/27/16.
 */

@RunWith(JUnit4.class)
public class MpnClientUnitTest {

	private MpnClient mpnClient = new MpnClientImpl();

	@Test
	public void testPushAndroidNotifications() throws IOException {
		ArrayList<String> registrationIds = new ArrayList<String>();
		registrationIds.add("");
		registrationIds.add("");
		mpnClient.pushAndroidNotifications(new AndroidNotificationWrapper(new AndroidNotification("Notification Title", "Notification Message"), registrationIds));
		mpnClient.pushAppleNotifications(new ArrayList<Device>(), new AppleNotification());
		assertTrue(true);
	}
}
