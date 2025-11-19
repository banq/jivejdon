<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("X-Robots-Tag", "noindex, nofollow");
%>
 
<bean:define id="title"  value=" 道场查询" />
<%@ include file="../common/IncludeTop.jsp" %>
<meta name="robots" content="noindex, nofollow">


<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-12">
				<div class="box">	

<ul class="nav nav-tabs">
  <li ><a href="<%=request.getContextPath()%>/threads/">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval/">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>	
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>
</ul>

</div>
</div>
</div>
</div>


<%@ include file="searchInputView.jsp" %>


<%@include file="../common/IncludeBottom.jsp"%>

