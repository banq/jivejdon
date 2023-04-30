<%@ page session="false" %>
<%@page isELIgnored="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<bean:define id="title"  value="分类主题" />
<%@ include file="../common/IncludeTop.jsp" %>
<meta name="robots" content="noindex,nofollow">
<bean:parameter name="queryType" id="queryType" value=""/>

<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">
<%
int i = 3;
int h = 0 ;
%>
<logic:iterate id="threadTag" name="tagsListForm" property="list" >
 <%
  if(i % 3==0){ 
 %>
 <div class="row">	
 <%}%>

 <div class="col-md-4">
 <div class="box">	
  <div class="linkblock">
	<div id='ajax_tagID=<bean:write name="threadTag" property="tagID"/>' style="width:300px; heigh:500px; border:none;overflow:hidden;">
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
    </div>
      <div class="lazyload" >
	    <!-- 
        <script>
         load('/query/tt/${threadTag.tagID}', function(xhr) {
  	       document.getElementById('ajax_tagID=<bean:write name="threadTag" property="tagID"/>').innerHTML = xhr.responseText;
         });
        </script>
        -->
	</div>	
   </div>  
 </div>
</div>
 
<% i = i+1;%>
<%
  if(i % 3==0){ 
 %>
  </div>
 <%}%>

</logic:iterate>

<%
  if(i % 3 !=0){ 
 %>
  </div>
 <%}%>

<div class="box">
<div class="tres">        
  
<MultiPagesREST:pager actionFormName="tagsListForm" page="/tags/p"  >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>
      </div>
   
</div>	
</logic:greaterThan>
</logic:present>


<%@ include file="searchInputView.jsp" %>
<%@include file="../common/IncludeBottomBody.jsp"%>
<%@include file="../account/loginAJAX.jsp"%>

<script defer>
document.addEventListener("DOMContentLoaded", function(event) { 
  
    $('.lazyload').lazyload();

});
</script>
  
</body>
</html>
