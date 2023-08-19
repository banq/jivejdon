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

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.sitemap.Sitemap;
import com.jdon.jivejdon.spi.component.sitemap.SitemapHelper;
import com.jdon.jivejdon.spi.component.sitemap.SitemapRepository;
import com.jdon.jivejdon.spi.component.sitemap.SitemapService;
import com.jdon.jivejdon.spi.component.sitemap.Url;
import com.jdon.jivejdon.spi.component.sitemap.UrlSet;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod;
import com.jdon.jivejdon.presentation.action.util.ForumUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class SitemapServlet extends HttpServlet {
	private static final int expire = 24 * 60 * 60;
	private final static Logger logger = LogManager
			.getLogger(SitemapServlet.class);
	private static final int MAXCOUNT = 2000;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomizedThrottle customizedThrottle;
	private long lastModifiedDate = 0L;
	private ServletContext servletContext;

	private String sitemapUrl;
	private String threadUrl;
	private CharArrayWriter baosindex = null;
	private Map<Integer, CharArrayWriter> baos1s = new TreeMap();
	private Map<Integer, CharArrayWriter> baos2s = new TreeMap();

	private boolean checkSpamHit(HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil
					.getComponentInstance("customizedThrottle", servletContext);
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "SITEMAP");
		return customizedThrottle.processHitFilter(hitKey);
	}

	private Collection<Sitemap> genSitemaps(HttpServletRequest request) {
		Collection<Sitemap> sitemaps = new ArrayList();
		try {
			SitemapRepository sitemapRepository = (SitemapRepository) WebAppUtil
					.getComponentInstance("sitemapRepository", servletContext);
			try {
				PageIterator pi = sitemapRepository.getUrls(0, 1);
				return genSitemaps(pi.getAllCount());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return sitemaps;
	}

	private Collection<Sitemap> genSitemaps(int allcount) {
		Collection<Sitemap> sitemaps = new ArrayList();
		try {
			int numPages = 0;
			int count = this.MAXCOUNT;
			if (allcount != count) {
				numPages = (int) Math.ceil((double) allcount / (double) count);
			} else {
				numPages = 1;
			}
			int start = 0;
			SitemapRepository sitemapRepository = (SitemapRepository) WebAppUtil
					.getComponentInstance("sitemapRepository", servletContext);
			SitemapService entityFactory = (SitemapService) WebAppUtil
					.getService("sitemapService", servletContext);
			for (int i = 1; i <= numPages; i++) {
				try {
					PageIterator pi = sitemapRepository.getUrls(start,
							this.MAXCOUNT);
					if (pi.hasNext()) {
						Url url = entityFactory.getUrl((Long) pi.next());
						String lastUpdateDate = url.getCreationDate()
								.substring(0, 10);
						Sitemap sitemap = new Sitemap(sitemapUrl + "/2/"
								+ start + ".xml", lastUpdateDate);
						sitemaps.add(sitemap);
					}
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				start = start + count;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return sitemaps;
	}


	private Collection<Sitemap> genThreadSitemaps(HttpServletRequest request) {
		Collection<Sitemap> sitemaps = new ArrayList();
		try {
			PageIterator pi = getThreadPI(request, 0);
			int count = MAXCOUNT;
			int allCount = pi.getAllCount();
			int numPages = 0;
			if (allCount != count) {
				numPages = (int) Math.ceil((double) allCount / (double) count);
			} else {
				numPages = 1;
			}
			int start = 0;

			for (int i = 1; i <= numPages; i++) {
				pi = getThreadPI(request, start);
				Long threadId = null;
				while (pi.hasNext()) {
					threadId = (Long) pi.next();
				}
				ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
						.getService("forumMessageQueryService", request);
				ForumThread thread = forumMessageQueryService
						.getThread(threadId);
				if (thread != null) {
					String lastUpdateDate = thread.getState().getModifiedDate()
							.substring(0, 10);
					Sitemap sitemap = new Sitemap(sitemapUrl + "/1/"
							+ start + ".xml", lastUpdateDate);
					sitemaps.add(sitemap);
				}
				start = start + count;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return sitemaps;
	}

	private Collection<UrlSet> genThreadUrlSet(HttpServletRequest request) {
		int startInt = 0;
		try {
			String start = request.getParameter("start");
			if (start != null) {
				startInt = Integer.parseInt(start);
			}
			Collection<UrlSet> urlsets = new ArrayList();
			PageIterator pi = getThreadPI(request, startInt);
			while (pi.hasNext()) {
				Long threadId = (Long) pi.next();
				urlsets.add(new UrlSet(threadUrl + threadId + ".html", "1"));
			}
			return urlsets;

		} catch (Exception e) {
			logger.error(e);
			return new ArrayList();
		}

	}

	private Collection<UrlSet> genUrlSet(HttpServletRequest request) {

		try {
			int startInt = 0;
			String start = request.getParameter("start");
			if (start != null) {
				startInt = Integer.parseInt(start);
			}
			Collection<UrlSet> urlsets = new ArrayList();
			PageIterator pi = null;
			SitemapRepository sitemapRepository = (SitemapRepository) WebAppUtil
					.getComponentInstance("sitemapRepository", servletContext);
			SitemapService entityFactory = (SitemapService) WebAppUtil
					.getService("sitemapService", servletContext);
			pi = sitemapRepository.getUrls(startInt, MAXCOUNT);
			while (pi.hasNext()) {
				Url url = entityFactory.getUrl((Long) pi.next());
				urlsets.add(new UrlSet(url.getIoc(), "1"));
			}
			return urlsets;

		} catch (Exception e) {
			logger.error(e);
			return new ArrayList();
		}

	}

	private PageIterator getThreadPI(HttpServletRequest request, int startInt) {
		try {
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
					.getService("forumMessageQueryService", servletContext);
			ResultSort resultSort = new ResultSort();
			resultSort.setOrder_ASCENDING();
			ThreadListSpec threadListSpec = new ThreadListSpecForMod();
			threadListSpec.setResultSort(resultSort);

			return forumMessageQueryService.getThreads(startInt, MAXCOUNT,
					threadListSpec);
		} catch (Exception e) {
			logger.error(e);
			return new PageIterator();
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();
		String domainurl = config.getInitParameter("sitemapUrl");
		if (!UtilValidate.isEmpty(domainurl)) {
			this.sitemapUrl = domainurl;
		}

		String threadUrl = config.getInitParameter("threadUrl");
		if (!UtilValidate.isEmpty(threadUrl)) {
			this.threadUrl = threadUrl;
		}

	}

	private CharArrayWriter outIndex(Collection<Sitemap> sitemaps) {
		CharArrayWriter writer = new CharArrayWriter();
		try {
			SitemapHelper.writeSitemapIndex(writer, sitemaps.iterator());
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
		// if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request,
		// 		response)) {
		// 	return;
		// }

		if (!checkSpamHit(request)) {
			((HttpServletResponse) response).sendError(404);
			return;
		}

		if (lastModifiedDate == 0 || lastModifiedDate < modelLastModifiedDate) {
			lastModifiedDate = modelLastModifiedDate;
			clearLast();
		}

		try {
			String sub = request.getParameter("sub");
			if (sub == null) {
				if (baosindex == null) {
					baosindex = new CharArrayWriter();
					Collection<Sitemap> sitemaps = new ArrayList();
					Collection<Sitemap> threadSitemaps = genThreadSitemaps(request);
					if (!threadSitemaps.isEmpty())
						sitemaps.addAll(genThreadSitemaps(request));
					Collection<Sitemap> genSitemaps = genSitemaps(request);
					if (!genSitemaps.isEmpty())
						sitemaps.addAll(genSitemaps);

					if (!sitemaps.isEmpty())
						baosindex = outIndex(sitemaps);
				}
				writeToResponse(response, baosindex.toCharArray());

			} else {
				int startInt = 0;
				String start = request.getParameter("start");
				if (start != null) {
					startInt = Integer.parseInt(start);
				}
				if (sub.equals("1")) {
					CharArrayWriter baos1 = baos1s.get(startInt);
					if (baos1 == null) {
						baos1 = new CharArrayWriter();
						Collection threadUrlSet = genThreadUrlSet(request);
						if (!threadUrlSet.isEmpty()) {
							baos1 = outUrls(threadUrlSet);
							baos1s.put(startInt, baos1);
						}

					}
					writeToResponse(response, baos1.toCharArray());
				} else if (sub.equals("2")) {
					CharArrayWriter baos2 = baos2s.get(startInt);
					if (baos2 == null) {
						baos2 = new CharArrayWriter();
						Collection urlSet = genUrlSet(request);
						if (!urlSet.isEmpty()) {
							baos2 = outUrls(genUrlSet(request));
							baos2s.put(startInt, baos2);
						}
					}
					writeToResponse(response, baos2.toCharArray());
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void clearLast() {
		if (!baos1s.isEmpty()) {
			int lastKey = (Integer) getLastElement(this.baos1s.keySet());
			this.baos1s.remove(lastKey);
		}

		if (!baos2s.isEmpty()) {
			int lastKey = (Integer) getLastElement(this.baos2s.keySet());
			this.baos2s.remove(lastKey);
		}

	}

	private Object getLastElement(final Collection c) {
		if (c.isEmpty())
			return null;
		final Iterator itr = c.iterator();
		Object lastElement = itr.next();
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		return lastElement;
	}

	private void writeToResponse(HttpServletResponse response, char[] chararray) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(chararray);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("writeToResponse" + e);
		}

	}

}
