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
package com.jdon.jivejdon.model.shortmessage;

import com.jdon.jivejdon.model.account.Account;
import com.jdon.util.UtilValidate;

//@Model
public class ShortMessage {
	public final static int MESSAGE_MAX_COUNT = 100;

	// ID
	protected Long msgId;

	// title of message
	protected String messageTitle;

	// content
	protected String messageBody;

	// from
	protected String messageFrom;

	// destination
	protected String messageTo;

	// owner
	protected Account account;

	//
	protected ShortMessageState shortMessageState;

	public ShortMessage() {
		// this.setCacheable(false);
		shortMessageState = new ShortMessageState();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	public ShortMessageState getShortMessageState() {
		return shortMessageState;
	}

	public void setShortMessageState(ShortMessageState shortMessageState) {
		this.shortMessageState = shortMessageState;
	}

	public void setHasSent(boolean hasSent) {
		this.shortMessageState.setHasSent(hasSent);
	}

	public void notifyAccount(int count) {
		this.getShortMessageState().notifyAccount(count);
	}

	public boolean isSatify(int maxMessageCount) {
		if (UtilValidate.isEmpty(getMessageTitle()))
			return false;
		if (account.isAdmin())
			return true;

		if (maxMessageCount < MESSAGE_MAX_COUNT)
			return true;
		else
			return false;

	}

}
