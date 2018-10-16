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

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.StringUtil;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageForm extends BaseForm {
	public final static int subjectMaxLength = 50;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int bodyMaxLength = 58190;
	private Long messageId;

	private String creationDate;

	private String modifiedDate;

	private ForumThread forumThread;

	private Forum forum;

	private com.jdon.jivejdon.model.Account account;

	private ForumMessage parentMessage;

	// for upload files
	private AttachmentsVO attachment;;

	private boolean authenticated = true;// default true

	private MessageVO messageVO;

	// not let messageVo be Filtered , it should be save to DB.
	private boolean messageVOFiltered = true;

	private boolean masked;

	private boolean replyNotify;

	// modify

	/**
	 * for initMessage of the ForumMessageService
	 */
	public MessageForm() {
		messageVO = new MessageVO();

		forum = new Forum(); // for parameter forum.forumId=xxx
		forumThread = new ForumThread();
		account = new com.jdon.jivejdon.model.Account();
		parentMessage = new ForumMessage();

		forumThread = new ForumThread();
	}

	/**
	 * @return Returns the account.
	 */
	public com.jdon.jivejdon.model.Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            The account to set.
	 */
	public void setAccount(com.jdon.jivejdon.model.Account account) {
		this.account = account;
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the messageId.
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            The messageId to set.
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the rewardPoints.
	 */
	public int getRewardPoints() {
		return messageVO.getRewardPoints();
	}

	/**
	 * @param rewardPoints
	 *            The rewardPoints to set.
	 */
	public void setRewardPoints(int rewardPoints) {
		messageVO.setRewardPoints(rewardPoints);
	}

	/**
	 * @return Returns the parentMessage.
	 */
	public ForumMessage getParentMessage() {
		return parentMessage;
	}

	/**
	 * @param parentMessage
	 *            The parentMessage to set.
	 */
	public void setParentMessage(ForumMessage parentMessage) {
		this.parentMessage = parentMessage;
	}

	public String getBody() {
		return messageVO.getBody();
	}

	public void setBody(String body) {
		messageVO.setBody(body);
	}

	public String getSubject() {
		return messageVO.getSubject();
	}

	public void setSubject(String subject) {
		messageVO.setSubject(subject);
	}

	/**
	 * @return Returns the forumThread.
	 */
	public ForumThread getForumThread() {
		return forumThread;
	}

	/**
	 * @param forumThread
	 *            The forumThread to set.
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum
	 *            The forum to set.
	 */
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

	public String getTagTitles() {
		if ((messageVO.getTagTitle() != null) && (messageVO.getTagTitle().length != 0)) {
			return StringUtil.merge(messageVO.getTagTitle(), " ");
		} else
			return "";
	}

	public void setTagTitles(String tagTitles) {
		if (!UtilValidate.isEmpty(tagTitles))
			messageVO.setTagTitle(tagTitles.split("(\\s)+"));
	}

	public String[] getTagTitle() {
		return messageVO.getTagTitle();
	}

	public void setTagTitle(String[] tagTitle) {
		messageVO.setTagTitle(tagTitle);
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
	 * donot need get method, upload is in uploadService.getAllUploadFiles
	 * public AttachmentsVO getAttachment() { return attachment; }
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
			if (addErrorIfStringEmpty(errors, "subject.required", this.getSubject
                    ()))
			    return;
			if (addErrorIfStringEmpty(errors, "body.required", getBody()))
                return;

			if (this.getParentMessage() == null)
				if (this.getForum() == null || this.getForum().getForumId() == null) {
					errors.add("need forum");
					return;
				}
			if (UtilValidate.isEmpty(this.getSubject()) || UtilValidate.isEmpty(this.getBody())) {
				errors.add("subject or body is null");
				return;
			}
			if (UtilValidate.isEmpty(this.getSubject().replaceAll("[^\\p{L}\\p{N}]", ""))
					|| UtilValidate.isEmpty(this.getBody().replaceAll("[^\\p{L}\\p{N}]", ""))) {
				errors.add("subject or body is null");
				return;
			}
			if ((this.getSubject() != null) && (this.getSubject().length() > subjectMaxLength)) {
				errors.add("subject length too long");
				return;
			}
			if ((getBody() != null) && (getBody().length() >= bodyMaxLength)) {
				errors.add("body.sizeerror" );
				return;
			}
		}
	}
}
