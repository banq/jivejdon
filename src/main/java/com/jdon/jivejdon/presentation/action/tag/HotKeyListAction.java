package com.jdon.jivejdon.presentation.action.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.property.HotKeys;
import com.jdon.jivejdon.service.property.TagService;

public class HotKeyListAction extends Action {
	private final static Logger logger = LogManager.getLogger(TagsListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter hotkeys");
		HotKeys hotKeys = (HotKeys) this.servlet.getServletContext().getAttribute("HOTKEYS");
		if (hotKeys == null) {
			TagService othersService = (TagService) WebAppUtil.getService("othersService", this.servlet.getServletContext());
			hotKeys = othersService.getHotKeys();
			this.servlet.getServletContext().setAttribute("HOTKEYS", hotKeys);
		}
		return mapping.findForward("hotkeys");
	}

}
