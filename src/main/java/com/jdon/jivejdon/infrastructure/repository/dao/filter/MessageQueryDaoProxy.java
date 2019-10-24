/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.infrastructure.repository.dao.filter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.util.CachedCollection;
import com.jdon.jivejdon.infrastructure.repository.query.MessagePageIteratorSolver;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.MessageQueryDaoSql;
import com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository;

/**
 * @author banq(http://www.jdon.com)
 * 
 */

public class MessageQueryDaoProxy extends MessageQueryDaoSql implements Startable {

	public static final String Thread_Name = "Thread_Name";

	private MessageSearchRepository messageSearchProxy;

	private final Map<String, CachedCollection> caches;

	public MessageQueryDaoProxy(JdbcTempSource jdbcTempSource, MessagePageIteratorSolver messagePageIteratorSolver, AccountDao accountDao,
			MessageSearchRepository messageSearchProxy) {
		super(jdbcTempSource, messagePageIteratorSolver);
		this.messageSearchProxy = messageSearchProxy;
		caches = new ConcurrentHashMap();
	}

	/**
	 * 
	 * public ForumThreadState getForumThreadState(ForumThread forumThread){
	 * ForumThreadState forumThreadState = (ForumThreadState)
	 * containerUtil.getModelFromCache(forumThread.getThreadId(),
	 * ForumThreadState.class); if ((forumThreadState == null) ||
	 * (forumThreadState.isModified())){ forumThreadState =
	 * super.getForumThreadState(forumThread);
	 * containerUtil.addModeltoCache(forumThread.getThreadId(),
	 * forumThreadState); } return forumThreadState; }
	 */

	public Collection find(String query, int start, int count) {
		return messageSearchProxy.find(query, start, count);
	}

	private String getSearchKey(String name, String query, int start, int count) {
		StringBuilder sb = new StringBuilder(name);
		sb.append(query).append(start).append(count);
		return sb.toString().intern();
	}

	public Collection findThread(String query, int start, int count) {
		String searchKey = getSearchKey(Thread_Name, query, start, count);
		CachedCollection cc = caches.get(searchKey);
		if (cc == null) {
			Collection list = messageSearchProxy.findThread(query, start, count);
			cc = new CachedCollection(Thread_Name, list);
			if (caches.size() > 100)
				caches.clear();
			caches.put(searchKey, cc);
		}
		return cc.getList();
	}

	public int findThreadsAllCount(String query) {
		Integer allCount = messageSearchProxy.findThreadsAllCount(query);
		return allCount.intValue();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		caches.clear();

	}

}
