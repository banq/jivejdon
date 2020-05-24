<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
    domainUrl = domainUrl + request.getContextPath();
String url = "";
if (request.getParameter("threadId") != null){
  url = domainUrl + "/" + request.getParameter("threadId");
}else if (request.getParameter("fullurl") != null){
  url = request.getParameter("fullurl");
}
%>
<!doctype html>
<html>
<head>
<title>微信扫描</title>
    <meta charset="utf-8">
    <meta http-equiv="content-language" content="zh-CN" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="robots" content="noindex">
<script type="text/javascript" src="/common/jquery.min.js"></script>
<script type="text/javascript" src="/common/js/jquery-qrcode-min.js"></script>
<style>
body {
	font-size:11px;
	margin: 0px;
}
</style>
</head><body>
<div id="qrcode"></div>
<script>
        jQuery('#qrcode').qrcode({
                text        : "<%= url%>"
        });        
</script>		
微信扫描或下载识别二维码

</body></html>
		