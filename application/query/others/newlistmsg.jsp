<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>最新评论</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<script type="text/javascript" src="/common/js/prototype.js"></script>
</head>
  <body leftmargin="0" rightmargin="0" topmargin="0">
</logic:notEqual>
<div id="frame">
  <bean:parameter id="tablewidth" name="tablewidth" value="288"/>
  <bean:parameter id="count" name="count" value="8"/>
  <script>
function threadNewList(){
    new Ajax.Updater('frame', '<%=request.getContextPath()%>/query/threadNewList.shtml?noheader=on&tablewidth=<bean:write name="tablewidth"/>&count=<bean:write name="count"/>', { method: 'get' });	
}
function messageNewList(){
    new Ajax.Updater('frame', '<%=request.getContextPath()%>/query/messageNewList.shtml?noheader=on&tablewidth=<bean:write name="tablewidth"/>&count=<bean:write name="count"/>', { method: 'get' });	
}
</script>
  <table width='<bean:write name="tablewidth"/>' border="0" cellpadding="0" cellspacing="6" class="frame-yy"  >
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#f0f0f0">
          <tr>
            <td align="center"   bgcolor="#c3d9e7" width="50%"><font color="#ffffff" onmouseover="threadNewList()"><b>最新主题</b></font></td>
            <td  align="center"  width="50%"><a href="<%=request.getContextPath()%>/messages"><b>最新评论</b></a></td>
          </tr>
        </table>
    <tr>
      <td ><%
String coutlength = (String)pageContext.getAttribute("count");
%>
        <logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" length='<%=coutlength%>' >
          <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
          <bean:define id="forum" name="forumMessage" property="forum" />
          <table bgcolor="#f0f0f0"
         cellpadding="3" cellspacing="0" border="0" width='<bean:write name="tablewidth"/>' >
            <tr>
            <td><img src="/images/forum_new.gif" width="12" height="12"></td>
              <td width="100%" style='word-WRAP: break-word'><logic:equal name="forumMessage" property="root" value="true">
                <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />' target="_blank">
                </logic:equal>
                <logic:equal name="forumMessage" property="root" value="false"><a href='<%=request.getContextPath()%>/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />' target="_blank" rel="nofollow"></logic:equal>
                <bean:write name="forumMessage" property="modifiedDate3" />
                &nbsp;
                <bean:write name="forumMessage" property="account.username" />
                发表了评论   ></a></td>
            </tr>
          </table>
        </logic:iterate></td>
    </tr>
  </table>
</div>
<logic:notEqual name="noheader" value="on">
  </body>
  </html>
</logic:notEqual>
