package edu.byu.chartblock.test;

import edu.byu.chartblock.AccountCodeValidation;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 3:32 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:chartBlock-context.xml"
})
public class AccountCodeValidationImplTest extends BaseTest {
	private static final Logger LOG = Logger.getLogger(AccountCodeValidationImplTest.class);

	@Autowired
	private AccountCodeValidation accountCodeValidation;

	@Test
	public void testAccountCodeValidation() throws Exception {
		assertNotNull("need client", accountCodeValidation);
		accountCodeValidation.getAccount("11360000-6010-00000");
		LOG.debug("Done.");
	}
}
