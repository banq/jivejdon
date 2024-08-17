package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.jdon.util.UtilValidate;

public class QueryViewAction extends Action {
	private final static Logger logger = LogManager.getLogger(ThreadQueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("enter ThreadQueryViewAction");
		String query = request.getParameter("query");
		if (UtilValidate.isEmpty(query))
			return mapping.findForward("view");
		
		
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("search"));
		redirect.addParameter("query", query);
		return redirect;

		// QueryForm qForm = (QueryForm) form;
		// if (UtilValidate.isEmpty(qForm.getQueryType())) {
		// logger.debug("enter view");
		// return mapping.findForward("view");
		// } else {// queryType is not empty
		// logger.debug("enter result");
		// if (qForm.getQueryType().equals("messageQueryAction")) {
		// return mapping.findForward("messageQueryAction");
		// }
		// if (qForm.getQueryType().equals("userMessageQueryAction")) {
		// return mapping.findForward("userMessageQueryAction");
		// } else {
		// return mapping.findForward("threadQueryAction");
		// }

		// }

	}

}
