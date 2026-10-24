<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
java.util.Map<String, java.util.List<com.jdon.jivejdon.domain.model.ForumThread>> taggedThreadsByTag =
    (java.util.Map<String, java.util.List<com.jdon.jivejdon.domain.model.ForumThread>>) request.getAttribute("taggedThreadsByTag");
java.util.List<com.jdon.jivejdon.domain.model.ForumThread> taggedThreads =
    taggedThreadsByTag == null ? null : taggedThreadsByTag.get(request.getParameter("tagID"));
if (taggedThreads != null) {
    request.setAttribute("taggedThreadList", taggedThreads);
}
%>
<bean:parameter id="count" name="count" value="4"/>
<%
String count = (String) pageContext.getAttribute("count");
%>
<logic:present name="taggedThreadList">
<div class="widget" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
<ul>
<logic:iterate indexId="i" id="forumThread" name="taggedThreadList" length='<%=count%>'>
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
</logic:present>
