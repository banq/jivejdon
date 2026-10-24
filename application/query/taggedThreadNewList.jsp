<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.ArrayList, java.util.List, java.util.TreeSet" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="com.jdon.controller.WebAppUtil" %>
<%@ page import="com.jdon.controller.model.PageIterator" %>
<%@ page import="com.jdon.jivejdon.api.property.TagService" %>
<%@ page import="com.jdon.jivejdon.api.query.ForumMessageQueryService" %>
<%@ page import="com.jdon.jivejdon.domain.model.ForumThread" %>
<%@ page import="com.jdon.jivejdon.domain.model.property.ThreadTag" %>
<%@ page import="com.jdon.jivejdon.spi.component.mapreduce.ThreadDigComparator" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%
String tagIdParam = request.getParameter("tagID");
String countParam = request.getParameter("count");
if (tagIdParam == null || !tagIdParam.matches("\\d{1,10}")) {
    throw new ServletException("A valid tagID is required.");
}

long tagId;
int count;
try {
    tagId = Long.parseLong(tagIdParam);
    count = countParam == null ? 6 : Integer.parseInt(countParam);
} catch (NumberFormatException e) {
    throw new ServletException("Invalid tagID or count.", e);
}
if (count < 1 || count > 30) {
    throw new ServletException("Count must be between 1 and 30.");
}

TagService tagService =
        (TagService) WebAppUtil.getService("othersService", application);
ThreadTag threadTag = tagService.getThreadTag(tagId);
if (threadTag == null) {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return;
}

ForumMessageQueryService forumMessageQueryService =
        (ForumMessageQueryService) WebAppUtil.getService(
                "forumMessageQueryService", application);
PageIterator pageIterator = tagService.getTaggedThread(tagId, 0, count);
TreeSet<Long> sortedThreadIds = new TreeSet<Long>(
        new ThreadDigComparator(forumMessageQueryService));
for (Object threadId : pageIterator.getKeys()) {
    sortedThreadIds.add((Long) threadId);
}

List<ForumThread> forumThreads = new ArrayList<ForumThread>();
for (Long threadId : sortedThreadIds) {
    ForumThread forumThread = forumMessageQueryService.getThread(threadId);
    if (forumThread != null) {
        forumThreads.add(forumThread);
    }
}
request.setAttribute("threadTag", threadTag);
request.setAttribute("taggedThreadList", forumThreads);
%>
<div class="widget" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
<ul>
<logic:iterate indexId="i" id="forumThread" name="taggedThreadList">
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
<logic:equal name="i" value="0">
<li>
  <h3 class="vid-name">
    <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" title="<bean:write name="forumThread" property="rootMessage.shortBody[20]" />" class="hover-preload"><bean:write name="forumThread" property="name"/></a>
  </h3>
  <div class="smallgray">
    <bean:write name="forumThread" property="rootMessage.shortBody[50]" />.
  </div>
</li>
</logic:equal>
<logic:greaterThan name="i" value="0">
<li class="info">
  <h3 class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" title="<bean:write name="forumThread" property="rootMessage.shortBody[20]" />" class="hover-preload"><bean:write name="forumThread" property="name"/></a></h3>
</li>
</logic:greaterThan>
</logic:iterate>
</ul>
</div>
