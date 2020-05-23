<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page import="com.jdon.jivejdon.util.ToolsUtil"%>
<%@page import="com.jdon.jivejdon.presentation.form.MessageForm"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
 <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/js/bootstrap.min.css"  type="text/css">
	<!-- Custom Fonts -->
    <link rel="stylesheet" href="https://static.jdon.com/js/font-awesome-4.4.0/css/font-awesome.min.css"  type="text/css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="https://static.jdon.com/common/js/styles/style.css">
  <!-- jQuery and Modernizr-->
  <script src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>

  <!-- Core JavaScript Files -->
  <script src="https://static.jdon.com/js/bootstrap.min.js"></script>
<title>享道</title>
<%-- 
<%@ include file="/common/security.jsp" %>
<%@ include file="/common/headerBody.jsp" %>
<script type="text/javascript" src="/common/js/prototype.js"></script>
--%>
</head>
<body>
<%@ include file="/account/loginAJAX.jsp" %>

<%
String subject = "";
if (request.getParameter("subject") != null){
	subject = request.getParameter("subject");
    subject = ToolsUtil.replaceBlank(subject, "\t|\r|\n");
    if (subject.length() >= MessageForm.subjectMaxLength)
       subject = subject.substring(0, MessageForm.subjectMaxLength);
}

String url = "";
if (request.getParameter("url") != null)
	url = request.getParameter("url");

%>

<center>
<a name="post"></a>
<jsp:include page="threadPost.jsp" flush="true">   
   <jsp:param name="forumId">
      <jsp:attribute name="value" >        
     </jsp:attribute>
   </jsp:param>   
</jsp:include>
</center>
<script>

document.getElementById('replySubject').value='<%=subject%>';
//document.getElementById('formBody').setAttribute("cols", "60");
//document.getElementById('formBody').setAttribute("rows", "12"); 
//document.getElementById('formBody').value='[url=<%=url%>]<%=subject%>[/url]';
document.getElementById('formBody').value='<%=url%>';
</script>

</body>
</html>