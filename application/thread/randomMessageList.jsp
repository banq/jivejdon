<%@ page session="false" %>
<%
response.sendRedirect(request.getContextPath() +"/"+ request.getAttribute("threadId") + ".html");
return;
%>