<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageForm">
<bean:define id="forum" name="messageForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="../common/IncludeTop.jsp" %>
  <!-- jQuery and Modernizr-->
  <script src="https://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>

  <!-- Core JavaScript Files -->
  <script src="https://libs.baidu.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
response.setStatus(HttpServletResponse.SC_OK);
%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
<META HTTP-EQUIV="Expires" CONTENT="0">   

<div class="box">
<div class="comment">
<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<html:form action="/message/postSaveAction.sthml" method="post" target="target_new" styleId="messageNew" onsubmit="return checkPost(this);" >

<html:hidden property="action" />
<html:hidden property="messageId" />
<html:hidden property="forumThread.threadId" />

<div class="row">
	<div class="col-md-6">       
        <div class="form-group">    
     <html:select name="messageForm" styleClass="form-control" property="forum.forumId" styleId="forumId_select">
       <html:option value="">请选择</html:option>
       <html:optionsCollection name="forums" value="forumId" label="name"/>       
     </html:select>
  
        </div>
    </div>
    <div class="col-md-6">
    </div>
</div>
<logic:equal name="messageForm" property="authenticated"  value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="messageForm" property="authenticated" value="true">
  
 <jsp:include page="messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="false"/>   
 </jsp:include> 
 

<div class="row">
	<div class="col-md-12">       
        <div class="form-group"> 
              <button type="submit" class="btn btn-4 btn-block" name="formButton" id="formSubmitButton">发布</button>
        </div>
      </div>
</div>

  <table cellpadding="4" cellspacing="0" border="0" width="400">
<tr>
	<td width="20">&nbsp;</td>
  <logic:equal name="messageForm" property="action" value="edit">
	<td>
       <html:link page="/message/messageDeleAction.shtml" paramId="messageId" paramName="messageForm" paramProperty="messageId">
       删除本贴(只有无跟贴才可删除)
       </html:link>	
	</td>
	 <logic:equal name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=false" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
        取消屏蔽
        </html:link></td>   
     </logic:equal>           
        
    <logic:notEqual name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=true" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
       屏蔽该贴
        </html:link></td>    
     </logic:notEqual>        
     
   </logic:equal>  
</tr>
</table>

</logic:equal>   

</html:form>
</div>
</div>

</logic:notEmpty>
</logic:notEmpty>
<br>
<br>
<br>
<br>
<br>
<div></div>
</body></html>

