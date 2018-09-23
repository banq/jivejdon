package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.util.UtilValidate;

public class QueryViewAction extends Action {
	private final static Logger logger = LogManager.getLogger(ThreadQueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter ThreadQueryViewAction");

		QueryForm qForm = (QueryForm) form;
		loadForumOptions(request, qForm);
		if (UtilValidate.isEmpty(qForm.getQueryType())) {
			logger.debug("enter view");
			return mapping.findForward("view");
		} else {// queryType is not empty
			logger.debug("enter result");
			if (qForm.getQueryType().equals("messageQueryAction")) {
				return mapping.findForward("messageQueryAction");
			}
			if (qForm.getQueryType().equals("userMessageQueryAction")) {
				return mapping.findForward("userMessageQueryAction");
			} else {
				return mapping.findForward("threadQueryAction");
			}

		}

	}

	private void loadForumOptions(HttpServletRequest request, QueryForm qForm) {
		if (qForm.getForums().size() != 0) {
			return;
		}
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", this.servlet.getServletContext());
		PageIterator pi = forumService.getForums(0, 30);
		Object[] ids = pi.getKeys();
		for (int i = 0; i < ids.length; i++) {
			qForm.getForums().add(forumService.getForum((Long) ids[i]));
		}
	}
}
