package com.jdon.jivejdon.infrastructure.repository.builder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.infrastructure.repository.dao.ForumDao;

public class ForumDirector {
	private final static Logger logger = LogManager.getLogger(ForumDirector.class);

	private final ForumDao forumDao;
	
	public ForumDirector(ForumDao forumDao) {
		this.forumDao = forumDao;
	}

	private final ConcurrentHashMap<Long, FutureTask<Forum>> inflightAccounts =
        new ConcurrentHashMap<>();

	private Forum loadForum(Long forumId) {
		FutureTask<Forum> task = new FutureTask<>(() -> {
			return forumDao.getForum(forumId);
		});

		FutureTask<Forum> existing = inflightAccounts.putIfAbsent(forumId, task);

		if (existing == null) {
			existing = task;
			task.run();
		}

		try {
			return existing.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			inflightAccounts.remove(forumId, existing);
		}
	}

	
	public Forum getForum(Long forumId) {
		logger.debug(" enter getForum for forumId=" + forumId);
		if (forumId == null)
			return null;
		// 使用 computeIfAbsent 确保每个 forumId 只创建一个实例，避免并发重复创建
		return loadForum(forumId);

	}
	

}
