package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;

public class ThreadRandomList extends ModelListAction {
    private final static String module = ThreadRandomList.class.getName();
    private ForumMessageQueryService forumMessageQueryService;
    protected ForumService forumService;

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

    @Override
    public PageIterator getPageIterator(HttpServletRequest arg0, int start, int count) {
        ThreadListSpec threadListSpec = null;
        ResultSort resultSort = new ResultSort();
        resultSort.setOrder_DESCENDING();
        threadListSpec = new ThreadListSpec();
        threadListSpec.setResultSort(resultSort);
        int allCount = getForumService().getThreadAllCount();
        if (allCount == 0)
            return new PageIterator();
        int pageCount = allCount / count;
        int nowPage = (int) (Math.random() * pageCount);
        start = nowPage * count;
        return getForumMessageQueryService().getThreads(start, count, threadListSpec);
    }

    public Object findModelIFByKey(HttpServletRequest request, Object key) {
        ForumThread thread = null;
        try {
            thread = getForumMessageQueryService().getThread((Long) key);
        } catch (Exception e) {
            Debug.logError("getThread error:" + e, module);
        }
        return thread;
    }
}
