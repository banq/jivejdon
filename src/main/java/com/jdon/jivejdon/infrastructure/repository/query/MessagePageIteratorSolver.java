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

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.PageIteratorSolverFixed;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * CQRS query Manager.
 * 
 * MessageQueryDao share this class, so share one PageIteratorSolver. when
 * create/update happened in MessageDaoSql, it can clear the cache in
 * PageIteratorSolverFixed that shared with MessageQueryDaoSql, there are many batch
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

	private final ConcurrentMap<String, PageIteratorSolverFixed> permanentsolvers;

	/**
	 * @param pageIteratorSolver
	 * @param containerUtil
	 */
	public MessagePageIteratorSolver(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {
		this.jdbcTempSource = jdbcTempSource;
		this.containerUtil = containerUtil;
		this.permanentsolvers = new ConcurrentHashMap<>();

	}

	@Override
	public void start() {


	}



	public void clearPerm() {
		for (PageIteratorSolverFixed pageIteratorSolver : permanentsolvers.values()) {
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
	public PageIteratorSolverFixed getPageIteratorSolver(String key) {
		return permanentsolvers.computeIfAbsent(key, k->new PageIteratorSolverFixed(jdbcTempSource.getDataSource(), containerUtil.getCacheManager()));
		// return new PageIteratorSolverFixed(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());

	}

	public void clearPageIteratorSolver(String key) {
		PageIteratorSolverFixed pageIteratorSolver = (PageIteratorSolverFixed) permanentsolvers.get(key);
		if (pageIteratorSolver != null)
			pageIteratorSolver.clearCache();
		permanentsolvers.remove(key);
	}

	public void clearPageIteratorSolver() {
		permanentsolvers.clear();
	}

	

	@Override
	public void stop() {
		
		
		jdbcTempSource = null;
	}

}
