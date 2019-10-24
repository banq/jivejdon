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
package com.jdon.jivejdon.infrastructure.component.sitemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

@Component("sitemapRepository")
@Introduce("modelCache")
public class SitemapRepositoryImpl implements SitemapRepository {
	private final static Logger logger = LogManager.getLogger(SitemapRepositoryImpl.class);

	private JdbcTempSource jdbcTempSource;

	private PageIteratorSolver pageIteratorSolver;

	private Constants constants;

	public SitemapRepositoryImpl(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
	}

	public void clearCacheOfItem() {
		pageIteratorSolver.clearCache();
	}

	@Override
	public void insert(Url url) throws Exception {
		try {
			String sql = "INSERT INTO sitemap (id , url , name, creationDate) " + "VALUES (?, ?, ?, ?)";
			List queryParams = new ArrayList();
			queryParams.add(url.getUrlId());
			queryParams.add(url.getIoc());
			queryParams.add(url.getName());
			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			queryParams.add(saveDateTime);
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearCacheOfItem();

	}

	@Override
	public void update(Url url) throws Exception {
		try {
			String sql = "update sitemap set name=?, url=? where id=?";
			List queryParams = new ArrayList();
			queryParams.add(url.getName());
			queryParams.add(url.getIoc());
			queryParams.add(url.getUrlId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearCacheOfItem();

	}

	@Override
	public void delete(Url url) throws Exception {
		String sql = "delete from sitemap where id=?";
		List queryParams = new ArrayList();
		queryParams.add(url.getUrlId());
		jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		clearCacheOfItem();

	}

	@Around
	public Url getUrl(long Id) {
		String GET_FIELD = "select  * from sitemap where id = ?";
		List queryParams = new ArrayList();
		queryParams.add(Id);

		Url ret = null;

		try {
			List list = pageIteratorSolver.queryMultiObject(queryParams, GET_FIELD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = new Url();
				ret.setName((String) map.get("name"));
				ret.setIoc((String) map.get("url"));
				String saveDateTime = ((String) map.get("creationDate")).trim();
				String displayDateTime = constants.getDateTimeDisp(saveDateTime);
				ret.setCreationDate(displayDateTime);
				ret.setUrlId(Id);
			}
		} catch (Exception e) {
			logger.error("getUrl" + e);
		}
		return ret;
	}

	@Override
	public PageIterator getUrls(int start, int count) throws Exception {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from sitemap  ORDER BY creationDate DESC";
		String GET_ALL_ITEMS = "select id  from sitemap  ORDER BY creationDate DESC";
		return pageIteratorSolver.getDatas("", GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, start, count);

	}

	public boolean checkUrl(String ioc) {
		String GET_FIELD = "select  * from sitemap where url = ?";
		List queryParams = new ArrayList();
		queryParams.add(ioc);
		boolean ret = false;
		try {
			List list = pageIteratorSolver.queryMultiObject(queryParams, GET_FIELD);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = true;
			}
		} catch (Exception e) {
			logger.error("getUrl" + e);
		}
		return ret;
	}

}
