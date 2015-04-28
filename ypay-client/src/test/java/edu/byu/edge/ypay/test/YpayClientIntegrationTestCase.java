package edu.byu.edge.ypay.test;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import edu.byu.auth.client.CredentialClient;
import edu.byu.auth.client.impl.ApiKeyClientImpl;
import edu.byu.auth.client.impl.SharedSecretFileCredentialResolver;
import edu.byu.edge.ypay.v1.client.YpayClientImpl;
import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;
import edu.byu.edge.ypay.v1.domain.invoice.LineItemType;
import edu.byu.edge.ypay.v1.domain.invoice.ObjectFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by wct5 on 2/18/15.
 */
@RunWith(JUnit4.class)
public class YpayClientIntegrationTestCase {

	private static final Logger LOG = Logger.getLogger(YpayClientIntegrationTestCase.class);
	private final MyYpayClient client = initializeClient();

	private MyYpayClient initializeClient() {
		try {
			ApiKeyClientImpl apiKeyClient = new ApiKeyClientImpl(2500, 2500);
			final SharedSecretFileCredentialResolver resolver = new SharedSecretFileCredentialResolver();
			resolver.setCredentialFile(new File("/opt/prod/edge.cred"));
			resolver.setKeyFile(new File("/opt/prod/edge.key"));
			resolver.afterPropertiesSet();
			apiKeyClient.setResolver(resolver);
			apiKeyClient.afterPropertiesSet();
			MyYpayClient client = new MyYpayClient("https://ypay-stg.byu.edu/payments/service/rest/v1/", "parking-citations", apiKeyClient);
			client.setInvoiceContext(JAXBContext.newInstance(ObjectFactory.class));
			client.initialize();
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void testGettingData() throws Exception {
		final Calendar cal = Calendar.getInstance();
		cal.set(2014, Calendar.OCTOBER, 16);
		final InvoiceListType invoiceListType = client.findInvoicesForPersonOnDay("600281622", cal.getTime());
		assertNotNull(invoiceListType);
	}

	@Test
	public void testGetInvoiceId() {
		final String response =
				"HTTP/1.1 201 Created\n" +
				"Date: Tue, 28 Apr 2015 15:29:30 GMT\n" +
				"Set-Cookie: JSESSIONID=a49abo87vaiouyh.w1; Path=/payments/service/; Secure; HttpOnly\n" +
				"Location: https://ypay-stg.byu.edu/payments/service/rest/v1/parking-citations/invoices/230900\n" +
				"Content-Type: application/xml\n" +
				"Transfer-Encoding: chunked\n\n" +
				"53\n" +
				"https://ypay-stg.byu.edu/payments/service/rest/v1/parking-citation/invoices/230900";
		assertNotNull(response);
		assertTrue(client.getInvoiceId(response).equals("230900"));
	}

	@Test
	public void testCreateInvoice() {
		List<LineItemType> lineItemList = new ArrayList<LineItemType>();
		LineItemType line1 = new LineItemType();
		line1.setLineItemId("TEST_ID2");
		line1.setDescription("TEST");
		line1.setAmount(new BigDecimal(2));
		line1.setDueDate(new XMLGregorianCalendarImpl(new GregorianCalendar(2014, Calendar.DECEMBER, 31)));
		lineItemList.add(line1);
		String invoiceId = client.createInvoice(String.valueOf(System.currentTimeMillis()), "806267732", lineItemList);
		assertNotNull(invoiceId);
		assertTrue(client.deleteInvoice(invoiceId));
	}

	private static class MyYpayClient extends YpayClientImpl {

		public MyYpayClient(String clientSystemId, CredentialClient credentialClient) {
			super(clientSystemId, credentialClient);
		}

		public MyYpayClient(String baseUrl, String clientSystemId, CredentialClient credentialClient) {
			super(baseUrl, clientSystemId, credentialClient);
		}

		@Override
		public void setInvoiceContext(JAXBContext invoiceContext) {
			super.setInvoiceContext(invoiceContext);
		}

		@Override
		public void initialize() throws Exception {
			super.initialize();
		}

		@Override
		public InvoiceListType findInvoicesForPersonOnDay(String personId, Date day) {
			return super.findInvoicesForPersonOnDay(personId, day);
		}

		@Override
		public String createInvoice(String clientTransactionId, String owner, List<LineItemType> lineItemList) {
			return super.createInvoice(clientTransactionId, owner, lineItemList);
		}

		@Override
		public String createInvoice(String clientTransactionId, String returnUrl, String notificationUrl, String owner, List<LineItemType> lineItemList) {
			return super.createInvoice(clientTransactionId, returnUrl, notificationUrl, owner, lineItemList);
		}

		@Override
		public boolean deleteInvoice(String invoiceId) {
			return super.deleteInvoice(invoiceId);
		}

		@Override
		protected String getInvoiceId(String response) {
			return super.getInvoiceId(response);
		}

		@Override
		protected File getTempFile(String personId) {
			return super.getTempFile(personId);
		}
	}
}
