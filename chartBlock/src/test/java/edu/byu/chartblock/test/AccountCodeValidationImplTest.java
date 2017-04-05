package edu.byu.chartblock.test;

import edu.byu.chartblock.AccountCodeValidation;
import edu.byu.chartblock.ValidateChartBlockResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 3:32 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml"
})
public class AccountCodeValidationImplTest extends BaseTest {
	private static final Logger LOG = LogManager.getLogger(AccountCodeValidationImplTest.class);

	@Autowired
	private AccountCodeValidation accountCodeValidation;

	@Test
	public void testAccountCodeValidation1() throws Exception {
		assertNotNull("need client", accountCodeValidation);
		final ValidateChartBlockResult account = accountCodeValidation.getAccount("11405500-6000-40391");
		assertTrue(account.isSuccessful());
		final Map<String, String> map = account.getFields();
		final List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		for (final String k : keys) {
			LOG.debug(k + " -> " + map.get(k));
		}
		LOG.debug("Done.");
	}

	@Test
	public void testAccountCodeValidation2() throws Exception {
		assertNotNull("need client", accountCodeValidation);
		accountCodeValidation.getAccount("11360000-6010-00000");
		LOG.debug("Done.");
	}
}
