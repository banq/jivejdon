/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.ProcessingInstruction;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.spi.component.sitemap.SitemapRepository;
import com.jdon.jivejdon.spi.component.sitemap.SitemapService;
import com.jdon.jivejdon.spi.component.sitemap.Url;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.RequestUtil;
import com.jdon.util.UtilValidate;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.WireFeedOutput;

public class RSSGenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomizedThrottle customizedThrottle;
	private String channel_title = "";
	private String channel_des = "";
	private ServletContext servletContext;
	private final int LENGTH = 10;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String title = config.getInitParameter("title");
		if (!UtilValidate.isEmpty(title)) {
			channel_title = title;
		}

		String description = config.getInitParameter("description");
		if (!UtilValidate.isEmpty(description)) {
			channel_des = description;
		}

		servletContext = config.getServletContext();

	}

	protected boolean checkModifiedEtagFilter(HttpServletRequest request, HttpServletResponse response) {
		int expire = 1 * 60 * 60;
		if (!ToolsUtil.checkHeaderCacheForum(expire, this.getServletContext(), request, response)) {
			return false;
		}
		return true;
	}

	// private boolean checkSpamHit(HttpServletRequest request) {
	// if (customizedThrottle == null) {
	// customizedThrottle = (CustomizedThrottle)
	// WebAppUtil.getComponentInstance("customizedThrottle",
	// servletContext);
	// }
	// HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "RSS");
	// return customizedThrottle.processHitFilter(hitKey);
	// }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!checkModifiedEtagFilter(request, response)) {
			return;
		}

		// if (!checkSpamHit(request)) {
		// ((HttpServletResponse) response).sendError(404);
		// return;
		// }

		try {

			SyndFeed feed = new SyndFeedImpl();
			// rss_0.90, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0 rss_2.0
			// or atom_0.3
			String rssType = request.getParameter("rssType");
			if (UtilValidate.isEmpty(rssType)) {
				rssType = "rss_2.0";
			}
			feed.setFeedType(rssType);

			String url = RequestUtil.getAppURL(request);
			feed.setTitle(channel_title);
			feed.setLink(url + "/");
			feed.setDescription(channel_des);
			feed.setEncoding("UTF-8");

			if (request.getParameter("forumId") != null) {
				String forumId = request.getParameter("forumId");
				if (!StringUtils.isNumeric(forumId) || forumId.length() > 3) {
					return;
				}
				List<SyndEntrySorted> entries = addForums(request, url, forumId);

				entries.addAll(this.addsitemap(request, url));
				Collections.sort(entries);
				Collections.reverse(entries);
				feed.setEntries(entries.subList(0, LENGTH));
			} else if (request.getParameter("tagID") != null) {
				String tagID = request.getParameter("tagID");
				if (!StringUtils.isNumeric(tagID) || tagID.length() > 10) {
					return;
				}

				List<SyndEntrySorted> entries = addTagThreads(request, url, tagID);
				entries.addAll(this.addsitemap(request, url));
				Collections.sort(entries);
				Collections.reverse(entries);
				feed.setEntries(entries.subList(0, LENGTH));

			} else {
				// it is threads
				List<SyndEntrySorted> entries = addThreads(request, url);

				entries.addAll(this.addsitemap(request, url));
				Collections.sort(entries);
				Collections.reverse(entries);
				feed.setEntries(entries.subList(0, LENGTH));
				// }
			}

			// request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/xml; charset=utf-8");
			Writer writer = response.getWriter();

			WireFeedOutput feedOutput = new WireFeedOutput();
			Document doc = feedOutput.outputJDom(feed.createWireFeed());
			// create the XSL processing instruction
			Map<String, String> xsl = new HashMap<>();
			xsl.put("href", "/js/rss-style.xsl");
			xsl.put("type", "text/xsl");
			ProcessingInstruction pXsl = new ProcessingInstruction("xml-stylesheet", xsl);
			doc.addContent(0, pXsl);

			// write the document to the servlet response
			XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
			outputter.output(doc, writer);

			// SyndFeedOutput output = new SyndFeedOutput();
			// // output.output(feed, writer);

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<SyndEntrySorted> addForums(HttpServletRequest request, String url, String forumId) {

		List<SyndEntrySorted> entries = new ArrayList<SyndEntrySorted>();

		PageIterator pi = null;

		ThreadListSpec threadListSpec = new ThreadListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		threadListSpec.setResultSort(resultSort);

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", this.getServletContext());

		String tagID = request.getParameter("tagID");
		if (tagID == null || !StringUtils.isNumeric(tagID) || tagID.length() > 10) {
			return entries;
		}
		pi = forumMessageQueryService.getThreads(Long.parseLong(forumId), 0, LENGTH,
				resultSort);
		while (pi.hasNext()) {
			Long threadId = (Long) pi.next();
			ForumThread thread = getForumThread(request, threadId);
			if (thread != null)
				addMessage(url, entries, thread.getRootMessage(), request);
		}

		return entries;
	}

	private List<SyndEntrySorted> addTagThreads(HttpServletRequest request, String url, String tagID) {

		List<SyndEntrySorted> entries = new ArrayList<SyndEntrySorted>();

		PageIterator pi = null;

		ThreadListSpec threadListSpec = new ThreadListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		threadListSpec.setResultSort(resultSort);

		TagService othersService = (TagService) WebAppUtil.getService("othersService",
				this.getServletContext());

		Long tagIDL = Long.parseLong(tagID);
		ThreadTag tag = othersService.getThreadTag(tagIDL);
		if (tag == null)
			return entries;

		pi = othersService.getTaggedThread(tagIDL, 0, LENGTH);
		while (pi.hasNext()) {
			Long threadId = (Long) pi.next();
			ForumThread thread = getForumThread(request, threadId);
			if (thread != null)
				addMessage(url, entries, thread.getRootMessage(), request);
		}

		return entries;
	}

	private List<SyndEntrySorted> addThreads(HttpServletRequest request, String url) {

		List<SyndEntrySorted> entries = new ArrayList<SyndEntrySorted>();

		PageIterator pi = null;

		ThreadListSpec threadListSpec = new ThreadListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		threadListSpec.setResultSort(resultSort);

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", this.getServletContext());
		pi = forumMessageQueryService.getThreads(0, LENGTH, threadListSpec);

		while (pi.hasNext()) {
			Long threadId = (Long) pi.next();
			ForumThread thread = getForumThread(request, threadId);
			if (thread != null)
				addMessage(url, entries, thread.getRootMessage(), request);
		}

		return entries;

	}

	private List<SyndEntrySorted> addsitemap(HttpServletRequest request, String url) {
		String startS = request.getParameter("start");
		if (UtilValidate.isEmpty(startS)) {
			startS = "0";
		}
		int start = Integer.parseInt(startS);

		String countS = request.getParameter("count");
		int count = LENGTH;
		if (!UtilValidate.isEmpty(countS)) {
			count = Integer.parseInt(countS);
		}

		List<SyndEntrySorted> entries = new ArrayList<SyndEntrySorted>();

		PageIterator pi = null;
		SitemapRepository sitemapRepository = (SitemapRepository) WebAppUtil.getComponentInstance("sitemapRepository",
				servletContext);
		try {
			pi = sitemapRepository.getUrls(start, count);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SitemapService entityFactory = (SitemapService) WebAppUtil.getService("sitemapService", servletContext);

		while (pi.hasNext()) {
			Url urlsm = (Url) entityFactory.getUrl((Long) pi.next());
			addSitemapUrl(url, entries, urlsm, request);
		}

		return entries;

	}

	private void addSitemapUrl(String url, List<SyndEntrySorted> entries, Url urlsm, HttpServletRequest request) {
		try {
			SyndEntrySorted entry = new SyndEntrySorted();
			entry.setTitle(urlsm.getName());
			entry.setLink(urlsm.getIoc());

			Date publishedDate = Constants.parseDateTime(urlsm.getCreationDate());
			entry.setPublishedDate(publishedDate);
			entry.setUpdatedDate(publishedDate);

			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue("");
			entry.setDescription(description);

			entries.add(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ForumThread getForumThread(HttpServletRequest request, Long key) {
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", this.getServletConfig().getServletContext());
		try {
			return forumMessageQueryService.getThread(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addMessage(String url, List<SyndEntrySorted> entries, ForumMessage message,
			HttpServletRequest request) {
		try {
			SyndEntrySorted entry = new SyndEntrySorted();
			entry.setTitle(message.getMessageVO().getSubject());
			entry.setLink(getItemLink(url, message, request));

			Date publishedDate = Constants.parseDateTime(message.getCreationDate());
			entry.setPublishedDate(publishedDate);
			entry.setAuthor(message.getAccount().getUsername());
			Date updateDate = Constants.parseDateTime(message.getModifiedDate());
			entry.setUpdatedDate(updateDate);

			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(message.getMessageVO().getShortBody(300));
			entry.setDescription(description);

			if (message.isRoot()) {
				List<SyndCategory> cats = new ArrayList<SyndCategory>();
				for (Object o : message.getForumThread().getTags()) {
					ThreadTag tag = (ThreadTag) o;
					SyndCategory cat = new SyndCategoryImpl();

					cat.setTaxonomyUri(url + "/tag/" + tag.getTagID() + "/");
					cat.setName(tag.getTitle());
					cats.add(cat);
				}
				entry.setCategories(cats);
			}
			entries.add(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getItemLink(String url, ForumMessage message, HttpServletRequest request) {

		String relativeLink = "/" + message.getForumThread().getThreadId().toString()
				+ message.getForumThread().getPinyinToken() + ".html";
		return url + relativeLink;

	}

	public void destroy() {
		super.destroy();
	}

	private class SyndEntrySorted extends SyndEntryImpl implements Comparable<SyndEntry> {

		private static final long serialVersionUID = 1L;

		@Override
		public int compareTo(SyndEntry syndEntry2) {
			return getPublishedDate().compareTo(syndEntry2.getPublishedDate());
		}

	}
}