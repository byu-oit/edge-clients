package edu.byu.edge.person.basic.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import edu.byu.edge.person.basic.PersonAddressLookup;
import edu.byu.edge.person.basic.domain.PersonAddress;

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
public class PersonAddressLookupUnitTest {
	private static final Logger LOG = Logger.getLogger(PersonAddressLookupUnitTest.class);

	@Autowired
	@Qualifier("personAddressLookup")
	private PersonAddressLookup personAddressLookup;
	
	//These tests will probably fail when Wyatt moves :)
	
	@Test
	public void testPerformAddressSearch() throws Exception {
		StopWatch watch = new StopWatch("Test PersonAddressLookup.performAddressSearch");
		//size = 20337
		String name = "Taylor";
		String addressLine1 = null;
		String city = null;
		String state = null;
		String zip = null;
		watch.start("by name..");
		List<PersonAddress> addressInformation = personAddressLookup.performAddressSearch(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 15000);
		
		//size = 8241
		name = "Taylor";
		addressLine1 = null;
		city = null;
		state = "UT";
		zip = null;
		watch.start("by name and state..");
		addressInformation = personAddressLookup.performAddressSearch(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 5000);
		
		//size = 149
		name = "Taylor";
		addressLine1 = null;
		city = "Springv";
		state = "UT";
		zip = null;
		watch.start("by name and state and partial city..");
		addressInformation = personAddressLookup.performAddressSearch(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 100);
		
		//size = 148
		name = "Taylor";
		addressLine1 = null;
		city = "Springv";
		state = "UT";
		zip = "84663";
		watch.start("by name and state and partial city and zip..");
		addressInformation = personAddressLookup.performAddressSearch(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() >= 100);
		
		//size = 5
		name = "Taylor";
		addressLine1 = "Glenbarr";
		city = "Springv";
		state = "UT";
		zip = "84663";
		watch.start("by name and state and partial city and zip and partial address..");
		addressInformation = personAddressLookup.performAddressSearch(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 1);
		
		LOG.info(watch.prettyPrint());
	}
	
	@Test
	public void testPerformAddressSearchOptimized() throws Exception {
		StopWatch watch = new StopWatch("Test PersonAddressLookup.performAddressSearchOptimized");
		//size = 20337
		String name = "Taylor";
		String addressLine1 = null;
		String city = null;
		String state = null;
		String zip = null;
		watch.start("by name..");
		List<PersonAddress> addressInformation = personAddressLookup.performAddressSearchOptimized(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 15000);
		
		//size = 8241
		name = "Taylor";
		addressLine1 = null;
		city = null;
		state = "UT";
		zip = null;
		watch.start("by name and state..");
		addressInformation = personAddressLookup.performAddressSearchOptimized(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 5000);
		
		//size = 149
		name = "Taylor";
		addressLine1 = null;
		city = "Springv";
		state = "UT";
		zip = null;
		watch.start("by name and state and partial city..");
		addressInformation = personAddressLookup.performAddressSearchOptimized(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 100);
		
		//size = 148
		name = "Taylor";
		addressLine1 = null;
		city = "Springv";
		state = "UT";
		zip = "84663";
		watch.start("by name and state and partial city and zip..");
		addressInformation = personAddressLookup.performAddressSearchOptimized(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() >= 100);
		
		//size = 5
		name = "Taylor";
		addressLine1 = "Glenbarr";
		city = "Springv";
		state = "UT";
		zip = "84663";
		watch.start("by name and state and partial city and zip and partial address..");
		addressInformation = personAddressLookup.performAddressSearchOptimized(name, addressLine1, city, state, zip);
		watch.stop();
		
		assertTrue(addressInformation.size() > 1);
		
		LOG.info(watch.prettyPrint());
	}
}
