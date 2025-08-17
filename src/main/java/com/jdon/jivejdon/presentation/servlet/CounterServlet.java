package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.util.UtilValidate;

public class CounterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletContext servletContext;

    private ForumMessageQueryService forumMessageQueryService;
    private ThreadViewCounterJob threadViewCounterJob;

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
        String ua = req.getHeader("User-Agent");
        if (ua != null && ua.matches("(?i).*(bot|crawl|spider|curl|wget).*")) {
            return; // 忽略爬虫
        }

       String threadId = req.getParameter("threadId");
		if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length() > 20)
			 return; 

		try {
			String ip = req.getRemoteAddr();
			CompletableFuture.supplyAsync(() -> {
				return getForumMessageQueryService().getThread(Long.parseLong(threadId));
			}).thenAccept(forumThread -> {
				if (forumThread != null && !UtilValidate.isEmpty(ip))
					getThreadViewCounterJob().saveViewCounter(forumThread.addViewCount(ip));
			});
		} catch (Exception e) {

		}

        resp.setHeader("X-Robots-Tag", "noindex, nofollow");

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
