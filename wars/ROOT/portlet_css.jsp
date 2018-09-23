<%
response.setContentType("text/css");
long d = System.currentTimeMillis();
response.addDateHeader("Last-Modified", d);
response.addDateHeader("Expires", d+(5*24*60*60*1000));  
%>
#portlet-dragging * {
filter: alpha(opacity=75);
}
.portlet-box {
background-color: #FFFFFF;
border: 1px solid #6699CC;
padding: 15px 0 0 0;
border-top: none;
}
.portlet-minimum-height {
height: 100px;
}
.portlet-header-bar {
background-color: none;
position: relative;
text-align: left;
top: 9px;
z-index: 2;
}
.portlet-title {
background: #6699CC url(<%=request.getContextPath()%>/images/portlet/portlet_title_bg_gradient.gif) repeat-x;
color: #4A517D;
font-family: Tahoma, Arial;
height: 27px;
left: 10px;
padding: 0px 3px 0px 3px;
position: absolute;
}
.portlet-small-icon-bar {
height: 1em;
padding-right: 10px;
text-align: right;
}
.portlet-small-icon {
height: 14px;
margin: -1px;
width: 14px;
}
.portlet-top-decoration {
background: url(<%=request.getContextPath()%>/images/portlet/portlet_corner_ul.gif) no-repeat top left;
height: 5px;
}
.portlet-top-decoration DIV {
background: url(<%=request.getContextPath()%>/images/portlet/portlet_corner_ur.gif) no-repeat top right;
height: 5px;
}
.portlet-top-decoration DIV DIV {
background: #FFFFFF;
border-top: 1px solid #6699CC;
font-size: 0;
height: 5px;
margin: 0 5px 0 5px;
}
.portlet-inner-top {
margin: 0 auto 0 auto;
}
.portlet-bottom-blank {
margin-bottom: 5px;
}
.portlet-bottom-decoration {
background: url(<%=request.getContextPath()%>/images/portlet/portlet_corner_bl.gif) no-repeat top left;
height: 5px;
}
.portlet-bottom-decoration DIV {
background: url(<%=request.getContextPath()%>/images/portlet/portlet_corner_br.gif) no-repeat top right;
height: 5px;
}
.portlet-bottom-decoration DIV DIV {
background: #FFFFFF;
border-bottom: 1px solid #6699CC;
font-size: 0;
height: 5px;
margin: 0 5px 0 5px;
}
.portlet-bottom-decoration-2 {
background: url(<%=request.getContextPath()%>/images/portlet/shadow/middle.gif) repeat-x;
width: 100%;
}
.portlet-bottom-decoration-2 DIV {
background: url(<%=request.getContextPath()%>/images/portlet/shadow/left.gif) no-repeat;
}
.portlet-bottom-decoration-2 DIV DIV {
background: url(<%=request.getContextPath()%>/images/portlet/shadow/right.gif) no-repeat top right;
font-size: 0;
height: 6px;
}
