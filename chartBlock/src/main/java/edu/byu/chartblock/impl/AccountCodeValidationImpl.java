package edu.byu.chartblock.impl;

import com.sun.jersey.api.client.filter.ClientFilter;
import edu.byu.chartblock.AccountCodeValidation;
import edu.byu.chartblock.ValidateChartBlockResult;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 2:11 PM
 */
public class AccountCodeValidationImpl extends BaseClient implements AccountCodeValidation {

	private static final Logger LOG = Logger.getLogger(AccountCodeValidationImpl.class);

	final private String BODY = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:y='http://ws.byu.edu/namespaces/ps/Y_CF_VALIDATION_V1_Y2_RQ'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<y:Y_CF_VALIDATION_V1_Y2>" +
			"<y:MODE>D</y:MODE>" +
			"<y:CHARTBLOCK>" +
			"<y:SEQ_NBR>1</y:SEQ_NBR>" +
			"<y:EFFDT></y:EFFDT>" +
			"<y:OPERATING_UNIT>%s</y:OPERATING_UNIT>" +
			"<y:ACCOUNT>%s</y:ACCOUNT>" +
			"<y:CLASS_FIELD>%s</y:CLASS_FIELD>" +
			"<y:SOURCE>PC</y:SOURCE>" +
			"</y:CHARTBLOCK>" +
			"</y:Y_CF_VALIDATION_V1_Y2>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";

	/**
	 * @param baseUrl     the base url of the service
	 * @param filter      the nonce encoding filter
	 * @param readTimeout the default read timeout for the service
	 */
	protected AccountCodeValidationImpl(final String baseUrl, final ClientFilter filter, final int readTimeout) {
		super(baseUrl, filter, readTimeout);
		super.setSoapAction("ValidateChartblock.v1");
	}

	@Override
	public ValidateChartBlockResult getAccount(String accountCode) {
		final String[] account = accountCode.split("-");
		if (account.length != 3) {
			throw new IllegalStateException("Invalid account code");
		}
		if (account[0].length() != 8 && account[1].length() != 4 && account[2].length() != 5) {
			throw new IllegalStateException("Invalid account code format");
		}
		final String formattedBody = String.format(BODY, account[0], account[1], account[2]);
		try {
			return callGetAccountCode(formattedBody);
		} catch (final Exception e) {
			if (super.processExceptionForRetry(e)) {
				LOG.warn("Error...xss...retrying " + e.getMessage());
				try {
					return callGetAccountCode(formattedBody);
				} catch (final Exception e2) {
					LOG.error("Error on retry...", e2);
					return new ValidateChartBlockResult(false, null);
				}
			} else {
				LOG.error("Error...", e);
				return new ValidateChartBlockResult(false, null);
			}
		}
	}

	private ValidateChartBlockResult callGetAccountCode(String formattedBody) {
		final String post = getResource().path("").accept("application/xml").post(String.class, formattedBody);
		final int a = post.indexOf("<DETAIL>") + 8;
		final int b = post.indexOf("</DETAIL>");
		final String detail = post.substring(a, b);
		final String[] parts = detail.split("\\/");
		final ValidateChartBlockResult vcbr = new ValidateChartBlockResult();
		final Map<String, String> map = new HashMap<String, String>();
		int count = 0;
		vcbr.setSuccessful(true);
		for (String p : parts) {
			count++;
			final int c = p.indexOf("<");
			if (c < 0) continue;
			p = p.replaceFirst("<", "");
			String name = p.substring(0, p.indexOf(">"));
			if (count > 1) {
				name = p.substring(p.indexOf(">") + 1, p.indexOf("<"));
				name = name.substring(0, name.indexOf(">"));
			}
			String value = p.substring(p.indexOf(">") + 1, p.indexOf("<"));
			if (value.contains(">")) {
				value = value.substring(value.indexOf(">") + 1);
			}
			map.put(name, value);
		}
		vcbr.setFields(map);
		return vcbr;
	}
}
