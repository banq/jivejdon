<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forumThread" name="threadForm"/>

<bean:define id="title" value=" 更改板块"/>
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
<form action="<%=request.getContextPath()%>/message/threadToForum/save.shtml" method="post">

	
    <input type="hidden" name="threadId"
           value="<bean:write name="forumThread" property="threadId" />"/>

       <bean:define id="forum" name="forumThread" property="forum" > 
    </bean:define>
   <div style="text-align: center;">
       <select class="form-control" name="forumId" id="forumId_select" style="width: auto; min-width: 200px; max-width: 500px; display: inline-block;"></select>   
    </div>

           <br>  <input type="submit" value=" 确定 Ctrl+Enter "/>
</form>

    	
</logic:equal>

</div>

 <%@include file="../../common/IncludeBottomBody.jsp"%>

 
<script>

document.addEventListener("DOMContentLoaded", function(event) { 
     load(getContextPath() +'/message/forumListJSON.shtml', function(xhr) {
     var dataArray = JSON.parse(xhr.responseText);  
         for (var i in dataArray){  
            var optn = document.createElement('option');
           optn.text = dataArray[i].name;
           optn.value = dataArray[i].forumId;          
           document.getElementById('forumId_select').options.add(optn);
         
        }  
const select = document.getElementById("forumId_select");
const currentForumId = '<bean:write name="forum" property="forumId" />';
for (let i = 0; i < select.options.length; i++) {
    if (select.options[i].value === currentForumId) {
        select.selectedIndex = i;
        break;
    }
}
    });
});    
</script>


</body></html>


