<%-- <%
response.setContentType("text/html");
response.setDateHeader("Expires", 0);
response.setHeader("Location", request.getContextPath());
response.setStatus(301); 
%> --%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head><meta charset="UTF-8"><title>验证码测试</title></head>
<body>
  <a href="<%=request.getContextPath()%>/captcha/referer.shtml">点击测试腾讯验证</a>
</body>
</html>