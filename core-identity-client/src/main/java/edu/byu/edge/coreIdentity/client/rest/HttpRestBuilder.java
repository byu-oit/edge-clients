package edu.byu.edge.coreIdentity.client.rest;

import edu.byu.edge.coreIdentity.client.exceptions.RestHttpException;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created by Scott Hutchings on 8/30/2016.
 */
public class HttpRestBuilder {

	private String url;
	private String method;
	private String accept;
	private String authorization;
	private String contentType;
	private String body;
	private Map<String, String> queryParams;

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

	public HttpRestBuilder body(String body){
		this.body = body;
		return this;
	}

	public HttpRestBuilder queryParams(Map<String, String> queryParams){
		this.queryParams = queryParams;
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

	public String post() throws RestHttpException {
		this.method = "POST";
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
			if (body != null || queryParams != null){
				connection.setDoOutput(true);
				final OutputStream outputStream = connection.getOutputStream();
				final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
				if (body != null){
					bufferedWriter.write(body);
				} else if (queryParams != null) {
					bufferedWriter.write(getQueryString(queryParams));
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			}
			final int responseCode = connection.getResponseCode();
			if (responseCode != 200){
				throw new RestHttpException("Error code: " + connection.getResponseCode() +
						" " + connection.getResponseMessage() + " for URL: " + url);
			}
			BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
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

	private String getQueryString(Map<String, String> queryParams) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (String key : queryParams.keySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(key, "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(queryParams.get(key), "UTF-8"));
		}
		return result.toString();
	}
}
