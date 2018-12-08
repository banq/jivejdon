<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forumThread" name="threadForm"/>

<bean:define id="title" value=" 添加标签"/>
<%@ include file="../messageHeader.jsp" %>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css">
<script type="text/javascript" src="/common/js/prototype.js"></script>
<script language="javascript" src="/common/js/autocomplete.js"></script>
<bean:define id="ForumMessage" name="threadForm" property="rootMessage"/>

<script>
    var options = {
        script: '<%=request.getContextPath()%>/message/tags.shtml?method=tags&',
        varname: 'q',
        json: true,
        shownoresults: true,
        maxresults: 16,
        callback: function (obj) {
            $('json_info').update('');
        }
    };


    function ac(id) {
        new AutoComplete(id, options);
    }


</script>
<span id='json_info'></span>

<br><br>
<div align="center">

	
	<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="threadForm" property="authenticated"
             value="true">

<form action="<%=request.getContextPath()%>/message/tag/savetags.shtml?method=savetags" method="post">


	
    <input type="hidden" name="threadId"
           value="<bean:write name="forumThread" property="threadId" />"/>

	
	<input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_0"
           onfocus="javascript:ac(this.id)" value=''/>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_1"
           onfocus="javascript:ac(this.id)" value=''/>
    <br>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_2"
           onfocus="javascript:ac(this.id)" value=''/>
    <input type="text" name="tagTitle" size="13" maxlength="25"
           id="searchV_3"
           onfocus="javascript:ac(this.id)" value=''/>
    <br> <html:submit>提交</html:submit>
</form>
<logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i">
    <script>
		  document.getElementById('searchV_<bean:write name="i"/>').value ='<bean:write name="threadTag" property="title" />' 
      
    </script>
</logic:iterate>
	</logic:equal>
<br><br><br><br>

<a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>"
   target="_blank">
    <b><span
            class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name"/></span></b></a>
</div>
<%--  jquery prototype
<%@include file="../../common/IncludeBottom.jsp"%>
--%>
