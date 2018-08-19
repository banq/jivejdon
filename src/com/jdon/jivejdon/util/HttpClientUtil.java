package com.jdon.jivejdon.util;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtil {
	static DefaultHttpClient httpClient = new DefaultHttpClient();

	public static String get(String url, String encoding) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = httpClient.execute(httpGet);
		return getContent(res, encoding);
	}

	public static String get(String url, String encoding, DefaultHttpClient client) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = client.execute(httpGet);
		return getContent(res, encoding);
	}

	public static String post(String url, StringEntity se, String host, String referer, String encoding) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(se);
		httpPost.setHeader("Host", host);
		httpPost.setHeader("Referer", referer);
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Accept-Language", "zh-cn");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("UA-CPU", "x86");
		httpPost.setHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; InfoPath.2; CIBA)");
		httpPost.setHeader("Connection", "close");
		HttpResponse response = httpClient.execute(httpPost);

		return getContent(response, encoding);
	}

	public static String httpPost(String url, String queryString, String encoding) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(queryString));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.getParams().setParameter("http.socket.timeout", new Integer(20000));
		httpPost.setHeader("Connection", "close");
		HttpResponse response = httpClient.execute(httpPost);

		return getContent(response, encoding);
	}

	public static String getContent(HttpResponse res, String encoding) throws Exception {
		HttpEntity ent = res.getEntity();
		String result = IOUtils.toString(ent.getContent(), encoding);
		ent.consumeContent();
		return result;
	}

	public static InputStream getStream(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = httpClient.execute(httpGet);
		return res.getEntity().getContent();
	}

	public static InputStream getStream(String url, DefaultHttpClient client) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; InfoPath.2; CIBA)");
		httpGet.setHeader("Referer", "http://reg.126.com/regmail126/userRegist.do?action=fillinfo");
		// httpGet.setHeader("Accept", "*/*");
		// httpGet.setHeader("Accept-Language", "zh-cn");
		// httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Connection", "close");
		HttpResponse res = client.execute(httpGet);
		return res.getEntity().getContent();
	}

	public static void main(String args[]) throws Exception {
		String urlString = "http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx/getCountryCityByIp?theIpAddress=122.227.134.234";
		String value = HttpClientUtil.get(urlString, "utf-8");
		System.out.println(value);
	}
}
