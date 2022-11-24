<%@ page contentType="text/css; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
body,table,td,select,input,div,form{color:#444;font:400 15px/1.8 "Microsoft YaHei",Rajdhani,Play,Helvetica Neue,Helvetica,Helvetica,Arial,sans-serif;-webkit-font-smoothing:antialiased;text-rendering:optimizeLegibility;line-height:1.8em}body{
	margin-top: 0px;
	background: #fff url('<%=request.getContextPath()%>/images/body_bg_verlauf.png') repeat-x;
}a img{border:0;}A{text-decoration:none;color:#036;}a:visited{text-decoration:none;color: #666;font-weight:lighter;}A:hover{text-decoration:underline;color:#eab30d;}form,textarea{font-size:12px;COLOR:#000;}input{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}textarea{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}select{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}ul{FONT-SIZE:14px;COLOR:#000;list-style-type:square;}li{FONT-SIZE:14px;COLOR:#000;}ol{FONT-SIZE:14px;COLOR:#000;list-style-type:decimal;line-height:150%;font-style:normal;font-weight:normal;}.small{font-size:12px;font-family:Tahoma,Verdana;}.smallgray{font-size:12px;COLOR:#999;display:inline;}.blackgray{font-size:12px;COLOR:#bbb;font-family:Tahoma,Verdana;}.small11{font-size:11px;font-family:Arial,Tahoma;}h4{font-size:14px;}h3{font-size:15px;}h2{font-size:20px;}.big16{font-size:16px;}.big18{font-size:18px;}.bige20{font-size:20px;}.body_href{font-size:14px;text-decoration:none;color:#284b78;font-weight:bold;}.white{color:#fff;}.bodystyle{FONT-SIZE:14px;}
A.a03{text-decoration:none;color:#fff;font-size:12px;}
.title{font-size:14px;font-weight:bold;line-height:144%;color:#093;}.article{font-size:14px;line-height:150%;color:#212121;}.tpc_content{font-size: 14px;letter-spacing:0.5px;line-height:25px;font-weight:normal;color:#000;margin: 5px;}.home_content{font-size:13px;color:#333;line-height:150%;}.gen-2{clear:both;}pre{white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;}DIV.pagination{padding:3px;margin:3px;font-size:10px;font-weight:bold;font-family:Arial,Times;}DIV.pagination a{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid #9aafe5;text-decoration:none;color:#2e6ab1;}DIV.pagination a:hover,.pagination a:active{border:1px solid #dd6900;color:#000;background-color:lightyellow;text-decoration:none;}DIV.pagination .current{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid navy;font-weight:bold;background-color:#2e6ab1;color:#FFF;}DIV.pagination .disabled{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid #929292;color:#929292;}
DIV.tres{PADDING-RIGHT: 6px; PADDING-LEFT: 0px; FONT-SIZE: 13px; PADDING-BOTTOM: 4px; COLOR: #313031; PADDING-TOP: 4px; FONT-FAMILY: Verdana,Tahoma,Arial,Helvetica,Sans-Serif; }DIV.tres A{BORDER-RIGHT: #b7d8ee 1px solid; PADDING-RIGHT: 6px; BORDER-TOP: #b7d8ee 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 4px; MARGIN: 0px 3px; BORDER-LEFT: #b7d8ee 1px solid; COLOR: #0030ce; PADDING-TOP: 5px; BORDER-BOTTOM: #b7d8ee 1px solid; TEXT-DECORATION: none}
DIV.tres A:hover{BORDER-RIGHT: #b7d8ee 1px solid; BORDER-TOP: #b7d8ee 1px solid; BORDER-LEFT: #b7d8ee 1px solid; COLOR: #0066a7; BORDER-BOTTOM: #b7d8ee 1px solid; BACKGROUND-COLOR: #d2eaf6}
DIV.tres A:active{BORDER-RIGHT: #b7d8ee 1px solid; BORDER-TOP: #b7d8ee 1px solid; BORDER-LEFT: #b7d8ee 1px solid; COLOR: #0066a7; BORDER-BOTTOM: #b7d8ee 1px solid; BACKGROUND-COLOR: #d2eaf6}
DIV.tres SPAN.current{BORDER-RIGHT: #b7d8ee 1px solid; PADDING-RIGHT: 6px; BORDER-TOP: #b7d8ee 1px solid; PADDING-LEFT: 5px; FONT-WEIGHT: bold; PADDING-BOTTOM: 4px; MARGIN: 0px 3px; BORDER-LEFT: #b7d8ee 1px solid; COLOR: #444444; PADDING-TOP: 5px; BORDER-BOTTOM: #b7d8ee 1px solid; BACKGROUND-COLOR: #d2eaf6}DIV.tres SPAN.disabled{DISPLAY:none;}.threadTitle{font-size: 14px}.tooltip_content{FONT-SIZE:14px;overflow:hidden;MARGIN:10px;PADDING-RIGHT:3px;PADDING-LEFT:3px;PADDING-BOTTOM:3px;PADDING-TOP:3px;}.post-tag{color:#3e6d8e;background-color:#e0eaf1;border-bottom:1px solid #3e6d8e;border-right:1px solid #7f9fb6;padding:3px 3px 3px 4px;text-decoration:none;white-space:nowrap;line-height:2;}.post-tag:hover{background-color:#3e6d8e;color:#e0eaf1;border-bottom:1px solid #37607d;border-right:1px solid #37607d;text-decoration:none;}.b_content_line{float:none;clear:none;display:block;overflow:hidden;width:658px;text-align:left;border-top:dashed 1px #e7e8ea;height:10px;margin-top:30px;}.quote_title{PADDING-RIGHT:5px;PADDING-LEFT:0;FONT-WEIGHT:bold;PADDING-BOTTOM:5px;MARGIN:5px 0 0 10px;PADDING-TOP:5px;}.quote_body{background:#f4f5f7 3px 3px no-repeat;border:1px dashed #BBB;padding:8px 12px 8px 36px;margin:5px 0;FONT-WEIGHT:lighter;}.displaycode{margin-top:0;margin-bottom:0;font-family:Andale Mono,Lucida Console,Monaco,fixed,monospace;font-size:11px;}.code-outline{background-color:#fff;border:1px solid #ccc;padding:5px 5px 5px 5px;}div.linkblock:hover{}div.post-headline h1,div.post-headline h2{margin:0;padding:0;}div.post-footer{clear:both;display:block;}div.post-footer{margin:0;padding:5px;background:#F5F5F5;color:#666;line-height:18px;}div.post-footer a:link,div.post-footer div.post-footer a:active{color:#333;font-weight:normal;text-decoration:none;}div.post-footer a:hover{color:#333;font-weight:normal;text-decoration:underline;}.overlay_dialog{background-color:#666;filter:alpha(opacity=60);-moz-opacity:.6;opacity:.6;}.overlay___invisible__{background-color:#666;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;}.dialog_nw{width:9px;height:23px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_left.gif) no-repeat 0 0;}.dialog_n{background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_mid.gif) repeat-x 0 0;height:23px;}.dialog_ne{width:9px;height:23px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_right.gif) no-repeat 0 0;}.dialog_e{width:2px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/center_right.gif) repeat-y 0 0;}.dialog_w{width:2px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/center_left.gif) repeat-y 0 0;}.dialog_sw{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_left.gif) no-repeat 0 0;}.dialog_s{background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_mid.gif) repeat-x 0 0;height:19px;}.dialog_se{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_right.gif) no-repeat 0 0;}.dialog_sizer{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/sizer.gif) no-repeat 0 0;cursor:se-resize;}.dialog_close{width:14px;height:14px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/close.gif) no-repeat 0 0;position:absolute;top:5px;left:8px;cursor:pointer;z-index:2000;}.dialog_minimize{width:14px;height:15px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/minimize.gif) no-repeat 0 0;position:absolute;top:5px;left:28px;cursor:pointer;z-index:2000;}.dialog_maximize{width:14px;height:15px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/maximize.gif) no-repeat 0 0;position:absolute;top:5px;left:49px;cursor:pointer;z-index:2000;}.dialog_title{float:left;height:14px;font-family:Tahoma,Arial,sans-serif;font-size:12px;text-align:center;width:100%;color:#000;}.dialog_content{overflow:auto;color:#DDD;font-family:Tahoma,Arial,sans-serif;font-size:10px;background-color:#123;}.top_draggable,.bottom_draggable{cursor:move;}.status_bar{font-size:12px;}.status_bar input{font-size:12px;}.wired_frame{display:block;position:absolute;border:1px #000 dashed;}.dialog{display:block;position:absolute;}.dialog table.table_window{border-collapse:collapse;border-spacing:0;width:100%;margin:0;padding:0;}.dialog table.table_window td,.dialog table.table_window th{padding:0;}.dialog .title_window{-moz-user-select:none;}

.userbox{
    margin:0px auto 0px auto;height:45px;overflow:hidden;border-bottom:solid 1px #D5D5D5;background:#F5F5F5;
	width: 100%;
}
.jdon-space{
	line-height: 5px;
}
.userbox a {	
color:#888888;
padding:3px 2px;
}
.userbox form {	
color:#888888;
padding:3px 2px;
}
.site-logo img{float:left;;top:2px;left:0;}
DIV.diggArea {
	MARGIN: 5px;
	FLOAT: left;
	WIDTH: 45px;
	HEIGHT: 53px;
	BACKGROUND: url(<%=request.getContextPath()%>/images/dig.png); 
	background-repeat: no-repeat;
}

.diggArea .diggNum {
	DISPLAY: block;
	COLOR: #a200ff;
	FONT-WEIGHT: lighter;
	FONT-SIZE: 16px;
	WIDTH: 45px;
	FONT-FAMILY: Georgia;
	HEIGHT: 30px;
	TEXT-ALIGN: center;
	margin: 0px;
	padding-top: 5px;
	padding-right: 0px;
	padding-bottom: 0px;
	padding-left: 0px;
}
.diggArea .top8 {
	padding-top:0px;
}

.diggArea .diggLink {
	DISPLAY: block; 
	WIDTH: 45px; 
	COLOR: #999; 
	TEXT-ALIGN: center;
}

#subbox{width:80px;}
#subbox span{display:block;overflow:hidden;float:left;width:56px;*width:76px; white-space:nowrap; text-overflow:ellipsis; }
.linkblock {  padding:5px 15px; background:#fff; border:1px solid #ddd; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);}
.frame-yy{
width:auto; float:left;
	 padding:1px 1px;
background:#fff; border:1px solid #ddd; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);	
}
.important {
	width: 285px;
	padding: 8px;
	margin: 0px;
	background-color: #F7F7F7;
	border: 1px solid #ddd;
	border-radius: 3px;
	box-shadow: 0 15px 10px -15px rgba(0,0,0,0.4);
    white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
.important a{
   line-height:23px;text-decoration:none;color:#888;
}
.thumbn{
    width: 100px;
	display: block;
	float: left;
	margin: 10px;
	background: #fff;
}
<%@ include file="./common/js/styles/default_css.jsp"%>
<%@ include file="./common/js/styles/mac_os_x_css.jsp"%>
<%@ include file="./common/js/styles/alert_css.jsp"%>
<%@ include file="./common/js/styles/autocomplete_css.jsp"%>
