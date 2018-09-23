/*
 * Copyright 2003-2009 the original author or authors.
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
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.event.domain.producer.read.LazyLoaderRole;
import com.jdon.jivejdon.model.util.LazyLoader;
import com.jdon.treepatterns.TreeNodeFactory;
import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;

/**
 * ForumThreadTreeModel lifecycle is not same as ForumThreadState
 * 
 * @author banq
 * 
 */
@Model
public class ForumThreadTreeModel extends LazyLoader {
	private final Long threadId;

	private final LazyLoaderRole lazyLoaderRole;

	private volatile TreeModel treeModel;

	public ForumThreadTreeModel(Long threadId, LazyLoaderRole lazyLoaderRole) {
		super();
		this.threadId = threadId;
		this.lazyLoaderRole = lazyLoaderRole;
	}

	/**
	 * blocking waiting TreeModel loaded
	 * 
	 * @return Returns the treeModel.
	 */
	public TreeModel getTreeModel() {
		if (treeModel == null)
			treeModel = (TreeModel) super.loadBlockedResult();
		else
			super.clear();
		return treeModel;
	}

	public DomainMessage getDomainMessage() {
		return lazyLoaderRole.loadTreeModel(threadId);
	}

	public void addChildAction(ForumMessageReply forumMessageReply) {
		if (treeModel != null)
			getTreeModel().addChild(forumMessageReply.getParentMessage().getMessageId(), forumMessageReply.getMessageId());
	}

	public Long getThreadId() {
		return threadId;
	}

	public void acceptVisitor(Long startNode, TreeVisitor treeVisitor) {
		TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(getTreeModel());
		TreeNodeVisitable treeNode = TreeNodeFactory.createNode(startNode);
		treeNode.accept(treeVisitor);
	}

}
