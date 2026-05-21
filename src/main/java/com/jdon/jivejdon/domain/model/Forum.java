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

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.spi.pubsub.publish.ThreadEventSourcingRole;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class Forum implements Serializable {
	private final static Logger logger = LogManager.getLogger(Forum.class);
    private static final long serialVersionUID = 1L;

    private Long forumId;
    private String name;
    private String description;
    private String creationDate;
    private long modifiedDate;
    private Collection propertys;

    private ForumState forumState;

    @Inject
    public LazyLoaderRole lazyLoaderRole;

    @Inject
    public SubPublisherRoleIF publisherRole;

    @Inject
    public ThreadEventSourcingRole eventSourcingRole;

    public Forum() {
        this.forumState = new ForumState(this);
    }

	public static Forum createDTO() {
        Forum dto = new Forum();
        dto.setForumState(null); // 明确将其业务状态置空，使其退化为纯数据对象
        return dto;
    }

    @OnCommand("postTopicMessageCommand")
    public void postMessage(PostTopicMessageCommand postTopicMessageCommand) {
        if (forumState == null) return;

        if (forumState.isRepeatedMessage(postTopicMessageCommand)) {
            logger.error("repeat message error: " + postTopicMessageCommand.getMessageVO().getSubject());
            return;
        }

        DomainMessage domainMessage = null;
        ForumMessage rootForumMessage = null;
        
        // 直接使用 forumState 内部维护的锁
        synchronized (forumState.getStateLock()) {
            domainMessage = forumState.saveTopicMessage(postTopicMessageCommand);
            if (domainMessage == null) return;
            
            rootForumMessage = (ForumMessage) domainMessage.getBlockEventResult();
            if (rootForumMessage != null) {
                forumState.threadPosted(rootForumMessage);
            }
        }
        
        if (rootForumMessage != null) {
            forumState.topicMessagePosted(rootForumMessage);
        }
    }

    public void addNewMessage(ForumMessageReply forumMessageReply) {
        if (forumState != null) {
            forumState.addNewMessageState(forumMessageReply);
        }
    }

    public void updateNewMessage(ForumMessage forumMessage) {
        if (forumState != null) {
            forumState.updateLatestPostState(forumMessage);
        }
    }

    // ==========================================
    // GETTERS & SETTERS (保持纯净)
    // ==========================================

    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getForumId() { return forumId; }
    public void setForumId(Long forumId) { this.forumId = forumId; }

    public long getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(long modifiedDate) { this.modifiedDate = modifiedDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Collection getPropertys() { return propertys; }
    public void setPropertys(Collection propertys) { this.propertys = propertys; }

    public ForumState getForumState() { return forumState; }
    public void setForumState(ForumState forumState) { this.forumState = forumState; }

    public boolean hasForumState() { return forumState != null; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forum forum = (Forum) o;
        if (forum.getForumId() == null || this.forumId == null) return false;
        return this.forumId.longValue() == forum.getForumId().longValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.forumId);
    }

}
