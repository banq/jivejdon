package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadTagList;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.strutsutil.ModelListForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
		// ThreadTagList threadTagList = getThreadApprovedNewList().getThreadTagList();
		TagService tagService = (TagService) WebAppUtil.getService("othersService",
				this.servlet.getServletContext());
		List<ThreadTag> tagLists = Arrays.asList(tagService.getThreadTags(0, 30).getKeys())
				.stream()
				.map(tagId -> tagService.getThreadTag((Long) tagId))
				.collect(Collectors.toList());
		Collections.shuffle(tagLists);
		tagsHotListForm.setList(tagLists);
		tagsHotListForm.setAllCount(tagLists.size());
		return mapping.findForward("success");
	}
}
