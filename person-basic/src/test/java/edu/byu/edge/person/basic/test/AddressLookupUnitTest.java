package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.AddressLookup;
import edu.byu.edge.person.basic.domain.Address;
import edu.byu.edge.person.basic.domain.AddressType;
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
 * Time: 12:17 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class AddressLookupUnitTest {
	private static final Logger LOG = Logger.getLogger(AddressLookupUnitTest.class);

	@Autowired
	@Qualifier("addressLookup")
	private AddressLookup addressLookup;

	@Test
	public void testAddress() throws Exception {
		List<Address> addressInformation = addressLookup.getAddressByPersonId("249262982");
		LOG.info(addressInformation.size());
		LOG.info(addressInformation.get(0).getAddressType());
		Address address = addressLookup.getAddressByPersonIdAndType("249262982", AddressType.PRM);
		LOG.info(address.getLine1());
	}
}
