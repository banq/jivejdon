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
package com.jdon.jivejdon.repository.builder;

import java.util.Map;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Reward;

public class AccountInitFactory {
	private final static Logger logger = LogManager.getLogger(AccountInitFactory.class);

	private Constants constants;

	public AccountInitFactory(Constants constants) {
		super();
		this.constants = constants;
	}

	// // 0=no visible 1=visible 2=no visible + validate 3=visible +
	// validate
	public int getEmailVisible(boolean emailVisible, int oldEmailVisible) {
		if (oldEmailVisible < 2) {
			return emailVisible ? 1 : 0;
		} else if (oldEmailVisible >= 2) {
			return emailVisible ? 3 : 2;
		}
		return 0;

	}

	// // 0=no visible 1=visible 2=no visible + validate 3=visible +
	// validate
	public boolean getEmailVisible(int emailVisible) {
		if (emailVisible < 2) {
			return emailVisible == 1;
		} else if (emailVisible >= 2) {
			return emailVisible == 3;
		}
		return false;

	}

	// // 0=no visible 1=visible 2=no visible + validate 3=visible +
	// validate
	public int addEmailValidate(boolean emailValidate, int oldEmailVisible) {
		if (oldEmailVisible < 2 && emailValidate) {
			return oldEmailVisible + 2;
		} else if (oldEmailVisible >= 2 && !emailValidate) {
			return oldEmailVisible - 2;
		}
		return oldEmailVisible;
	}

	// // 0=no visible 1=visible 2=no visible + validate 3=visible +
	// validate
	public boolean getEmailValidate(int emailVisible) {
		if (emailVisible < 2) {
			return false;
		} else if (emailVisible >= 2) {
			return true;
		}
		return false;

	}

	public Account create(Map map) {
		Account ret = new Account();
		try {
			ret.setUserIdLong((Long) map.get("userID"));
			ret.setUsername((String) map.get("username"));
			ret.setPassword((String) map.get("passwordHash"));
			ret.setEmail((String) map.get("email"));
			int emailVisible = ((Integer) map.get("emailVisible")).intValue();
			// 0=no visible 1=visible 2=no visible + validate 3=visible +
			// validate
			ret.setEmailVisible(getEmailVisible(emailVisible));
			ret.setEmailValidate(getEmailValidate(emailVisible));

			int rewardPoints = ((Integer) map.get("rewardPoints")).intValue();
			ret.setReward(new Reward(rewardPoints));

			String saveDateTime = ((String) map.get("modifiedDate")).trim();
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			ret.setModifiedDate(displayDateTime);

			saveDateTime = ((String) map.get("creationDate")).trim();
			displayDateTime = constants.getDateTimeDisp(saveDateTime);
			ret.setCreationDate(displayDateTime);

		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
		return ret;

	}
}
