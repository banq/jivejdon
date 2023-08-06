<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>JiveJdon论坛管理</title>
</head>

<frameset rows="90,*" bordercolor="#0099cc" border="0" frameborder="0" framespacing="0" >
	<frame src="tabs.jsp" name="header" scrolling="no" marginheight="0" marginwidth="0" noresize>
	<frameset cols="130,*" bordercolor="#0099cc" border="0" frameborder="0" >
		<frame src="<html:rewrite page="/admin/sidebar.shtml?method=system"/>" name="sidebar" scrolling="auto" marginheight="0" marginwidth="0" noresize>
			<frame src="main.jsp" name="main" scrolling="auto">
    </frameset>
</html>
