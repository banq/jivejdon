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

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.domain.model.subscription.SubscribedState;
import com.jdon.jivejdon.domain.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;

/**
 * Forum State ValueObject this is a embeded class in Forum.
 * 
 * state is a value Object of DDD, and is immutable
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumState  {
	private final AtomicLong threadCount;
    private final AtomicLong messageCount;
    private final AtomicReference<ForumMessage> latestPost;

    private final Forum forum;
    private final Object loadInitStateLock = new Object();
    
    // 状态对象内部自己的锁，替代原先 Forum 的本地锁逻辑
    private final Object stateLock = new Object();

    private SubscribedState subscribedState;

    public ForumState(Forum forum) {
        super();
        this.forum = forum;
        this.messageCount = new AtomicLong(Integer.MIN_VALUE);
        this.threadCount = new AtomicLong(Integer.MIN_VALUE);
        this.latestPost = new AtomicReference<>();
    }

    /**
     * 暴露内部的锁对象给 Forum 里的同步块使用
     */
    public Object getStateLock() {
        return this.stateLock;
    }

    /**
     * 判断是否为重复提交的消息
     */
    public boolean isRepeatedMessage(PostTopicMessageCommand postTopicMessageCommand) {
        if (this.getLatestPost() == null)
            return false;
        return this.getLatestPost().isSubjectRepeated(postTopicMessageCommand.getMessageVO().getSubject());
    }

    /**
     * 处理新主题贴发布成功后的计数与最新消息更新 (内部直接使用自己的锁)
     */
    public void threadPosted(ForumMessage rootForumMessage) {
        synchronized (stateLock) {
            this.addThreadCount();
            this.setLatestPost(rootForumMessage);
        }
        this.subscriptionNotify(rootForumMessage);
    }

    /**
     * 处理新回复消息时的计数更新 (由原 Forum.addNewMessage 迁移至此)
     */
    public void addNewMessageState(ForumMessageReply forumMessageReply) {
        synchronized (stateLock) {
            this.addMessageCount();
            this.setLatestPost(forumMessageReply);
        }
    }

    /**
     * 更新最新消息 (由原 Forum.updateNewMessage 迁移至此)
     */
    public void updateLatestPostState(ForumMessage forumMessage) {
        synchronized (stateLock) {
            this.setLatestPost(forumMessage);
        }
    }

    // =========================================================================
    // 依赖注入角色的调用
    // =========================================================================

    public DomainMessage saveTopicMessage(PostTopicMessageCommand postTopicMessageCommand) {
        if (forum == null || forum.eventSourcingRole == null)
            return null;
        return forum.eventSourcingRole.saveTopicMessage(postTopicMessageCommand);
    }

    public void topicMessagePosted(ForumMessage rootForumMessage) {
        if (forum == null || forum.eventSourcingRole == null)
            return;
        forum.eventSourcingRole.topicMessagePosted(new TopicMessagePostedEvent(rootForumMessage));
    }

    public void subscriptionNotify(ForumMessage forumMessage) {
        if (forum != null && forum.publisherRole != null) {
            forum.publisherRole.subscriptionNotify(new ForumSubscribedNotifyEvent(forum.getForumId(), forumMessage));
        }
    }

    public void loadinitState() {
        synchronized (loadInitStateLock) {
            if (messageCount.get() != Integer.MIN_VALUE)
                return;
            
            if (forum == null || forum.lazyLoaderRole == null) 
                return;

            DomainMessage dm = forum.lazyLoaderRole.loadForumState(forum.getForumId());
            OneOneDTO oneOneDTO = null;
            try {
                oneOneDTO = (OneOneDTO) dm.getEventResult();
                if (oneOneDTO != null) {
                    OneOneDTO oneOneDTO2 = (OneOneDTO) oneOneDTO.getParent();
                    if (oneOneDTO2 != null) {
                        threadCount.set((Long) oneOneDTO.getChild());
                        latestPost.set((ForumMessage) oneOneDTO2.getParent());
                        messageCount.set((Long) oneOneDTO2.getChild());
                    }
                    dm.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // 基础状态访问器
    // ==========================================

    public int getMessageCount() {
        if (messageCount.get() == Integer.MIN_VALUE)
            synchronized (messageCount) {
                if (messageCount.get() == Integer.MIN_VALUE)
                    loadinitState();
            }
        return (int) messageCount.get();
    }

    public long addMessageCount() {
        getMessageCount();
        return this.messageCount.incrementAndGet();
    }

    public int getThreadCount() {
        if (threadCount.longValue() == Integer.MIN_VALUE)
            synchronized (threadCount) {
                if (threadCount.longValue() == Integer.MIN_VALUE)
                    loadinitState();
            }
        return threadCount.intValue();
    }

    public long addThreadCount() {
        getThreadCount();
        return this.threadCount.incrementAndGet();
    }

    public ForumMessage getLatestPost() {
        if (this.latestPost.get() == null)
            synchronized (latestPost) {
                if (this.latestPost.get() == null)
                    loadinitState();
            }
        return latestPost.get();
    }

    public void setLatestPost(ForumMessage forumMessage) {
        getLatestPost();
        this.latestPost.set(forumMessage);
    }

    public Forum getForum() { return forum; }

    public void updateSubscriptionCount(int count) {
        getSubscribedState().update(count);
    }

    public SubscribedState getSubscribedState() {
        if (subscribedState == null && forum != null && forum.getForumId() != null)
            subscribedState = new SubscribedState(new ForumSubscribed(forum.getForumId()));
        return subscribedState;
    }

    public String getModifiedDate() {
        if (getLatestPost() != null)
            return getLatestPost().getModifiedDate();
        else
            return "";
    }
}
