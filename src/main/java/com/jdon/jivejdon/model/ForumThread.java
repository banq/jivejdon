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
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.event.domain.producer.write.MessageEventSourcingRole;
import com.jdon.jivejdon.model.event.MessageMovedEvent;
import com.jdon.jivejdon.model.event.ThreadNameSavedEvent;
import com.jdon.jivejdon.model.event.ThreadTagsSavedEvent;
import com.jdon.jivejdon.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.state.ForumThreadStateFactory;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.event.ThreadSubscribedNotifyEvent;
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.model.thread.ViewCounter;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;
import com.jdon.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ForumThread is the aggregation root, be composed of roo message and its reply
 * messages.
 * <p>
 * root message is the aggregation root too.
 *
 * @author <a href="mailto:banq@163.com">banq</a>
 */
@Model
public class ForumThread extends ForumModel {
	private static final long serialVersionUID = 1L;
	@Inject
	public LazyLoaderRole lazyLoaderRole;
	@Inject
	public SubPublisherRoleIF subPublisherRole;
	@Inject
	public LobbyPublisherRoleIF lobbyPublisherRole;
	@Inject
	public MessageEventSourcingRole eventSourcing;

	private final Long threadId;

	// same as rootMessage's creationDate
	private String creationDate;
	// contain some abstract properties
	private Collection propertys;
	private ThreadTagsVO threadTagsVO;
	private volatile Forum forum;
	/**
	 * the root message of a thread. The root message is a special first
	 * message
	 * that is intimately tied to the thread for most forumViews. All other
	 * messages in the thread are children of the root message.
	 */
	private volatile ForumMessage rootMessage;
	private volatile AtomicReference<ForumThreadState> state;
	// update mutable
	private volatile ViewCounter viewCounter;
	// update mutable
	private volatile ForumThreadTreeModel forumThreadTreeModel;
	@Inject
	private ForumThreadStateFactory threadStateManager;
	private long creationDate2;


	/**
	 * normal can be cached reused
	 *
	 * @param rootMessage
	 */
	public ForumThread(ForumMessage rootMessage, Long tIDInt, Forum forum) {
		this.rootMessage = rootMessage;
		this.threadId = tIDInt;
		this.forum = forum;
		this.state = new AtomicReference(new ForumThreadState(this));
		this.threadTagsVO = new ThreadTagsVO(this, new ArrayList());
		this.propertys = new ArrayList();
		this.viewCounter = new ViewCounter(this);
	}

//	/**
//	 * DTO object, limited usage
//	 */
//	private ForumThread() {
//		this.threadTagsVO = new ThreadTagsVO(this, new ArrayList());
//		this.propertys = new ArrayList();
////		this.state = new AtomicReference(new ForumThreadState(this));
//		this.viewCounter = new ViewCounter(this);
////		this.rootMessage = new ForumMessage();
//	}


	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public long getCreationDate2() {
		return creationDate2;
	}

	public void setCreationDate2(long creationDate) {
		this.creationDate2 = creationDate;
	}

	public Forum getForum() {
		return forum;
	}


	public void setForum(Forum forum) {
		this.forum = forum;
//		if (this.rootMessage != null) {
//			rootMessage.setForum(forum);
//		}
	}


	public Long getThreadId() {
		return threadId;
	}

	public String getName() {
		if (this.getRootMessage() == null){
			System.err.println(" thread rootmessage is null" + threadId);
			return "null";
		}
		return this.getRootMessage().getMessageVO().getSubject();
	}

	//from threadForm getName calling
	public void setName(String name){
		this.getRootMessage().updateSubject(name);
	}

	public void updateName(String name) {
		this.getRootMessage().updateSubject(name);
		eventSourcing.saveName(new ThreadNameSavedEvent(this.getThreadId(),
				name));
	}



	public String getShortname() {
		return StringUtil.shorten(getName());
	}

	public Collection getPropertys() {
		return propertys;
	}


	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public ForumMessage getRootMessage() {
		return rootMessage;
	}


	public void setRootMessage(ForumMessage rootMessage) {
		if (rootMessage == null) return;
		if (rootMessage instanceof ForumMessageReply){
			System.err.println("root message must be ForumMessage threadId=" + this.threadId + " messageId="+rootMessage.getMessageId());
			return;
		}
		this.rootMessage = rootMessage;
//		this.rootMessage.setForumThread(this);
	}

	/**
	 * @return Returns the forumThreadState.
	 */
	public ForumThreadState getState() {
		return state.get();
	}

	public void setState(ForumThreadState state) {
		if (state == null) return;
		this.state.lazySet(state);
	}

	public Collection<ThreadTag> getTags() {
		return this.threadTagsVO.getTags();
	}

	public void changeTags(String[] tagTitles) {
		if (tagTitles == null || tagTitles.length == 0)
			return;
		getRootMessage().setTagTitle(tagTitles);
		eventSourcing.saveTagTitles(new ThreadTagsSavedEvent(this.threadId,
				Arrays.asList(tagTitles)));
	}

	public String[] getTagTitles() {
		return getRootMessage().getTagTitle();
	}

	public boolean isRoot(ForumMessage message) {
		try {
			if (message.getMessageId().longValue() == getRootMessage()
					.getMessageId().longValue())
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * after inserted into DB, change the state, so it is Eventually consistent;
	 * in MessageListNav2Action, client will be waiting  until Eventually consistent!
	 *
	 * @param forumMessageReply
	 */
	public void changeState(ForumMessageReply forumMessageReply) {
		this.threadStateManager.addNewMessage(this, forumMessageReply);
		this.forum.addNewMessage(forumMessageReply);
	}

	public void addNewMessage(ForumMessage forumMessageParent,
							  ForumMessageReply forumMessageReply) {
		try {
//			forumMessageReply.setParentMessage(forumMessageParent);
//			forumMessageParent.setForumThread(this);
//			forumMessageReply.setForumThread(this);

			ForumMessage oldmessage = this.getState().getLastPost();
			//removeed to changeState
//			this.threadStateManager.addNewMessage(this, forumMessageReply);
//			this.forum.addNewMessage(forumMessageReply);
			getForumThreadTreeModel().addChildAction(forumMessageReply);

			notifyLobby(forumMessageReply);

			Date olddate = Constants.parseDateTime(oldmessage.getCreationDate
					());
			if (Constants.timeAfter(1, olddate)) {// a event per one hour
				subPublisherRole.subscriptionNotify(new
						ThreadSubscribedNotifyEvent(this.getThreadId()));
			}
		} catch (Exception e) {
			System.err.print("error in forumThread:" + this.threadId + " " +
					e);
		}
	}

	private void notifyLobby(ForumMessageReply forumMessageReply) {
		Notification notification = new Notification();
		notification.setSource(new ForumMessageDTO(forumMessageReply));
		lobbyPublisherRole.notifyLobby(notification);

	}

	public Forum moveForum(ForumMessage forumMessage, Forum newForum) {
		if ((isRoot(forumMessage)) && (forumMessage.isLeaf())) {
			DomainMessage dm = this.lazyLoaderRole.loadForum(newForum
					.getForumId());
			newForum = (Forum) dm.getBlockEventResult();
//			forumMessage.setForum(newForum);
			setForum(newForum);

			eventSourcing.moveMessage(new MessageMovedEvent(forumMessage
					.getMessageId(), newForum.getForumId()));
			dm.clear();
		}
		return newForum;
	}

	public void updateMessage(ForumMessage forumMessage) {
		if (isRoot(forumMessage)) {
			this.setRootMessage(forumMessage);
			changeTags(forumMessage.getTagTitle());
		}

		threadStateManager.updateMessage(this, forumMessage);
		this.forum.updateNewMessage(forumMessage);

	}


	public boolean isLeaf(ForumMessage forumMessage) {
		if (!this.isSolid()) {
			System.err.print("this thread is not embedded, threadId = " +
					threadId + " " + this.hashCode());
			return false;
		}

		boolean ret = false;
		try {
			TreeModel treeModel = getForumThreadTreeModel().getTreeModel();
			ret = treeModel.isLeaf(forumMessage.getMessageId());
		} catch (Exception e) {
			String error = e + " isLeaf forumMessageId=" + forumMessage
					.getMessageId();
			System.err.print(error);
		}
		return ret;
	}

	public ForumThreadTreeModel getForumThreadTreeModel() {
		if (forumThreadTreeModel == null)
			forumThreadTreeModel = new ForumThreadTreeModel(threadId, this
					.lazyLoaderRole);
		return forumThreadTreeModel;
	}

	public void addViewCount() {
		viewCounter.addViewCount();
	}

	// return count
	public long getViewCount() {
		return viewCounter.getViewCount();
	}

	// return viewcount object
	public ViewCounter getViewCounter() {
		return viewCounter;
	}

	public void setViewCounter(ViewCounter viewCounter) {
		this.viewCounter = viewCounter;
	}

	public void acceptTreeModelVisitor(long startMessageId, TreeVisitor
			treeVisitor) {
		getForumThreadTreeModel().acceptVisitor(startMessageId, treeVisitor);
	}

	public ThreadTagsVO getThreadTagsVO() {
		return threadTagsVO;
	}

	public void setThreadTagsVO(ThreadTagsVO threadTagsVO) {
		this.threadTagsVO = threadTagsVO;

		// set rootMessage's titles;
		String[] tagTitles = new String[threadTagsVO.getTags().size()];
		int i = 0;
		for (Object o : threadTagsVO.getTags()) {
			ThreadTag tag = (ThreadTag) o;
			tagTitles[i] = tag.getTitle();
			i++;
		}
		getRootMessage().setTagTitle(tagTitles);
	}

	/**
	 * all dig for reply messages add to root Message
	 * @param message
	 */
	public void addDig(ForumMessage message) {
		if (message.getMessageId().longValue() != rootMessage.getMessageId().longValue()) {
			rootMessage.messaegDigAction();
		}

	}

	// not use the field modifiedDate in DB.
	public String getModifiedDate() {
		if (getState().getLastPost() != null)
			return getState().getModifiedDate();
		else
			return this.creationDate;

	}

}
