<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>
<bean:write name="title" />
</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<script type="text/javascript" src="/common/js/prototype.js"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>
<script language="javascript" src="/common/js/autocomplete.js"></script>

</head>
<body bgcolor='#ffffff'>



<!-- Support for non-traditional but simple message -->
<logic:present name="message">
  <b><font color="BLUE"><bean:write name="message" /></font></b>
</logic:present>

<!-- Support for non-traditional but simpler use of errors... -->
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>

<html:errors/>