<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page import="com.jdon.jivejdon.util.ToolsUtil"%>
<%@page import="com.jdon.jivejdon.presentation.form.MessageForm"%>
<bean:parameter id="forumId" name="forumId" value="" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
 <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="https://cdn.jdon.com/js/bootstrap.min.css"  type="text/css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="https://cdn.jdon.com/common/js/styles/style.css">
  <!-- jQuery and Modernizr-->
  <script src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>

  <!-- Core JavaScript Files -->
  <script src="https://static.jdon.com/js/bootstrap.min.js"></script>
  <script src="//cdn.jdon.com/common/login2.js"></script>
<title>享道</title>
<%-- 
<%@ include file="/common/security.jsp" %>
<%@ include file="/common/headerBody.jsp" %>
<script type="text/javascript" src="/common/js/prototype.js"></script>
--%>
</head>
<body>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
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
<jsp:include page="threadPost.jsp" flush="true"/>   
</center>

<!-- ensure jquery was loaded, cannot load jquery twice -->
  <link rel="stylesheet" href="/common/autocomplete/jquery-ui.css" type="text/css">
  <script src="/common/autocomplete/jquery-ui.js"></script>

  <script>
      $( function() {
          $( "#searchV_0" ).autocomplete({
              source: "/message/tags.shtml?method=tags",
              minLength: 2,
              delay: 1500
          });
          $( "#searchV_1" ).autocomplete({
              source: "/message/tags.shtml?method=tags",
              minLength: 2,
              delay: 1500
          });
          $( "#searchV_2" ).autocomplete({
              source: "/message/tags.shtml?method=tags",
              minLength: 2,
              delay: 1500
          });
          $( "#searchV_3" ).autocomplete({
              source: "/message/tags.shtml?method=tags",
              minLength: 2,
              delay: 1500
          });



      } );
  </script>

<script>

document.getElementById('replySubject').value='<%=subject%>';
//document.getElementById('formBody').setAttribute("cols", "60");
//document.getElementById('formBody').setAttribute("rows", "12"); 
//document.getElementById('formBody').value='[url=<%=url%>]<%=subject%>[/url]';
document.getElementById('formBody').value='<%=url%>';
</script>

</body>
</html>