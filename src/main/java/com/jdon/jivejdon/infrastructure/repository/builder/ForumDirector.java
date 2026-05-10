package com.jdon.jivejdon.infrastructure.repository.builder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.infrastructure.repository.dao.ForumDao;

public class ForumDirector {
	private final static Logger logger = LogManager.getLogger(ForumDirector.class);

	private final ForumDao forumDao;
	
	// 缓存 Forum 实例，防止并发创建多个相同 forumId 的实例
	private final ConcurrentMap<Long, Forum> forumCache = new ConcurrentHashMap<>();

	public ForumDirector(ForumDao forumDao) {
		this.forumDao = forumDao;
	}

	public Forum getForum(Long forumId) {
		logger.debug(" enter getForum for forumId=" + forumId);
		if (forumId == null)
			return null;
		try {
			// 使用 computeIfAbsent 确保每个 forumId 只创建一个实例，避免并发重复创建
			return forumCache.computeIfAbsent(forumId, this::loadForum);
		} catch (Exception e) {
			logger.error("Error getting forum for forumId=" + forumId, e);
			return null;
		}
	}
	
	private Forum loadForum(Long forumId) {
		return forumDao.getForum(forumId);
	}

}
