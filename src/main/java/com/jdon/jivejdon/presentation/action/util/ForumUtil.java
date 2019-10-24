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
package com.jdon.jivejdon.presentation.action.util;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import javax.servlet.ServletContext;

public class ForumUtil {


	public static long getForumsLastModifiedDate(ServletContext sc) {
		long restult = System.currentTimeMillis();
		try {
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService)
					WebAppUtil.getService("forumMessageQueryService", sc);
			ResultSort resultSort = new ResultSort();
			resultSort.setOrder_DESCENDING();
			ThreadListSpec threadListSpec = new ThreadListSpec();
			threadListSpec.setResultSort(resultSort);
			PageIterator pageIterator = forumMessageQueryService.getThreads(0, 1,
					threadListSpec);
			if (pageIterator.hasNext()) {
				ForumThread thread = forumMessageQueryService.getThread((Long) pageIterator.next
						());
				restult = thread.getCreationDate2();
			}
		} catch (Exception ex) {
		}
		return restult;
	}

}
