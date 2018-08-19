<%@ page session="false" %>
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(30 * 24 * 60 * 60, request, response);
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<bean:define id="title"  value=" 标签列表" />
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter name="queryType" id="queryType" value=""/>


<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">

<center>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres" >        
    <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>    
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签 

<MultiPagesREST:pager actionFormName="tagsListForm" page="/tags/p"  >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>

</div>      
    </td>
</tr>
</table>


<script>

var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
TooltipManager.showNow(e);   
}

</script>

<table width="600">
<tr><td></td><td>包含主题数</td><td>关注状态</td><td>加关注</td></tr>
<logic:iterate id="threadTag" name="tagsListForm" property="list" >
<tr><td>
  <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span class="big18"><bean:write name="threadTag" property="title" /></span>      
    </a>
   </span>
   
     
   &nbsp;&nbsp;&nbsp;&nbsp;
   </td>
<td><bean:write name="threadTag" property="assonum" /></td>
<td>

<span id='count_<bean:write name="threadTag" property="tagID"/>'>
<logic:greaterThan name="threadTag" property="subscriptionCount" value="0">
<a href="<%=request.getContextPath() %>/social/contentfollower.shtml?subscribedId=<bean:write name="threadTag" property="tagID" />&subject=<bean:write name="threadTag" property="title" />" target="_blank"  rel="nofollow" class="whitelink">
  <bean:write name="threadTag" property="subscriptionCount"/>人已关注
</a>

</logic:greaterThan>
</span>
</td>
<td>
<span class="blackgray">
<a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="threadTag" property="tagID"/>"
 target="_blank" title="当本标签有新内容加入 自动通知我"  rel="nofollow">
 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签 有新回复自动通知我" border="0" />+
 </a>
</span>

 </td>
</tr>
</logic:iterate>

</table>



<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres">        
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签  
<MultiPagesREST:pager actionFormName="tagsListForm" page="/tags/p"  >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>
      </div>
    </td>
</tr>
</table>
</center>
</logic:greaterThan>
</logic:present>

<br><br><br><br><br><br><br><br>

<%@ include file="searchInputView.jsp" %>

<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>