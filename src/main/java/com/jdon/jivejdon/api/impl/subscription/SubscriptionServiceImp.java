package com.jdon.jivejdon.api.impl.subscription;

import com.jdon.annotation.Service;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.intercept.Stateful;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.spi.component.email.ValidateCodeEmail;
import com.jdon.jivejdon.spi.component.subscription.action.EmailAction;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.domain.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.domain.model.subscription.subscribed.TagSubscribed;
import com.jdon.jivejdon.domain.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository;
import com.jdon.jivejdon.infrastructure.repository.acccount.Userconnector;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.subscription.SubscriptionService;
import com.jdon.jivejdon.api.util.SessionContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.TreeSet;

@Stateful
@Service("subscriptionService")
public class SubscriptionServiceImp implements SubscriptionService {
	private final static Logger logger = LogManager.getLogger(SubscriptionServiceImp.class);

	private SubscriptionRepository subscriptionRepository;

	protected SessionContext sessionContext;

	protected SessionContextUtil sessionContextUtil;

	protected Account account;

	protected ValidateCodeEmail validateCodeEmail;

	protected Userconnector userconnector;

	protected ForumMessageQueryService forumMessageQueryService;

	public SubscriptionServiceImp(SessionContextUtil sessionContextUtil, SubscriptionRepository subscriptionRepository,
			ValidateCodeEmail validateCodeEmail, Userconnector userconnector, ForumMessageQueryService forumMessageQueryService) {
		super();
		this.subscriptionRepository = subscriptionRepository;
		this.sessionContextUtil = sessionContextUtil;
		this.validateCodeEmail = validateCodeEmail;
		this.userconnector = userconnector;
		this.forumMessageQueryService = forumMessageQueryService;
	}

	public Account getloginAccount() {
		if (account == null)
			account = sessionContextUtil.getLoginAccount(sessionContext);
		return account;
	}

	public Subscription initSubscription(EventModel em) {
		Subscription subscription = (Subscription) em.getModelIF();
		return subscriptionRepository.getFullSub(subscription);

	}

	@Override
	public void createSubscription(EventModel em) {
		Subscription subscription = (Subscription) em.getModelIF();
		subscription.setAccount(getloginAccount());
		try {
			Long subscriptionId = subscriptionRepository.getSubscriptionDao().isSubscription(subscription.getSubscribed().getSubscribeId(),
					subscription.getAccount().getUserId());
			if (subscriptionId != null) {
				subscription.setSubscriptionId(subscriptionId);
				deleteSubscription(em);
			}
			if (subscription.getSubscriptionActionHolder().hasActionType(EmailAction.class) && !subscription.getAccount().isEmailValidate()) {
				validateCodeEmail.send(subscription.getAccount());
			}

			subscriptionRepository.createSubscription(subscription);

			subscription = getSubscription(subscription.getSubscriptionId());
			subscription.updateSubscriptionCount(1);
		} catch (Exception e) {
			logger.error("createSubscription error:" + getloginAccount().getUsername() + " subscription:" + subscription.getSubscriptionId()+ " error:"+e.getMessage());
			em.setErrors(Constants.ERRORS);
		}

	}

	/**
	 * 1=关注你了 2=已关注 3=相互关注
	 */
	public Integer checkSubscription(Long subscribedId) {
		Integer result = 0;
		Account account = getloginAccount();
		Long subscriptionId = subscriptionRepository.getSubscriptionDao().isSubscription(subscribedId, account.getUserId());
		Long subscriptionId2 = subscriptionRepository.getSubscriptionDao().isSubscription(account.getUserIdLong(), Long.toString(subscribedId));
		if (subscriptionId2 != null && subscriptionId != null) {
			result = 3;
		} else if (subscriptionId2 == null && subscriptionId != null) {
			result = 2;
		} else if (subscriptionId2 != null && subscriptionId == null) {
			result = 1;
		}
		return result;

	}

	public void deleteSubscription(Long subscribedId) {
		Account account = getloginAccount();
		Long subscriptionId = subscriptionRepository.getSubscriptionDao().isSubscription(subscribedId, account.getUserId());
		if (subscriptionId != null) {
			Subscription subscription = getSubscription(subscriptionId);
			subscriptionRepository.deleteSubscription(subscription);
			subscription.updateSubscriptionCount(-1);
		}

	}

	public void updateSubscription(EventModel em) {
		deleteSubscription(em);
		createSubscription(em);
	}

	@Override
	public void deleteSubscription(EventModel em) {
		Subscription subscriptionp = (Subscription) em.getModelIF();
		try {
			Subscription subscription = getSubscription(subscriptionp.getSubscriptionId());
			if (subscription != null) {
				subscriptionRepository.deleteSubscription(subscription);
				subscription.updateSubscriptionCount(-1);
			} else {
				logger.error("deleteSubscription error: no this subscription:" + subscriptionp.getSubscriptionId());
			}
		} catch (Exception e) {
			logger.error("deleteSubscription error: subscription:" + subscriptionp.getSubscriptionId() + " error:"+e.getMessage());
		}

	}

	@Override
	public Subscription getSubscription(Long id) {
		return subscriptionRepository.getSubscription(id);
	}

	// for loging user , userId is invalid
	public PageIterator getSubscriptionsForTag(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), TagSubscribed.TYPE, start, count);
	}

	// for loging user , userId is invalid
	public PageIterator getSubscriptionsForAccount(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), AccountSubscribed.TYPE, start, count);
	}

	// for loging user , userId is invalid
	public PageIterator getSubscriptionsForThread(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), ThreadSubscribed.TYPE, start, count);
	}

	// for loging user , userId is invalid
	public PageIterator getSubscriptionsForForum(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), ForumSubscribed.TYPE, start, count);
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public PageIterator getFollowing(Long userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getFollowing(Long.toString(userId), AccountSubscribed.TYPE, start, count);
	}

	public PageIterator getFollower(Long subscribedId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getFollower(subscribedId, start, count);
	}

	@Override
	public PageIterator getThreadListByUserFollowing(String userId, int start, int count) {
		PageIterator pi = getFollowing(Long.parseLong(userId), start, count);
		PageIterator pi2 = forumMessageQueryService.getThreadListByUser(userId, start, count);
		if (pi.getAllCount() == 0 && pi2.getAllCount() == 0)
			return new PageIterator();

		TreeSet<Long> threads = new TreeSet(new Comparator() {
			public int compare(Object thread1, Object thread2) {
				if (((Long) thread1).longValue() > ((Long) thread2).longValue()) {
					return -1; // returning the first object
				} else if (((Long) thread1).longValue() == ((Long) thread2).longValue()) {
					return 0;
				} else {
					return 1;
				}
			}

		});

		int allcount = pi2.getAllCount();
		while (pi2.hasNext()) {
			Long threadId = (Long) pi2.next();
			threads.add(threadId);
		}

		while (pi.hasNext()) {
			Long userFollowingId = (Long) pi.next();
			PageIterator pifollowing = forumMessageQueryService.getThreadListByUser(Long.toString(userFollowingId), start, count);
			allcount = pifollowing.getAllCount() + allcount;
			while (pifollowing.hasNext()) {
				Long threadId = (Long) pifollowing.next();
				threads.add(threadId);
			}
		}

		PageIterator returnPI = new PageIterator(allcount, threads.toArray());
		return returnPI;
	}

}
