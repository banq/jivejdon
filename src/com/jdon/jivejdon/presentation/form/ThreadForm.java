/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.form;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThreadState;

public class ThreadForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long threadId;

	private String name;

	private String creationDate;

	private Collection propertys;

	private Forum forum;

	private ForumMessage rootMessage;

	private ForumThreadState state;

	private Collection tags;

	private boolean authenticated;

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if(addErrorIfStringEmpty(errors, "need subject", this.getName()))
			return;
	}

	public Long getThreadId() {
		return threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	public ForumThreadState getState() {
		return state;
	}

	public void setState(ForumThreadState state) {
		this.state = state;
	}

	public Collection getTags() {
		return tags;
	}

	public void setTags(Collection tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Collection getPropertys() {
		return propertys;
	}

	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public ForumMessage getRootMessage() {
		return rootMessage;
	}

	public void setRootMessage(ForumMessage rootMessage) {
		this.rootMessage = rootMessage;
	}

}
