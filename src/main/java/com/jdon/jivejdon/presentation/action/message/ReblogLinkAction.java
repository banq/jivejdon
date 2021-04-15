package com.jdon.jivejdon.presentation.action.message;

import org.apache.struts.action.Action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.Debug;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.jivejdon.domain.model.property.Property;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.presentation.form.PropertysForm;
import com.jdon.jivejdon.api.property.TagService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import com.jdon.jivejdon.domain.model.ForumThread;

public class ReblogLinkAction extends Action {
    public final static String module = ReblogLinkAction.class.getName();

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PropertysForm df = (PropertysForm) actionForm;
        String threadFromId = request.getParameter("threadId");

        TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
        Collection<String> urls = df.getPropertys().stream().map(e -> e.getValue()).collect(Collectors.toList());
        for (String url : urls) {
            if (extracted(url)) {
                Long threadId = extractFromUrl(url, request);
                if (threadId != null)
                    othersService.saveReBlogLink(new OneOneDTO(Long.parseLong(threadFromId), threadId));

            }
        }
        Collection<Long> tos = othersService.getReBlogLink(Long.parseLong(threadFromId));
        if (tos != null && tos.size() != 0) {
            Collection<Property> props = new ArrayList<>();
            String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
            for (Long threadId : tos) {
                props.add(new Property("", domainUrl + "/" + Long.toString(threadId)));
            }
            df.setPropertys(props);
        } else
            df.getPropertys().add(new Property("", ""));
        return actionMapping.findForward("forward");
    }

    private boolean extracted(String url) {
        return url != null && !url.equals("");
    }

    private Long extractFromUrl(String url, HttpServletRequest request) {
        String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
        if (!url.startsWith(domainUrl)) {
            Debug.logError("domainUrl error:" + domainUrl, module);
            return null;
        }
        String threadId = url.replaceAll(domainUrl + "/", "");
        if (threadId == null) {
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

    private String includeUrl(Long threadId, HttpServletRequest request) {
        String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
        return domainUrl + "/" + threadId;

    }

}