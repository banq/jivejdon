<%--  /forum/messageNavList2.shtml  == > MessageListNavAction ==>navf2.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<%
  long pMessageId = ((Long) request.getAttribute("pMessageId")).longValue();
  long messageId = ((Long) request.getAttribute("messageId")).longValue();

  String url = request.getContextPath() + "/forum/messageNavList2.shtml?pMessage=" + pMessageId + "&message=" + messageId;
  url = url + "&ver=" + java.util.UUID.randomUUID().toString();
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

