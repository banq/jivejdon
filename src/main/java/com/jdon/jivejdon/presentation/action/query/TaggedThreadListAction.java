/*
 * Copyright 2003-2005 the original author or authors.
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

import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigComparator;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class TaggedThreadListAction extends ModelListAction {
	private final static String module = TaggedThreadListAction.class.getName();
	private ForumMessageQueryService forumMessageQueryService;
	private final static int COUNT = 30;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

		/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 * .HttpServletRequest, int, int)
	 */
		public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
			TagService othersService = (TagService) WebAppUtil.getService("othersService",
					this.servlet.getServletContext());
			String tagID = request.getParameter("tagID");
			if (tagID == null || !StringUtils.isNumeric(tagID) || tagID.length() > 10) {
				return new PageIterator();
			}

			Long tagIDL = Long.parseLong(tagID);
			ThreadTag tag = othersService.getThreadTag(tagIDL);
			if (tag == null)
				return new PageIterator();

			request.setAttribute("TITLE", tag.getTitle());
			request.setAttribute("threadTag", tag);

			PageIterator pi = othersService.getTaggedThread(tagIDL, start, count);
			TreeSet<Long> threadIds = Arrays.stream(pi.getKeys())
                .map(id -> (Long) id)
                .filter(id -> getForumMessageQueryService().getThread(id) != null)
                .collect(Collectors.toCollection(() -> new TreeSet<>(new ThreadDigComparator(getForumMessageQueryService()))));
            return new PageIterator(pi.getAllCount(), threadIds.toArray());
			
		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 * .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumThread thread = null;
		try {
			thread = getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			Debug.logError("getThread error:" + e, module);
		}
		return thread;
	}

}
