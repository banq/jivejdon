package com.jdon.jivejdon.presentation.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.Debug;

public class UpdateThreadNameAction extends Action {
    public final static String module = UpdateThreadNameAction.class.getName();

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String name  = request.getParameter("name");
        if (name == null || name.length() == 0){
            actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
        }
        String threadId  = request.getParameter("threadId");
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
                .getService("forumMessageService", this.servlet.getServletContext());
        try {
            forumMessageService.updateThreadName(Long.parseLong(threadId), name);
            return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
        } catch (Exception e) {
            Debug.logError("UpdateThreadNameAction error:" + e + " threadId=" + threadId, module);
        }
        return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
    }
}
