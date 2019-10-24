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
package com.jdon.jivejdon.spi.component.block;

import com.jdon.jivejdon.infrastructure.repository.dao.SetupDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * From Apache Roller
 * <p>
 * Represents a list of banned ip addresses.
 * <p>
 * ip can be 202.1.2.3 or can be 202.1.*.* or 202.1.2.* or 202.*.*.*
 * <p>
 * modified by banQ
 * <p>
 * This base implementation gets its list from a file on the filesystem. We are
 * also aware of when the file changes via some outside source and we will
 * automatically re-read the file and update the list when that happens.
 */
public class IPBanListManager implements Runnable, IPBanListManagerIF {
	private final static Logger log = LogManager.getLogger(IPBanListManager.class);

	private final String PERSISTENCE_NAME = "IPBANLIST";

	private final IPHolder ipHolder;
	// file listing the ips that are banned
	private final SetupDao setupDao;
	private final WhiteIPs whiteIPs;
	private boolean myLastModified = false;

	public IPBanListManager(SetupDao setupDao, WhiteIPs whiteIPs) {
		this.setupDao = setupDao;
		this.whiteIPs = whiteIPs;
		log.debug("INIT");
		ipHolder = new IPHolder();
		this.loadBannedIps();
	}

	public boolean isIPAddress(String ipaddr) {
		boolean flag = false;
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.(" +
				"(?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
				"\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" +
				"\\b");
		Matcher m = pattern.matcher(ipaddr);
		flag = m.matches();
		return flag;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.spi.component.block.IPBanListManagerIF#isBanned(java.lang
	 * .String)
	 */
	public boolean isBanned(String remoteIp) {
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		if (remoteIp != null) {
			return this.ipHolder.isBanned(remoteIp);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.spi.component.block.IPBanListManagerIF#addBannedIp(java.lang
	 * .String)
	 */
	public void addBannedIp(String ip) {
		if (ip == null || ip.equals("localhost") || ip.equals("127.0.0.1") || whiteIPs.isWhite
				(ip)) {
			return;
		}
		if (!isIPAddress(ip)) return;
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		try {
			ipHolder.addressAdd(ip);

			Thread thread = new Thread(this);
			thread.run();
			log.debug("ADDED " + ip);
		} catch (Exception e) {
			log.error("Error adding banned ip ", e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.spi.component.block.IPBanListManagerIF#deleteBannedIp(java
	 * .lang.String)
	 */
	public void deleteBannedIp(String ip) {
		if (ip == null) {
			return;
		}
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		try {
			// remove
			ipHolder.addressRemove(ip);

			Thread thread = new Thread(this);
			thread.run();

			log.debug("DEL " + ip);
		} catch (Exception e) {
			log.error("Error delete banned ip ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.jivejdon.spi.component.block.IPBanListManagerIF#getAllBanIpList()
	 */
	public Collection getAllBanIpList() {
		this.loadBannedIpsIfNeeded(false);
		return ipHolder.getAllBanIpList();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.jdon.jivejdon.spi.component.block.IPBanListManagerIF#clear()
	 */
	public void clear() {
		myLastModified = true;
		ipHolder.clear();
	}

	public void run() { // TaskEngine.addTask call
		saveBanIpList();
		log.info("save ip finished!");
	}

	private void saveBanIpList() {
		try {
			String saveS = ipHolder.addressLoad();
			setupDao.saveSetupValue(PERSISTENCE_NAME, saveS);
			myLastModified = true;
		} finally {
		}
	}

	/**
	 * Load the list of banned ips from a file. This clears the old list and
	 * loads exactly what is in the file.
	 */
	private synchronized void loadBannedIps() {

		try {
			// sort is low performance
			// Set newBannedIpList = new TreeSet(new StringSortComparator());

			String ipListText = setupDao.getSetupValue(PERSISTENCE_NAME);
			BufferedReader in = new BufferedReader(new StringReader(ipListText));

			String ip = null;
			while ((ip = in.readLine()) != null) {
				ipHolder.addressAdd(ip);
				log.debug("READED " + ip);
			}

			in.close();

			// list updated, reset modified file
			// this.bannedIps = newBannedIpList;
		} catch (Exception ex) {
			log.error("Error loading banned ips from file", ex);
		}

	}

	/**
	 * Check if the banned ips file has changed and needs to be reloaded.
	 */
	private void loadBannedIpsIfNeeded(boolean forceLoad) {

		if (myLastModified || forceLoad) {
			// need to reload
			this.loadBannedIps();
			myLastModified = false;
		}
	}
}
