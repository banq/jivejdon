<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>	

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="robots" content="noindex">
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />

<script>
function shareto(id){
var pic="";
var url=encodeURIComponent(window.top.document.location.href);
var title=encodeURIComponent(window.top.document.title);
var _gaq = _gaq || [];

if(id=="fav"){
addBookmark(document.title);
return;
}else if(id=="qzone"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'QZone', 1]);
window.open(' http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+url);
return;
}else if(id=="sina"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'SinaT', 1]);
//window.open(' http://v.t.sina.com.cn/share/share.php?title='+title+'&url='+url+'&source=bookmark','_blank');
window.open(" http://service.weibo.com/share/share.php?url="+url+"&appkey=610475664&title="+title,"_blank","width=615,height=505");
return;
}else if(id=="facebook"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'facebook', 1]);
window.open(' http://facebook.com/share.php?u=' + url + '&t=' + title,'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes');
return;
}else if(id=="twitter"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'twitter', 1]);
window.open('http://twitter.com/share?url=' + url + '&text=' + title,'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes');
return;
}else if(id=="googlebuzz"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'GoogleBuzz', 1]);
window.open(' https://plus.google.com/share?url='+url,'_blank');
return;
}else if(id=="douban"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'Douban', 1]);
var d=document,e=encodeURIComponent,s1=window.getSelection,s2=d.getSelection,s3=d.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',r=' http://www.douban.com/recommend/?url='+e(d.location.href)+'&title='+e(d.title)+'&sel='+e(s)+'&v=1',x=function(){if(!window.open(r,'douban','toolbar=0,resizable=1,scrollbars=yes,status=1,width=450,height=330'))location.href=r+'&r=1'};
if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}
return;
}else if(id=="renren"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'RenRen', 1]);
window.open(' http://www.connect.renren.com/share/sharer?url='+url+'&title='+title,'_blank','resizable=no');
return;
}else if(id=="xianguo"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'XianGuo', 1]);
window.open(' http://xianguo.com/service/submitdigg/?link='+url+'&title='+title,'_blank');
return;
}else if(id=="mail"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'Mail', 1]);
window.open('mailto:?subject='+title+'&body='+encodeURIComponent('这是我看到了一篇很不错的文章，分享给你看看！\r\n\r\n')+title+encodeURIComponent('\r\n')+url);
return;
}
}
</script>
<style type="text/css"> 
#share a{BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid}
</style>
</head><body>
<p>

<div id="share">
<a href="javascript:void()" onClick="window.open('<%=request.getContextPath()%>/common/barcode.jsp?fullurl='+window.top.document.location.href,'newwindow','height=300,width=300,top=0,left=0,toolbar=no,menubar=no,location=no, status=no')"> 
微信分享</a>
<a href="javascript:shareto('qzone')" title="分享到QQ空间">QQ空间</a>
<a href="javascript:shareto('sina')" title="分享到新浪微博">新浪微博</a>
<a href="javascript:shareto('douban')" title="分享到豆瓣">豆瓣</a>
<a href="javascript:shareto('renren')" title="分享到人人网">人人网</a>
<a href="javascript:shareto('googlebuzz')" title="分享到Google+">Google+</a>
<a href="javascript:shareto('facebook')" title="收藏到 - Facebook">Facebook</a>
<a href="javascript:shareto('twitter')" title="收藏到 - Twitter">Twitter</a>
<a href="javascript:shareto('xianguo')" title="分享到鲜果网">鲜果网</a>
<a href="javascript:shareto('mail')" title="发送邮件分享给朋友">邮件</a>
<a href="javascript:void()" onClick='copyToClipBoard()' > 复制网址</a>


</div>    

<script language="javascript">
function copyToClipBoard()
{
try{
 var clipBoardContent=window.top.document.title;
 clipBoardContent+='\r\n' + window.top.document.location.href;
 window.clipboardData.setData("Text",clipBoardContent);
 alert("复制成功，请粘贴到你的QQ/MSN上推荐给你的好友！\r\n\r\n内容如下：\r\n" +clipBoardContent);
}catch(e){}	

}
</script>

</body></html>