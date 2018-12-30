<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 添加标签" />
<%@ include file="../header.jsp" %>
<!-- jQuery and Modernizr-->
<script src="https://cdn.jdon.com/js/jquery-2.1.1.min.js"></script>

<!-- Core JavaScript Files -->
<script src="https://cdn.jdon.com/js/bootstrap.min.js"></script>
<!-- at first load jquery , cannot load jquery twice -->
<link rel="stylesheet" href="/common/autocomplete/jquery-ui.css" type="text/css">
<script src="/common/autocomplete/jquery-ui.js"></script>
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
<span id='json_info'></span>

 <form action="<%=request.getContextPath()%>/admin/tag/thread.shtml" method="post" >
   帖子编号：<input name="threadId" type="text">   
    <input type="submit" >
 </form>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres">        
     符合查询主题贴共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 
<MultiPages:pager actionFormName="threadListForm" page="/admin/tag/threadTagList.shtml" >
<MultiPages:prev name=" 上一页 " />
<MultiPages:index displayCount="3" />
<MultiPages:next  name=" 下一页 " />
</MultiPages:pager>
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>

<div id="tooltip_content_go"  style="display:none">
<div class="tooltip_content">
			<div class="title">前往下页:</div>
			<div class="form">
				<input type="text" style="width: 50px;" id="pageToGo">
        <input type="button" value=" Go " onClick="goToAnotherPage('<html:rewrite page="/message/threadViewQuery.shtml"/>',
				<bean:write name="threadListForm" property="count" />);" />
				
			</div>
 </div>
</div> 
</div>      
    </td>
</tr>
</table>

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="80%">
<tr><td>
    <table bgcolor="#cccccc"
     cellpadding="3" cellspacing="1" border="0" width="100%">
    <tr bgcolor="#868602" background="<%=request.getContextPath()%>/images/tableheadbg.gif">
        <td ><b><font color="#ffffff">&nbsp; 标签 &nbsp;</font></b></td>
         <td><b><font color="#ffffff">&nbsp; 回复 &nbsp;</font></b></td>
         <td>删除</td>
        <td >
        <table width="100%"><tr><td>
          <b><font color="#ffffff">&nbsp; 主题名</font></b>
          </td><td >
           </td></tr>
         </table>
        </td>
       
    </tr>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <tr bgcolor="#FFFFEC">
        <td nowrap>
        <form action="<%=request.getContextPath()%>/admin/tag/savetags.shtml?method=savetags" method="post" target="target_new">
       
        
	<input type="hidden" name="threadId" value="<bean:write name="forumThread" property="threadId" />"/>
          <input type="text" name="tagTitle" size="13" maxlength="25" id="searchV0_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:loadAcJS(this.id)" value=''/>
          <input type="text" name="tagTitle" size="13" maxlength="25" id="searchV1_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:loadAcJS(this.id)" value=''/>
          <br>
          <input type="text" name="tagTitle" size="13" maxlength="25" id="searchV2_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:loadAcJS(this.id)" value=''/>
          <input type="text" name="tagTitle" size="13" maxlength="25" id="searchV3_<bean:write name="forumThread" property="threadId" />" onfocus="javascript:loadAcJS(this.id)" value=''/>
     <br><p align="right"> <html:submit>提交</html:submit></p>
    </form>		
         <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i">
             <script>
                 document.getElementById('searchV<bean:write name="i"/>_<bean:write name="forumThread" property="threadId" />').value = '<bean:write name="threadTag" property="title" />';
             </script>
        </logic:iterate>

        </td>
         <td align="center">
            <bean:write name="forumThread" property="state.messageCount" />            
        </td>
        <td><html:link page="/message/messageDeleAction.shtml?" paramId="messageId" paramName="forumThread" paramProperty="rootMessage.messageId" onclick="return delConfirm()" target="_blank">
        <img src="../images/button_delete.gif" width="17" height="17" alt="删除该贴..." border="0"
        ></html:link></td>
        <td>
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name" /></span></b></a>
	
        </td>
       
   
        
    </tr>
</logic:iterate>


    </table>
    </td>
</tr>
</table>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres">        
     符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPages:pager actionFormName="threadListForm" page="/admin/tag/threadTagList.shtml"  >
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>
<MultiPages:prev name=" 上一页 " />
<MultiPages:index displayCount="3" />
<MultiPages:next  name=" 下一页 " />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>

<%@include file="../footer.jsp"%>


