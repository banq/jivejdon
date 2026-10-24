<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page import="java.util.ArrayList, java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="com.jdon.controller.model.PageIterator" %>
<%@ page import="com.jdon.controller.WebAppUtil" %>
<%@ page import="com.jdon.jivejdon.api.query.ForumMessageQueryService" %>
<%@ page import="com.jdon.jivejdon.domain.model.ForumThread" %>
<%@ page import="com.jdon.jivejdon.domain.model.query.ResultSort" %>
<%@ page import="com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%
String forumIdParam = request.getParameter("forumId");
String countParam = request.getParameter("count");
if (forumIdParam == null || !forumIdParam.matches("\\d{1,10}")) {
    throw new ServletException("A valid forumId is required.");
}

long forumId;
int count;
try {
    forumId = Long.parseLong(forumIdParam);
    count = countParam == null ? 5 : Integer.parseInt(countParam);
} catch (NumberFormatException e) {
    throw new ServletException("Invalid forumId or count.", e);
}
if (count < 1) {
    throw new ServletException("Count must be greater than zero.");
}

ForumMessageQueryService forumMessageQueryService =
        (ForumMessageQueryService) WebAppUtil.getService(
                "forumMessageQueryService", application);
ThreadListSpecForMod threadListSpec = new ThreadListSpecForMod();
ResultSort resultSort = new ResultSort();
resultSort.setOrder_DESCENDING();
threadListSpec.setResultSort(resultSort);

PageIterator pageIterator =
        forumMessageQueryService.getThreads(forumId, 0, count, threadListSpec);
List<ForumThread> forumThreads = new ArrayList<ForumThread>();
for (Object threadId : pageIterator.getKeys()) {
    ForumThread forumThread = forumMessageQueryService.getThread((Long) threadId);
    if (forumThread != null) {
        forumThreads.add(forumThread);
    }
}
request.setAttribute("threadNewList", forumThreads);
%>
<ul style="list-style-type:none;padding:0" itemscope itemtype="https://schema.org/ItemList">
<logic:iterate indexId="i" id="forumThread" name="threadNewList">
    <%@ include file="others/threadListCore.jsp" %>
</logic:iterate>
</ul>
