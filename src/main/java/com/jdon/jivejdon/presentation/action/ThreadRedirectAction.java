package com.jdon.jivejdon.presentation.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.ModelDispAction;

/**
 * 处理旧格式URL到新格式URL的301重定向
 * 将 /threadId.html 重定向到 /threadId-pinyinToken.html
 */
public class ThreadRedirectAction extends ModelDispAction {

    private ForumMessageQueryService forumMessageQueryService;

    private TagService tagService;

    private Map<Long,String> pinyinResultCaches = new java.util.concurrent.ConcurrentHashMap<>();

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, 
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        String threadId  = request.getParameter("threadId");
        
        if (threadId == null || threadId.length() == 0 || !StringUtils.isNumeric(threadId) || threadId.length() > 20) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        try {
            // ForumThread forumThread = getForumMessageQueryService().getThread(Long.parseLong(threadId));
            // if (forumThread == null) {
            //     response.sendError(HttpServletResponse.SC_NOT_FOUND);
            //     return null;
            // }

            // // 获取拼音token
            // String pinyinToken = forumThread.getPinyinToken();

            String pinyinToken = getPinyinToken(Long.parseLong(threadId));
            if(pinyinToken == null || pinyinToken.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            
            // 构建新的URL
            String newUrl = request.getContextPath() + "/" + threadId + pinyinToken + ".html";
            
            // 发送301永久重定向
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", newUrl);
            response.setHeader("Cache-Control", "public, max-age=86400"); // 缓存1天即可            
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

    public TagService getTagService() {
        if (tagService == null) {
            tagService = (TagService) WebAppUtil.getService("othersService",
                    this.servlet.getServletContext());
        }
        return tagService;
    }

    private String getPinyinToken(long threadId){
        if (pinyinResultCaches.containsKey(threadId)) {
            return pinyinResultCaches.get(threadId);
        }

        String pinyinResultCache = null;

        String token = getTagService().getThreadToken(threadId);
		if (token == null || token.trim().isEmpty()) {
            // 使用getName()的前四个汉字转为拼音
            String name =  getForumMessageQueryService().getThreadName(threadId);
            if (name == null || name.trim().isEmpty()) {
                return "";
            }
            String pinyinFromName = com.jdon.jivejdon.util.PinyinUtils.toPinyinFromFirstFourChinese(name);
            if (pinyinFromName != null && !pinyinFromName.trim().isEmpty()) {
                pinyinResultCache = pinyinFromName.startsWith("-") ? pinyinFromName : "-" + pinyinFromName;
                pinyinResultCaches.put(threadId, pinyinResultCache);
                return pinyinResultCache;
            }
            return "";
        }
        String pinyinResult = com.jdon.jivejdon.util.PinyinUtils.toPinyin(token);
        pinyinResultCache = pinyinResult.startsWith("-") ? pinyinResult : "-" + pinyinResult;
        pinyinResultCaches.put(threadId, pinyinResultCache);
		return pinyinResultCache;
    }
}