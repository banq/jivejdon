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
package com.jdon.jivejdon.presentation.action;

import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq </a>
 * 
 */
public class ThreadPrevNexListAction extends Action {
	private final static String module = ThreadPrevNexListAction.class.getName();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String currentThreadId = request.getParameter("thread");
		if ((UtilValidate.isEmpty(currentThreadId)) || (!StringUtils.isNumeric(currentThreadId))) {
			Debug.logError("require the paramters : currentThreadId ");
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		}
		try {
			Long currentThreadIdL = new Long(currentThreadId);
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
					this.servlet.getServletContext());
			List threads = forumMessageQueryService.getThreadsPrevNext(currentThreadIdL);

			int index = -1;
			if (threads.size() != 0) {
				ForumThread currentThread = forumMessageQueryService.getThread(currentThreadIdL);
				index = threads.indexOf(currentThread);
			}
			Debug.logVerbose(" found the currentThread the index=" + index, module);

			// change the iterator for display
			ListIterator li = threads.listIterator();

			if (index != -1) {
				while (li.nextIndex() != index) {
					li.next();
				}
			}

			request.setAttribute("ThreadsPrevNext", li); // for above

			ListIterator li2 = threads.listIterator();
			if (index != -1) {
				while (li2.nextIndex() != index) {
					li2.next();
				}
			}
			request.setAttribute("ThreadsPrevNext2", li2);// for below

		} catch (Exception e) {
			Debug.logError(e, module);
		}
		return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
	}

}
