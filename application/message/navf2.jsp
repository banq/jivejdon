<%--  /message/messageNavList2.shtml  == > MessageListNavAction ==>navf2.jsp ==> (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
<%
  long pMessageId = ((Long) request.getAttribute("pMessageId")).longValue();
  long messageId = ((Long) request.getAttribute("messageId")).longValue();

  String url = request.getContextPath() + "/message/messageNavList2.shtml?pMessage=" + pMessageId + "&message=" + messageId;
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
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/banq/jivejdon/application/js/jdon.css"  type="text/css">  
  <!-- jQuery and Modernizr-->
  <script src="https://cdn.jsdelivr.net/gh/banq/jivejdon/application/js/jquery-bootstrap2.js"></script>
  <script src="/common/js/waitingfor.js"></script>
</head>
<body>
<script>
    waitingDialog.show('回贴已经收到，正在跳转..');
    setTimeout(function () {
        window.top.location.href = '<%=url%>';
        waitingDialog.hide();
    }, 2000);
</script>
</body>
</html>

