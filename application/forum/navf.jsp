<%@page import="java.util.Enumeration"%>
<%@ include file="../common/503warn.jsp"%> <%-- for search spammer bot  --%>

<%--  /forum/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<meta name="robots" content="nofollow"> 
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(1, request, response);
int start = ((Integer)request.getAttribute("start")).intValue();
long threadId = ((Long)request.getAttribute("threadId")).longValue();
long messageId = ((Long)request.getAttribute("messageId")).longValue();


String url = request.getContextPath();
if (start == 0)
    url = url + "/forum/messageList.shtml?thread=" + threadId + "&messageId=" + messageId;
else
	url = url + "/forum/messageList.shtml?thread=" + threadId + "&start=" + start + "&messageId=" + messageId;

Enumeration e = request.getParameterNames();
while (e.hasMoreElements()) {            
      String paramName = (String) e.nextElement();
      if (paramName.equals(request.getParameter(paramName)))
               url = url + "&" + paramName + "=" + request.getParameter(paramName);
            
}
url = url + "&ver=" + java.util.UUID.randomUUID().toString();
%>
<% 
response.setStatus(301);
response.setHeader( "Location", url );
response.setHeader( "Connection", "close" );
%>
