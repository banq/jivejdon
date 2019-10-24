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
package com.jdon.jivejdon.domain.model.realtime;

import java.util.Date;

import com.jdon.annotation.Model;

@Model
public class Notification {

	private long id;
	private long sourceId;
	private Object source;
	private String subject;
	private String content;
	private Date createDate;

	private Date deadDate;
	// 0: for All
	private int deliverLevel;

	private int scopeSeconds;

	public Notification() {

		this.createDate = new Date();
		this.id = this.hashCode() + createDate.getTime();

		//
		this.scopeSeconds = 60;
		setScopeSeconds(scopeSeconds);
	}

	public long getId() {
		return id;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}

	public int getDeliverLevel() {
		return deliverLevel;
	}

	public void setDeliverLevel(int deliverLevel) {
		this.deliverLevel = deliverLevel;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public int getScopeSeconds() {
		return scopeSeconds;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setScopeSeconds(int scopeSeconds) {
		this.scopeSeconds = scopeSeconds;
		long deadDateL = createDate.getTime() + scopeSeconds * 1000;
		this.deadDate = new Date(deadDateL);
	}

}
