<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>
Url
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta   http-equiv="pragma"   content="no-store">
<link href="jdon.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3>Sitemap Url</h3>
<p>
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>
<script language="JavaScript" type="text/javascript">
<!--

function checkPost() {
      var check = false;
      if ((document.urlForm.ioc.value != "")
          && (document.urlForm.name.value != "")){
          check = true;
      }else{
          alert("you must input something!");
      }
      return check;
}
//-->
</script>

<html:form action="/admin/sitemap/urlSaveAction.shtml" method="POST" onsubmit="return checkPost();">
<html:hidden property="action"/>
<html:hidden property="urlId"/>

网址:<html:text property="ioc"/>
<br>
标题:<html:text property="name"/>
<br>
<html:submit property="submit" value="保存"/>
</html:form>
</body>
</html:html>
