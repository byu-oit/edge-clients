package edu.byu.mpn.tests;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import edu.byu.mpn.client.impl.MpnClientImpl;
import edu.byu.mpn.client.interfaces.MpnClient;
import edu.byu.mpn.domain.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cwoodfie on 4/27/16.
 */
@Ignore
@RunWith(JUnit4.class)
public class MpnClientUnitTest {

	private MpnClientImpl mpnClient = new MpnClientImpl();

	@Before
	public void setup() {

	}

	@Test
	public void testPushAndroidNotifications() throws IOException {
		List<String> androidRegistrationIds = new ArrayList<String>();
//		androidRegistrationIds.add("");
//		androidRegistrationIds.add("");
//		GoogleResponse googleResponse = mpnClient.pushAndroidNotifications(new AndroidNotificationWrapper(new AndroidNotification("Notification Title", "Notification Message"), androidRegistrationIds));
//		assertEquals(2, googleResponse.getSuccess());
		assertTrue(true);
	}

	@Ignore
	@Test
	public void testCreatePlatformEndpoint() {
		mpnClient.createPlatformEndpoint("");
	}

	@Test
	public void testPublishNotification() {
		mpnClient.publishNotification("Hi Nathan", "");
	}

	@Test
	public void testSubscribeDevice() {
		mpnClient.subscribeDevice("", "");
	}

	@Test
	public void testUnsubscribeDevice() {
		mpnClient.unsubscribeDevice("");
	}
}
