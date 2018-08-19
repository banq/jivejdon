<%@ page session="false" %>

<%
String threadId = request.getParameter("thread");
String redirectUrl = request.getContextPath() + "/" +
  threadId + ".html";
response.sendRedirect(redirectUrl);
return;
%>
