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
package com.jdon.jivejdon.spi.component.throttle.post;

import com.jdon.cache.UtilCache;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.spi.component.block.IPBanListManagerIF;
import com.jdon.jivejdon.spi.component.throttle.ClientInfo;
import com.jdon.jivejdon.util.ExpiringCacheEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * * From Apache Roller org.apache.roller.util.GenericThrottle
 * 
 * A tool used to provide throttling support.
 * 
 * The basic idea is that if the # of hits from a client within a certain
 * interval of time is greater than the threshold value then the client is
 * considered to be abusive.
 * 
 */
public class ThrottleManager implements Startable {

	private final static Logger log = LogManager.getLogger(ThrottleManager.class);

	private final UtilCache clientHistoryCache;

	private final IPBanListManagerIF iPBanListManager;

	private final Map<String, ThresholdLimit> limit;

	public ThrottleManager(IPBanListManagerIF iPBanListManager) {
		// 30 minutes
		this.clientHistoryCache = new UtilCache(100, 30 * 60 * 1000, false);
		this.iPBanListManager = iPBanListManager;
		this.limit = new HashMap();
	}

	public boolean checkValidate(String ip, String category) {
		if (iPBanListManager.isBanned(ip))
			return false;

		if (processHit(ip, category)) {
			log.error("post checkValidate: " + ip + " threshold=" + limit.get(category).getInterval());
			return false;
		}

		return true;
	}

	public boolean checkValidateByCustomerId(String cliendId, String ip, String category) {
		if (!checkValidate(ip, category))
			return false;

		if (processHit(cliendId, category)) {
			log.error("post checkValidateByCustomerId: " + cliendId + " threshold=" + limit.get(category).getInterval());
			return false;
		}

		return true;
	}


	public void blockIP(String ip) {
		iPBanListManager.addBannedIp(ip);
	}

	/**
	 * Process a new hit from the client.
	 * 
	 * Each call to this method increments the hit count for the client and then
	 * returns a boolean value indicating if the hit has pushed the client over
	 * the threshold.
	 * 
	 * @retuns true if client is abusive, false otherwise
	 */
	private boolean processHit(String clientId, String category) {

		if (clientId == null) {
			return false;
		}

		// see if we have any info about this client yet
		ClientInfo client = null;
		ExpiringCacheEntry cacheEntry = (ExpiringCacheEntry) clientHistoryCache.get(clientId);
		if (cacheEntry != null) {
			log.debug("HIT " + clientId);
			client = (ClientInfo) cacheEntry.getValue();

			// this means entry had expired
			if (client == null) {
				log.debug("EXPIRED " + clientId);
				clientHistoryCache.remove(clientId);
			}
		}

		// if we already know this client then update their hit count and
		// see if they have surpassed the threshold
		if (client != null) {

			log.debug("STATUS " + clientId + " - " + client.hits + " hits since " + client.start);

			// abusive client
			if (client.hits >= limit.get(category).getThreshold()) {
				return true;
			}
			client.hits++;
		} else {
			log.debug("NEW " + clientId);

			// first timer
			ClientInfo newClient = new ClientInfo();
			newClient.hits = 1;
			newClient.id = clientId;

			ExpiringCacheEntry newEntry = new ExpiringCacheEntry(newClient, limit.get(category).getInterval());
			clientHistoryCache.put(clientId, newEntry);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.spi.component.throttle.ThrottleManagerIF#isAbusive(java.lang
	 * .String)
	 */
	public boolean isAbusive(String clientId) {
		if (clientId == null) {
			return false;
		}
		if (iPBanListManager.isBanned(clientId)) {
			return true;
		}
		return false;

	}

	public void clear() {
		clientHistoryCache.clear();
		iPBanListManager.clear();
	}

	public void setCategoryParams(String category, ThresholdLimit thresholdLimit) {
		this.limit.put(category, thresholdLimit);
	}

	public boolean contain(String category) {
		return this.limit.containsKey(category);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		this.clientHistoryCache.stop();

	}

}
