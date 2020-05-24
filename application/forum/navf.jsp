<%--  /forum/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<%
  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(1, request, response);
  long forumId = ((Long) request.getAttribute("forumId")).longValue();
  long messageId = ((Long) request.getAttribute("messageId")).longValue();

  String url = request.getContextPath() + "/forum/messageNavList.shtml?forum=" + forumId + "&message=" + messageId;
  url = url + "&ver=" + java.util.UUID.randomUUID().toString();
%>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="robots" content="noindex">
  <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="https://static.jdon.com/js/bootstrap.min.css" type="text/css">
  <!-- jQuery and Modernizr-->
  <script src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>
  <!-- Core JavaScript Files -->
  <script src="https://static.jdon.com/js/bootstrap.min.js"></script>
  <script src="https://static.jdon.com/js/waitingfor.js"></script>
</head>
<body>
<script>
    waitingDialog.show('新文已经收到，正在跳转..');
    setTimeout(function () {
        window.top.location.href = '<%=url%>';
        waitingDialog.hide();
    }, 2000);
</script>
</body>
</html>