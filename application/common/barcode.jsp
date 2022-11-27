<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
    domainUrl = domainUrl + request.getContextPath();
String url = "";
if (request.getParameter("threadId") != null){
  url = domainUrl + "/" + request.getParameter("threadId");
}else if (request.getParameter("fullurl") != null){
  url = request.getParameter("fullurl");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="robots" content="noindex">
<script type="text/javascript" src="https://static.jdon.com/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/common/js/jquery-qrcode-min.js"></script>
<style>
body {
	font-size:11px;
	margin: 0px;
}
</style>
</head><body>
<div id="qrcode"></div>
<canvas id="myCanvas" width="120" height="120" style="border:1px solid #000000;display:none;"></canvas>
<script>
        jQuery('#qrcode').qrcode({
          width: 120, //宽度   
            height:120, //高度 
                text        : "<%= url%>"
        });        
//从 canvas 提取图片 image
function convertCanvasToImage(canvas) {
    //新Image对象，可以理解为DOM
    var image = new Image();
    // canvas.toDataURL 返回的是一串Base64编码的URL，当然,浏览器自己肯定支持
    // 指定格式 PNG
    image.src = canvas.toDataURL("image/png");
    return image;
}
//获取网页中的canvas对象
var mycanvas1=document.getElementsByTagName('canvas')[0];
 
//将转换后的img标签插入到html中
var img=convertCanvasToImage(mycanvas1);
$('#qrcode').html(img);     
</script>		
微信扫描或下载二维图片

</body></html>
		