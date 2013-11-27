package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.PhoneLookup;
import edu.byu.edge.person.basic.domain.PhoneInformation;
import edu.byu.edge.person.basic.domain.PhoneType;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/10/12
 * Time: 10:58 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class PhoneLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(PhoneLookupUnitTest.class);

	@Autowired
	@Qualifier("phoneLookup")
	private PhoneLookup phoneLookup;

	@Test
	public void testPhoneLookup() throws Exception {
		final List<PhoneInformation> phoneInformation = phoneLookup.getPhoneInformationByPersonId("249262982");
		LOG.info(phoneInformation.size());
		LOG.info(phoneInformation.get(0).getPhoneType());
		List<PhoneInformation> phone = phoneLookup.getPhoneInformationByPersonIdAndType("249262982", PhoneType.MAL);
		logInformation(phone);
		phone = phoneLookup.getPhoneInformationByPersonIdAndType("903201972", PhoneType.MAL);
		logInformation(phone);
	}

	private void logInformation(List<PhoneInformation> phone) {
		for (final PhoneInformation pi : phone) {
			LOG.info(pi.getPhoneNumber());
		}
	}
}
