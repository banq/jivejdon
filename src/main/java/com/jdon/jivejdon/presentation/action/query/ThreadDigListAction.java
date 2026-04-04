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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.strutsutil.ModelListForm;
public class ThreadDigListAction extends Action {
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	private ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
		    forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getComponentInstance("forumMessageQueryService",
					this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext());

		return threadApprovedNewList;
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int DigsListMAXSize = 30;
		String wSize = request.getParameter("wSize");
		if (wSize != null && wSize.length() > 0) {
			DigsListMAXSize = Integer.parseInt(wSize);
		}
		ModelListForm threadListForm = (ModelListForm) form;
		List<Long> sortedWindows = getThreadApprovedNewList().getApprovedThreads(0, DigsListMAXSize).stream()
			.sorted(new DigCountComparator(getForumMessageQueryService())).collect(Collectors.toList());
		

		// 根据排序后的 threadId 获取 ForumThread
		Collection<ForumThread> digThreads = sortedWindows.stream()
				.map(e -> getForumMessageQueryService().getThread(e))
				.collect(Collectors.toList());

		threadListForm.setList(digThreads);
		threadListForm.setAllCount(digThreads.size());
		return mapping.findForward("success");
	}

	/**
	 * 按照 ForumThread 的 getDigCount() 结果排序的内部类
	 */
	private static class DigCountComparator implements Comparator<Long> {
		private final ForumMessageQueryService forumMessageQueryService;

		public DigCountComparator(ForumMessageQueryService forumMessageQueryService) {
			this.forumMessageQueryService = forumMessageQueryService;
		}

		@Override
		public int compare(Long threadId1, Long threadId2) {
			if (threadId1.longValue() == threadId2.longValue())
				return 0;
			
			ForumThread thread1 = forumMessageQueryService.getThread(threadId1);
			ForumThread thread2 = forumMessageQueryService.getThread(threadId2);

			// 检查 thread1 和 thread2 是否为空
			if (thread1 == null && thread2 == null) {
				return 0;
			} else if (thread1 == null) {
				return 1;
			} else if (thread2 == null) {
				return -1;
			}

			// 按照 getDigCount() 排序（降序）
			int digCount1 = thread1.getRootMessage().getMessagePropertysVO().getDigCount();
			int digCount2 = thread2.getRootMessage().getMessagePropertysVO().getDigCount();
			
			if (digCount1 > digCount2)
				return -1; // 降序排列
			else if (digCount1 < digCount2)
				return 1;
			else {
				// digCount 相同时，按 getViewCount 排序
				if (thread1.getViewCount() > thread2.getViewCount())
					return -1;
				else
					return 1;
			}
		}
	}
}
