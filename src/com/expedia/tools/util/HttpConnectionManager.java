package com.expedia.tools.util;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * get thread safe httpclient
 * 
 * @author 032341
 * 
 */
public class HttpConnectionManager {

	private static HttpParams httpParams;

	private static HttpClient httpClient;

	private static ThreadSafeClientConnManager connectionManager;

	public final static String CONFIG_MAX_TOTAL_CONNECTIONS = "max_total_connections";

	public final static String CONFIG_WAIT_TIMEOUT = "wait_timeout";

	public final static String CONFIG_MAX_ROUTE_CONNECTIONS = "max_route_connections";

	public final static String CONFIG_CONNECT_TIMEOUT = "connect_timeout";

	public final static String CONFIG_READ_TIMEOUT = "read_timeout";

	public final static String CONFIG_FILE_NAME = "http_connection_base_config.properties";

	private static void initSetting(){
		httpParams = new BasicHttpParams();
		HttpProtocolParams
				.setUserAgent(
						httpParams,
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);

		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				getConfigDate(CONFIG_WAIT_TIMEOUT));

		HttpConnectionParams.setConnectionTimeout(httpParams,
				getConfigDate(CONFIG_CONNECT_TIMEOUT));
		HttpConnectionParams.setSoTimeout(httpParams,
				getConfigDate(CONFIG_READ_TIMEOUT));
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		connectionManager = new ThreadSafeClientConnManager(schemeRegistry);

		connectionManager
				.setMaxTotal(getConfigDate(CONFIG_MAX_TOTAL_CONNECTIONS));

		connectionManager
				.setDefaultMaxPerRoute(getConfigDate(CONFIG_MAX_ROUTE_CONNECTIONS));

	}

	private static int getConfigDate(String key){
		ExpConfigManager ec = new ExpConfigManager();
		return Integer.valueOf((String) ec.getValue(CONFIG_FILE_NAME, key));
	}

	public static HttpClient createHttpClient() {
		initSetting();
		httpClient = new DefaultHttpClient(connectionManager, httpParams);
		return httpClient;
	}

	public static void closeHttpClient(HttpClient httpclient) {
		if (httpclient != null && httpClient.getConnectionManager() != null)
			httpclient.getConnectionManager().shutdown();
	}

}