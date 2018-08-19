<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page session="false" %>
  
<%  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 *  60 * 60, request, response);
%>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:size id="threadCount" name="threadListForm" property="list" />
<logic:notEqual name="threadCount" value="0">

<bean:parameter id="tablewidth" name="tablewidth" value="155"/>
<bean:parameter id="tagID" name="tagID" value=""/>
<table width='<bean:write name="tablewidth"/>' border="0" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
  <tr>
    <td bgcolor="#c3d9e7"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="25" align="center"  bordercolor="#eeeeee">       
        <a href='<%=request.getContextPath()%>/tags/<bean:write name="tagID" />' target="_blank" >
        <font color="#ffffff" ><b> <bean:write name="TITLE" /></b></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
          <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  rel="nofollow"><img src="/images/user_add.gif" width="18" height="18" border="0" /></a>

        </td>      
      </tr>
      <tr>
        <td >
        <table  width="100%"  cellpadding="1" bgcolor="#ffffff"><tr><td>
        
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >

<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="0" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'>
                <img src="/images/dot.gif" width="18" height="18"  border="0" align="absmiddle" /> 
                             
                  <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
             title="<bean:write name="forumThread" property="name" />" target="_blank" class="smallgray">
             <bean:write name="forumThread" property="name" /></a>
                      
                </td>
              </tr>
              
            </table>
            
</logic:iterate>
</td></tr></table>
</td>
      </tr>        
    </table></td>
  </tr>
</table>


</logic:notEqual>