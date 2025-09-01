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
package com.jdon.jivejdon.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.MessageRemoveCommand;
import com.jdon.jivejdon.domain.event.MessageOwnershipChangedEvent;
import com.jdon.jivejdon.domain.event.MessagePropertiesRevisedEvent;
import com.jdon.jivejdon.domain.event.MessageRemovedEvent;
import com.jdon.jivejdon.domain.event.ThreadNameRevisedEvent;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.domain.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.domain.model.realtime.Notification;
import com.jdon.jivejdon.domain.model.reblog.ReBlogVO;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.domain.model.subscription.event.ThreadSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.domain.model.thread.ViewCounter;
import com.jdon.jivejdon.spi.pubsub.publish.MessageEventSourcingRole;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;
import com.jdon.jivejdon.util.Constants;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;
import com.jdon.util.StringUtil;

/**
 * ForumThread is the aggregation root, be composed of roo message and its reply
 * messages.
 * <p>
 * root message is the aggregation root too.
 *
 * @author <a href="mailto:banq@163.com">banq</a>
 */
@Model
public class ForumThread {
	private final static Logger logger = LogManager.getLogger(ForumThread.class);
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
	private final RootMessage rootMessage;

	private Forum forum;
	// same as rootMessage's creationDate
	private String creationDate;
	// contain some abstract properties
	private Collection<Property> propertys;
	private ThreadTagsVO threadTagsVO;

	/**
	 * the root message of a thread. The root message is a special first message
	 * that is intimately tied to the thread for most forumViews. All other messages
	 * in the thread are children of the root message.
	 */
	
	private final ForumThreadState state;
	// update mutable
	private volatile ViewCounter viewCounter;
	// update mutable
	private volatile ForumThreadTreeModel forumThreadTreeModel;

	private long creationDate2;

	private ReBlogVO reBlogVO;	

	private AtomicReference<Boolean>  built ;

	private volatile String pinyinResultCache;

	/**
	 * normal can be cached reused
	 *
	 * @param rootMessage
	 */
	public ForumThread(RootMessage rootMessage, Long tIDInt) {
		
		this.rootMessage = rootMessage;
		this.threadId = tIDInt;
	
		this.state = new ForumThreadState(this);
		// this.threadTagsVO = new ThreadTagsVO(this, new ArrayList());
		this.propertys = new ArrayList<Property>();
		this.viewCounter = new ViewCounter(this);
		this.built = new AtomicReference<>(false);
	}

	// new ForumThread() is Banned
	private ForumThread() {
		this(null, Long.MAX_VALUE);
	}


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

	public Long getThreadId() {
		return threadId;
	}

	public String getName() {
		if (this.getRootMessage() == null) {
			logger.error("getName(): thread rootmessage is null" + threadId);
			return "null";
		}
		return this.getRootMessage().getMessageVO().getSubject();
	}

	// from threadForm getName calling
	public void setName(String name) {
		this.getRootMessage().updateSubject(name);
	}

	public void updateName(String name) {
		this.getRootMessage().updateSubject(name);
		eventSourcing.saveName(new ThreadNameRevisedEvent(this.getThreadId(), name));
	}

	public String getShortname() {
		return StringUtil.shorten(getName());
	}

	public Collection<Property> getPropertys() {
		return propertys;
	}

	public void setPropertys(Collection<Property> propertys) {
		this.propertys = propertys;
	}

	public ForumMessage getRootMessage() {
		if (rootMessage == null) {
			logger.error("getRootMessage: rootMessage is null! threadId:" + threadId);
		}
		return (ForumMessage)rootMessage;
	}

	// private void setRootMessage(ForumMessage rootMessage) {
	// 	if (rootMessage == null)
	// 		return;
	// 	if (rootMessage instanceof ForumMessageReply) {
	// 		logger.error("root message must be ForumMessage threadId=" + this.threadId + " messageId="
	// 				+ rootMessage.getMessageId());
	// 		return;
	// 	}
	// 	this.rootMessage = rootMessage;
	// }

	/**
	 * @return Returns the forumThreadState.
	 */
	public ForumThreadState getState() {
		return state;
	}

	public boolean isRoot(ForumMessage message) {
		try {
			if (message.getMessageId().longValue() == getRootMessage().getMessageId().longValue())
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * after inserted into DB, change the state, so it is Eventually consistent; in
	 * MessageListNav2Action, client will be waiting until Eventually consistent!
	 *
	 * @param forumMessageReply
	 */
	private synchronized void changeState(ForumMessageReply forumMessageReply) {
		this.state.setLatestPost(forumMessageReply);
		this.state.addMessageCount();
		this.forum.addNewMessage(forumMessageReply);
	}

	public void addNewMessage(ForumMessage forumMessageParent, ForumMessageReply forumMessageReply) {
		try {
			ForumMessage oldmessage = this.getState().getLatestPost();
			changeState(forumMessageReply);
			getForumThreadTreeModel().addChildAction(forumMessageReply);

			notifyLobby(forumMessageReply);

			Date olddate = Constants.parseDateTime(oldmessage.getCreationDate());
			if (Constants.timeAfter(1, olddate)) {// a pubsub per one hour
				subPublisherRole.subscriptionNotify(new ThreadSubscribedNotifyEvent(this.getThreadId()));
			}
		} catch (Exception e) {
			logger.error("error in forumThread:" + this.threadId + " " + e);
		}
	}

	private void notifyLobby(ForumMessageReply forumMessageReply) {
		Notification notification = new Notification();
		notification.setSource(new ForumMessageDTO(forumMessageReply));
		lobbyPublisherRole.notifyLobby(notification);

	}

	public Forum moveForum(ForumMessage forumMessage, Forum newForum) {
		if ((isRoot(forumMessage)) && (forumMessage.isLeaf())) {
			DomainMessage dm = this.lazyLoaderRole.loadForum(newForum.getForumId());
			newForum = (Forum) dm.getBlockEventResult();
			forumMessage.setForum(newForum);
			this.forum = newForum;

			eventSourcing
					.moveMessage(new MessageOwnershipChangedEvent(forumMessage.getMessageId(), newForum.getForumId()));
			dm.clear();
		}
		return newForum;
	}

	public void updateMessage(ForumMessage forumMessage) {		
		this.state.setLatestPost(forumMessage);
		this.forum.updateNewMessage(forumMessage);
	}

	public boolean isLeaf(ForumMessage forumMessage) {
		if (!built.get()) {
			logger.error("this thread is not embedded, threadId = " + threadId + " " + this.hashCode());
			return false;
		}

		boolean ret = false;
		try {
			TreeModel treeModel = getForumThreadTreeModel().getTreeModel();
			ret = treeModel.isLeaf(forumMessage.getMessageId());
		} catch (Exception e) {
			String error = e + " isLeaf forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
		}
		return ret;
	}

	public ForumThreadTreeModel getForumThreadTreeModel() {
		if (forumThreadTreeModel == null)
			forumThreadTreeModel = new ForumThreadTreeModel(threadId, this.lazyLoaderRole);
		return forumThreadTreeModel;
	}

	public ViewCounter addViewCount(String ip) {
		viewCounter.addViewCount(ip);
		return viewCounter;

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

	public void acceptTreeModelVisitor(long startMessageId, TreeVisitor treeVisitor) {
		getForumThreadTreeModel().acceptVisitor(startMessageId, treeVisitor);
	}

	public ThreadTagsVO getThreadTagsVO() {
		return threadTagsVO;
	}

	public Collection<ThreadTag> getTags() {
		return  (built.get())? this.threadTagsVO.getTags():new ArrayList<>();
	}

	public String getToken(){
		return (built.get())? this.threadTagsVO.getToken():"";
	}

	public String getPinyinToken(){
		if (pinyinResultCache != null) {
            return pinyinResultCache;
        }

        String token = getToken();
		if (token == null || token.trim().isEmpty()) {
            return "";
        }
        String pinyinResult = com.jdon.jivejdon.util.PinyinUtils.toPinyin(token);
        if (pinyinResult.startsWith("-")) {
            pinyinResultCache = pinyinResult;
        } else {
            pinyinResultCache = "-" + pinyinResult;
        }
		return pinyinResultCache;
    }

	public void changeTags(ThreadTagsVO threadTagsVO) {
		this.getThreadTagsVO().subscriptionNotify(threadTagsVO.getTags());
		this.threadTagsVO = threadTagsVO;
		this.pinyinResultCache = null;
	}

	public String[] getTagTitles() {
		return threadTagsVO.getTags().stream().map(ThreadTag::getTitle).toArray(String[]::new);
	}

	// /**
	//  * all dig for reply messages add to root Message
	//  * 
	//  * @param message
	//  */
	// public void addDig(ForumMessage message) {
	// 	if (message.getMessageId().longValue() != ((ForumMessage)rootMessage).getMessageId().longValue()) {
	// 		((ForumMessage)rootMessage).messaegDigAction();
	// 	}

	// }

	// not use the field modifiedDate in DB.
	public String getModifiedDate() {
		if (getState().getLatestPost() != null)
			return getState().getModifiedDate();
		else
			return this.creationDate;

	}

	public void delete(ForumMessage delforumMessage) {
		eventSourcing.deleteMessage(new MessageRemoveCommand(delforumMessage.getMessageId()));
		eventSourcing.delThread(new MessageRemovedEvent(delforumMessage));

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ForumThread forumThread = (ForumThread) o;
		if (this.threadId == null || forumThread.getThreadId() == null)
			return false;
		return this.threadId.longValue() == forumThread.getThreadId().longValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.threadId);
	}


	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void build(Forum forum, ThreadTagsVO threadTagsVO) {
		if (!built.get())
			synchronized (built) {
				if (!built.get()) {
					this.forum = forum;
					this.threadTagsVO = threadTagsVO;
					built.set(true);;
				}
			}
	}


	public ReBlogVO getReBlogVO() {
	    if (reBlogVO == null &&  lazyLoaderRole != null)
	        reBlogVO = new ReBlogVO(this);
	    return reBlogVO;
	}

	public void messaegDigAction(String ip) {
        this.getRootMessage().getMessagePropertysVO().addMessageDigCount(ip);
        // this.forumThread.addDig(this);
        eventSourcing.saveMessageProperties(
                new MessagePropertiesRevisedEvent(getRootMessage().getMessageId(), getRootMessage().getMessagePropertysVO().getPropertys()));

  
    }
}
