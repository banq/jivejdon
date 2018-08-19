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
<link rel="stylesheet" href="/common/jivejdon5.css"  type="text/css">
<title>享道</title>
<%@ include file="/common/security.jsp" %>
<%@ include file="/common/headerBody.jsp" %>
<script type="text/javascript" src="/common/js/prototype.js"></script>
</head>
<%@ include file="/account/loginAJAX.jsp" %>
<body>
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

$('replySubject').value='<%=subject%>';
$('formBody').setAttribute("cols", "60");
$('formBody').setAttribute("rows", "12"); 
$('formBody').value='[url=<%=url%>]<%=subject%>[/url]';
</script>

</body>
</html>