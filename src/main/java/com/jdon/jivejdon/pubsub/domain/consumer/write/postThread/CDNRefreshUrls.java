package com.jdon.jivejdon.event.domain.consumer.write.postThread;

public class CDNRefreshUrls {

	private final String[] urls;

	public CDNRefreshUrls(String[] urls) {
		this.urls = urls;
	}

	public String[] getUrls() {
		return urls;
	}
}
