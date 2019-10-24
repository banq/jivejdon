package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;

public interface NotifySubscribed {

	Long getSubscribeId();

	ShortMessage createShortMessage(Subscription subscription);

}
