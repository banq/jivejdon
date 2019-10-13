/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.auth;

import com.jdon.bussinessproxy.TargetMetaDef;
import com.jdon.container.access.TargetMetaRequest;
import com.jdon.container.access.TargetMetaRequestsHolder;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.model.account.Account;
import com.jdon.jivejdon.model.auth.PermissionRule;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Permission Interceptor
 * 
 * this class is configured in WEB-INF/myaspect.xml of this web project.
 * 
 * and configure the myaspect.xml in web.xml, so the system can load it.
 * 
 * <context-param> <param-name>aspectConfigure</param-name>
 * <param-value>WEB-INF/myaspect.xml</param-value> </context-param>
 * 
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */

public class PermissionInterceptor implements MethodInterceptor {
	private final static Logger logger = LogManager.getLogger(PermissionInterceptor.class);

	protected final SessionContextUtil sessionContextUtil;

	private final OperationAuthorization authorization;

	private final TargetMetaRequestsHolder targetMetaRequestsHolder;

	/**
	 * @param permissionXmlParser
	 */
	public PermissionInterceptor(OperationAuthorization authorization, SessionContextUtil sessionContextUtil,
			TargetMetaRequestsHolder targetMetaRequestsHolder) {
		this.sessionContextUtil = sessionContextUtil;
		this.authorization = authorization;
		this.targetMetaRequestsHolder = targetMetaRequestsHolder;

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
		TargetMetaDef targetMetaDef = targetMetaRequestsHolder.getTargetMetaRequest().getTargetMetaDef();

		boolean hasPerm = false;

		Method method = invocation.getMethod();
		String methodNameNow = method.getName();
		String serviceName = targetMetaDef.getName();
		String roleName = null;

		PermissionRule permissionRule = authorization.getPermissionRule();
		if (!permissionRule.isOperationAuthenticated(serviceName, methodNameNow)) {
			return invocation.proceed();
		}
		try {
			TargetMetaRequest targetMetaRequest = targetMetaRequestsHolder.getTargetMetaRequest();
			SessionContext sessionContext = targetMetaRequest.getSessionContext();
			if (sessionContext != null) {
				Account account = sessionContextUtil.getLoginAccount(sessionContext);
				if (account != null) {
					roleName = account.getRoleName();
					logger.debug("the roleName=" + roleName);

					hasPerm = permissionRule.isOperationAuthenticatedByRole(serviceName, methodNameNow, roleName);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

		if (hasPerm)
			return invocation.proceed();
		else {
			String info = "Permission Error: your role is " + roleName + ", no permission operate method: " + methodNameNow + " for "
					+ targetMetaDef.getClassName();
			logger.error(info);
			setErrors(invocation, Constants.NOPERMISSIONS);
			return null;
		}
	}

	private void setErrors(MethodInvocation invocation, String info) {
		if (invocation == null)
			return;
		if (invocation.getArguments() == null)
			return;
		Object arg = invocation.getArguments()[0];
		if ((arg != null) && arg.getClass().isAssignableFrom(EventModel.class)) {
			EventModel em = (EventModel) arg;
			em.setErrors(info);
		}
	}

}
