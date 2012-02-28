package com.expedia.tools.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.expedia.tools.exception.CompareToolsException;

public class HttpProxyRequest {

	private static HttpClient httpclient = HttpConnectionManager
			.createHttpClient();

	public String postRequestExpedia(String url, Map<String, String> parameters)
			throws CompareToolsException {
		HttpPost post = new HttpPost(url);
		String responseContent = "";
		try {
			List<NameValuePair> nameValuePair = setNameValuePair(parameters);
			post.setEntity(new UrlEncodedFormEntity(nameValuePair));
			responseContent = httpclient
					.execute(post, new ExpResponseHandler());

		} catch (IOException e) {
			HttpConnectionManager.closeHttpClient(httpclient);
			throw new CompareToolsException("execute post request error"
					+ e.getMessage(), e);
		}
		return responseContent;
	}

	public String getRequestExpedia(String url, String contextPath,
			Map<String, String> parameters) throws CompareToolsException {
		return this.getRequestExpedia(url, contextPath, parameters);
	}

	@SuppressWarnings("unchecked")
	private String getRequestExpedia(String url, String contextPath, Object p)
			throws CompareToolsException {
		URI uri;
		String responseContent = "";
		try {
			if (p instanceof Map) {
				Map<String, String> parameters = (Map<String, String>) p;
				List<NameValuePair> nameValuePair = setNameValuePair(parameters);
				uri = URIUtils
						.createURI("http", url, -1, contextPath,
								URLEncodedUtils.format(nameValuePair,
										HTTP.UTF_8), null);

			} else {
				String parameters = (String) p;
				uri = URIUtils.createURI("http", url, -1, contextPath,
						parameters, null);
			}
			HttpGet httpget = new HttpGet(uri);
			responseContent = httpclient.execute(httpget,
					new ExpResponseHandler());
		} catch (Exception e) {
			HttpConnectionManager.closeHttpClient(httpclient);
			throw new CompareToolsException("execute get request error"
					+ e.getMessage(), e);
		}
		return responseContent;

	}

	public String getRequestExpedia1(String url, String contextPath,
			String parameters) throws CompareToolsException {
		return this.getRequestExpedia(url, contextPath, parameters);
	}

	private List<NameValuePair> setNameValuePair(Map<String, String> parameters) {
		List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(2);
		for (Iterator<Map.Entry<String, String>> its = parameters.entrySet()
				.iterator(); its.hasNext();) {
			Map.Entry<String, String> entry = its.next();
			namevaluepair.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));

		}
		return namevaluepair;
	}

	class ExpResponseHandler implements ResponseHandler<String> {

		public ExpResponseHandler() {

		}

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			String charset = "UTF-8";
			HttpEntity entity = response.getEntity();

			if (entity != null) {

				return EntityUtils.toString(entity, charset);

			} else {

				return null;

			}
		}

	}

}
