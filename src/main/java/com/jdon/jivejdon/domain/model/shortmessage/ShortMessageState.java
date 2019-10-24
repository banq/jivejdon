/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.domain.model.shortmessage;

import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.infrastructure.repository.shortmessage.ShortMessageRepository;

import java.util.Observable;

/**
 * ShortMessageState.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: May 29, 2008
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public class ShortMessageState extends Observable {
	// be read
	private boolean hasRead;

	// be sent
	private boolean hasSent;

	// send time
	private String sendTime;

	private ShortMessage shortMessage;

	public ShortMessageState() {
		super();

	}

	public ShortMessage getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
	}

	public boolean isHasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}

	/**
	 * 
	 * @param hasRead
	 * @param shortMessage
	 * @see ShortMessageRepository#update(java.util.Observable,
	 *      java.lang.Object)
	 */
	public void setHasRead(boolean hasRead, ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
		if ((hasRead) && (!this.hasRead)) {
			this.hasRead = hasRead;
			notifyAccount(this);
		} else
			this.hasRead = hasRead;
	}

	public void notifyAccount(Object o) {
		setChanged();
		notifyObservers(o);

	}

	public boolean isHasSent() {
		return hasSent;
	}

	public void setHasSent(boolean hasSent) {
		this.hasSent = hasSent;

	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
