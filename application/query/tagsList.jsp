<%@ page session="false" %>
<%@page isELIgnored="false" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 
<bean:define id="title"  value="分类主题" />
<%@ include file="../common/IncludeTop.jsp" %>
<bean:parameter name="queryType" id="queryType" value=""/>

<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">

<div class="row">
  <div class="col-md-12">
    <div class="box"> 


<ul class="pagination pull-right">
<MultiPagesREST:pager actionFormName="tagsListForm" page="/tags/p"  >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="8" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>

</ul>


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
  
    
      <div>
        <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" class="post-tag">
		    <bean:write name="threadTag" property="title" /></a>
	      <a href="/tag-<bean:write name="threadTag" property="tagID"/>/rss"><i class="fa fa-feed"></i></a>
      </div>
      <div>
      <jsp:include page="/query/tt.shtml?start=0&length=10&tablewidth=160&count=10&tagID=${threadTag.tagID}" flush="true"></jsp:include>
	  
      </div>  
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

<ul class="pagination pull-right">
<MultiPagesREST:pager actionFormName="tagsListForm" page="/tags/p"  >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="8" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>

</ul>
    </div>
  </div>
</div>
</logic:greaterThan>
</logic:present>


<%@ include file="searchInputView.jsp" %>
<%@include file="../common/IncludeBottomBody.jsp"%>
<%@include file="../account/loginAJAX.jsp"%>

  
</body>
</html>
