<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<logic:notEmpty name="messageForm">

<bean:define id="forum" name="messageForm" property="forum"  />
<bean:define id="title" name="forum" property="name" />
<%@ include file="../header.jsp" %>


<div data-role="header" data-theme="e">
		<h1>修改</h1>
	</div><!-- /header -->

	<div data-role="content" data-theme="d">	
		
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
response.setStatus(HttpServletResponse.SC_OK);
%>

<html:form action="/mobile/message/messageSaveAction.sthml" method="post" styleId="messageNew" onsubmit="return checkPost(this);" >
<html:hidden property="action" />
<html:hidden property="messageId" />
<html:hidden property="forumThread.threadId" />
	<bean:parameter name="newPageNo" id="newPageNo" value="0"/>
<input type="hidden" name=newPageNo value="<bean:write name="newPageNo"/>" />
<logic:equal name="messageForm" property="authenticated"  value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="messageForm" property="authenticated" value="true">

<html:select name="messageForm" property="forum.forumId">
       <html:option value="">请选择</html:option>
       <html:optionsCollection name="forums"  value="forumId" label="name"/>       
     </html:select>

标题:<html:text  property="subject" styleId="replySubject" size="20" maxlength="35" tabindex="3" />

内容:
<html:textarea property="body" cols="26" rows="10" styleClass="tpc_content" styleId="formBody" tabindex="4"></html:textarea>	
<br><input type="submit" value="修改" name="formButton" id="formSubmitButton" > 

<br>
<logic:equal name="messageForm" property="action" value="edit">
	     <a href="<html:rewrite page="/mobile/message/messageDeleAction.shtml" paramId="messageId" paramName="messageForm" paramProperty="messageId" />" data-role="button">
       删除
       </a>
</logic:equal>  
<script>

function forwardNewPage(fmainurl, fmainPars, anchor){
      var url = fmainurl + fmainPars + "#" + anchor;
      //window.alert("forward new page");      
      window.location.href =  url;
      
}

</script>


</logic:equal>   


</html:form>
</logic:notEmpty>


</div>		   
<%@include file="../js.jsp"%>  
<%@include file="../footer.jsp"%> 
