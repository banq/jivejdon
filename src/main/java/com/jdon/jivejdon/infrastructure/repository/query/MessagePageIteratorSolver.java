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
package com.jdon.jivejdon.infrastructure.repository.query;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * CQRS query Manager.
 * 
 * MessageQueryDao share this class, so share one PageIteratorSolver. when
 * create/update happened in MessageDaoSql, it can clear the cache in
 * PageIteratorSolver that shared with MessageQueryDaoSql, there are many batch
 * inquiry operations in MessageQueryDaoSql. so, that will refresh the result of
 * batch inquiry
 * 
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
@Component
public class MessagePageIteratorSolver implements Startable {

	private JdbcTempSource jdbcTempSource;

	private ContainerUtil containerUtil;

	private final ConcurrentMap<String, PageIteratorSolver> permanentsolvers;

	/**
	 * @param pageIteratorSolver
	 * @param containerUtil
	 */
	public MessagePageIteratorSolver(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {
		this.jdbcTempSource = jdbcTempSource;
		this.containerUtil = containerUtil;
		this.permanentsolvers = new ConcurrentHashMap<String, PageIteratorSolver>();

	}

	@Override
	public void start() {


	}



	public void clearPerm() {
		for (PageIteratorSolver pageIteratorSolver : permanentsolvers.values()) {
			pageIteratorSolver.clearCache();
		}
		permanentsolvers.clear();
	}

	/**
	 * @return Returns the containerUtil.
	 */
	public ContainerUtil getContainerUtil() {
		return containerUtil;
	}

	/**
	 * @param containerUtil
	 *            The containerUtil to set.
	 */
	public void setContainerUtil(ContainerUtil containerUtil) {
		this.containerUtil = containerUtil;
	}

	/**
	 * @return Returns the pageIteratorSolver.
	 */
	public PageIteratorSolver getPageIteratorSolver(String key) {
		PageIteratorSolver pageIteratorSolver = (PageIteratorSolver) permanentsolvers.get(key);
		if (pageIteratorSolver == null) {
			synchronized (this.permanentsolvers) {
				if (pageIteratorSolver == null) {
					pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
					permanentsolvers.put(key, pageIteratorSolver);
				}
			}
		}
		return pageIteratorSolver;
	}

	public void clearPageIteratorSolver(String key) {
		PageIteratorSolver pageIteratorSolver = (PageIteratorSolver) permanentsolvers.get(key);
		if (pageIteratorSolver != null)
			pageIteratorSolver.clearCache();
		permanentsolvers.remove(key);
	}

	

	@Override
	public void stop() {
		
			try {
				clearPerm();
				ScheduledExecutorUtil.scheduExecStatic.shutdownNow();
			} catch (Exception e) {
			}
		jdbcTempSource = null;
	}

}
