package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.Email;
import edu.byu.edge.person.basic.EmailAddressLookup;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 10/26/12
 * Time: 1:27 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class EmailLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(EmailLookupUnitTest.class);

	@Autowired
	@Qualifier("emailLookup")
	private EmailAddressLookup emailAddressLookup;

	@Test
	public void testEmailLookup() throws Exception {
		Email email = emailAddressLookup.getByPersonId("249262982");
		LOG.info(email.getEmailAddress());
		email = emailAddressLookup.getByPersonId("TEST");
		LOG.info(email);
	}
}
