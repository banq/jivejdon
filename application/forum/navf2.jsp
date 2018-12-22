<%@page import="java.util.Enumeration"%>
<%@ include file="../common/503warn.jsp"%> <%-- for search spammer bot  --%>

<%--  /forum/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<meta name="robots" content="nofollow">
<%
  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(1, request, response);
  int start = ((Integer) request.getAttribute("start")).intValue();
  long pMessageId = ((Long) request.getAttribute("pMessageId")).longValue();
  long messageId = ((Long) request.getAttribute("messageId")).longValue();


  String url = request.getContextPath();
  if (start == 0)
    url = url + "/forum/messageNavList2.shtml?pMessage=" + pMessageId + "&message=" + messageId;
  else
    url = url + "/forum/messageNavList2.shtml?pMessage=" + pMessageId + "&start=" + start + "&message=" + messageId;

  Enumeration e = request.getParameterNames();
  while (e.hasMoreElements()) {
    String paramName = (String) e.nextElement();
    if (paramName.equals(request.getParameter(paramName)))
      url = url + "&" + paramName + "=" + request.getParameter(paramName);

  }
  url = url + "&ver=" + java.util.UUID.randomUUID().toString() + "#" + messageId;
%>

<%--
response.setStatus(301);
//response.setHeader( "Location", url );
  response.setHeader("Refresh", "500;url=" + url);
response.setHeader( "Connection", "close" );
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="https://cdn.jdon.com/js/bootstrap.min.css" type="text/css">
  <!-- jQuery and Modernizr-->
  <script src="https://cdn.jdon.com/js/jquery-2.1.1.min.js"></script>
  <!-- Core JavaScript Files -->
  <script src="https://cdn.jdon.com/js/bootstrap.min.js"></script>
  <script src="/js/waitingfor.js"></script>
</head>
<html>
<body>
<script>
    waitingDialog.show('回贴已经收到，正在跳转..');
    setTimeout(function () {
        window.top.location.href = '<%=url%>';
        waitingDialog.hide();
    }, 1000);
</script>
</body>
</html>

