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
package com.jdon.jivejdon.spi.component.throttle.post;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.account.Account;

import java.util.Calendar;
import java.util.Date;

@Component
public class Throttler {

	protected final ThrottleManager throttleManager;
	private final VIPUserThrottleConf vipUserThrottleConf;
	private final NewUserThrottleConf newUserThrottleConf;

	public Throttler(ThrottleManager throttleManager, VIPUserThrottleConf vipUserThrottleConf, NewUserThrottleConf newUserThrottleConf) {
		super();
		this.throttleManager = throttleManager;
		this.vipUserThrottleConf = vipUserThrottleConf;
		this.newUserThrottleConf = newUserThrottleConf;

	}

	public boolean checkVIPValidate(Account account) {
		if (timeLimit())// closed at evening
			return false;
		if (!throttleManager.contain("checkVIPValidate")) {
			ThresholdLimit thresholdLimit = new ThresholdLimit(vipUserThrottleConf.getThreshold(), vipUserThrottleConf.getInterval());
			throttleManager.setCategoryParams("checkVIPValidate", thresholdLimit);
		}
		return throttleManager.checkValidateByCustomerId(account.getUserId(), account.getPostIP(), "checkVIPValidate");

	}

	public boolean checkNewUserPostValidate(Account account) {
		if (timeLimit())// closed at evening
			return false;
		if (!throttleManager.contain("checkNewUserPostValidate")) {
			ThresholdLimit thresholdLimit = new ThresholdLimit(newUserThrottleConf.getThreshold(), newUserThrottleConf.getInterval());
			throttleManager.setCategoryParams("checkNewUserPostValidate", thresholdLimit);
		}
		return throttleManager.checkValidateByCustomerId(account.getUserId(), account.getPostIP(), "checkNewUserPostValidate");
	}

	private boolean timeLimit() {
		// at evening  we closed
		Calendar startingCalendar = Calendar.getInstance();
		startingCalendar.setTime(new Date());
		if (startingCalendar.get(Calendar.HOUR_OF_DAY) < newUserThrottleConf.getTimeLimitS()
				|| startingCalendar.get(Calendar.HOUR_OF_DAY) > newUserThrottleConf.getTimeLimitE()) {
			return true;
		} else
			return false;
	}

	public boolean isAbusive(String ip) {
		return throttleManager.isAbusive(ip);
	}

	public void blockIP(String ip) {
		throttleManager.blockIP(ip);
	}

	public VIPUserThrottleConf getVipUserThrottleConf() {
		return vipUserThrottleConf;
	}
}
