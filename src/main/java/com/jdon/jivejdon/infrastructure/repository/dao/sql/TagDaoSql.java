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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.PageIteratorSolverFixed;
import com.jdon.model.query.block.Block;

public class TagDaoSql implements TagDao {
	private final static Logger logger = LogManager.getLogger(TagDaoSql.class);

	private final PageIteratorSolverFixed pageIteratorSolverTag;

	private final PageIteratorSolverFixed pageIteratorSolverThreadTag;

	private final JdbcTempSource jdbcTempSource;

	private final SequenceDao sequenceDao;

	public TagDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, SequenceDao sequenceDao) {
		this.pageIteratorSolverTag = new PageIteratorSolverFixed(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.pageIteratorSolverThreadTag = new PageIteratorSolverFixed(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.sequenceDao = sequenceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#createTag(com.jdon.jivejdon.domain.model
	 * .ThreadTag)
	 */
	public void createTag(ThreadTag threadTag) throws Exception {
		try {
			String ADD_SQL = "INSERT INTO tag(tagID, title, assonum)" + " VALUES (?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(threadTag.getTagID());
			queryParams.add(threadTag.getTitle());
			queryParams.add(threadTag.getAssonum());

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	
	public void delThreadTag(Long threadID) throws Exception {
		String SQL = "DELETE FROM threadTag WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
	}


	public void addThreadTag(Long tagID, Long threadID) throws Exception {
		String SQL = "INSERT INTO threadTag(threadTagID, threadID, tagID)" + " VALUES (?,?,?)";
		List queryParams = new ArrayList();
		Long threadTagID = sequenceDao.getNextId(Constants.OTHERS);
		queryParams.add(threadTagID);
		queryParams.add(threadID);
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
		pageIteratorSolverThreadTag.clearCache();
	}

	
	public void delThreadTag(Long tagID, Long threadID) throws Exception {
		String SQL = "DELETE FROM threadTag WHERE threadID=? and tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
	}

	public void deleteTag(Long tagID) throws Exception {
		String SQL = "DELETE FROM tag WHERE  tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);

		String SQL2 = "DELETE FROM threadTag WHERE  tagID =?";
		List queryParams2 = new ArrayList();
		queryParams2.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams2, SQL2);

	}

	public boolean checkThreadTagRelation(Long tagID, Long threadID) {
		String LOAD_SQL = "SELECT threadTagID FROM threadTag WHERE threadID=? and tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		queryParams.add(tagID);
		boolean ret = false;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = true;
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}



	public Collection<Long> getThreadTagIDs(Long threadID) {
		String SQL = "select tagID FROM threadTag WHERE threadID=? ";
		List<Long> queryParams = new ArrayList<>();
		queryParams.add(threadID);
		Collection<Long> ret = new ArrayList<>();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret.add((Long) map.get("tagID"));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public void createThreadToken(Long threadID, String token) throws Exception {
		try {
			String ADD_SQL = "INSERT INTO token(threadID, token)" + " VALUES (?,?)";
			List queryParams = new ArrayList();
			queryParams.add(threadID);
			queryParams.add(token);
			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void delThreadToken(Long threadID) throws Exception {
		String SQL = "DELETE FROM token WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
	}

	public String getThreadToken(Long threadID){
		String LOAD_SQL = "SELECT token FROM token WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		String ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = (String) map.get("token");
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.sql.TagDao#getThreadTag(java.lang.Long)
	 */
	public ThreadTag getThreadTag(Long tagID) {
		logger.debug("enter getThreadTag for tagID:" + tagID);
		String LOAD_SQL = "SELECT tagID, title, assonum FROM tag WHERE tagID=?";
		List queryParams = new ArrayList();
		queryParams.add(tagID);
		ThreadTag ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = new ThreadTag();
				Map map = (Map) iter.next();
				ret.setTagID((Long) map.get("tagID"));
				ret.setTitle((String) map.get("title"));
				ret.setAssonum((Integer) map.get("assonum"));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public ThreadTag getThreadTagByTitle(String title) {
		logger.debug("enter getThreadTagByTitle for title:" + title);
		String LOAD_SQL = "SELECT tagID, title, assonum FROM tag WHERE title =?";
		List queryParams = new ArrayList();
		queryParams.add(title);
		ThreadTag ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = new ThreadTag();
				Map map = (Map) iter.next();
				ret.setTagID((Long) map.get("tagID"));
				ret.setTitle((String) map.get("title"));
				ret.setAssonum((Integer) map.get("assonum"));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public Collection<Long> searchTitle(String s) {
		logger.debug("enter searchTitle for title:" + s);
		// String LOAD_SQL = "SELECT title FROM tag WHERE locate('"+ s
		// +"', title) > 0 order " +
		// "by locate('" + s + "', title), title limit 50";
		String LOAD_SQL = "SELECT tagID FROM tag WHERE title like '%" + s + "%'";
		Collection<Long> ret = new ArrayList<Long>();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(new ArrayList(), LOAD_SQL);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret.add((Long) map.get("tagID"));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.sql.TagDao#getThreadTags(int, int)
	 */
	public PageIterator getThreadTags(int start, int count) {
        logger.debug("enter getThreadTags ..");
        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from tag";
        String GET_ALL_ITEMS = "select tagID from tag order by assonum DESC LIMIT ?, ?";

        List<Object> countParams = new ArrayList<>(); // no params for count
        Integer allCount = 0;
        try {
            Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
            if (allCounto instanceof Long)
                allCount = ((Long) allCounto).intValue();
            else
                allCount = ((Integer) allCounto).intValue();
        } catch (Exception e) {
            logger.error("getThreadTags count error: " + e);
            return new PageIterator();
        }

        List<Object> params = new ArrayList<>();
        params.add(start);
        params.add(count);
        List<Long> tagIDs = new ArrayList<>();
        try {
            List<Map<String, Object>> list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
            for (Map<String, Object> map : list) {
                tagIDs.add((Long) map.get("tagID"));
            }
        } catch (Exception e) {
            logger.error("getThreadTags list error: " + e);
            return new PageIterator();
        }
        return new PageIterator(allCount, tagIDs.toArray());
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.sql.TagDao#getTaggedThread(java.lang.Long,
	 * int, int)
	 */
	public PageIterator getTaggedThread(Long tagID, int start, int count) {
        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from threadTag where tagID =?";
        String GET_ALL_ITEMS = "select threadID from threadTag where tagID=? order by threadID DESC LIMIT ?, ?";

        List<Object> countParams = new ArrayList<>();
        countParams.add(tagID);
        Integer allCount = 0;
        try {
            Object allCounto = jdbcTempSource.getJdbcTemp().querySingleObject(countParams, GET_ALL_ITEMS_ALLCOUNT);
            if (allCounto instanceof Long)
                allCount = ((Long) allCounto).intValue();
            else
                allCount = ((Integer) allCounto).intValue();
        } catch (Exception e) {
            logger.error("getTaggedThread count error: " + e);
            return new PageIterator();
        }

        List<Object> params = new ArrayList<>();
        params.add(tagID);
        params.add(start);
        params.add(count);
        List<Long> threadIDs = new ArrayList<>();
        try {
            List<Map<String, Object>> list = jdbcTempSource.getJdbcTemp().queryMultiObject(params, GET_ALL_ITEMS);
            for (Map<String, Object> map : list) {
                threadIDs.add((Long) map.get("threadID"));
            }
        } catch (Exception e) {
            logger.error("getTaggedThread list error: " + e);
            return new PageIterator();
        }
        return new PageIterator(allCount, threadIDs.toArray());
    }


	public List<Long> getTaggedThread(Long tagID) {
		String LOAD_SQL = "SELECT threadID FROM threadTag where tagID=? order by threadID DESC";
		List queryParams = new ArrayList();
		queryParams.add(tagID);
		List<Long> ret = new ArrayList<>();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret.add((Long) map.get("threadID"));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public int getThreadTagCount(Long tagID) {
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from threadTag where tagID =? ";
		List queryParams = new ArrayList();
		queryParams.add(tagID);
		ThreadTag ret = null;
		try {
			Object result = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, GET_ALL_ITEMS_ALLCOUNT);
			return (Integer)result;
		} catch (Exception se) {
			logger.error(se);
		}
		return 0;
	}

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#updateTag(com.jdon.jivejdon.domain.model
	 * .ThreadTag)
	 */
	public void updateTag(ThreadTag threadTag) throws Exception {
		String SQL = "UPDATE tag SET title=?, assonum=? WHERE tagID=?";

		List queryParams = new ArrayList();
		queryParams.add(threadTag.getTitle());
		queryParams.add(threadTag.getAssonum());
		queryParams.add(threadTag.getTagID());

		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
	}

}
