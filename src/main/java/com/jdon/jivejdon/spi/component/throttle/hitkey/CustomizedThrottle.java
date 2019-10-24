package com.jdon.jivejdon.spi.component.throttle.hitkey;

public interface CustomizedThrottle {

	/*
	 * throttling protection against spammers
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.spi.component.throttle.ThrottleManagerIF#processHitFilter
	 * (java.lang.String)
	 */
	boolean processHitFilter(HitKeyIF hitKey);

	boolean processHit(HitKeyIF hitKey);

	boolean clientIsCached(HitKeyIF hitKey);

	void addBanned(String ip);

	void removeBanned(String ip);

	public void clearCache();

}