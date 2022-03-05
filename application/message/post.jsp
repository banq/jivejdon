
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<bean:parameter id="forumId" name="forumId" value="" />

<!--  deirectly call this :/forum/post.jsp -->
<%@ include file="../common/IncludeTop.jsp" %>

<a name="post"></a>
<jsp:include page="threadPost.jsp" flush="true"/>         
  

<%@include file="../common/IncludeBottom.jsp"%> 

<!-- at first load jquery , cannot load jquery twice -->
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