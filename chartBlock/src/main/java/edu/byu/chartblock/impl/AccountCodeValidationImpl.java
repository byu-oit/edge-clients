package edu.byu.chartblock.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.auth.client.AccessTokenClient;
import edu.byu.chartblock.AccountCodeValidation;
import edu.byu.chartblock.ValidateChartBlockResult;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 2:11 PM
 */
public class AccountCodeValidationImpl implements AccountCodeValidation {

	private static final Logger LOG = Logger.getLogger(AccountCodeValidationImpl.class);
	public static final String URL_STRING = "%s/?operating_unit=%s&account_field=%s&class_field=%s&journal_source=%s&effective_date=%s";

	private final String baseUrl;
	private final AccessTokenClient accessTokenClient;

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
	 * @param accessTokenClient the access token client for wso 2
	 */
	protected AccountCodeValidationImpl(final String baseUrl, final AccessTokenClient accessTokenClient) {
		this.baseUrl = baseUrl;
		this.accessTokenClient = accessTokenClient;
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

		try {
			final URL url = new URL(String.format(URL_STRING, baseUrl, account[0], account[1], account[2], "", ""));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", accessTokenClient.obtainAuthorizationHeaderString());

			final String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			final ValidateChartBlockResult chartBlockResult = new ValidateChartBlockResult();
			ObjectMapper mapper = new ObjectMapper();
			final JsonNode rootNode = mapper.readTree(result).findPath("chartblock");
			chartBlockResult.setSuccessful("Success".equals(rootNode.findPath("status").asText()));
			final JsonNode operatingUnit = rootNode.findPath("operating_unit");
//			Map<String, String> map = iterateThroughNode(operatingUnit, "operating_unit");
			final Map<String, String> map = new HashMap<String, String>();
			final JsonNode department = operatingUnit.findPath("department");
			map.put("DEPARTMENT_ID", department.findPath("value").asText());
			map.put("DEPARTMENT_NAME", department.findPath("description").asText());
			final JsonNode contact = department.findPath("contact");
			map.put("CONTACT_NAME", contact.findPath("name").asText());
			map.put("CONTACT_NETID", contact.findPath("net_id").asText());
			map.put("DEPARTMENT_CONTACT_EMAIL", contact.findPath("email_address").asText());
			final JsonNode manager = department.findPath("manager");
			map.put("MANAGER_NAME", manager.findPath("name").asText());
			map.put("MANAGER_NETID", manager.findPath("net_id").asText());
			map.put("MANAGER_EMAIL", manager.findPath("email_address").asText());
			chartBlockResult.setFields(map);
			return chartBlockResult;
		} catch (MalformedURLException e) {
			LOG.error("Error...", e);
			return new ValidateChartBlockResult(false, null);
		} catch (IOException e) {
			LOG.error("Error...", e);
			return new ValidateChartBlockResult(false, null);
		}
	}

	private Map<String, String> iterateThroughNode(final JsonNode node, final String nodeName){
		final Map<String, String> map = new HashMap<String, String>();
		for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
			Map.Entry<String, JsonNode> entry = it.next();
			final JsonNode jsonNode = entry.getValue();
			final String key = nodeName.toUpperCase() + "_" + entry.getKey().toUpperCase();
			if (jsonNode.isTextual()){
				map.put(key, jsonNode.asText());
			} else {
				map.putAll(iterateThroughNode(jsonNode, key));
			}
		}
		return map;
	}
}
