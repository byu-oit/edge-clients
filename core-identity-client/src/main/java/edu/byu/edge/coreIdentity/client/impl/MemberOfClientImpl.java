package edu.byu.edge.coreIdentity.client.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by eric on 2/3/16.
 */
public class MemberOfClientImpl implements MemberOfClient {
	private static final Logger LOG = Logger.getLogger(MemberOfClientImpl.class);

	private ApiKeyClient apiKeyClient;

	public MemberOfClientImpl(ApiKeyClient apiKeyClient) {
		this.apiKeyClient = apiKeyClient;
	}

	@Override
	public boolean isPersonMemberOfGroup(String personId, String group) {
		try {
			final URL url = new URL("https://ws.byu.edu/rest/v1/identity/person/membersOf/PARKING_LIMIT_OVERRIDE/" + personId);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", apiKeyClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");

			String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			LOG.debug(result);
			return result.contains("\"rows\": 1");

		} catch (MalformedURLException e) {
			LOG.error("Error in identity client", e);
		} catch (IOException e) {
			LOG.error("Error in identity client", e);
		}
		return false;
	}
}
