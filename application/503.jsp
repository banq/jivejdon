<%@ page contentType="text/html; charset=UTF-8"%>


<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkCount(request.getRemoteAddr(), 10)){
	response.sendError(404);
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
        
        <title>503 错误</title>
    </head>
    <body>
        503 错误
        

    </body>
</html>
