package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.subscription.action.EmailAction;
import com.jdon.jivejdon.manager.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.repository.builder.SubscriptionInitFactory;
import com.jdon.jivejdon.repository.dao.SubscriptionDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

public class SubscriptionDaoSql implements SubscriptionDao {
	private final static Logger logger = LogManager.getLogger(SubscriptionDaoSql.class);

	protected JdbcTempSource jdbcTempSource;

	protected Constants constants;

	protected PageIteratorSolver pageIteratorSolver;

	protected SubscriptionInitFactory subscriptionInitFactory;

	public SubscriptionDaoSql(JdbcTempSource jdbcTempSource, Constants constants, ContainerUtil containerUtil,
			SubscriptionInitFactory subscriptionInitFactory) {
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
		this.subscriptionInitFactory = subscriptionInitFactory;
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());

	}

	public void createSubscription(Subscription subscription) {
		try {
			String ADD_SUB = "INSERT INTO subscription(subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail, notifymode)"
					+ " VALUES (?,?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(subscription.getSubscriptionId());
			queryParams.add(subscription.getAccount().getUserId());

			Subscribed subscribed = subscription.getSubscribed();
			queryParams.add(subscribed.getSubscribeType());
			queryParams.add(subscribed.getSubscribeId());

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			subscription.setCreationDate(displayDateTime);

			queryParams.add(subscription.getSubscriptionActionHolder().hasActionType(ShortMsgAction.class));
			queryParams.add(subscription.getSubscriptionActionHolder().hasActionType(EmailAction.class));

			queryParams.add(subscription.getNotifymode());
			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SUB);

		} catch (Exception e) {
			logger.error(e);
		}
	}

	public Subscription getSubscription(Long id) {
		logger.debug("enter getSubscription for id:" + id);
		String LOAD_SUB = "SELECT subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail, notifymode FROM subscription WHERE subscriptionID=?";
		List queryParams = new ArrayList();
		queryParams.add(id);

		Subscription ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = subscriptionInitFactory.populateSubscription(map);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public Long isSubscription(Long subscribedID, String userId) {
		String LOAD_SUB = "SELECT subscriptionID FROM subscription WHERE subscribedID=? and userId = ?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);
		queryParams.add(userId);

		Long subscriptionID = null;
		try {
			Object id = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, LOAD_SUB);
			logger.debug("id =" + id);
			if (id instanceof Long)
				subscriptionID = (Long) id;
		} catch (Exception se) {
			logger.error(se);
		}
		return subscriptionID;
	}

	public void deleteSubscription(Long subscriptionID) {
		try {
			String sql = "DELETE FROM subscription WHERE subscriptionID=?";
			List queryParams = new ArrayList();
			queryParams.add(subscriptionID);
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	public PageIterator getSubscriptions(String userId, int subscribedtype, int start, int count) {
		logger.debug("enter getSubscriptions ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) FROM subscription WHERE userId=? and subscribedtype =?";

		String GET_ALL_ITEMS = "select subscriptionID FROM subscription WHERE userId=? and subscribedtype =? ";

		Collection params = new ArrayList(2);
		params.add(userId);
		params.add(new Integer(subscribedtype));
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start, count);

	}

	@Override
	public Collection<Subscription> getSubscriptionsForsubscribed(Long subscribedID) {
		logger.debug("enter getSubscriptions for id:" + subscribedID);
		String LOAD_SUB = "SELECT subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail, notifymode FROM subscription WHERE subscribedID=?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);

		Collection<Subscription> rets = new ArrayList();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Subscription ret = subscriptionInitFactory.populateSubscription(map);
				rets.add(ret);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return rets;
	}

	public int getSubscriptionsForsubscribedCount(Long subscribedID) {
		logger.debug("enter getSubscriptions for id:" + subscribedID);
		String LOAD_SUB = "SELECT count(1)  FROM subscription WHERE subscribedID=?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, LOAD_SUB);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
		} catch (Exception se) {
			logger.error("subscribedID=" + subscribedID + " error:" + se);
		}
		return count;
	}

	@Override
	public Collection<Subscription> getSubscriptions(String userId) {
		logger.debug("enter getSubscriptions for userid:" + userId);
		String LOAD_SUB = "SELECT subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail, notifymode FROM subscription WHERE userId=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);

		Collection<Subscription> rets = new ArrayList();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Subscription ret = subscriptionInitFactory.populateSubscription(map);
				rets.add(ret);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return rets;
	}

	@Override
	public int getSubscriptionsCount(String userId, int subscribedtype) {
		logger.debug("enter getSubscriptionsCount for userid:" + userId);
		String LOAD_SUB = "SELECT count(1)  FROM subscription WHERE userId=? and subscribedtype =?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		queryParams.add(subscribedtype);
		int count = 0;
		try {
			Object counto = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, LOAD_SUB);
			if (counto instanceof Long)// for mysql 5
				count = ((Long) counto).intValue();
			else
				count = ((Integer) counto).intValue(); // for mysql 4
		} catch (Exception se) {
			logger.error("userid=" + userId + " error:" + se);
		}
		return count;
	}

	public PageIterator getFollowing(String userId, int subscribedtype, int start, int count) {
		logger.debug("enter getFollowing ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) FROM subscription WHERE userId=? and subscribedtype =?";

		String GET_ALL_ITEMS = "select subscribedID FROM subscription WHERE userId=? and subscribedtype =? ";

		Collection params = new ArrayList(2);
		params.add(userId);
		params.add(new Integer(subscribedtype));
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start, count);

	}

	public PageIterator getFollower(Long subscribedID, int start, int count) {
		logger.debug("enter getFollower ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) FROM subscription WHERE subscribedID=? ";

		String GET_ALL_ITEMS = "select userId FROM subscription WHERE subscribedID=?  ";

		Collection params = new ArrayList(1);
		params.add(subscribedID);
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start, count);

	}
}
