<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="threadForm">
<bean:define id="forum" name="threadForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>


    <bean:define id="ForumMessage" name="threadForm" property="rootMessage"  />

<div class="col-md-offset-4 col-md-4">

<html:form action="/message/updateAction.shtml" method="post" >
<html:hidden property="threadId" />
    <html:hidden property="method" value="updateThreadName" />


<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="threadForm" property="authenticated"
             value="true">
<p>
    标题：<html:text property="name" styleId="replySubject" size="80"
                  maxlength="100"/>
  <p>  <input type="submit" value=" 确定 Ctrl+Enter "/>
<p><p><p>

</logic:equal>   

</html:form>
</div>


</logic:notEmpty>
</logic:notEmpty>

<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>
<p></p>


<%@include file="../common/IncludeBottom.jsp"%>

