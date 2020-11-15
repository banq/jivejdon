<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%-- 这里是因为被tagsList.jsp的bean:include调用，只能是gbk，不能为正常utf-8，否则乱码 --%>    
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(6 * 60 * 60, request, response);
%>
<logic:empty name="threadTag">
  <%return 404;%>
</logic:empty>
<logic:notEmpty name="threadTag">

<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>
<div>
	<a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
		<bean:write name="threadTag" property="title" />
	</a>
	&nbsp;&nbsp;
	<%--<a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="threadTag" property="tagID"/>" target="_blank" title="当本标签有新内容加入 自动通知我"  rel="nofollow">--%>
		<%--<i class="fa fa-heart"></i>--%>
		<%--<logic:notEmpty  name="threadTag" property="subscriptionCount" >--%>
			<%--<logic:greaterThan name="threadTag" property="subscriptionCount" value="0">--%>
				<%--<bean:write name="threadTag" property="subscriptionCount"/>--%>
			<%--</logic:greaterThan>--%>
		<%--</logic:notEmpty>--%>
	<%--</a>--%>
</div>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
	<div><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" class="smallgray"><bean:write name="forumThread" property="name" /></a></div>
</logic:iterate>

</logic:notEmpty>