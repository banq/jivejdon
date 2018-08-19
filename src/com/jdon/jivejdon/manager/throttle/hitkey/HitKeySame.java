/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.manager.throttle.hitkey;

import com.jdon.util.UtilValidate;

public class HitKeySame implements HitKeyIF {

	private String ip;
	private String id;

	public HitKeySame(String ip, String id) {
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
		return ip + "HitKey3";
	}

	// same id
	public boolean satisfy(HitKeyIF hitkey) {
		if (hitkey.getHitIp().equals(this.getHitIp()))
			if (hitkey.getBeHitId().equals(this.getBeHitId()))
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
