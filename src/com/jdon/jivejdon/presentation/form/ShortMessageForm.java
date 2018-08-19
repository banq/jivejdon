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
package com.jdon.jivejdon.presentation.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.ModelForm;

/**
 * ShortMessageForum.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-22
 * </p>
 * 
 * @author GeXinying
 * @version 1.0
 */
public class ShortMessageForm extends BaseForm {
	private int bodyMaxLength = 400;
	//
	private static final long serialVersionUID = -1124521350092345755L;

	// ID
	private Long msgId;

	// title of message
	private String messageTitle;

	// content
	private String messageBody;

	// from
	private String messageFrom;

	// destination
	private String messageTo;

	// owner
	private com.jdon.jivejdon.model.Account account;

	private String registerCode;

	//
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

	public String getFilterMessageBody() {
		return ToolsUtil.convertURL(messageBody);
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public void setFilterMessageBody(String messageBody) {
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

	public int getBodyMaxLength() {
		return bodyMaxLength;
	}

	public void setBodyMaxLength(int bodyMaxLength) {
		this.bodyMaxLength = bodyMaxLength;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	@Override
	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (getAction() != null && !ModelForm.DELETE_STR.equals(getAction())) {
			if (!SkinUtils.verifyRegisterCode(registerCode, request)) {
				errors.add("registerCode  dismatch");
			}

			if ((this.getMessageTitle() != null) && (this.getMessageTitle().length() > 20)) {
				errors.add("subject lengt too long");
			}

			if ((getMessageBody() != null) && (getMessageBody().length() >= bodyMaxLength)) {
				errors.add("body's max length should < " + bodyMaxLength);
			}
		}

	}

}
