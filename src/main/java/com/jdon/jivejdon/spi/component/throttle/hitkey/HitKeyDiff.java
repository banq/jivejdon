package com.jdon.jivejdon.spi.component.throttle.hitkey;

import com.jdon.util.UtilValidate;

public class HitKeyDiff implements HitKeyIF {

	private String ip;
	private String id;

	/**
	 * 
	 * @param ip
	 * @param id
	 * @param userAgent
	 * @param threshold
	 * @param interval
	 *            seconds
	 */
	public HitKeyDiff(String ip, String id) {
		super();
		this.ip = ip;
		this.id = id;
	}

	public String getHitIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBeHitId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return ip + "HitKey";
	}

	public boolean satisfy(HitKeyIF hitkey) {
		if (hitkey.getHitIp().equals(this.getHitIp()))
			// two different messageId will be true
			if (!hitkey.getBeHitId().equals(this.getBeHitId()))
				return true;
			else
				return false;
		return false;
	}

	public boolean isEmpty() {
		if (UtilValidate.isEmpty(ip) || UtilValidate.isEmpty(id))
			return true;
		else
			return false;
	}

}
