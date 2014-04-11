package edu.byu.edge.person.basic.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.byu.edge.person.basic.CodeCountryLookup;
import edu.byu.edge.person.basic.domain.CodeCountry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class CodeCountryLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(CodeCountryLookupUnitTest.class);

	@Autowired
	@Qualifier("codeCountryLookup")
	private CodeCountryLookup codeCountryLookup;

	@Test
	public void testIndividualLookup(){
		CodeCountry codeCountry = codeCountryLookup.getCountryByCodeId("SAU");
		assertTrue(codeCountry.getCountryPhonePrefix().equals("966"));
		
		codeCountry = codeCountryLookup.getCountryByCodeId("SLC");
		assertTrue(codeCountry.getCountryPhonePrefix().equals("758"));
		
		codeCountry = codeCountryLookup.getCountryByCodeId("SOI");
		assertTrue(codeCountry.getCountryPhonePrefix().equals("677"));
		
		codeCountry = codeCountryLookup.getCountryByCodeId("SWI");
		assertTrue(codeCountry.getCountryPhonePrefix().equals("41"));
		
		codeCountry = codeCountryLookup.getCountryByCodeId("TAH");
		assertTrue(codeCountry.getCountryPhonePrefix().equals("689"));
	}
	
	@Test
	public void testAllLookup(){
		List<CodeCountry> allCodeCountries = codeCountryLookup.getAllCodeCountryCodes();
		assertTrue(allCodeCountries.size() > 50);
	}

}
