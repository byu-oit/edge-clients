package edu.byu.edge.ypay.v1.client;

import edu.byu.auth.client.CredentialClient;
import edu.byu.edge.ypay.v1.domain.invoice.*;

import javax.xml.bind.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wct5 on 2/18/15.
 */
public class YpayClientImpl implements YpayClient {

	private static final String SEARCH_STRING = "%sinvoices/search?clientSystemId=%s&paidByIds=%s&start=0&results=64&paymentStartDate=%s&paymentEndDate=%s";
	private static final String CREATE_INVOICE_STRING = "%s%s/invoices";
	private static final String DELETE_INVOICE_STRING = CREATE_INVOICE_STRING + "/%s";

	protected final String baseUrl;
	protected final String clientSystemId;
	protected final CredentialClient credentialClient;
	protected JAXBContext invoiceContext;
	protected ThreadLocal<Unmarshaller> unmarshallerThreadLocal;
	protected ThreadLocal<Marshaller> marshallerThreadLocal;

	public YpayClientImpl(final String clientSystemId, final CredentialClient credentialClient) {
		this("https://ypay.byu.edu/payments/service/rest/v1/", clientSystemId, credentialClient);
	}

	public YpayClientImpl(final String baseUrl, final String clientSystemId, final CredentialClient credentialClient) {
		this.baseUrl = baseUrl;
		this.clientSystemId = clientSystemId;
		this.credentialClient = credentialClient;
	}

	public void setInvoiceContext(final JAXBContext invoiceContext) {
		this.invoiceContext = invoiceContext;
	}

	public void initialize() throws Exception {
		unmarshallerThreadLocal = new InvoiceUnmarshallerThreadLocal(invoiceContext);
		marshallerThreadLocal = new InvoiceMarshallerThreadLocal(invoiceContext);
	}

	public InvoiceListType findInvoicesForPersonOnDay(final String personId, final Date day) {
		PrintStream urlout = null;
		InputStream urlin = null;
		FileOutputStream fileout = null;
		File tempFile = null;
		try {
			final String[] days = getDayBeforeAndDayAfter(day);
			final URL url = new URL(String.format(SEARCH_STRING, baseUrl, clientSystemId, personId, days[0], days[1]));
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			tempFile = getTempFile(personId);
			urlin = connection.getInputStream();
			fileout = new FileOutputStream(tempFile);
			int i;
			byte[] buf = new byte[4096];
			while ((i = urlin.read(buf)) > 0) {
				fileout.write(buf, 0, i);
			}
			return ((JAXBElement<InvoiceListType>) unmarshallerThreadLocal.get().unmarshal(tempFile)).getValue();
		} catch (Throwable e) {
			throw new RuntimeException("Unable to load invoices.", e);
		} finally {
			if (urlout != null) {
				urlout.close();
			}
			if (urlin != null) {
				try {
					urlin.close();
				} catch (IOException ignore) {
				}
			}
			if (fileout != null) {
				try {
					fileout.close();
				} catch (IOException ignore) {
				}
			}
			if (tempFile != null) {
				tempFile.delete();
			}
		}
	}

	@Override
	public String createInvoice(String clientTransactionId, String owner, List<LineItemType> lineItemList) {
		return createInvoice(clientTransactionId, "", "", owner, lineItemList);
	}

	@Override
	public String createInvoice(String clientTransactionId, String returnUrl, String notificationUrl, String owner, List<LineItemType> lineItemList) {
		//Check for valid values
		if(clientTransactionId == null || clientTransactionId.isEmpty()) {
			throw new IllegalArgumentException("Invalid client transaction ID");
		}
		if(owner == null || owner.isEmpty()) {
			throw new IllegalArgumentException("Invalid owner");
		}
		if(lineItemList == null || lineItemList.isEmpty()) {
			throw new IllegalArgumentException("Invalid line items");
		}

		//Populate the invoice request body
		InvoiceRequestType invoiceRequestType = new InvoiceRequestType();
		invoiceRequestType.setClientSystemTransactionId(clientTransactionId);
		invoiceRequestType.setReturnUrl(returnUrl);
		invoiceRequestType.setNotificationUrl(notificationUrl);
		invoiceRequestType.setOwner(owner);
		invoiceRequestType.setPaymentSourceAllowed(PaymentSourceAllowedType.ALL);

		LineItemListType lineItems = new LineItemListType();
		lineItems.getLineItem().addAll(lineItemList);
		invoiceRequestType.setLineItems(lineItems);

		try {
			final URL url = new URL(String.format(CREATE_INVOICE_STRING, baseUrl, clientSystemId));
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setDoOutput(true);

			marshallerThreadLocal.get().marshal(invoiceRequestType, connection.getOutputStream());

			final Map<String, List<String>> headerFields = connection.getHeaderFields();
			if(!headerFields.containsKey("Location")) {
				throw new IOException("Response missing 'Location' header");
			}
			List<String> locations = headerFields.get("Location");
			if(locations.size() != 1) {
				throw new IOException("Response contains multiple 'Location' headers");
			}

			return getInvoiceId(locations.get(0));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteInvoice(String invoiceId) {
		if(invoiceId == null || invoiceId.isEmpty()) {
			throw new IllegalArgumentException("Invalid client trasaction ID");
		}

		try {
			final URL url = new URL(String.format(DELETE_INVOICE_STRING, baseUrl, clientSystemId, invoiceId));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.getInputStream();
			System.out.println(connection.getResponseCode());
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected String getInvoiceId(String response) {
		final String URL = String.format(CREATE_INVOICE_STRING, baseUrl, clientSystemId);
		final int urlStartIndex = response.indexOf(URL) + URL.length() + 1;
		final int urlEndIndex = response.indexOf("\n", urlStartIndex);
		if(urlEndIndex == -1) {
			return response.substring(urlStartIndex).trim();
		} else {
			return response.substring(urlStartIndex, urlEndIndex).trim();
		}
	}

	protected File getTempFile(final String personId) {
		final File tempFile;
		try {
			tempFile = File.createTempFile(clientSystemId + "." + personId + ".", ".xml");
			tempFile.deleteOnExit();
			return tempFile;
		} catch (IOException e) {
			throw new IllegalStateException("Unable to create temp file.", e);
		}
	}

	protected static String[] getDayBeforeAndDayAfter(final Date day) {
		final String[] result = new String[2];
		final Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DATE, -1);
		result[0] = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.DATE, 2);
		result[1] = String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		return result;
	}

	private static class InvoiceUnmarshallerThreadLocal extends ThreadLocal<Unmarshaller> {
		private final JAXBContext context;

		private InvoiceUnmarshallerThreadLocal(final JAXBContext context) {
			this.context = context;
		}

		@Override
		protected Unmarshaller initialValue() {
			try {
				return context.createUnmarshaller();
			} catch (JAXBException e) {
				throw new IllegalStateException("Unable to create unmarshaller.", e);
			}
		}
	}

	private static class InvoiceMarshallerThreadLocal extends ThreadLocal<Marshaller> {
		private final JAXBContext context;

		private InvoiceMarshallerThreadLocal(JAXBContext context) {
			this.context = context;
		}

		@Override
		protected Marshaller initialValue() {
			try {
				return context.createMarshaller();
			} catch (JAXBException e) {
				throw new IllegalStateException("Unable to create marshaller.", e);
			}
		}
	}
}
