package edu.byu.edge.ypay.test;

import edu.byu.auth.client.impl.ApiKeyClientImpl;
import edu.byu.auth.client.impl.SharedSecretFileCredentialResolver;
import edu.byu.edge.ypay.v1.client.YpayClient;
import edu.byu.edge.ypay.v1.client.YpayClientImpl;
import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;
import edu.byu.edge.ypay.v1.domain.invoice.ObjectFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

/**
 * Created by wct5 on 2/18/15.
 */
@RunWith(JUnit4.class)
public class YpayClientIntegrationTestCase {

	private static final Logger LOG = Logger.getLogger(YpayClientIntegrationTestCase.class);

	@Test
	public void testGettingData() throws Exception {
		final Calendar cal = Calendar.getInstance();
		cal.set(2014, Calendar.OCTOBER, 16);
		final ApiKeyClientImpl apiKeyClient = new ApiKeyClientImpl(2500, 2500);
		final SharedSecretFileCredentialResolver resolver = new SharedSecretFileCredentialResolver();
		resolver.setCredentialFile(new File("/opt/prod/edge.cred"));
		resolver.setKeyFile(new File("/opt/prod/edge.key"));
		resolver.afterPropertiesSet();
		apiKeyClient.setResolver(resolver);
		apiKeyClient.afterPropertiesSet();
		final YpayClientImpl client = new YpayClientImpl("parking-citations", apiKeyClient);
		client.setInvoiceContext(JAXBContext.newInstance(ObjectFactory.class));
		client.initialize();
		final InvoiceListType invoiceListType = client.findInvoicesForPersonOnDay("600281622", cal.getTime());
		assertNotNull(invoiceListType);
	}
}
