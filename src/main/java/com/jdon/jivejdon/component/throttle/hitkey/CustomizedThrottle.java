package com.jdon.jivejdon.component.throttle.hitkey;

public interface CustomizedThrottle {

	/*
	 * throttling protection against spammers
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.component.throttle.ThrottleManagerIF#processHitFilter
	 * (java.lang.String)
	 */
	boolean processHitFilter(HitKeyIF hitKey);

	boolean processHit(HitKeyIF hitKey);

	boolean clientIsCached(HitKeyIF hitKey);

	void addBanned(String ip);

	void removeBanned(String ip);

	public void clearCache();

}