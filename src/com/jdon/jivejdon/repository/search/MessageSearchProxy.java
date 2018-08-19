package com.jdon.jivejdon.repository.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.config.ConfigurationException;
import org.compass.core.engine.SearchEngineException;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.query.MessageSearchSpec;
import com.jdon.jivejdon.repository.dao.sql.MessageUtilSQL;
import com.jdon.jivejdon.util.ThreadTimer;

public class MessageSearchProxy implements Startable, MessageSearchRepository {
	private final static Logger logger = LogManager.getLogger(MessageSearchProxy.class);
	private Compass compass;

	private final Map<Long, MessageSearchSpec> caches;

	private MessageUtilSQL messageUtilSQL;

	private ThreadTimer threadTimer;

	public MessageSearchProxy(MessageUtilSQL messageUtilSQL, ThreadTimer threadTimer) {
		this.caches = new ConcurrentHashMap();
		this.messageUtilSQL = messageUtilSQL;
		this.threadTimer = threadTimer;
	}

	// for directly invoked.
	public MessageSearchProxy(boolean rebuild) {
		this.caches = new ConcurrentHashMap();
		start();
	}

	public void start() {
		init();

	}

	public void stop() {
		this.caches.clear();
		this.compass.close();
		this.compass = null;
	}

	public void init() {
		try {
			logger.debug("compass init");
			CompassConfiguration config = new CompassAnnotationsConfiguration();
			config.setSetting(CompassEnvironment.CONNECTION, "/target/testindex");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.pre", "<font color=CC0033>");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.post", "</font>");
			config.setSetting("compass.engine.optimizer.schedule.period", "3600");
			config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
			config.addClass(com.jdon.jivejdon.model.Forum.class);
			config.addClass(com.jdon.jivejdon.model.message.MessageVO.class);
			compass = config.buildCompass();
			// compassTemplate = new CompassTemplate(compass);
		} catch (ConfigurationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (SearchEngineException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (CompassException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void createMessageTimer(ForumMessage forumMessage) {
		AppendMessageThread thread = new AppendMessageThread(forumMessage);
		thread.setMessageSearchProxy(this);
		threadTimer.offer(thread);
	}

	public void createMessageReplyTimer(ForumMessageReply forumMessageReply) {
		AppendMessageThread thread = new AppendMessageThread(forumMessageReply);
		thread.setMessageSearchProxy(this);
		threadTimer.offer(thread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#createMessage
	 * (com.jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	public void createMessage(ForumMessage forumMessage) {
		logger.debug("MessageSearchProxy.createMessage");
		if (forumMessage == null) {
			logger.error("forumMessage is null");
			return;
		}

		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(forumMessage);
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * createMessageReply(com.jdon.jivejdon.model.ForumMessageReply)
	 */
	@Override
	public void createMessageReply(ForumMessageReply forumMessageReply) {
		logger.debug("MessageSearchProxy.createMessageReply");
		if (forumMessageReply == null) {
			logger.error("forumMessageReply is null");
			return;
		}

		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(forumMessageReply);
			tx.commit();
		} catch (SearchEngineException ex) {

		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#updateMessage
	 * (com.jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	public void updateMessage(ForumMessage forumMessage) {
		logger.debug("MessageSearchProxy.updateMessage");
		if (forumMessage == null) {
			logger.error("forumMessage is null");
			return;
		}

		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, forumMessage.getMessageId());
			MessageVO messageVO = new MessageVO();
			MessageVO messageVOClone = forumMessage.getMessageVOClone();
			messageVO.setSubject(messageVOClone.getSubject());
			messageVO.setBody(messageVOClone.getBody());
			messageVO.setTagTitle(messageVOClone.getTagTitle());
			messageS.setMessageVO(messageVO);
			tx = session.beginTransaction();
			session.save(messageS);
			tx.commit();
		} catch (SearchEngineException ex) {

		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#deleteMessage
	 * (java.lang.Long)
	 */
	@Override
	public void deleteMessage(Long forumMessageId) {
		logger.debug("MessageSearchProxy.deleteMessage");
		if (forumMessageId == null) {
			logger.error("forumMessageId is null");
			return;
		}

		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, forumMessageId);
			session.delete(messageS);
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#find(java
	 * .lang.String, int, int)
	 */
	public Collection find(String query, int start, int count) {
		logger.debug("MessageSearchProxy.find");
		Collection result = new ArrayList();
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		MessageSearchSpec messageSearchSpec = null;
		CompassHits hits = null;
		try {
			tx = session.beginTransaction();
			hits = session.find(query);
			logger.debug("Found [" + hits.getLength() + "] hits for [" + query + "] query");
			int end = start + count;
			if (end >= hits.getLength())
				end = hits.getLength();

			for (int i = start; i < end; i++) {
				logger.debug("create  messageSearchSpec collection");
				ForumMessage smessage = (ForumMessage) hits.data(i);
				messageSearchSpec = new MessageSearchSpec();
				messageSearchSpec.setMessageId(smessage.getMessageId());

				MessageVO mVO = new MessageVO();
				String body = hits.highlighter(i).fragment("body");
				mVO.setBody(body);
				String subject = hits.highlighter(i).fragment("subject");
				mVO.setSubject(subject);
				messageSearchSpec.setMessageVO(mVO);

				// String tagTitle[] =
				// hits.highlighter(i).fragments("tagTitle");
				// messageSearchSpec.setTagTitle(tagTitle);
				messageSearchSpec.setResultAllCount(hits.getLength());
				result.add(messageSearchSpec);
			}
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (hits != null)
				hits.close();
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * findThreadsAllCount(java.lang.String)
	 */
	public int findThreadsAllCount(String query) {
		logger.debug("findThreadsAllCount.find");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		int allCount = 0;
		try {
			tx = session.beginTransaction();
			CompassHits hits = session.find(query);
			allCount = hits.getLength();
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return allCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#findThread
	 * (java.lang.String, int, int)
	 */
	public Collection findThread(String query, int start, int count) {
		logger.debug("MessageSearchProxy.find");
		Collection result = new ArrayList();
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		MessageSearchSpec messageSearchSpec = null;
		try {
			tx = session.beginTransaction();
			CompassHits hits = session.find(query);
			logger.debug("Found [" + hits.getLength() + "] hits for [" + query + "] query");
			start = getNewStart(hits, start);
			int j = start;
			for (int i = start; j < start + count; i++) {
				if (i >= hits.getLength())
					break;
				logger.debug("create  messageSearchSpec collection");
				ForumMessage smessage = (ForumMessage) hits.data(i);
				messageSearchSpec = getMessageSearchSpec(smessage.getMessageId());
				if (messageSearchSpec.isRoot()) {
					messageSearchSpec.setMessageId(smessage.getMessageId());

					MessageVO mVO = new MessageVO();
					String body = hits.highlighter(i).fragment("body");
					mVO.setBody(body);
					String subject = hits.highlighter(i).fragment("subject");
					mVO.setSubject(subject);
					messageSearchSpec.setMessageVO(mVO);

					messageSearchSpec.setResultAllCount(hits.getLength());
					result.add(messageSearchSpec);
					j++;
				}
			}
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return result;
	}

	private int getNewStart(CompassHits hits, int end) {
		int newStart = 0;
		int j = 0;
		MessageSearchSpec messageSearchSpec;
		for (int i = 0; j < end; i++) {
			newStart = i;
			if (i >= hits.getLength())
				break;
			logger.debug("create  messageSearchSpec collection");
			ForumMessage smessage = (ForumMessage) hits.data(i);
			messageSearchSpec = getMessageSearchSpec(smessage.getMessageId());
			if (messageSearchSpec.isRoot()) {
				j++;
			}

		}
		return newStart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * getMessageSearchSpec(java.lang.Long)
	 */
	public MessageSearchSpec getMessageSearchSpec(Long messageId) {
		MessageSearchSpec mss = caches.get(messageId);
		if ((mss == null)) {
			mss = new MessageSearchSpec();
			boolean isRoot = messageUtilSQL.isRoot(messageId);
			mss.setRoot(isRoot);
			if (caches.size() > 100)
				caches.clear();
			caches.put(messageId, mss);
		}
		return mss;
	}

}
