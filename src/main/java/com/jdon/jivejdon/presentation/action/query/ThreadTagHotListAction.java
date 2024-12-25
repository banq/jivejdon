package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadTagList;
import com.jdon.strutsutil.ModelListForm;

public class ThreadTagHotListAction extends Action {

	private ThreadApprovedNewList threadApprovedNewList;

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext());

		return threadApprovedNewList;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelListForm tagsHotListForm = (ModelListForm) form;
		ThreadTagList threadTagList = getThreadApprovedNewList().getThreadTagList();
		tagsHotListForm.setList(threadTagList.getThreadTags());
		tagsHotListForm.setAllCount(threadTagList.getThreadTags().size());
		// request.setAttribute("Tags_ImageUrls", threadTagList.getImageUrls());
		return mapping.findForward("success");
	}
}
