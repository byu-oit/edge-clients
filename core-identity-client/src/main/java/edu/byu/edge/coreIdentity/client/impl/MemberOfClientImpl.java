package edu.byu.edge.coreIdentity.client.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.edge.coreIdentity.client.IdentityServiceException;
import edu.byu.edge.coreIdentity.client.MemberOfClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eric on 2/3/16.
 */
public class MemberOfClientImpl implements MemberOfClient, InitializingBean {
	private static final Logger LOG = Logger.getLogger(MemberOfClientImpl.class);

	private String baseUrl;
	private ApiKeyClient apiKeyClient;

	public MemberOfClientImpl(String baseUrl, ApiKeyClient apiKeyClient) {
		this.baseUrl = _cleanUrl(baseUrl);
		this.apiKeyClient = apiKeyClient;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(baseUrl);
		Assert.notNull(apiKeyClient);
	}

	@Override
	public boolean isPersonMemberOfGroup(String personId, String group) throws IdentityServiceException {
		try {
			final URL url = new URL("https://ws.byu.edu/rest/v1/identity/person/membersOf/" + safeSpaceGroupId(group) + "/" + personId);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml,text/xml");
			connection.setRequestProperty("Authorization", apiKeyClient.obtainAuthorizationHeaderString());
			connection.setRequestProperty("Content-Type", "application/xml");

			String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			return result.contains("\"rows\": 1");

		} catch (MalformedURLException e) {
			LOG.error("Error in identity client", e);
			throw new IdentityServiceException("Error determining user groups", e);
		} catch (IOException e) {
			LOG.error("Error in identity client", e);
			throw new IdentityServiceException("Error determining user groups", e);
		}
	}

	private static String safeSpaceGroupId(final String groupId) {
		return groupId == null ? "" : groupId.replaceAll(" ", "%20");
	}

	private static String _cleanUrl(final String base) {
		if (base.endsWith("/")) return base;
		else return base + "/";
	}
}
