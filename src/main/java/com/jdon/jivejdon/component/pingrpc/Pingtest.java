package com.jdon.jivejdon.component.pingrpc;

import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Pingtest {

	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL("http://ping.baidu.com/ping/RPC2"));
			config.setUserAgent("jdon.com");
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Object[] params = new Object[] { "jdon", "http://www.jdon.com/", "http://www.jdon.com/47686",
					 "http://www.jdon.com/rss" };
			String pMethodName = "weblogUpdates.extendedPing";
			Object result = client.execute(pMethodName, params);

			System.out.println(result);
		} catch (Exception e) {
			
		}
		
	}
}
