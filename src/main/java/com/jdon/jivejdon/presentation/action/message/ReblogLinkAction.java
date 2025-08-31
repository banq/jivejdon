package com.jdon.jivejdon.presentation.action.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.presentation.form.PropertysForm;
import com.jdon.util.Debug;

public class ReblogLinkAction extends Action {
    public final static String module = ReblogLinkAction.class.getName();

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PropertysForm df = (PropertysForm) actionForm;
        String threadFromId = request.getParameter("threadId");
        TagService othersService = (TagService) WebAppUtil.getService("othersService", 
                this.servlet.getServletContext());
        Collection<String> urls = df.getPropertys().stream().map(e -> e.getValue()).collect(Collectors.toList());
        if (urls.stream().anyMatch(url -> url != null)) {
            othersService.deleteReBlogLink(Long.parseLong(threadFromId));
            for (String url : urls)
                saveReblogLinkItem(Long.parseLong(threadFromId), url, request);
        }
        Collection<Long> tos = othersService.getReBlogLink(Long.parseLong(threadFromId));
        if (tos != null && tos.size() != 0) {
            Collection<Property> props = new ArrayList<>();
            String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
            ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
            for (Long threadId : tos) {
                ForumThread thread = forumMessageService.getThread(threadId);
                props.add(new Property("", domainUrl + "/" + Long.toString(threadId) + (thread != null ? thread.getPinyinToken() : "") + ".html"));
            }
            df.setPropertys(props);
        } else
            df.getPropertys().add(new Property("", ""));
        return actionMapping.findForward("forward");
    }

    private void saveReblogLinkItem(Long threadFromId, String url, HttpServletRequest request) {
        if (url != null && !url.equals("")) {
            Long threadId = extractFromUrl(url, request);
            if (threadId != null) {
                TagService othersService = (TagService) WebAppUtil.getService("othersService", 
                        this.servlet.getServletContext());
                othersService.saveReBlogLink(new OneOneDTO(threadFromId, threadId));
            }

        }

    }

    private Long extractFromUrl(String url, HttpServletRequest request) {
        String threadId = null;
        try {
            URI uri = new URI(url);
            String path = uri.getPath().replaceAll("/", "").replaceAll(".html", "");
            // 处理新格式：数字-字符串.html 或旧格式：数字.html
            if (path.contains("-")) {
                // 新格式：提取连字符前的数字部分
                threadId = path.split("-")[0];
            } else {
                // 旧格式：直接使用整个路径
                threadId = path;
            }
        } catch (URISyntaxException e) {
            Debug.logError("Url error:" + url, module);
        }
        if (threadId == null || !threadId.matches("\\d+")) {
            Debug.logError("threadId error:" + threadId, module);
            return null;
        }
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
                request);
        ForumThread thread = forumMessageService.getThread(Long.parseLong(threadId));
        if (thread == null) {
            Debug.logError("thread error:" + threadId, module);
            return null;
        }
        return thread.getThreadId();

    }

}