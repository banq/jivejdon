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
package com.jdon.jivejdon.presentation.form;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.util.UtilValidate;

/**
 * UI model
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageForm extends BaseForm {
	public final static int subjectMaxLength = 140;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int bodyMaxLength = 81900;
	private Long messageId;

	private ForumThread forumThread;

	private Forum forum;

	private Account account;

	private AnemicMessageDTO parentMessage;

	// for upload files
	private AttachmentsVO attachment;;

	private boolean authenticated = true;// default true

	private MessageVO messageVO;

	// not let messageVo be Filtered , it should be save to DB.
	private boolean messageVOFiltered = true;

	private boolean masked;

	private boolean replyNotify;

	private String[] tagTitle;

	private String token;

	// modify

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * for initMessage of the ForumMessageService
	 */
	public MessageForm() {
		messageVO = new MessageVO();
		forum = new Forum(); // for parameter forum.forumId=xxx
		forumThread = new ForumThread(null, null);
		forumThread.setForum(forum);
		account = new Account();
		parentMessage = new AnemicMessageDTO();

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public AnemicMessageDTO getParentMessage() {
		return parentMessage;
	}

	public void setParentMessage(AnemicMessageDTO parentMessage) {
		this.parentMessage = parentMessage;
	}

	public String getBody() {
		return messageVO.getBody();
	}

	public void setBody(String body) {
		messageVO = messageVO.builder().subject(this.getSubject()).body(body).build();
	}

	public String getSubject() {
		return messageVO.getSubject();
	}

	public void setSubject(String subject) {
		messageVO = messageVO.builder().subject(subject).body(this.getBody()).build();
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getBodyMaxLength() {
		return bodyMaxLength;
	}

	public void setBodyMaxLength(int bodyMaxLength) {
		this.bodyMaxLength = bodyMaxLength;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}
	//
	// public String getTagTitles() {
	// if ((this.tagTitles != null) && (this.tagTitles.length != 0)) {
	// return StringUtil.merge(this.tagTitles, " ");
	// } else
	// return "";
	// }
	//
	// public void setTagTitles(String tagTitle) {
	// if (!UtilValidate.isEmpty(tagTitle))
	// this.tagTitles = tagTitle.split("(\\s)+");
	// }

	public String[] getTagTitle() {

		return this.tagTitle;
	}

	public void setTagTitle(String[] tagTitle) {
		this.tagTitle = tagTitle;
	}

	public boolean isRoot() {
		if (this.forumThread.getRootMessage().getMessageId().longValue() == this.messageId.longValue())
			return true;
		else
			return false;
	}

	public boolean isAttached() {
		if (attachment == null)
			return false;

		if ((attachment.getUploadFiles() != null) && (attachment.getUploadFiles().size() != 0))
			return true;
		else
			return false;
	}

	public Collection getUploadFiles() {
		if (attachment == null)
			return new ArrayList();
		return attachment.getUploadFiles();
	}

	/**
	 * donot need get method, upload is in uploadService.getAllUploadFiles public
	 * AttachmentsVO getAttachment() { return attachment; }
	 * 
	 * @return
	 */

	public void setAttachment(AttachmentsVO attachment) {
		this.attachment = attachment;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public MessageVO getMessageVO() {
		return messageVO;
	}

	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

	public boolean isMessageVOFiltered() {
		return messageVOFiltered;
	}

	public void setMessageVOFiltered(boolean messageVOFiltered) {
		this.messageVOFiltered = messageVOFiltered;
	}

	public boolean isReplyNotify() {
		return replyNotify;
	}

	public void setReplyNotify(boolean replyNotify) {
		this.replyNotify = replyNotify;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (getMethod() == null || !getMethod().equalsIgnoreCase("delete")) {
			if (addErrorIfStringEmpty(errors, "need subject", this.getSubject()))
				return;
			if (addErrorIfStringEmpty(errors, "need.body", getBody()))
				return;

			if (this.getParentMessage() == null)
				if (this.getForum() == null || this.getForum().getForumId() == null) {
					errors.add("need.forum");
					return;
				}
			if (UtilValidate.isEmpty(this.getSubject()) || UtilValidate.isEmpty(this.getBody())) {
				errors.add("subject.or.body.is.null");
				return;
			}
			if ((this.getSubject() != null) && (this.getSubject().length() > subjectMaxLength)) {
				errors.add("subject.length.too.long");
				return;
			}
			if ((getBody() != null) && (getBody().length() >= bodyMaxLength)) {
				errors.add("body.sizeerror");
				return;
			}
			if (UtilValidate.isEmpty(this.getSubject().replaceAll("[^\\p{L}\\p{N}]", ""))
					|| UtilValidate.isEmpty(this.getBody().replaceAll("[^\\p{L}\\p{N}]", ""))) {
				errors.add("subject.or.body.is.null");
				return;
			}

		}
	}

	public String esacpeUtf(String input) {
		String utf8tweet = "";
		try {
			byte[] utf8Bytes = input.getBytes("UTF-8");

			utf8tweet = new String(utf8Bytes, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]",
				Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);

		return unicodeOutlierMatcher.replaceAll(" ");
	}
}
