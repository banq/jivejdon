package com.jdon.jivejdon.presentation.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.Debug;

public class UpdateThreadToForumAction extends Action {
    public final static String module = UpdateThreadToForumAction.class.getName();

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forumIdStr  = request.getParameter("forumId");
        if (forumIdStr == null || forumIdStr.length() == 0){
            return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
        }
        String threadIdStr  = request.getParameter("threadId");
        if (threadIdStr == null || threadIdStr.length() == 0){
            return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
        }
        //need request
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
                .getService("forumMessageService", request);
        ForumService forumService = (ForumService) WebAppUtil
                .getService("forumService", request);

        try {
            Long threadId = Long.parseLong(threadIdStr);
            Long forumId = Long.parseLong(forumIdStr);

             // 验证目标论坛是否存在
            Forum targetForum = forumService.getForum(forumId);
            if (targetForum == null) {
                return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
            }
            
            // 获取论坛帖子
            ForumThread forumThread = forumMessageService.getThread(threadId);
            if (forumThread == null) {
                return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
            }
            
            // 获取根消息
            ForumMessage rootMessage = forumThread.getRootMessage();
            if (rootMessage == null) {
                return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
            }
            
            // 调用moveForum方法移动帖子到新论坛
            forumThread.moveForum(rootMessage, targetForum);
            
            return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
        } catch (Exception e) {
            Debug.logError("UpdateThreadToForumAction error:" + e + " threadId=" + threadIdStr + " forumId=" + forumIdStr, module);
        }
        return actionMapping.findForward(FormBeanUtil.FORWARD_FAILURE_NAME);
    }
}