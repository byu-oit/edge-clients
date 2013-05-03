package edu.byu.edge.person.basic.test;

import edu.byu.edge.person.basic.BasicPersonLookup;
import edu.byu.edge.person.basic.domain.BasicPerson;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 9/24/12
 * Time: 5:02 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class BasicPersonUnitTest {

	private static final Logger LOG = Logger.getLogger(BasicPersonUnitTest.class);

	@Autowired
	@Qualifier("basicPersonLookup")
	private BasicPersonLookup basicPersonLookup;

	@Test
	public void testIndividualLookup(){
		BasicPerson basicPerson = basicPersonLookup.getPersonByPersonId("249262982");
		LOG.info(basicPerson.getBirthDate());
		List<String> personIds = new LinkedList<String>();
		callListOfPersons(personIds, 0);
		personIds.add("249262982");
		callListOfPersons(personIds, 1);
		personIds.add("903201972");
		callListOfPersons(personIds, 2);
		personIds.add("929211602");
		callListOfPersons(personIds, 3);
		personIds.add("948204812");
		callListOfPersons(personIds, 4);
		personIds.add("122208942");
		callListOfPersons(personIds, 5);
		personIds.add("500204392");
		callListOfPersons(personIds, 6);
		personIds.add("759205212");
		callListOfPersons(personIds, 7);
		personIds.add("115204872");
		callListOfPersons(personIds, 8);
		personIds.add("138205232");
		callListOfPersons(personIds, 9);
		personIds.add("234202552");
		callListOfPersons(personIds, 10);
		personIds.add("333202782");
		callListOfPersons(personIds, 11);
		personIds.add("548255012");
		callListOfPersons(personIds, 12);
		personIds.add("870252152");
		callListOfPersons(personIds, 13);
	}

	@Test
	public void testNetId() throws Exception {
		BasicPerson basicPerson = basicPersonLookup.getPersonByNetId("endor4");
		LOG.info(basicPerson.getBirthDate());
	}

	@Test
	public void testByuId() throws Exception {
		BasicPerson basicPerson = basicPersonLookup.getPersonByByuId("951038980");
		LOG.info(basicPerson.getBirthDate());
	}

	@Test
	public void testBYUIdSearch() throws Exception {
		final List<BasicPerson> basicPersons = basicPersonLookup.searchBy("5103898");
		assertTrue(basicPersons.size() > 0);
	}

	@Test
	public void testPersonIdSearch() throws Exception {
		final List<BasicPerson> basicPersons = basicPersonLookup.searchBy("=4926298");
		assertTrue(basicPersons.size() > 0);
	}

	@Test
	public void testNetIdSearch() throws Exception {
		final List<BasicPerson> basicPersons = basicPersonLookup.searchBy("wct5");
		assertTrue(basicPersons.size() > 0);
	}

	@Test
	public void testSortNameSearch() throws Exception {
		final List<BasicPerson> basicPersons = basicPersonLookup.searchBy("Hirschi, Tim");
		assertTrue(basicPersons.size() > 0);
	}

	@Test
	public void testNameOrNetIdSearch() throws Exception {
		List<BasicPerson> basicPersons = basicPersonLookup.searchBy("Ti Hirsch");
		assertTrue(basicPersons.size() > 0);
		basicPersons = basicPersonLookup.searchBy("thirschi");
		assertTrue(basicPersons.size() > 0);
		basicPersons = basicPersonLookup.searchBy("Sarah Van Dyke");
		assertTrue(basicPersons.size() > 0);
		basicPersons = basicPersonLookup.searchBy("Jerome Remington Hirschi");
		assertTrue(basicPersons.size() > 0);
	}

	private void callListOfPersons(List<String> personIds, int expectedSize){
		List<BasicPerson> basicPersons = basicPersonLookup.getPersonsByListPersonIds(personIds);
		assertEquals(String.valueOf(personIds.size()) + " isn't the size", basicPersons.size(), expectedSize);
	}

}
