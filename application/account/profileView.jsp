<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

<logic:notEmpty name="accountProfileForm" property="account">
<logic:notEmpty name="accountProfileForm" property="account.username">
<bean:parameter id="winwidth" name="winwidth" value=""/>

<logic:empty name="winwidth" >
<bean:define id="title"  value=" 个人用户信息" />
<%@ include file="../common/IncludeTop.jsp" %>

</logic:empty>

<logic:empty name="winwidth" >
<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
</logic:empty>

<logic:notEmpty name="winwidth" >
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<table bgcolor="#fff"
 cellspacing="0" cellpadding="0" border="0" width="<bean:write name="winwidth" />" align="center">
 </logic:notEmpty>
 
<tr><td>

<table bgcolor="#fff"
 cellspacing="3" cellpadding="3" border="0" width="100%">
<tr>
 <td>
  <bean:define id="account" name="accountProfileForm" property="account"/>
    <a href='<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>' target="_blank" class="smallgray">
  <logic:notEmpty name="account" property="uploadFile">
                  <logic:equal name="account" property="roleName" value="User">
     				  <img src="/img/account/<bean:write name="account" property="userId"/>"  border='0' width="115" height="100" class="post_author_pic"/>
				  </logic:equal>
                  <logic:equal name="account" property="roleName" value="SinaUser">                 
                     <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' width="115" height="100" class="post_author_pic"/>
                  </logic:equal>
                  <logic:equal name="account" property="roleName" value="TecentUser">                 
                      <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' width="115" height="100" class="post_author_pic"/>
                  </logic:equal>                                    			  				  
  </logic:notEmpty>
				
  <logic:empty name="account" property="uploadFile">
				  <img src="/images/nouserface_1.gif" width="50" height="50" border="0" >
	</logic:empty>
    </a>	
 </td>
</tr>
<tr><td>
<a href='<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>' target="_blank" class="smallgray"><bean:write name="accountProfileForm" property="account.username"/></a>
</td></tr>
<tr>
 <td align="center">

<table cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
    <table cellpadding="3" cellspacing="1" border="0" width="100%">

<logic:iterate id="property" name="accountProfileForm" property="propertys" indexId="i">
<tr>
  <td align="right" class="smallgray" nowrap>
   <bean:write name="property"  property="name"  />&nbsp;&nbsp;
   </td>
  <td nowrap>
   <bean:write name="accountProfileForm"  property='<%= "property[" + i + "].value" %>'   filter="false" />
  </td>
</tr>
</logic:iterate>  
  

    </table>
  </td>
</tr></table>
 </td>
</tr></table>
</td></tr>
</table>
<center>
<logic:present name="principal" >  
     <html:link page="/account/protected/editAccountForm.shtml?action=edit" paramId="username" paramName="principal" target="_blank">
      编辑个人信息</html:link>
</logic:present>
</center>
<logic:empty name="winwidth" >

<%@include file="../common/IncludeBottom.jsp"%>
</logic:empty>

</logic:notEmpty>
</logic:notEmpty>
