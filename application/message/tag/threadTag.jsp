<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forumThread" name="threadForm"/>

<bean:define id="title" value=" 添加标签"/>
<%@ include file="../messageHeader.jsp" %>


<bean:define id="ForumMessage" name="threadForm" property="rootMessage"/>


<br><br>
<div style="text-align: center">

	
	<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="threadForm" property="authenticated"
             value="true">

    <h3><bean:write name="forumThread" property="name" /></h3>         
   <br>          
<form action="<%=request.getContextPath()%>/message/tag/savetags.shtml?method=savetags" method="post">


	
    <input type="hidden" name="threadId"
           value="<bean:write name="forumThread" property="threadId" />"/>

	
	<input type="text" name="tagTitle" size="13" maxlength="25"
         id="searchV_0"
         onfocus="javascript:loadAcJS(this.id)" value=''/>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_1"
           onfocus="javascript:loadAcJS(this.id)" value=''/>
    <br>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_2"
           onfocus="javascript:loadAcJS(this.id)" value=''/>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_3"
           onfocus="javascript:loadAcJS(this.id)" value=''/>
    <br>搜索token: <input type="text" name="token" size="13" maxlength="25" id="token"/>
           <br> <html:submit>提交</html:submit>
</form>
<script>
<logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i">
    
        document.getElementById('searchV_<bean:write name="i"/>').value = '<bean:write name="threadTag" property="title" />';
    
</logic:iterate>
document.getElementById('token').value = '<bean:write name="forumThread" property="token" />';
    
</script>    	
    	
</logic:equal>
<br><br><br><br>

<a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html"
   target="_blank">
    <b><span
            class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name"/></span></b></a>
</div>

 <%@include file="../../common/IncludeBottomBody.jsp"%>

 
<link rel="stylesheet" href="/common/autocomplete/jquery-ui.css" type="text/css">
<script defer src="/common/autocomplete/jquery-ui.js"></script>
<script>

    function loadAcJS(thisId) {
       $("#" + thisId).autocomplete({
            source: "/message/tags.shtml?method=tags",
            minLength: 1,
            autoFocus: false,
            delay: 500
        });
    }

</script>
</body></html>



