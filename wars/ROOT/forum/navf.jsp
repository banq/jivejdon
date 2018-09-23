<%@page import="java.util.Enumeration"%>
<%@ include file="../common/503warn.jsp"%> <%-- for search spammer bot  --%>

<%-- (urlrewrite.xml)/nav/([0-9]+)/([0-9]+) == > /forum/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<meta name="robots" content="nofollow"> 
<% 
int start = ((Integer)request.getAttribute("start")).intValue();
long threadId = ((Long)request.getAttribute("threadId")).longValue();

String url = request.getContextPath();
if (start == 0)
    url = url + "/forum/messageList.shtml?thread=" + threadId ;
else
	url = url + "/forum/messageList.shtml?thread=" + threadId + "&start=" + start;

Enumeration e = request.getParameterNames();
while (e.hasMoreElements()) {            
      String paramName = (String) e.nextElement();
      if (paramName.equals(request.getParameter(paramName)))
               url = url + "&" + paramName + "=" + request.getParameter(paramName);
            
}
%>
<% 
response.setStatus(301);
response.setHeader( "Location", url );
response.setHeader( "Connection", "close" );
%>
