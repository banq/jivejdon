package com.jdon.jivejdon.repository.builder;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.TagNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.repository.acccount.AccountFactory;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.property.TagRepository;

@Component
public class SubscribedFactory {

	private ForumFactory forumAbstractFactory;
	private TagRepository tagRepository;
	private AccountFactory accountFactory;

	public SubscribedFactory(ThreadNotifyMessage threadNotifyMessageTemp, TagNotifyMessage tagNotifyMessageTemp,
			AccountNotifyMessage accountNotifyMessageTemp, ForumFactory forumAbstractFactory, TagRepository tagRepository,
			AccountFactory accountFactory) {
		this.forumAbstractFactory = forumAbstractFactory;
		this.tagRepository = tagRepository;
		this.accountFactory = accountFactory;
	}

	public static Subscribed createTransient(int subscribeType, Long subscribeId) {
		if (subscribeType == ForumSubscribed.TYPE) {
			return new ForumSubscribed(subscribeId);
		} else if (subscribeType == ThreadSubscribed.TYPE) {
			return new ThreadSubscribed(subscribeId);
		} else if (subscribeType == TagSubscribed.TYPE) {
			return new TagSubscribed(subscribeId);
		} else if (subscribeType == AccountSubscribed.TYPE) {
			return new AccountSubscribed(subscribeId);
		} else
			return null;

	}

	public void embedFull(Subscription subscription) {
		int subscribeType = subscription.getSubscribeType();
		Subscribed subscribed = subscription.getSubscribed();
		if (subscribeType == ForumSubscribed.TYPE) {
			((ForumSubscribed) subscribed).setForum(forumAbstractFactory.getForum(subscribed.getSubscribeId()));
		} else if (subscribeType == ThreadSubscribed.TYPE) {
			try {
				((ThreadSubscribed) subscribed).setForumThread(forumAbstractFactory.getThread
						(subscribed.getSubscribeId()).get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (subscribeType == TagSubscribed.TYPE) {
			((TagSubscribed) subscribed).setTag(tagRepository.getThreadTag(subscribed.getSubscribeId()));
		} else if (subscribeType == AccountSubscribed.TYPE) {
			((AccountSubscribed) subscribed).setAccount(accountFactory.getFullAccount(subscribed.getSubscribeId().toString()));
		}
	}

}
