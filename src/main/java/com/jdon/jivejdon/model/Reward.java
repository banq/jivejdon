/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.model;

import com.jdon.annotation.Model;



/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
@Model
public class Reward  {

	//about the forum 
	private int rewardPoints;

	private int messageCount;

	/**
	 * @param rewardPoints
	 */
	public Reward(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Reward() {
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * @param messageCount The messageCount to set.
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	/**
	 * @return Returns the rewardPoints.
	 */
	public int getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * @param rewardPoints The rewardPoints to set.
	 */
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

}
