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

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
public class MessageRecursiveListAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(MessageRecursiveListAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		String messageId = request.getParameter("messageId");
		if (messageId == null) {
			String threadIds = request.getParameter("threadId");
			if (threadIds != null) {
				ForumThread thread = forumMessageQueryService.getThread(Long.parseLong(threadIds));
				messageId = thread.getRootMessage().getMessageId().toString();
			}else {
				logger.error(" getPageIterator error : messageId is null");
				return new PageIterator();
			}
		}
		return forumMessageQueryService.getRecursiveMessages(Long.parseLong(messageId), start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", this.servlet.getServletContext());
		return forumMessageService.getMessage((Long) key);
	}

	public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, ModelListForm modelListForm)
			throws Exception {
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		
		String messageId = request.getParameter("messageId");
		if (messageId == null) {
			String threadIds = request.getParameter("threadId");
			if (threadIds != null) {
				ForumThread thread = forumMessageQueryService.getThread(Long.parseLong(threadIds));
				messageId = thread.getRootMessage().getMessageId().toString();
			}else{
				logger.error("customizeListForm error : messageId is null");
			}
		}
		ForumMessage forumParentMessage = forumMessageQueryService.getMessage(Long.parseLong(messageId));
		modelListForm.setOneModel(forumParentMessage);
	}

}
