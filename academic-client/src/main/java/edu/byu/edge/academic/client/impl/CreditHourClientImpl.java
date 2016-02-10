package edu.byu.edge.academic.client.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.edge.academic.client.CreditHourClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eric on 2/4/16.
 */
public class CreditHourClientImpl implements CreditHourClient, InitializingBean {
	private static final Logger LOG = Logger.getLogger(CreditHourClientImpl.class);

	private String baseUrl;
	private ApiKeyClient apiKeyClient;

	public CreditHourClientImpl(final String url, ApiKeyClient apiKeyClient) {
		this.baseUrl = _cleanUrl(url);
		this.apiKeyClient = apiKeyClient;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(baseUrl);
		Assert.notNull(apiKeyClient);
	}

	@Override
	public double getCreditHoursByPersonIdAndYearTerm(String personId, String yearTerm) {
		try {
			final URL url = new URL(baseUrl + personId);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", apiKeyClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");

			String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			LOG.debug(result);
//			return result.contains("\"rows\": 1");

		} catch (MalformedURLException e) {
			LOG.error("Error in identity client", e);
		} catch (IOException e) {
			LOG.error("Error in identity client", e);
		}
//		return false;
		return 1;
	}

	private static String _cleanUrl(final String base) {
		if (base.endsWith("/")) return base;
		else return base + '/';
	}

}
