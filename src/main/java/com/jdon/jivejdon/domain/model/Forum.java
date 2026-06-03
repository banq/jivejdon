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
import java.util.Optional;

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

    	// === 内部标志：区分Entity和DTO ===
	private boolean isDTO = false;

    public Forum() {
        this.isDTO = true;
    }

    public static Forum createEntity() {
        Forum forum = new Forum();
        forum.isDTO = false;
        return forum;
    }

    public static Forum createDTO() {
        Forum dto = new Forum();
        dto.isDTO = true;
        return dto;
    }

    @OnCommand("postTopicMessageCommand")
    public void postMessage(PostTopicMessageCommand postTopicMessageCommand) {
        initOrGetState()
                .ifPresent(state -> handlePostMessage(state, postTopicMessageCommand));
    }

    private void handlePostMessage(ForumState state, PostTopicMessageCommand postTopicMessageCommand) {
        if (state.isRepeatedMessage(postTopicMessageCommand)) {
            logger.error("repeat message error: " + postTopicMessageCommand.getMessageVO().getSubject());
            return;
        }

        DomainMessage domainMessage = null;
        ForumMessage rootForumMessage = null;

        // 直接使用 ForumState 内部维护的锁
        synchronized (state.getStateLock()) {
            domainMessage = state.saveTopicMessage(postTopicMessageCommand);
            if (domainMessage == null) return;

            rootForumMessage = (ForumMessage) domainMessage.getBlockEventResult();
            if (rootForumMessage != null) {
                state.threadPosted(rootForumMessage);
            }
        }

        if (rootForumMessage != null) {
            state.topicMessagePosted(rootForumMessage);
        }
    }

    private Optional<ForumState> initOrGetState() {
        if (isDTO || this.lazyLoaderRole == null) {
            return Optional.empty();
        }
        if (forumState == null) {
            forumState = new ForumState(this);
        }
        return Optional.of(forumState);
    }

    public void addNewMessage(ForumMessageReply forumMessageReply) {
        initOrGetState()
                .ifPresent(state -> state.addNewMessageState(forumMessageReply));
    }

    public void updateNewMessage(ForumMessage forumMessage) {
        initOrGetState()
                .ifPresent(state -> state.updateLatestPostState(forumMessage));
    }

    public ForumMessage getLatestPost() {
        return initOrGetState()
                .map(ForumState::getLatestPost)
                .orElse(null);
    }

    public void loadinitState(){
        initOrGetState()
                .ifPresent(ForumState::loadinitState);
    }

    public void updateSubscriptionCount(int count) {
		initOrGetState().ifPresent(state -> state.updateSubscriptionCount(count));
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
