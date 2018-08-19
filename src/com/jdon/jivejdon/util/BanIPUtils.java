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
package com.jdon.jivejdon.util;

import java.util.HashSet;
import java.util.Set;

public class BanIPUtils {

	private static Set ips = new HashSet();

	public static void addIPTables(String ip) {
		if (ips.contains(ip))
			return;
		if (ips.size() > 100)
			ips.clear();
		ips.add(ip);
		try {

			// java.util.Calendar rightNow = java.util.Calendar.getInstance();
			// int hour = rightNow.get(java.util.Calendar.HOUR_OF_DAY);
			Runtime sys = Runtime.getRuntime();
			System.err.println(ip + " was blocked");
			// for linux iptables
			// sys.exec("/usr/bin/sudo -u root /sbin/iptables -A INPUT -s "+
			// ip +" -j DROP");
			sys.exec("/usr/bin/sudo -u root /home/jdon/block " + ip);
			sys.exec("/usr/local/apache/bin/apachectl restart ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
