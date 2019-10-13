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
package com.jdon.jivejdon.component.throttle.hitkey;

import org.apache.logging.log4j.*;

public class HitConf {
	private final static Logger log = LogManager.getLogger(HitConf.class);

	// threshold and interval to determine who is abusive
	private int threshold = 25;

	private int interval = 60000; // milliseconds

	// see component.xml config.parameter
	public HitConf(String threshold, String interval) {
		// threshold can't be negative, that would mean everyone is abusive
		int thresh = 25;
		try {
			thresh = Integer.parseInt(threshold);
		} catch (Exception e) {
			log.warn("bad input for config property comment.throttle.threshold");
		}
		if (thresh > -1) {
			this.threshold = thresh;
		}

		// interval must be a positive value
		int inter = 60000;
		try {
			inter = Integer.parseInt(interval);
		} catch (NumberFormatException e) {
			log.warn("bad input for config property comment.throttle.interval");
		}
		if (inter > 0) {
			this.interval = inter * 1000;// convert from seconds to
			// milliseconds
		}

	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

}
