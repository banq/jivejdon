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
package com.jdon.jivejdon.auth;

import com.jdon.container.access.TargetMetaRequest;
import com.jdon.container.access.TargetMetaRequestsHolder;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.spi.component.block.ErrorBlockerIF;
import com.jdon.jivejdon.spi.component.filter.InputSwitcherIF;
import com.jdon.jivejdon.spi.component.throttle.post.Throttler;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.api.util.SessionContextUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * web-inf/myaspect.xml
 * 
 * @author banq
 * 
 */
public class CUDInputInterceptor implements MethodInterceptor {

	private final static Logger logger = LogManager.getLogger(CUDInputInterceptor.class);

	protected final SessionContextUtil sessionContextUtil;

	protected final Throttler throttler;

	protected final InputSwitcherIF inputSwitcherIF;

	private final TargetMetaRequestsHolder targetMetaRequestsHolder;

	private final ErrorBlockerIF errorBlockerIF;

	public CUDInputInterceptor(Throttler throttler, TargetMetaRequestsHolder targetMetaRequestsHolder, SessionContextUtil sessionContextUtil,
			InputSwitcherIF inputSwitcherIF, ErrorBlockerIF errorBlockerIF) {
		this.throttler = throttler;
		this.sessionContextUtil = sessionContextUtil;
		this.inputSwitcherIF = inputSwitcherIF;
		this.targetMetaRequestsHolder = targetMetaRequestsHolder;
		this.errorBlockerIF = errorBlockerIF;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.debug("enter PermissionInterceptor");

		Method method = invocation.getMethod();
		String methodNameNow = method.getName();

		TargetMetaRequest targetMetaRequest = targetMetaRequestsHolder.getTargetMetaRequest();
		SessionContext sessionContext = targetMetaRequest.getSessionContext();

		// only Intercept those users who has login.
		if (!sessionContextUtil.isLogin(sessionContext)) {
			return invocation.proceed();
		}

		// only Intercept those create or update or delete methods, only permit
		// read only
		if (!methodNameNow.contains("create") && !methodNameNow.contains("update") && !methodNameNow.contains("delete"))
			return invocation.proceed();

		if (isInputPermit(invocation)) {
			logger.error(Constants.INPUT_PERMITTED);
			return null;
		}

		Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (IPIsAllowed(methodNameNow, account))
			return invocation.proceed();
		else {
			errorBlockerIF.checkCount(account.getPostIP(), 5);
			setErrors(invocation, Constants.IP_PERMITTED);
			logger.error(Constants.IP_PERMITTED + account.getPostIP());
			return null;
		}
	}

	private void setErrors(MethodInvocation invocation, String info) {
		Object arg = invocation.getArguments()[0];
		if ((arg != null) && arg.getClass().isAssignableFrom(EventModel.class)) {
			EventModel em = (EventModel) arg;
			em.setErrors(info);
		}
	}

	private boolean isInputPermit(MethodInvocation invocation) {
		boolean isPermit = false;
		if (inputSwitcherIF.isInputPermit()) {
			setErrors(invocation, Constants.INPUT_PERMITTED);
			isPermit = true;
		}
		return isPermit;
	}

	private boolean IPIsAllowed(String methodNameNow, Account account) {
		boolean isAllowed = false;
		if (account == null)
			return isAllowed;

		//	if (methodNameNow.contains("create")) {
		if (methodNameNow.contains("createTopic")) {
			if (account.getMessageCount() > 2000)
				isAllowed = throttler.checkPostValidate(account);
		} else
			isAllowed = !throttler.isAbusive(account.getPostIP());
		return isAllowed;
	}

}
