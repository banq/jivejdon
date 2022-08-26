<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<logic:notEmpty name="threadForm" property="state">
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>最新贴</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
<META HTTP-EQUIV="Expires" CONTENT="0">
</head>

<body >


<logic:notEmpty name="threadForm" property="state.latestPost">
<bean:define id="forumMessage" name="threadForm" property="state.latestPost"/>
<logic:notEmpty name="forumMessage">
 

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="6" cellspacing="1" border="0" width="100%">

    <tr bgcolor="#FFFFEC">
        <td width="1%" rowspan="2" valign="top">
        
        </td>
        <td >
        <bean:write name="forumMessage" property="messageVO.subject"/>
       <br>
        <span class="smallgray">
          <bean:write name="forumMessage" property="creationDate" />
         </span>
      
         </td>
    </tr>
    <tr bgcolor="#FFFFEC">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td style='word-WRAP: break-word'>
     
  
<span class="article">
      <bean:write name="forumMessage" property="messageVO.shortBody[100]" />
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

</body>
</html>
</logic:notEmpty>