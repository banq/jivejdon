package com.jdon.jivejdon.service.imp.message;

import com.jdon.annotation.Service;
import com.jdon.annotation.Singleton;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.manager.query.HotThreadQueryManager;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.MessageSearchSpec;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.visitor.TreeNodePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Singleton
@Service("forumMessageQueryService")
public class ForumMessageQueryServiceImp implements ForumMessageQueryService {
	private final static Logger logger = LogManager.getLogger(ForumMessageQueryServiceImp.class);

	protected final MessageQueryDao messageQueryDao;

	protected final AccountFactory accountFactory;

	protected final HotThreadQueryManager queryManager;

	protected final ForumFactory forumBuilder;

	protected final ForumAbstractFactory forumAbstractFactory;

	public ForumMessageQueryServiceImp(MessageQueryDao messageQueryDaoy, AccountFactory accountFactory, HotThreadQueryManager queryManager,
			ForumFactory forumBuilder, ForumAbstractFactory forumAbstractFactory) {
		this.accountFactory = accountFactory;
		this.queryManager = queryManager;
		this.messageQueryDao = messageQueryDaoy;
		this.forumBuilder = forumBuilder;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public ForumMessage getMessage(Long messageId) {
		if (messageId == null)
			return null;
		return forumAbstractFactory.getMessage(messageId);

	}

	/**
	 * get a message Collection of the parentMessage. we use TreeModel
	 * implements it .
	 */
	public PageIterator getRecursiveMessages(Long messageId, int start, int count) {
		logger.debug("enter getRecursiveMessages");
		logger.debug("enter getRecursiveMessages, start=" + start + " count=" + count);
		ForumMessage forumMessage = forumBuilder.getMessage(messageId);
		if (forumMessage == null) {
			logger.error("the messageId  don't existed: " + messageId);
			return new PageIterator();
		}
		List sublist = null;
		try {
			List childernList = getRecursiveChildren(forumMessage);
			// 2. get a sub list from the all List by start and count
			logger.debug(" get the sub-collection for start=" + start + " childernList size" + childernList.size());
			int end = start + count;
			sublist = childernList.subList(start, (end < childernList.size()) ? end : childernList.size());
			return new PageIterator(childernList.size(), sublist.toArray());
		} catch (Exception e) {
			logger.error(e);
		}
		return new PageIterator();
	}

	protected List getRecursiveChildren(ForumMessage forumMessage) {
		List list = null;
		try {
			TreeVisitor messagePicker = new TreeNodePicker();
			forumMessage.getForumThread().acceptTreeModelVisitor(forumMessage.getMessageId(), messagePicker);
			list = ((TreeNodePicker) messagePicker).getResult();
			list.remove(forumMessage.getMessageId()); // remove the parent
		} catch (Exception e) {
			logger.error(e);
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.ForumMessageService#getMessages(java.lang.String
	 * , int, int)
	 */
	public PageIterator getMessages(Long threadId, int start, int count) {
		logger.debug("enter getMessages");
		if ((threadId == null) || (threadId.longValue() == 0))
			return new PageIterator();
		return messageQueryDao.getMessages(threadId, start, count);
	}

	public PageIterator getMessages(int start, int count) {
		return messageQueryDao.getMessages(start, count);
	}

	public PageIterator searchMessages(String query, int start, int count) {
		logger.debug("enter searchMessages");
		PageIterator pi = new PageIterator();
		try {
			List messageSearchSpecs = (List) messageQueryDao.find(query, start, count);
			int allCount = 0;
			if (messageSearchSpecs.size() > 0) {
				logger.debug("enter package  PageIterator");
				Iterator iter = messageSearchSpecs.iterator();
				while (iter.hasNext()) {
					MessageSearchSpec mss = (MessageSearchSpec) iter.next();
					ForumMessage message = forumBuilder.getMessage(mss.getMessageId());
					mss.setMessage(message);
					allCount = mss.getResultAllCount();
				}
				pi = new PageIterator(allCount, messageSearchSpecs.toArray());
				// this will let ModelListAction not call getmessageSearchSpecs
				// method
				pi.setElementsTypeIsKey(false);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return pi;
	}

	public PageIterator searchThreads(String query, int start, int count) {
		logger.debug("enter searchThreads");
		PageIterator pi = new PageIterator();
		try {
			List messageSearchSpecs = (List) messageQueryDao.findThread(query, start, count);
			int allCount = 0;
			if (messageSearchSpecs.size() > 0) {
				logger.debug("enter package  PageIterator");
				Iterator iter = messageSearchSpecs.iterator();
				while (iter.hasNext()) {
					MessageSearchSpec mss = (MessageSearchSpec) iter.next();
					ForumMessage message = forumBuilder.getMessage(mss.getMessageId());
					mss.setMessage(message);
					allCount = mss.getResultAllCount();
				}
				pi = new PageIterator(allCount, messageSearchSpecs.toArray());
				// this will let ModelListAction not call getmessageSearchSpecs
				// method
				pi.setElementsTypeIsKey(false);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return pi;
	}

	/**
	 * return query result for FourmMessage, it sorted by modifidate.
	 */
	public PageIterator getMessages(QueryCriteria qc, int start, int count) {
		logger.debug("enter getMessages for QueryCriteria");
		if (qc instanceof MultiCriteria) {
			// transfer msc username to userId;
			MultiCriteria mc = (MultiCriteria) qc;
			String username = mc.getUsername();
			if (username != null) {
				Account accountIn = new Account();
				accountIn.setUsername(username);
				Account account = accountFactory.getFullAccount(accountIn);
				if (account != null)
					mc.setUserID(account.getUserId());
				else
					mc.setUserID(username);
			}
			return messageQueryDao.getMessages(qc, start, count);
		} else {
			logger.error("it is not MultiCriteria");
			return new PageIterator();
		}
	}

	public PageIterator getMessageReplys(QueryCriteria qc, int start, int count) {
		logger.debug("enter getMessages for QueryCriteria");
		if (qc instanceof MultiCriteria) {
			// transfer msc username to userId;
			MultiCriteria mc = (MultiCriteria) qc;
			String username = mc.getUsername();
			if (username != null) {
				Account accountIn = new Account();
				accountIn.setUsername(username);
				Account account = accountFactory.getFullAccount(accountIn);
				if (account != null)
					mc.setUserID(account.getUserId());
				else
					mc.setUserID(username);
			}
			return messageQueryDao.getMessageReplys(qc, start, count);
		} else {
			logger.error("it is not MultiCriteria");
			return new PageIterator();
		}
	}

	public PageIterator getThreads(QueryCriteria qc, int start, int count) {
		logger.debug("enter getMessages for QueryCriteria");
		if (qc instanceof MultiCriteria) {
			// transfer msc username to userId;
			MultiCriteria mc = (MultiCriteria) qc;
			String username = mc.getUsername();
			if (username != null) {
				Account accountIn = new Account();
				accountIn.setUsername(username);
				Account account = accountFactory.getFullAccount(accountIn);
				if (account != null)
					mc.setUserID(account.getUserId());
				else
					mc.setUserID(username);
			}
			return messageQueryDao.getThreads(qc, start, count);
		} else {
			logger.error("it is not MultiCriteria");
			return new PageIterator();
		}
	}

	/*
	 * return query result for FourmThread, it sorted by thread modifidate.
	 */
	public PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort) {
		logger.debug("enter getThreads");
		return messageQueryDao.getThreads(forumId, start, count, resultSort);
	}

	public PageIterator getThreads(int start, int count, ThreadListSpec threadListSpec) {
		return messageQueryDao.getThreads(start, count, threadListSpec);
	}

	public ForumThread getThread(Long threadId) {
		return forumBuilder.getThread(threadId).orElse(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.service.ForumMessageService#getThreadsPrevNext(java
	 * .lang.String, int)
	 */
	public List getThreadsPrevNext(Long currentThreadId) {
		List threads = new ArrayList();
		logger.debug("enter getThreadsPrevNext");
		try {
			ForumThread thread = getThread(currentThreadId);
			if (thread == null) {
				return new ArrayList();
			}
			List resultIds = messageQueryDao.getThreadsPrevNext(thread.getForum().getForumId(), currentThreadId);

			int index = resultIds.indexOf(currentThreadId);
			logger.debug(" found the block ,size:" + resultIds.size() + " the index=" + index);
			if (index == -1)
				return new ArrayList();

			// transfer the forumThread from threadId Collection from the
			// resultIds;

			if (index >= 1) {
				Long prevThreadId = (Long) resultIds.get(index - 1);
				logger.debug(" prevThreadId=" + prevThreadId);
				threads.add(getThread(prevThreadId));
			}
			threads.add(getThread(currentThreadId));
			if (index < (resultIds.size() - 1)) {
				Long nextThreadId = (Long) resultIds.get(index + 1);
				logger.debug(" nextThreadId=" + nextThreadId);
				threads.add(getThread(nextThreadId));
			}
		} catch (Exception e) {
			logger.error(currentThreadId + "" + e);
		}
		return threads;
	}

	/**
	 * return query result for FourmThread, it sorted by message replies. call
	 * from ThreadHotAction getHotThreads is in object sorted not by SQL.
	 * 
	 */

	public PageIterator getHotThreads(QueryCriteria qc, int start, int count) {
		logger.debug("enter getThreads for QueryCriteria, messageReplyCountWindow =" + qc.getMessageReplyCountWindow());
		return queryManager.getHotThreadPageKeys(qc, start, count);
	}

	/**
	 * call from ThreadPopularAction this method is simple than getHotThreads,
	 * only for one page , no multi pages. no messageReplyCountWindow, donot
	 * need sorted by message replies popularThreads will get result from SQL.
	 * 
	 */
	public PageIterator popularThreads(int popularThreadsWindow, int count) {
		PageIterator pi = messageQueryDao.popularThreads(getQueryCriteria(popularThreadsWindow), count);
		try {
			if (pi.getAllCount() < count) {// reload before 30 days
				int lastcount = count - pi.getAllCount();
				PageIterator pi2 = messageQueryDao.popularThreads(getQueryCriteria(popularThreadsWindow), lastcount);//
				Object[] keys = pi.getKeys();
				Object[] keys2 = pi2.getKeys();
				Object[] newkeys = new Object[keys.length + keys2.length];
				System.arraycopy(keys, 0, newkeys, 0, keys.length);
				System.arraycopy(keys2, 0, newkeys, keys.length, keys2.length);
				pi = new PageIterator(newkeys.length, newkeys);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	private QueryCriteria getQueryCriteria(int popularThreadsWindow) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -popularThreadsWindow);

		QueryCriteria queryCriteria = new QueryCriteria();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		queryCriteria.setFromDate(year, month, day);
		return queryCriteria;
	}


	public PageIterator getThreadListByUser(String userId, int start, int count) {
		return messageQueryDao.getThreadListByUser(userId, start, count);
	}

	public PageIterator getMesageListByUser(String userId, int start, int count) {
		return messageQueryDao.getMesageListByUser(userId, start, count);

	}

}
