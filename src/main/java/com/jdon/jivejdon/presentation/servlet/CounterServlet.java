package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.util.UtilValidate;

public class CounterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletContext servletContext;

    private ForumMessageQueryService forumMessageQueryService;
    private ThreadViewCounterJob threadViewCounterJob;

    private static final Pattern BOT_PATTERN = Pattern.compile(
            "bot|spider|crawl|slurp|crawler|archiver|mediapartners|lighthouse|curl|wget|" +
                    "gptbot|claudebot|perplexity|bytespider|ccbot|applebot", 
            Pattern.CASE_INSENSITIVE);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletContext = config.getServletContext();
    }   

    public ForumMessageQueryService getForumMessageQueryService() {
        if (forumMessageQueryService == null)
            forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
                    servletContext);
        return forumMessageQueryService;
    }


    private ThreadViewCounterJob getThreadViewCounterJob() {
        if (threadViewCounterJob == null)
            threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
                    servletContext);
        return threadViewCounterJob;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("X-Robots-Tag", "noindex, nofollow");

        String ua = req.getHeader("User-Agent");
        if (ua != null && BOT_PATTERN.matcher(ua).find()) {
            return; // 忽略爬虫
        }

        String threadId = req.getParameter("threadId");
        if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length() > 20)
            return;

        String referer = req.getHeader("Referer");
        if (referer == null || !referer.contains(threadId))
            return;


		try {
			String ip = req.getRemoteAddr();
			Long threadIdLong = Long.parseLong(threadId);
            CompletableFuture.runAsync(() -> {
                Long modelLastModifiedDate = getForumMessageQueryService().getThreadModifiedDate(threadIdLong);
                long oneYearInMillis = 100L * 24 * 60 * 60 * 1000;
                long currentTime = System.currentTimeMillis();

                if (currentTime - modelLastModifiedDate < oneYearInMillis) {
                    ForumThread forumThread = getForumMessageQueryService().getThread(threadIdLong);
                    if (forumThread != null && !UtilValidate.isEmpty(ip)) {
                        getThreadViewCounterJob().saveViewCounter(forumThread.getViewCounter(), ip);
                    }
                } else {
                    getThreadViewCounterJob().saveAndIncrement(threadIdLong, ip);
                }
            });
		} catch (Exception e) {

		}

       

        resp.setContentType("image/gif");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        byte[] gif = {
                71, 73, 70, 56, 57, 97, 1, 0, 1, 0, (byte) 128, 0, 0,
                0, 0, 0, (byte) 255, (byte) 255, (byte) 255, 33, (byte) 249, 4, 1, 0, 0, 0, 0,
                44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59
        };

        resp.getOutputStream().write(gif);
    }

  
}
