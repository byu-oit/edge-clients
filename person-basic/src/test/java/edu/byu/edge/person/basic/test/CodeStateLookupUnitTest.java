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

import edu.byu.edge.person.basic.CodeStateLookup;
import edu.byu.edge.person.basic.domain.CodeState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:test-context.xml",
		"classpath:person-basic-context.xml"
})
public class CodeStateLookupUnitTest {

	private static final Logger LOG = Logger.getLogger(CodeStateLookupUnitTest.class);

	@Autowired
	@Qualifier("codeStateLookup")
	private CodeStateLookup codeStateLookup;

	@Test
	public void testIndividualLookup(){
		List<CodeState> codeStates = codeStateLookup.getStateByCountry("USA");
		assertTrue(codeStates.size() > 50);
		
		codeStates = codeStateLookup.getStateByCountry("CAN");
		assertTrue(codeStates.size() > 10);
		
		codeStates = codeStateLookup.getStateByCountry("SWI");
		assertTrue(codeStates.size() == 0);
	}

}
