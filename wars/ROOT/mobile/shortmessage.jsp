<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<bean:define id="title" value="系统消息" />

<%@ include file="header.jsp" %>

	<div data-role="header">
		<h1><b>解道jdon.com最新内容</b> </h1>
	</div><!-- /header -->
 
	<div data-role="content">	

<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>


<link rel="stylesheet" href="<html:rewrite page="/shortmessage/shortmsg_css.jsp"/>"	type="text/css">

<script type="text/javascript">
	function showItem(url) {
		// Update the contents of the product IFRAME with the specified url
		document.getElementById("product").src = url;
	}
</script>

<table width="100%" align="center"><tr><td valign="top" width="10%">
<div id="shortmessage_vertmenu">
	<h1>
		短消息
	</h1>
	<ul>
		<li>
			<a
				href="javascript:showItem('<%=request.getContextPath()%>/account/protected/shortmessageAction.shtml')"
				tabindex="1">写信息</a>
		</li>
		<li>
			<a
				href="javascript:showItem('<%=request.getContextPath()%>/account/protected/receiveListAction.shtml?count=10')"
				tabindex="2">收件箱</a>
		</li>
		<li>
			<a
				href="javascript:showItem('<%=request.getContextPath()%>/account/protected/sendListAction.shtml?count=10')"
				tabindex="3">发送箱</a>
		</li>
		<li>
			<a
				href="javascript:showItem('<%=request.getContextPath()%>/account/protected/draftListAction.shtml?count=10')"
				tabindex="4">草稿箱</a>
		</li>
		<li>
		<logic:present name="principal" >
		   <a	href="<%=request.getContextPath()%>/blog/<bean:write name="principal" />"
				tabindex="5">返回我的博客 更多功能</a>
         </logic:present>
			
		</li>
	</ul>
</div>
</td><td align="center">
<div id="shortmessage_content">
	<iframe align="top" width="90%" height="200" id="product" frameborder="0"		
		src="<%=request.getContextPath()%>/account/protected/receiveListAction.shtml?count=5"></iframe>
</div>

</td></tr></table>

 
</div><!-- /content -->
 
<%@include file="footer.jsp"%> 
