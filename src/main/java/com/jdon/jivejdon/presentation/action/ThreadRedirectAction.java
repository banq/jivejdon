package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelDispAction;

/**
 * 处理旧格式URL到新格式URL的301重定向
 * 将 /threadId.html 重定向到 /threadId-pinyinToken.html
 */
public class ThreadRedirectAction extends ModelDispAction {

    private ForumMessageQueryService forumMessageQueryService;

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, 
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String threadId  = request.getParameter("threadId");
        
        if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length() > 20) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        try {
            ForumThread forumThread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
            if (forumThread == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            // 获取拼音token
            String pinyinToken = forumThread.getPinyinToken();
            
            // 构建新的URL
            String newUrl = request.getContextPath() + "/" + threadId + pinyinToken + ".html";
            
            // 发送301永久重定向
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", newUrl);
            response.setHeader("Cache-Control", "max-age=31536000"); // 缓存一年
            response.setHeader("X-Robots-Tag", "noindex, nofollow"); // 不要让搜索引擎收录重定向URL
            
            return null; // 不需要转发，直接发送重定向响应
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    public ForumMessageQueryService getForumMessageQueryService() {
        if (forumMessageQueryService == null) {
            forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
                    this.servlet.getServletContext());
        }
        return forumMessageQueryService;
    }
}