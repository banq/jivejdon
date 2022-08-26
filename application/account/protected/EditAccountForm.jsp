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
编辑注册资料
</title>
  <link rel="stylesheet" href="//cdn.jdon.com/common/jivejdon5.css" type="text/css"/>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
  <script type="text/javascript" src="//cdn.jdon.com/common/js/prototype.js"></script>
<body>
<html:errors/>

<div align="center">
<center>注册资料修改 (密码只能重新设定)</center>
<html:form method="post" action="/account/protected/editSaveAccount.shtml" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="edit" />
<html:hidden name="accountForm" property="userId" />
<input type="hidden" name="actionType" value="editSave"/>           

<%@include file="../IncludeAccountFields.jsp"%>

</html:form>

<p>
</p>
</div>
</body></html>

