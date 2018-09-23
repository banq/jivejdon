<%@page import="java.util.Enumeration"%>
<%@ include file="../common/503warn.jsp"%> <%-- for search spammer bot  --%>
 
<% 
int start = ((Integer)request.getAttribute("start")).intValue();
long threadId = ((Long)request.getAttribute("threadId")).longValue();

String url = request.getContextPath();
if (start == 0)
    url = url + "/mobile/" + threadId ;
else
	url = url + "/mobile/" + threadId + "/" + start;

Enumeration e = request.getParameterNames();
while (e.hasMoreElements()) {            
      String paramName = (String) e.nextElement();
      if (paramName.equals(request.getParameter(paramName)))
               url = url + "/" + request.getParameter(paramName);
            
}
%>
<% 
response.setStatus(301);
response.setHeader( "Location", url );
response.setHeader( "Connection", "close" );
%>
