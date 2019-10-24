package com.jdon.jivejdon.presentation.action.tag;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.property.TagService;

public class TagsListAction extends Action {
	private final static Logger logger = LogManager.getLogger(TagsListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter tags");
		String q = request.getParameter("q");
		TagService othersService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
		Collection c = othersService.tags(q);
		request.setAttribute("TAGS", c);
		return mapping.findForward("tags");

	}

}
