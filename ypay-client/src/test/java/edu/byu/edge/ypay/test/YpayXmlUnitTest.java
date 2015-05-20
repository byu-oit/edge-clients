package edu.byu.edge.ypay.test;

import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import static org.junit.Assert.assertNotNull;

/**
 * Created by wct5 on 2/18/15.
 */
@RunWith(JUnit4.class)
public class YpayXmlUnitTest {

	private static final Logger LOG = Logger.getLogger(YpayXmlUnitTest.class);

	@Test
	public void testParse1() throws Exception {
		final JAXBContext jaxbContext = JAXBContext.newInstance(
//				edu.byu.edge.ypay.v1.domain.common.ObjectFactory.class,
				edu.byu.edge.ypay.v1.domain.invoice.ObjectFactory.class
//				edu.byu.edge.ypay.v1.domain.payments.ObjectFactory.class,
//				edu.byu.edge.ypay.v1.domain.profile.ObjectFactory.class,
//				edu.byu.edge.ypay.v1.domain.system.ObjectFactory.class

		);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final InvoiceListType obj = ((JAXBElement<InvoiceListType>) unmarshaller.unmarshal(getClass().getClassLoader().getResource("sample1.xml"))).getValue();
		assertNotNull(obj);
	}

}
