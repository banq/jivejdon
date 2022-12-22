<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>热点贴列表</title>
<style>
A{font-size: 14px; line-height:23px;text-decoration:none;color:#888;}a:visited{text-decoration:none;color: #666;font-weight:lighter;}A:hover{text-decoration:underline;color:#eab30d;}
.important {
	width: 285px;
	padding: 8px;
	margin: 0px;
	background-color: #F7F7F7;
	border: 1px solid #ddd;
	border-radius: 3px;
	box-shadow: 0 15px 10px -15px rgba(0,0,0,0.4);	
	overflow:hidden;
	white-space: normal !important;
	text-overflow:ellipsis;

}

</style>

</head>
<%--  
two caller:
no parameter: /query/hotlist.shtml

parameters:
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="400" HEIGHT="200"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=180&tablewidth=400"></iframe>


--%>
<body leftmargin="0" rightmargin="0" topmargin="0">
</logic:notEqual>
<bean:parameter id="tablewidth" name="tablewidth" value="160"/>
<div class="important">
<bean:parameter id="length" name="length" value="8"/>
<bean:size id="hotcount" name="threadListForm" property="list"/>
            <%
            String coutlength = (String)pageContext.getAttribute("length");
            %>
            
            <logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%= coutlength%>' >
<a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank"><bean:write name="forumThread" property="name" /></a><br>
            </logic:iterate>
</div>
<logic:notEqual name="noheader" value="on">


</body>
</html>
</logic:notEqual>