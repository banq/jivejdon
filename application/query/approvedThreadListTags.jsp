<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<div class="box">
<div class="col-lg-4">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length="2" >
<div class="linkblock">	
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />	
<div style="width:150px;margin:0 auto;height:300px">
    <bean:define id="body" name="forumMessage" property="messageVO.body" />    
    <h3> <a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/>.html" ><bean:write name="forumThread" property="name" /></a> </h3>  
      <br>          
      <p class="smallgray"> <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />..
	</div>
</div>        
</logic:iterate>
</div>

<div class="col-lg-4">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length="2" offset="2" >
<div class="linkblock">	
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />	
<div style="width:150px;margin:0 auto;height:300px">
    <bean:define id="body" name="forumMessage" property="messageVO.body" />    
    <h3> <a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/>.html" ><bean:write name="forumThread" property="name" /></a> </h3>  
      <br>          
      <p class="smallgray"> <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />..
	</div>			  
</div>        
</logic:iterate>
</div>

	<div class="col-lg-4">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length="2" offset="4" >
<div class="linkblock">	
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />	
<div style="width:150px;margin:0 auto;height:300px">
    <bean:define id="body" name="forumMessage" property="messageVO.body" />    
    <h3> <a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/>.html" ><bean:write name="forumThread" property="name" /></a> </h3>  
      <br>          
      <p class="smallgray"> <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />..
	</div>			  
</div>        
</logic:iterate>
	</div>

</div>