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
package com.jdon.jivejdon.presentation.sitemap;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod;
import com.jdon.jivejdon.presentation.action.util.ForumUtil;
import com.jdon.jivejdon.spi.component.sitemap.SitemapHelper;
import com.jdon.jivejdon.spi.component.sitemap.SitemapRepository;
import com.jdon.jivejdon.spi.component.sitemap.SitemapService;
import com.jdon.jivejdon.spi.component.sitemap.Url;
import com.jdon.jivejdon.spi.component.sitemap.UrlSet;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

public class SitemapServlet extends HttpServlet {
	private static final int expire = 24 * 60 * 60;
	private final static Logger logger = LogManager
			.getLogger(SitemapServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomizedThrottle customizedThrottle;
	private long lastModifiedDate = 0L;
	private ServletContext servletContext;

	private String threadUrl;
	private CharArrayWriter charArrayWriterBuffer;


	private Collection<UrlSet> genThreadUrlSet(HttpServletRequest request) {
		Collection<UrlSet> urlsets = new ArrayList();
		try {

			ForumService forumService = (ForumService) WebAppUtil
					.getService("forumService", servletContext);
			int allCount = forumService.getThreadAllCount();
			int start = 0;
			int numPages = 0;
			int count = 180;
			if (allCount != count) {
				numPages = (int) Math.ceil((double) allCount / (double) count);
			} else {
				numPages = 1;
			}

			for (int i = 1; i <= numPages; i++) {
				PageIterator pi = getThreadPI(request, start, count);
				Long threadId = null;
				while (pi.hasNext()) {
					threadId = (Long) pi.next();
					urlsets.add(new UrlSet(threadUrl + threadId + ".html"));
				}
				if (threadId == null) {
					continue;
				}
				start = start + count;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return urlsets;
	}

	private Collection<UrlSet> genUrlSet(HttpServletRequest request) {

		try {
			int startInt = 0;
			Collection<UrlSet> urlsets = new ArrayList();
			PageIterator pi = null;
			SitemapRepository sitemapRepository = (SitemapRepository) WebAppUtil
					.getComponentInstance("sitemapRepository", servletContext);
			SitemapService entityFactory = (SitemapService) WebAppUtil
					.getService("sitemapService", servletContext);
			pi = sitemapRepository.getUrls(startInt, 180);
			while (pi.hasNext()) {
				Url url = entityFactory.getUrl((Long) pi.next());
				urlsets.add(new UrlSet(url.getIoc()));
			}
			return urlsets;

		} catch (Exception e) {
			logger.error(e);
			return new ArrayList();
		}

	}

	private PageIterator getThreadPI(HttpServletRequest request, int startInt, int count) {
		try {
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
					.getService("forumMessageQueryService", servletContext);
			ResultSort resultSort = new ResultSort();
			resultSort.setOrder_ASCENDING();
			ThreadListSpec threadListSpec = new ThreadListSpecForMod();
			threadListSpec.setResultSort(resultSort);

			return forumMessageQueryService.getThreads(startInt, count,
					threadListSpec);
		} catch (Exception e) {
			logger.error(e);
			return new PageIterator();
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();

		String threadUrl = config.getInitParameter("threadUrl");
		if (!UtilValidate.isEmpty(threadUrl)) {
			this.threadUrl = threadUrl;
		}

	}

	private CharArrayWriter outUrls(Collection<UrlSet> urls) {
		CharArrayWriter writer = new CharArrayWriter();
		try {
			SitemapHelper.writeUrlset(writer, urls.iterator());
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}

		return writer;

	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long modelLastModifiedDate = ForumUtil.getForumsLastModifiedDate(
				this.getServletContext());
		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request,
				response)) {
			return;
		}

		// if (!checkSpamHit(request)) {
		// 	((HttpServletResponse) response).sendError(404);
		// 	return;
		// }

		if (lastModifiedDate == 0 || lastModifiedDate < modelLastModifiedDate) {
			lastModifiedDate = modelLastModifiedDate;
			clearLast();
		}

		try {
			if (this.charArrayWriterBuffer == null) {
				charArrayWriterBuffer = new CharArrayWriter();
				Collection<UrlSet> outUrlSet = new ArrayList<>();
				Collection<UrlSet> urlSet = genUrlSet(request);
				if (urlSet != null && !urlSet.isEmpty())
					outUrlSet.addAll(urlSet);
				Collection<UrlSet> threadUrlSet = genThreadUrlSet(request);
				if (threadUrlSet != null && !threadUrlSet.isEmpty())
					outUrlSet.addAll(threadUrlSet);
				charArrayWriterBuffer = outUrls(outUrlSet);

			}
			writeToResponse(response, charArrayWriterBuffer.toCharArray());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void clearLast() {
			this.charArrayWriterBuffer = null;

	}

	

	private void writeToResponse(HttpServletResponse response, char[] chararray) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/xml; charset=utf-8");
			Writer writer = response.getWriter();
			response.getWriter().write(chararray);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("writeToResponse" + e);
		}

	}

}
