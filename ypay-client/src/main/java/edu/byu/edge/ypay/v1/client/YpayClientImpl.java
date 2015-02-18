package edu.byu.edge.ypay.v1.client;

import edu.byu.auth.client.CredentialClient;
import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wct5 on 2/18/15.
 */
public class YpayClientImpl implements YpayClient {

	private static final String SEARCH_STRING = "%sinvoices/search?clientSystemId=%s&paidByIds=%s&start=0&results=64&paymentStartDate=%s&paymentEndDate=%s";

	protected final String baseUrl;
	protected final String clientSystemId;
	protected final CredentialClient credentialClient;
	protected JAXBContext invoiceContext;
	protected ThreadLocal<Unmarshaller> unmarshallerThreadLocal;

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
	}

	@Override
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
}
