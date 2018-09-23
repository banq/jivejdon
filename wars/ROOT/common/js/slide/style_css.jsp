<%
response.setContentType("text/css");
%>
* { word-break: break-all; } 
body { margin: 0; padding: 0; background: #FFF; color: #666666; text-align: center; font: 12px Arial, Helvetica, sans-serif; }
#wrap { margin: 0 auto; text-align: left; width: 960px; padding: 0 0px; w\idth: 960px; }


a { color: #666666; text-decoration: none; }
	a:hover { text-decoration: underline; color: #336600; }
		a:visited { color: #666;}
	a img { border: none; }



.sideL { width: 300px; float: left; overflow: hidden; }


.block { margin: 0 0 6px; border: 1px solid #DFDFDF; background: #FFF; }
	.block h3 { margin: 1px 0 0; font-size: 1em; color: #333333; line-height: 26px; padding-left: 0.5em; background: url(<%=request.getContextPath()%>/common/js/slide/dotline_h.gif) repeat-x bottom; }
		.block h3 a { color: #333333; }
		
		/*\*/ * html .sideblock h3 { height: 1%; } /**/
	

#slideimg { padding-top: 7px; }

#slidefooter { background: #333333; text-align:center; height: 27px; margin: 0 auto; overflow: hidden; }
#slidefooter a { color:#CCCCCC;}
#slideprev { background: url(<%=request.getContextPath()%>/common/js/slide/slide_prev.gif); width: 25px; height: 27px; text-indent: -9999px; float: left; overflow: hidden; }
#slidenext { background: url(<%=request.getContextPath()%>/common/js/slide/slide_next.gif); width: 25px; height: 27px; text-indent: -9999px; float: right; overflow: hidden; }
#slidetext { margin: 0; line-height: 29px; height: 27px; overflow: hidden; }
#slideplay { background: url(<%=request.getContextPath()%>/common/js/slide/slide_play.gif); width: 46px; height: 27px; text-indent: -9999px; float: right; overflow: hidden; }

