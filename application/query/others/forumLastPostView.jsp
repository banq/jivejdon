<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>最新贴</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
</head>

<body >
<logic:notEmpty name="forumForm" property="forumState">
<logic:notEmpty name="forumForm" property="forumState.latestPost">
    <bean:define id="forumMessage" name="forumForm" property="forumState.latestPost"/>
       <logic:notEmpty name="forumMessage">
 

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" style="text-align: center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="6" cellspacing="1" border="0" width="100%">

    <tr bgcolor="#FFFFEC">
        <td width="1%" rowspan="2" valign="top">
        
        </td>
        <td >
 
  <table width="100%"  cellpadding="1" cellspacing="1">
    <tr>
        <td width="97%">
        <bean:write name="forumMessage" property="messageVO.subject"/>
        </td>
        <td width="1%" nowrap="nowrap">
        <span class="smallgray">
          <bean:write name="forumMessage" property="creationDate" />
         </span>
        </td>
       <td width="1%" nowrap="nowrap" style="text-align: center">

          </td>
    </tr>
   </table>
   
         </td>
    </tr>
    <tr bgcolor="#FFFFEC">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td style='word-WRAP: break-word'>
     
  
<span class="artilce">
            <bean:write name="forumMessage" property="messageVO.shortBody[50]" />
</span>

        
        </td></tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>
                                                   
       </logic:notEmpty>
</logic:notEmpty>
</logic:notEmpty>
</body>
</html>
