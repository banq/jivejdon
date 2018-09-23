<%
response.setContentType("text/html");
response.setDateHeader("Expires", 0);
response.setHeader("Location", request.getContextPath());
response.setStatus(301); 
%>