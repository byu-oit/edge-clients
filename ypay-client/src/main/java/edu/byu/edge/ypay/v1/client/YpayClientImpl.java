package edu.byu.edge.ypay.v1.client;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.auth.client.CredentialClient;
import edu.byu.edge.ypay.v1.domain.invoice.*;
import edu.byu.spring.ByuAppStage;
import org.apache.log4j.Logger;

import javax.xml.bind.*;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wct5 on 2/18/15.
 */
public class YpayClientImpl implements YpayClient {
	private static final Logger LOG = Logger.getLogger(YpayClientImpl.class);

	private static final String SEARCH_STRING = "%sinvoices/search?clientSystemId=%s&paidByIds=%s&start=0&results=64&paymentStartDate=%s&paymentEndDate=%s";
	private static final String FIND_BY_CLIENT_SYSTEM_AND_OWNER_ID_URL_MASK = "%sinvoices/search?clientSystemId=%s&ownerIds=%s&results=10000";
	private static final String FIND_ON_DAY_URL_MASK = "%sinvoices/search?clientSystemId=%s&paidByIds=%s&start=0&results=64&paymentStartDate=%s&paymentEndDate=%s";
	private static final String FIND_BY_CLIENT_TX_ID_URL_MASK = "%sinvoices/search?clientSystemId=%s&start=0&results=64&clientSystemTransactionId=%s";
	private static final String CREATE_INVOICE_STRING = "%s%s/invoices";
	private static final String FIND_INVOICE_STRING = CREATE_INVOICE_STRING + "/%s";
	private static final String YPAY_PROD_URL = "https://ypay.byu.edu/payments/service/rest/v1/";
	private static final String YPAY_STAGE_URL = "https://ypay-stg.byu.edu/payments/service/rest/v1/";

	protected String baseUrl;
	protected final String clientSystemId;
	protected final CredentialClient credentialClient;
	protected JAXBContext invoiceContext;
	protected ThreadLocal<Unmarshaller> unmarshallerThreadLocal;
	protected ThreadLocal<Marshaller> marshallerThreadLocal;

	public YpayClientImpl(final String clientSystemId, final CredentialClient credentialClient) {
		this(YPAY_PROD_URL, clientSystemId, credentialClient);
	}

	public YpayClientImpl(final ByuAppStage appStage,  final String clientSystemId, final CredentialClient credentialClient) {
		this(appStage != null && appStage.isInProd() ? YPAY_PROD_URL : YPAY_STAGE_URL, clientSystemId, credentialClient);
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

	public void setToStageUrl() {
		this.baseUrl = YPAY_STAGE_URL;
	}

	public InvoiceListType findInvoicesForPersonOnDay(final String personId, final Date day) {
		PrintStream urlout = null;
		InputStream urlin = null;
		FileOutputStream fileout = null;
		File tempFile = null;
		try {
			final String[] days = getDayBeforeAndDayAfter(day);
			final URL url = new URL(String.format(FIND_ON_DAY_URL_MASK, baseUrl, clientSystemId, personId, days[0], days[1]));
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
	public long createInvoice(String clientTransactionId, String returnUrl, String notificationUrl, String owner, List<LineItemType> lineItemList) {
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
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setDoOutput(true);

			final OutputStream outputStream = connection.getOutputStream();
			marshallerThreadLocal.get().marshal(invoiceRequestType, outputStream);

			final Map<String, List<String>> headerFields = connection.getHeaderFields();
			if(!headerFields.containsKey("Location")) {
				throw new IOException("Response missing 'Location' header. Code: " + connection.getResponseCode());
			}
			List<String> locations = headerFields.get("Location");
			if(locations.size() != 1) {
				throw new IOException("Response contains multiple 'Location' headers");
			}

			return getInvoiceId(locations.get(0));

		} catch (MalformedURLException e) {
			LOG.error("Error creating YPayInvoice", e);
		} catch (IOException e) {
			LOG.error("Error creating YPayInvoice", e);
		} catch (JAXBException e) {
			LOG.error("Error creating YPayInvoice", e);
		}
		return -1L;
	}

	@Override
	public InvoiceType findInvoice(long invoiceId) {
		try {
			final URL url = new URL(String.format(FIND_INVOICE_STRING, baseUrl, clientSystemId, invoiceId));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");

			return ((JAXBElement<InvoiceType>) unmarshallerThreadLocal.get().unmarshal(connection.getInputStream())).getValue();
		} catch (MalformedURLException e) {
			LOG.error("Error in ypay client", e);
		} catch (IOException e) {
			LOG.error("Error in ypay client", e);
		} catch (JAXBException e) {
			LOG.error("Error in ypay client", e);
		}
		return null;
	}

	@Override
	public InvoiceType findInvoiceByClientTransactionId(final String clientTxId) {
		try {
			final URL url = new URL(String.format(FIND_BY_CLIENT_TX_ID_URL_MASK, baseUrl, clientSystemId, clientTxId));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");

			final InvoiceListType invoiceListType = ((JAXBElement<InvoiceListType>) unmarshallerThreadLocal.get().unmarshal(connection.getInputStream()))
					.getValue();
			if (invoiceListType == null || invoiceListType.getInvoice() == null || invoiceListType.getInvoice().isEmpty()) return null;
			final List<InvoiceType> invoiceTypeList = invoiceListType.getInvoice();
			if (invoiceTypeList.size() != 1) {
				LOG.warn("Multiple invoices found when searching by client transaction id. " + invoiceTypeList.size());
				Collections.sort(invoiceTypeList, new Comparator<InvoiceType>() {
					@Override
					public int compare(final InvoiceType o1, final InvoiceType o2) {
						return (int) (o2.getInvoiceId() - o1.getInvoiceId());
					}
				});
			} else {
				return invoiceTypeList.get(0);
			}
		} catch (MalformedURLException e) {
			LOG.error("Error in ypay client", e);
		} catch (IOException e) {
			LOG.error("Error in ypay client", e);
		} catch (JAXBException e) {
			LOG.error("Error in ypay client", e);
		}
		return null;
	}

	@Override
	public boolean deleteInvoice(long invoiceId) {
		try {
			final URL url = new URL(String.format(FIND_INVOICE_STRING, baseUrl, clientSystemId, invoiceId));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.getInputStream();
			return true;
		} catch (MalformedURLException e) {
			LOG.error("Error in ypay client", e);
		} catch (IOException e) {
			LOG.error("Error in ypay client", e);
		}
		return false;
	}

	@Override
	public int[] getTotalInvoicesAndMaxInvoiceIdForClientSystemIdAndPersonId(String clientSystemId, String personId) {
		try {
			final URL url = new URL(String.format(FIND_BY_CLIENT_SYSTEM_AND_OWNER_ID_URL_MASK, baseUrl, clientSystemId, personId));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", credentialClient.obtainAuthorizationHeaderString());

			String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			return extractCountAndLastInvoiceIdFromResponse(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new int[2];
	}

	private int[] extractCountAndLastInvoiceIdFromResponse(String response) {
		String regex = "<invoiceId>(\\d+)</invoiceId>";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(response);

		int count = 0;
		int maxInvoiceId = -1;

		while (matcher.find()) {
			count++;
			maxInvoiceId = Math.max(maxInvoiceId, Integer.parseInt(matcher.group(1)));
		}

		return new int[] {count, maxInvoiceId};
	}

	protected long getInvoiceId(String response) {
		final String URL = String.format(CREATE_INVOICE_STRING, baseUrl, clientSystemId);
		final int urlStartIndex = response.indexOf(URL) + URL.length() + 1;
		final int urlEndIndex = response.indexOf("\n", urlStartIndex);
		if (urlEndIndex == -1) {
			return Long.valueOf(response.substring(urlStartIndex).trim());
		} else {
			return Long.valueOf(response.substring(urlStartIndex, urlEndIndex).trim());
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
