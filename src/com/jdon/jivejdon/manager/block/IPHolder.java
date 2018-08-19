/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.manager.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class IPHolder {
	// set of ips that are banned, use a set to ensure uniqueness
	private Set<String> bannedIps;

	private List<Pattern> regexBannedIps;

	public IPHolder() {
		bannedIps = new HashSet();
		regexBannedIps = new ArrayList<Pattern>();

	}

	public Collection getAllBanIpList() {
		Collection result = new ArrayList(bannedIps);
		for (Pattern p : regexBannedIps) {
			result.add(p.toString());
		}
		return result;
	}

	public boolean isBanned(String remoteIp) {
		if (remoteIp == null)
			return false;
		if (bannedIps.contains(remoteIp))
			return true;
		else
			for (Pattern p : regexBannedIps)
				if (p.matcher(remoteIp).matches())
					return true;

		return false;
	}

	public void addressAdd(String ip) {
		if (bannedIps.contains(ip) || isContain(ip))
			return;
		if (ip.indexOf("*") != -1) {// if it is 202.1.*.*
			Pattern p = Pattern.compile(ip);
			regexBannedIps.add(p);
		} else
			bannedIps.add(ip);// if it is 202.1.1.1
	}

	private boolean isContain(String ip) {
		for (Pattern p : regexBannedIps) {
			if (p.toString().equals(ip))
				return true;
		}
		return false;
	}

	public void addressRemove(String ip) {
		if (ip.indexOf("*") != -1) {// if it is 202.1.*.*
			for (Iterator<Pattern> it = regexBannedIps.iterator(); it.hasNext();) {
				Pattern p = it.next();
				if (p.toString().equals(ip))
					it.remove();
			}
		} else {
			for (Iterator<String> it = bannedIps.iterator(); it.hasNext();) {
				String s = it.next();
				if (s.equals(ip))
					it.remove();
			}
		}

	}

	public String addressLoad() {
		StringBuilder sb = new StringBuilder();
		for (Object o : bannedIps) {
			sb.append((String) o).append("\n");
		}
		for (Pattern p : regexBannedIps) {
			sb.append(p.toString()).append("\n");
		}

		return sb.toString();
	}

	public void clear() {
		bannedIps.clear();
		regexBannedIps.clear();
	}

}
