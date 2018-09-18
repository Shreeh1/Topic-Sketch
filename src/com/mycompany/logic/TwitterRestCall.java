package com.mycompany.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class TwitterRestCall {
	static String consumerKeyStr = "aV8lanFby7bTEMl2JXfJPiuB7";
	static String consumerSecretStr = "3Di9ULBEzWt1PJUtCgvUnU7vXvvVE74cdxrNA7pfVeF1sTSSty";
	static String accessTokenStr = "759307560369303553-X1kMf7u6BapUEMqQIQRMaR9fCuXgoyd";
	static String accessTokenSecretStr = "awCfmbazBXRyk1ddMF7sUaCSD1XkR4cYc6T7QsAncpC2g";
	OAuthConsumer consumer;
	private JsonConverter jsonConverter;

	public TwitterRestCall() {
		jsonConverter = new JsonConverter();
		consumerKeyStr= readProperties("consumerKey");
		consumerSecretStr=readProperties("consumerSecret");
		accessTokenStr=readProperties("accessToken");
		accessTokenSecretStr=readProperties("accessTokenSecret");
		consumer = new CommonsHttpOAuthConsumer(consumerKeyStr,
				consumerSecretStr);

		consumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
	}

	private String readProperties(String key) {
		String url = "";
		try {
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(new File(
					"config.properties"));
			properties.load(fileInputStream);
			url = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public void getTrends() {
		try {
			
			// below checking process
			String url = readProperties("trends");
//			String url ="https://twitter.com/msisodia/status/724761513957965825";
			
			HttpGet request = new HttpGet(url);
			System.out.println("url checking"+url);
			System.out.println("request checking"+request);
			
			consumer.sign(request);
			HttpClient client = new DefaultHttpClient();
			/*client.getParams()
					.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);*/
			HttpResponse response = client.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode + ":"+ response.getStatusLine().getReasonPhrase());
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					"trends.txt"));

			fileOutputStream.write(IOUtils.toString(response.getEntity().getContent()).getBytes());
			jsonConverter.convertTrendsValue("trends.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getTweetSearch(String queryName)
	{
		try
		{
			String url = readProperties("search");
			System.out.println("Search URL"+url);
			queryName=queryName.replace("#", "");
			queryName=queryName.replace(" ", "+");
			url+="="+queryName+"&count=1000";
			System.out.println(url);
			HttpGet request = new HttpGet(url);
			System.out.println("request"+request);
			consumer.sign(request);
			HttpClient client = new DefaultHttpClient();
			/*client.getParams()
					.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);*/
			HttpResponse response = client.execute(request);	
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode + ":"
					+ response.getStatusLine().getReasonPhrase());
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					"search.txt"));

			fileOutputStream.write(IOUtils.toString(
					response.getEntity().getContent()).getBytes());
			jsonConverter.convertSearchValue("search.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void getAggregation()
	{
		try
		{
			jsonConverter.convertAggregationValue("search.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
