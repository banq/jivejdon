<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page session="false" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>标签列表</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<style type="text/css">
.tag0 { font-size: 12px;}
.tag1 { font-size: 13px;}
.tag6 { font-size: 9px; }
.tag5 { font-size: 15px; }
.tag9 { font-size: 10px; }
.tag2 { font-size: 18px; }
.tag4 { font-size: 20px;}
.tag7 { font-size: 22px; }
.tag8 { font-size: 14px; }
.tag4 { font-size: 9px; }
.tag10 { font-size: 10px; }

</style>
<bean:parameter id="tablewidth" name="tablewidth" value="155"/>
<bean:parameter id="bgcolor" name="bgcolor" value="#FFFFD7"/>

<body leftmargin="0" rightmargin="0" topmargin="0" bgcolor="<%=bgcolor%>">

<table width="<%=tablewidth%>"  bgcolor="<%=bgcolor%>"><tr><td>

<logic:iterate id="threadTag" name="tagsListForm" property="list"  indexId="i">
    <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" >
             <span class="tag<%=i%>" ><bean:write name="threadTag" property="title" /></span>
             (<bean:write name="threadTag" property="assonum" />)&nbsp;&nbsp;&nbsp;&nbsp;
    </a>
    
  
</logic:iterate>


</td></tr></table>


</body>
</html>
