package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.property.PropertyService;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.property.ThreadPropertys;

/**
 * change the ui_statie of thread one thread just only have one state(see
 * 
 * @ThreadPropertys)
 * @author oojdon
 * 
 */
public class StickyAction extends Action {

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PropertyService propertyService = (PropertyService) WebAppUtil.getService("propertyService", 
				this.servlet.getServletContext());
		String threadId = request.getParameter("threadId");
		String ui_state = request.getParameter("ui_state");
		String action = request.getParameter("action");

		Property property = new Property();
		property.setName(ThreadPropertys.UISTATE);
		property.setValue(ui_state);

		// if admin want to reset the thread to common state, he should delete
		// the property record directly
		if (action.equals("delete")) {
			propertyService.deleteThreadProperty(Long.parseLong(threadId), property);
			return null;
		}

		propertyService.updateThreadProperty(Long.parseLong(threadId), property);

		return null;
	}

}
