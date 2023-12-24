<%--  /message/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<%
  
  long forumId = ((Long) request.getAttribute("forumId")).longValue();
  long messageId = ((Long) request.getAttribute("messageId")).longValue();

  String url = request.getContextPath() + "/message/messageNavList.shtml?forum=" + forumId + "&message=" + messageId;
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
  
  <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="//cdn.jdon.com/js/jdon.css"  type="text/css">  
  <!-- jQuery and Modernizr-->
  <script src="//cdn.jdon.com/js/jquery-bootstrap2.js"></script>
  <script src="/common/js/waitingfor.js"></script>
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