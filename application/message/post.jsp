
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<bean:parameter id="forumId" name="forumId" value="" />

<!--  deirectly call this :/forum/post.jsp -->
<%@ include file="../common/IncludeTop.jsp" %>
<!-- jQuery and Modernizr-->
<script src="https://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>

<!-- Core JavaScript Files -->
<script src="https://libs.baidu.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

<a name="post"></a>
<jsp:include page="threadPost.jsp" flush="true"/>         
  

<%@include file="../common/IncludeBottom.jsp"%> 
<%
if (request.getParameter("to") != null){
%>
  <script>
  	document.getElementById("replySubject").value = document.getElementById("replySubject").value +"@<%=request.getParameter("to")%> ";
  	
  	</script>
<%
}
if (request.getParameter("tag") != null){
%>
  <script>
  var tagTitles = document.getElementsByName('tagTitle');
  tagTitles[0].value = "<%=request.getParameter("tag")%> ";
  	
  	</script>
<%
}
%>
<p><br><br>

  </body></html>