<%@ page contentType="text/css; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(50 * 24 * 60 * 60, request, response);
%>

<%@ include file="../../jivejdon_css.jsp"%>
.gen-2 {
	clear: both;
}


.table-entice{padding:1px 0;text-align:center;color:#F5F5F5;margin:0 auto}
.table-entice p{margin-bottom:0;}
.table-entice img{ 
    float:right;	
	padding:6px 5px;
	 position:static;
  +position:relative;
  top:-20%;left:-1%;
}
.table-entice .tres{
	float:left;
	padding:6px 5px;
	COLOR:#ffffff
}
.table-button{display:inline-block;
height:30px;
width:1036px;
color:#ffffff;
font-size:12px;
background:#ccc;
}

.table-button-left{
    float:left;	
    padding: 0px 8px;
    width:250px;
	text-align: left;
}

.table-button-right{
    float:right;	
    padding: 5px 20px;;
    width:200px;
	text-align: right;
}

a.whitelink{
 color:#ffffff;
 text-decoration:none;
}

.post_all{
  margin:0 auto;
  width: 1000px;
  padding:2px 20px 20px 20px; background:#fff; border:1px solid #ccc;border-top-color: #ffffff; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);
}

.post_warp {
	background-color: #ffffff;
	text-align: center;
	width: 980px;
	overflow:hidden;
	margin:0 auto;
	border-top-width: 1px;
	border-top-style: solid;
	border-right-style: none;
	border-bottom-style: solid;
	border-left-style: none;
	border-top-color: #00ABA9;
	border-bottom-width: 1px;
	border-bottom-color: #00ABA9;	
}

.post_sidebar {
	left: 1px;
	padding: 5px;
	top: 1px;
	width: 140px;
	float:left;
	border-top-style: none;
	border-right-style: dashed;
	border-bottom-style: none;
	border-left-style: none;
	border-right-width: 1px;
	border-right-color: #8CBF26;
	overflow:hidden;
	padding-bottom:9999px;
	margin-bottom:-9999px;
    background-color: #ffffff;
}


.post_author {  
	text-align:left;
	border: none;
	clear: both;
	float: left;
	width: 130px;		
}
.post_authorin{
  width: 130px;
	padding: 10px;
	margin-top: 5px;
}
.post_author_pic{
   padding:2px 2px; background:#fff; border:1px solid #ddd; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);
}



.post_body {
	background-color: #FFF;
	float:left;
	padding: 0px;	
	width: 800px;	
	border-top-width: 1px;
	border-top-style: dashed;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	border-top-color: #8CBF26;
}


.post_bodyin {
	border: none;	
	text-align: left;
	width: 750px;
    height: auto;
    padding: 5px;    
    MARGIN: 5px; 
    line-height:23px;
}

.post_body_content{
	min-height:300px;
}

.post_header {
	background-color: #ffffff;
	border: none;
	float:left;	
	padding: 6px;
	top: 1px;
	width: 744px;
}

.post_title {
	border: none;	
	text-align: left;
	width: 744px;
}
.post_titlename {
	border: none;
	clear: both;
	display: inline;
	float: left;	
	margin-left: 10px;
	margin-top: 10px;
	padding: 1px;
	width: 500px;
}

.post_titledate {
	border: none;
	display: inline;
	float: left;
	height: 23px;
	line-height: 23px;
	margin-left: 1px;
	margin-top: 10px;
	padding: 1px;
	
}

.post_titleright {
	border: none;
	display: inline;
	float: left;
	height: 23px;
	line-height: 23px;
	margin-left: 1px;
	margin-top: 10px;
	padding: 1px;
	text-align: center;
	
}

.post_titleright2{
	border: none;	
	float: right;
	text-align: right;
	margin: 1px;
	padding: 1px;
	width: 60px;
	min-height: 30px;
}

.post_titletag {
	background-color: #ffffff;
	border: none;
	display: inline;
	float: left;
	margin-left: 5px;
	margin-top: 1px;
	margin-bottom:5px;
	padding: 1px;
	width: 500px;
}

.approved{
  text-align: left;
  width: 675px;
  margin:0 auto;	
}
.box1, .box2, .box3, .box4 {
	float:left;
	background-repeat: no-repeat;
	background-position: left top;
	text-decoration: none;
	background-image: url(../../images/ti.png);
}

.box1{ background-position: 0 0; width: 18px; height: 18px;} 
.box2{ background-position: -23px 0; width: 18px; height: 18px; } 
.box3{ background-position: -46px 0; width: 18px; height: 18px; } 
.box4{ background-position: -69px 0; width: 18px; height: 18px; }

.box11, .box12, .box13{
	float:left;
	background-repeat: no-repeat;
	background-position: left top;
	text-decoration: none;
	background-image: url(../../images/rbs.png);
    display:inline;
}
.box11{ background-position: 0 0; width: 18px; height: 18px;} 
.box12{ background-position: -23px 0; width: 18px; height: 18px; } 
.box13{ background-position: -46px 0; width: 18px; height: 18px; }

#divname ul{margin:0px; padding:0px;}
ul.link li {
	background-image: url(../../images/aubs.png);    
    background-repeat: no-repeat;
    display: block;
    font-size: 16px;
    letter-spacing: -0.04em;
    padding: 1px 45px 2px 18px;
    text-decoration: none;
}
.box21{ background-position: 0 0;} 
.box22{ background-position: 0 -21px;} 
.box23{ background-position: 0 -44px;} 
.box24{ background-position: 0 -67px;} 

.pic-yy{
   padding:1px 1px; background:#fff; border:1px solid #ddd; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);
}
.advert{
   padding:0px;margin:0px;background:#FaFaFa; border:0px solid #ddd; border-radius:3px; box-shadow:0 15px 10px -15px rgba(0,0,0,0.4);

}
