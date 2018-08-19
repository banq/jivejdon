<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forumThread" name="threadForm" />

<bean:define id="title"  value=" 添加标签" />
<%@ include file="../header.jsp" %>
<script>
	 var options = {
		      script:'<%=request.getContextPath()%>/query/tags.shtml?method=tags&',
		      varname:'q',
		      json:true,
		      shownoresults:true,
		      maxresults:16,
		      callback: function (obj) { 
		               $('json_info').update('' ); 
		      }
     		};


	
		function ac(id){
	    	new AutoComplete(id,options);
		}
		
		
			
	</script>
<span id='json_info'></span>

 <form action="<%=request.getContextPath()%>/admin/tag/thread.shtml" method="post" >
   帖子编号：<input name="threadId" type="text">   
    <input type="submit" >
 </form>

<br><br>
<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>



        <form action="<%=request.getContextPath()%>/admin/tag/savetags.shtml?method=savetags" method="post" target="target_new">
       
        
	<input type="hidden" name="threadId" value="<bean:write name="forumThread" property="threadId" />"/>
	<input  type="text" name="tagTitle" size="13" maxlength="25" id="searchV0_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:ac(this.id)" value='' />
	<input  type="text" name="tagTitle" size="13" maxlength="25" id="searchV1_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:ac(this.id)"   value=''/>
	<br>	
    <input  type="text" name="tagTitle" size="13" maxlength="25" id="searchV2_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:ac(this.id)"  value=''/>
    <input  type="text" name="tagTitle" size="13" maxlength="25" id="searchV3_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:ac(this.id)"  value=''/>
     <br> <html:submit>提交</html:submit>
    </form>		
         <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i">
             <script>
             $('searchV<bean:write name="i"/>_<bean:write name="forumThread" property="threadId" />').value ='<bean:write name="threadTag" property="title" />'             
             </script>
        </logic:iterate>

       <br><br><br><br>
       
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name" /></span></b></a>
              <br>
             <bean:write name="forumThread" property="rootMessage.messageVO.body" filter="false" />

<%@include file="../footer.jsp"%>


