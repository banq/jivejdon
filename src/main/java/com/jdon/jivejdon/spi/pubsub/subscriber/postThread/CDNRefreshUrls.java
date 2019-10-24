package com.jdon.jivejdon.pubsub.domain.consumer.write.postThread;

public class CDNRefreshUrls {

	private final String[] urls;

	public CDNRefreshUrls(String[] urls) {
		this.urls = urls;
	}

	public String[] getUrls() {
		return urls;
	}
}
