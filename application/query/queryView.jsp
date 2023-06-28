<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 道场查询" />
<%@ include file="../common/IncludeTop.jsp" %>
<meta name="robots" content="noindex,nofollow">

<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-12">
				<div class="box">	

<ul class="nav nav-tabs">
  <li ><a href="<%=request.getContextPath()%>/threads">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>	
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li class="active"><a href="#" >搜索</a></li>
</ul>

</div>
</div>
</div>
</div>


<%@ include file="searchInputView.jsp" %>

<%-- <%@ include file="queryInputView.jsp" %> --%>

<%@include file="../common/IncludeBottom.jsp"%>

