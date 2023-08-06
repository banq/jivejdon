<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>
<bean:write name="title" />
</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

</head>
<body bgcolor='#ffffff'>



<logic:present name="message">
  <b><font color="BLUE"><bean:write name="message" /></font></b>
</logic:present>

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>

<html:errors/>