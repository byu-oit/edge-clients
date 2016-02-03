package edu.byu.edge.coreIdentity.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.byu.auth.client.ApiKeyClient;
import edu.byu.edge.coreIdentity.client.CoreIdentityClient;
import edu.byu.edge.coreIdentity.domain.CoreIdentity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Scott Hutchings on 2/3/2016.
 */
public class CoreIdentityClientImpl implements CoreIdentityClient {

	private static final HttpHost apiHost = new HttpHost("api.byu.edu", 443, "https");
	private static final HttpHost wsHost = new HttpHost("ws.byu.edu", 443, "https");
	private static final String PERSON_URL = "https://ws.byu.edu/authentication/services/rest/v1/identity/person";
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private ApiKeyClient apiKeyClient;

	public CoreIdentityClientImpl(ApiKeyClient apiKeyClient){
		this.apiKeyClient = apiKeyClient;
	}

	@Override
	public CoreIdentity getCoreIdentityByPersonId(String personId) {
		final String jsonResult = _doHttpGet(PERSON_URL + "/personId/" + personId);
		try {
			return MAPPER.readValue(jsonResult, CoreIdentity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CoreIdentity getCoreIdentityByByuId(String byuId) {
		final String jsonResult = _doHttpGet(PERSON_URL + "/byuId/" + byuId);
		try {
			return MAPPER.readValue(jsonResult, CoreIdentity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CoreIdentity getCoreIdentityByNetId(String netId) {
		final String jsonResult = _doHttpGet(PERSON_URL + "/netId/" + netId);
		try {
			return MAPPER.readValue(jsonResult, CoreIdentity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String _doHttpGet (String endpoint){
		CloseableHttpResponse response = null;
		final CloseableHttpClient client = _configureClient();
		final HttpGet get = new HttpGet(endpoint);
		String responseBody = null;
		get.addHeader("Accept", "application/json");
		get.addHeader("Authorization", apiKeyClient.obtainAuthorizationHeaderString());
		try{
			response = client.execute(wsHost, get);
			responseBody = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseBody;
	}

	private CloseableHttpClient _configureClient() {
		final ConnectionConfig connectionConfig = ConnectionConfig.copy(ConnectionConfig.DEFAULT).setBufferSize(1024).build();
		final SocketConfig socketConfig = SocketConfig.copy(SocketConfig.DEFAULT)
				.setSoKeepAlive(true).setSoLinger(4)
				.setSoReuseAddress(true).setSoTimeout(5000)
				.setTcpNoDelay(true).build();
		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(16, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(32);
		connectionManager.setMaxPerRoute(new HttpRoute(apiHost), 16);
		connectionManager.setMaxPerRoute(new HttpRoute(wsHost), 16);
		connectionManager.setDefaultSocketConfig(socketConfig);
		final RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
				.setConnectionRequestTimeout(5000)
				.setConnectTimeout(5000)
				.setSocketTimeout(5000)
				.build();
		return HttpClients.custom().useSystemProperties().disableAuthCaching().disableCookieManagement()
				.setConnectionManager(connectionManager)
				.setConnectionReuseStrategy(new DefaultConnectionReuseStrategy())
				.setDefaultConnectionConfig(connectionConfig)
				.setDefaultSocketConfig(socketConfig)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setMaxConnTotal(8).setDefaultRequestConfig(requestConfig)
				.build();
	}
}
