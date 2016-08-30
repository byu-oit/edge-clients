package edu.byu.edge.coreIdentity.client.rest;

import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public class HttpRestBuilder {

	private String url;
	private String method;
	private String accept;
	private String authorization;
	private String contentType;

	public HttpRestBuilder(String url) {
		this.url = url;
	}

	public HttpRestBuilder accept(String accept){
		this.accept = accept;
		return this;
	}

	public HttpRestBuilder authorization(String authorization){
		this.authorization = authorization;
		return this;
	}

	public HttpRestBuilder contentType(String contentType){
		this.contentType = contentType;
		return this;
	}

	public HttpRestBuilder method(String method) {
		this.method = method;
		return this;
	}

	public String get() throws RestHttpException {
		this.method = "GET";
		return execute();
	}

	public String execute() throws RestHttpException {
		URL urlObj = null;
		try {
			urlObj = new URL(url);
			final HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			connection.setRequestMethod(method);
			if (accept != null){
				connection.setRequestProperty("Accept", accept);
			}
			if (authorization != null){
				connection.setRequestProperty("Authorization", authorization);
			}
			if (contentType != null){
				connection.setRequestProperty("Content-Type", contentType);
			}
			final int responseCode = connection.getResponseCode();
			if (responseCode != 200){
				throw new RestHttpException("Error code: " + connection.getResponseCode() +
						" " + connection.getResponseMessage() + " for URL: " + url);
			}
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return response.toString();
		} catch (MalformedURLException e) {
			throw new RestHttpException("Malformed URL", e);
		} catch (ProtocolException e) {
			throw new RestHttpException("Invalid Protocol", e);
		} catch (IOException e) {
			throw new RestHttpException("IOException", e);
		}
	}
}
