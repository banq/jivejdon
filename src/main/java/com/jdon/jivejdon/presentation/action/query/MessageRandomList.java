package com.jdon.jivejdon.presentation.action.query;

import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;

public class MessageRandomList extends Action {
    private ForumMessageQueryService forumMessageQueryService;
    protected ForumService forumService;
    private final static ApprovedListSpec approvedListSpec  = new ApprovedListSpec();

    public ForumMessageQueryService getForumMessageQueryService() {
        if (forumMessageQueryService == null)
            forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
                    this.servlet.getServletContext());
        return forumMessageQueryService;
    }

    public ForumService getForumService() {
        if (forumService == null)
            forumService = (ForumService) WebAppUtil.getService("forumService",
                    this.servlet.getServletContext());
        return forumService;
    }

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        ThreadListSpec threadListSpec = null;
        ResultSort resultSort = new ResultSort();
        resultSort.setOrder_DESCENDING();
        threadListSpec = new ThreadListSpec();
        threadListSpec.setResultSort(resultSort);
        int allCount = getForumService().getThreadAllCount();
        if (allCount == 0)
            return actionMapping.findForward("failure");

        while (request.getAttribute("threadId") == null) {
            int start = ThreadLocalRandom.current().nextInt(allCount);
            PageIterator pi = getForumMessageQueryService().getThreads(start, 100, threadListSpec);
            while (pi.hasNext()) {
                Long threadId = (Long) pi.next();
                ForumThread thread = getForumMessageQueryService().getThread(threadId);
                if (approvedListSpec.isExcelledDiscuss(thread, 5) 
                        || approvedListSpec.isDigged(thread, 2)                      
                        || approvedListSpec.isTagged(thread, 2)
                        || approvedListSpec.isLinked(thread, 1)) {
                        request.setAttribute("threadId", threadId);
                }
            }
        }
        return actionMapping.findForward("success");
    }

}
