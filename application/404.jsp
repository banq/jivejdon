<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page isELIgnored="false" %>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF,com.jdon.jivejdon.manager.email.EmailHelper" %>
<%@ page import="com.jdon.jivejdon.manager.email.EmailVO" %>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkRate(request.getRemoteAddr(), 4)){
  String isSendMail = (String) this.getServletContext().getAttribute(request.getRemoteAddr() + "404");
  if (isSendMail == null) {%>
<jsp:include page="/admin/user/BanIP.jsp" flush="true"/>
<%
		EmailHelper emailHelper = (EmailHelper)WebAppUtil.getComponentInstance("emailHelper", this.getServletContext());
     	String subject = "404";     	
    	String body = "404 attack:" + request.getRemoteAddr() + " from url:" + request.getHeader("Referer") ;
    	String toEmail = "banq@163.com";
    	String toName = "banq";
    	String fromEmail = "banq@163.com";
    	EmailVO emailVO = new EmailVO(toName, toEmail, "", fromEmail, subject, body + " from:" + fromEmail, com.jdon.jivejdon.util.EmailTask.NOHTML_FORMAT);
    	emailHelper.send(emailVO);
		this.getServletContext().setAttribute(request.getRemoteAddr()+"404","true");
	}
    return;
}

%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="refresh" content="60;url=”https://www.jdon.com" />
    <title>该页无法找到404 ERROR</title>
    <link rel="stylesheet" type="text/css" href="//cdn.jdon.com/common/style/cmstop-error.css" media="all">
</head>
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
  <script>
     (adsbygoogle = window.adsbygoogle || []).push({
          google_ad_client: "ca-pub-7573657117119544",
          enable_page_level_ads: true
     });
  </script>
<body class="body-bg">

<center>
<div class="main">
    <p class="title">404错误！<br> 非常抱歉，你访问的网址
    <br>
	<script>
    document.write(window.location.href);
    </script>
    <br>在Jdon.com无法找到. 去除多余非法字符后再试试看
 <br>可进入<a href="/message/threadViewQuery.shtml">查询页面</a>查找
    <a href="<%=request.getContextPath() %>/" class="btn">Jdon.com首页</a>
</div>
</center>

</body></html>
