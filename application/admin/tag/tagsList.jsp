<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<bean:define id="title"  value=" 标签列表" />
<%@ include file="../header.jsp" %>

<bean:parameter name="queryType" id="queryType" value=""/>


<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">



<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres">        
    <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>    
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签 
<MultiPages:pager actionFormName="tagsListForm" page="/admin/tag/tagsList.shtml"  >
<MultiPages:prev name=" 上页 " />
<MultiPages:index displayCount="3" />
<MultiPages:next  name=" 下页 " />
</MultiPages:pager>

    </td>
</tr>
</table>
<html:errors/>
<center>
<table><tr><td>
<%
int i = 0;
%>
<logic:iterate id="threadTag" name="tagsListForm" property="list" >
    <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" class="post-tag">
             <span class="big18"><bean:write name="threadTag" property="title" /></span>  
    </a>
    (<bean:write name="threadTag" property="assonum" />)
    <html:link page="/admin/tag/tagAction.shtml?action=edit" paramId="tagID" paramName="threadTag" paramProperty="tagID">             
               编辑 </html:link>
             &nbsp;&nbsp;&nbsp;&nbsp;

    
    <%
    if (i > 5){
       out.println("<br/>");
       i = 0;
    }else
       i++;
    %>
</logic:iterate>


</td></tr></table>
</center>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres">        
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签  
<MultiPages:pager actionFormName="tagsListForm" page="/admin/tag/tagsList.shtml"  >

<MultiPages:prev name=" 上页 " />
<MultiPages:index displayCount="3" />
<MultiPages:next  name=" 下页 " />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>
<br><br><br><br><br><br><br><br>

<%@include file="../footer.jsp"%>