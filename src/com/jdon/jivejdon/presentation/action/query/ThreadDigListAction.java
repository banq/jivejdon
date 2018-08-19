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
package com.jdon.jivejdon.presentation.action.query;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.manager.mapreduce.ThreadDigList;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.strutsutil.ModelListForm;

public class ThreadDigListAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelListForm threadListForm = (ModelListForm) form;
		ThreadApprovedNewList threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil.getComponentInstance("threadApprovedNewList",
				this.servlet.getServletContext());
		ThreadDigList messageDigList = threadApprovedNewList.getThreadDigList();
		Collection<ForumThread> digThreads = messageDigList.getDigs();
		threadListForm.setList(digThreads);
		threadListForm.setAllCount(digThreads.size());
		return mapping.findForward("success");
	}
}
