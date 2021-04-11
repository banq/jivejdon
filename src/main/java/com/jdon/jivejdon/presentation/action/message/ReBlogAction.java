/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.presentation.action.message;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.model.ModelForm;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Response for post a reply message.
 * 
 * it try to connect a aggregates to another aggregates
 * 
 * @author banq
 * 
 */
public class ReBlogAction extends Action {
	private final static Logger logger = LogManager.getLogger(ReBlogAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("enter ReBlogAction");

		MessageForm messageReplyForm = (MessageForm) form;
		try {
			if (!UtilValidate.isEmpty(request.getParameter("onlyreblog")))
				onlyreBlog(messageReplyForm, request);
			else if (!UtilValidate.isEmpty(request.getParameter("reblog")))
				reBlog(messageReplyForm, request);
			else
				return mapping.findForward("onlyreply");
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage(e.getMessage());
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveErrors(request, errors);
			return mapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
		}
		return mapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
	}

	protected void onlyreBlog(MessageForm messageReplyForm, HttpServletRequest request) throws Exception {
		try {
			if (messageReplyForm.getParentMessage() == null
					|| messageReplyForm.getParentMessage().getMessageId() == null) {
				throw new Exception("parentMessage.messageId is null");
			}
			Long pmessageId = messageReplyForm.getParentMessage().getMessageId();
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
					request);
			ForumMessage pmsg = forumMessageService.getMessage(pmessageId);
			if (pmsg == null) {
				throw new Exception("parentMessage is null");
			}

			Long topicMessageId = createTopic(messageReplyForm, request);
			if (topicMessageId == null)
				throw new Exception(Constants.ERRORS);

			forumMessageService.reBlog(pmessageId, topicMessageId);
			// reload reblog to collection, the parant has new child;
			pmsg.setReBlogVO(null);
			messageReplyForm.setMessageId(topicMessageId);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	protected void reBlog(MessageForm messageReplyForm, HttpServletRequest request) throws Exception {
		try {
			Long topicMessageId = createTopic(messageReplyForm, request);
			if (topicMessageId == null)
				throw new Exception(Constants.ERRORS);

			Long replyMessageId = createReply(messageReplyForm, request);
			if (replyMessageId == null)
				throw new Exception(Constants.ERRORS);

			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
					request);
			forumMessageService.reBlog(replyMessageId, topicMessageId);

			messageReplyForm.setMessageId(replyMessageId);

		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	protected Long createTopic(MessageForm messageReplyForm, HttpServletRequest request) throws Exception {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
				request);

		AnemicMessageDTO anemicMessageDTO = new AnemicMessageDTO();
		Long topicMessageId = null;
		try {
			formCopyToModelIF(messageReplyForm, anemicMessageDTO);

			EventModel em = new EventModel();
			em.setModelIF(anemicMessageDTO);
			topicMessageId = forumMessageService.createTopicMessage(em);
			if (em.getErrors() != null) {
				throw new Exception(em.getErrors());
			}
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(Constants.ERRORS);
		}
		return topicMessageId;
	}

	protected Long createReply(MessageForm messageReplyForm, HttpServletRequest request) throws Exception {
		Long replyMessageId = null;
		try {
			AnemicMessageDTO anemicMessageDTO = new AnemicMessageDTO();
			formCopyToModelIF(messageReplyForm, anemicMessageDTO);
			EventModel em = new EventModel();
			em = new EventModel();
			em.setModelIF(anemicMessageDTO);
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
					request);
			replyMessageId = forumMessageService.createReplyMessage(em);
			if (em.getErrors() != null) {
				throw new Exception(em.getErrors());
			}
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(Constants.ERRORS);
		}
		return replyMessageId;

	}

	public void formCopyToModelIF(ModelForm form, Object model) throws Exception {
		if (model == null || form == null)
			return;
		try {
			PropertyUtils.copyProperties(model, form);
		} catch (InvocationTargetException ie) {
			String error = "error happened in getXXX method of ModelForm:" + form.getClass().getName() + " error:" + ie;
			throw new Exception(error);

		} catch (Exception e) {
			String error = " ModelForm:" + form.getClass().getName() + " copy To Model:" + model.getClass().getName()
					+ " error:" + e;
			throw new Exception(error);
		}
	}

}
