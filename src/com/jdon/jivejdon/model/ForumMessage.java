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
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.event.domain.producer.write.MessageEventSourcingRole;
import com.jdon.jivejdon.event.domain.producer.write.ShortMPublisherRole;
import com.jdon.jivejdon.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.model.event.MessagePropertiesUpdatedEvent;
import com.jdon.jivejdon.model.event.MessageUpdatedEvent;
import com.jdon.jivejdon.model.event.ReplyMessageCreatedEvent;
import com.jdon.jivejdon.model.event.UploadFilesSavedEvent;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.proptery.MessagePropertysVO;
import com.jdon.jivejdon.model.reblog.ReBlogVO;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * ForumMessage is a message in Forum.
 * 
 * ForumMessageDecorator is its child class.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
@Searchable
public class ForumMessage extends ForumModel implements Cloneable {
	private static final long serialVersionUID = 1L;
	@Inject
	public LazyLoaderRole lazyLoaderRole;
	@Inject
	public MessageEventSourcingRole eventSourcing;
	@Inject
	public ShortMPublisherRole shortMPublisherRole;
	@SearchableId
	private Long messageId;
	private String creationDate;
	private long modifiedDate;
	private Account account; // owner
	private Account operator; // operator this message,maybe Admin or others;
	private volatile ForumThread forumThread;
	@SearchableReference
	private Forum forum;
	@SearchableComponent
	private MessageVO messageVO;
	private Collection outFilters;
	private boolean replyNotify;
	private AttachmentsVO attachmentsVO;
	private MessagePropertysVO messagePropertysVO;
	private ReBlogVO reBlogVO;

	// created from repository that will be in memory, it is Entity
	public ForumMessage(Long messageId) {
		this.messageId = messageId;
		this.messageVO = new MessageVO();

	}

	// created in UI, catch the messageForm's copy data, it is DTO.
	public ForumMessage() {
		this.setParameter(true);
		this.messageVO = new MessageVO();
		this.account = new Account();
		this.attachmentsVO = new AttachmentsVO(new Long(0), new ArrayList());
		this.messagePropertysVO = new MessagePropertysVO(new Long(0), new ArrayList());
	}

	public void finishDTO() {
		this.attachmentsVO = null;
		this.messagePropertysVO = null;
		this.setParameter(false);
	}

	/**
	 * @return Returns the account.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            The account to set.
	 */
	public void setAccount(com.jdon.jivejdon.model.Account account) {
		this.account = account;
	}

	public Account getOperator() {
		return operator;
	}

	public void setOperator(Account operator) {
		this.operator = operator;
	}

	public boolean isLeaf() {
		return this.forumThread.isLeaf(this);
	}

	public boolean isRoot() {
		return this.forumThread.isRoot(this);
	}

	public void applyFilters() {
		try {
			if (outFilters == null)
				return;
			if (this.messageVO.isFiltered())
				return;
			synchronized (this) {
				Iterator iter = outFilters.iterator();
				while (iter.hasNext()) {
					MessageRenderSpecification mrs = ((MessageRenderSpecification) iter.next());
					mrs.render(this);
				}
				messageVO.setFiltered(true);
			}
		} catch (Exception e) {
			System.err.print(" applyFilters error:" + e + getMessageId());
		}
	}

	public MessageVO getMessageVO() {
		return messageVO;
	}

	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

	public MessageVO getMessageVOClone() throws Exception {
		return (MessageVO) this.messageVO.clone();
	}

	public void reloadMessageVOOrignal() {
		DomainMessage em = lazyLoaderRole.reloadMessageVO(this.messageId);
		messageVO = (MessageVO) em.getBlockEventResult();
		setMessageVO(messageVO);
		em.clear();
	}

	/**
	 * post a reply forumMessage
	 * 
	 * @param forumMessageReply
	 */
	@OnCommand("addreplyForumMessage")
	public void addChild(ForumMessageReply forumMessageReply) {
		try {
			// basic construct
			forumMessageReply.setParentMessage(this);
			forumMessageReply.setForumThread(this.getForumThread());
			forumMessageReply.setForum(this.getForum());

			// in eventHandler, after saving to DB, this forumMessageReply will
			// be saved to cache.
			eventSourcing.addReplyMessage(new ReplyMessageCreatedEvent(forumMessageReply));

			forumThread.addNewMessage(this, forumMessageReply);

			forumMessageReply.setOutFilters(this.getOutFilters());

			// messagecount + 1
			forumMessageReply.getAccount().updateMessageCount(1);

			// no need here, the new forumMessageReply will be fetch from cache
			// (messageDirector.getMessage)

			// and then, in messageDirector.getMessage there will have a
			// applyFilters.

			// this.applyFilters();
		} catch (Exception e) {
			System.err.print(" addReplyMessage error:" + e + this.messageId);
		}
	}

	/**
	 * doing after put a exist forumMessage
	 * 
	 * @param newForumMessageInputparamter
	 */
	@OnCommand("updateForumMessage")
	public void update(ForumMessage newForumMessageInputparamter) {
		try {
			merge(newForumMessageInputparamter);

			ForumThread forumThread = this.getForumThread();
			forumThread.updateMessage(this);

			if (newForumMessageInputparamter.getForum() != null) {
				Long newforumId = newForumMessageInputparamter.getForum().getForumId();
				if (newforumId != null && getForum().getForumId() != newforumId) {
					forumThread.moveForum(this, newForumMessageInputparamter.getForum());
				}
			}

			// save this updated message to db
			eventSourcing.saveMessage(new MessageUpdatedEvent(newForumMessageInputparamter));

			this.applyFilters();
		} catch (Exception e) {
			System.err.print(" updateMessage error:" + e + this.messageId);
		}
	}

	/**
	 * merge the new into myself, include aggregation or association parts
	 * 
	 * @param newForumMessageInputparamter
	 * @throws Exception
	 */
	private void merge(ForumMessage newForumMessageInputparamter) throws Exception {
		long now = System.currentTimeMillis();
		setModifiedDate(now);

		setOperator(newForumMessageInputparamter.getOperator());

		// 1. aggregation
		setMessageVO((MessageVO) newForumMessageInputparamter.getMessageVO().clone());

		// 2. association upload files
		// send message that include a new value Object. not send a entity
		// object such as this
		Collection uploads = newForumMessageInputparamter.getAttachment().getUploadFiles();
		if (uploads != null) {
			eventSourcing.saveUploadFiles(new UploadFilesSavedEvent(this.messageId, uploads));
			// update memory
			this.getAttachment().setUploadFiles(uploads);
		}

		// 3. association message property
		Collection props = newForumMessageInputparamter.getMessagePropertysVO().getPropertys();
		this.getMessagePropertysVO().replacePropertys(props);
		// merge with old properties;
		eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId, getMessagePropertysVO().getPropertys()));
	}

	public void updateMasked(boolean masked) {
		this.getMessagePropertysVO().updateMasked(masked);
		eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId, getMessagePropertysVO().getPropertys()));

		this.getForumThread().updateMessage(this);

		this.reloadMessageVOOrignal();
		this.applyFilters();
	}

	public AttachmentsVO getAttachment() {
		if (attachmentsVO == null)
			attachmentsVO = new AttachmentsVO(this.messageId, this.lazyLoaderRole);
		return attachmentsVO;
	}

	public void setAttachment(AttachmentsVO attachmentsVO) {
		this.attachmentsVO = attachmentsVO;
	}

	/**
	 * this mesages is masked by admin
	 *
	 * @param forumMessage
	 *
	 * @return boolean
	 */
	public boolean isMasked() {
		return this.getMessagePropertysVO().isMasked();
	}

	public void setMasked(boolean masked) {
		getMessagePropertysVO().setMasked(masked);
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

	public String getCreationDateForDay() {
		return creationDate.substring(2, 16);
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
		if (this.modifiedDate == 0)
			return "";
		return Constants.getDefaultDateTimeDisp(modifiedDate);
	}

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedDate3() {
		if (modifiedDate == 0)
			return "";
		return Constants.convertDataToPretty(modifiedDate);
	}

	public long getModifiedDate2() {
		return modifiedDate;
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

	public Collection getOutFilters() {
		return outFilters;
	}

	public void setOutFilters(Collection outFilters) {
		this.outFilters = outFilters;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getDigCount() {
		return this.getMessagePropertysVO().getDigCount();
	}

	public MessagePropertysVO getMessagePropertysVO() {
		if (messagePropertysVO == null && lazyLoaderRole != null)
			messagePropertysVO = new MessagePropertysVO(messageId, this.lazyLoaderRole);
		else if (messagePropertysVO == null && lazyLoaderRole == null) {
			System.err.print("my god, how I was bornd?");
		}
		return messagePropertysVO;
	}

	public void setMessagePropertysVO(MessagePropertysVO messagePropertysVO) {
		this.messagePropertysVO = messagePropertysVO;
	}

	public void preloadWhenView() {
		getMessagePropertysVO().preload();
		getAttachment().preloadUploadFileDatas();// preload img
	}

	public void messaegDigAction() {
		this.getMessagePropertysVO().addMessageDigCount();
		this.forumThread.addDig(this);
		eventSourcing.saveMessageProperties(new MessagePropertiesUpdatedEvent(this.messageId, getMessagePropertysVO().getPropertys()));
	}

	public boolean isReplyNotify() {
		return replyNotify;
	}

	public void setReplyNotify(boolean replyNotify) {
		this.replyNotify = replyNotify;
	}

	public String getPostip() {
		return this.getMessagePropertysVO().getPostip();
	}

	public ReBlogVO getReBlogVO() {
		if (reBlogVO == null)
			reBlogVO = new ReBlogVO(this.messageId, this.lazyLoaderRole);
		return reBlogVO;
	}

	public void setReBlogVO(ReBlogVO reBlogVO) {
		this.reBlogVO = reBlogVO;
	}

}
