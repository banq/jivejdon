<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/javascript; charset=UTF-8" %>

  
<%  
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(30 * 24 * 60 * 60, request, response);    
    response.setContentType("text/javascript; charset=utf-8");
%>


<%
int width = 240;
if (request.getParameter("width")!= null)
	width = Integer.parseInt(request.getParameter("width"));

int height = 180;
if (request.getParameter("height")!= null)
	height = Integer.parseInt(request.getParameter("height"));

%>

var xsTextBar = 1; //是否显示文字链接，1为显示，0为不显示
var xsPlayBtn = 0; //是否显示播放按钮，1显示，0为不显示
var xsImgSize = new Array(<%=width%>,<%=height%>); //幻灯图片的尺寸，格式为“宽度,高度”
 
var xsImgs = new Array();
var xsImgLinks = new Array();
var xsImgTexts = new Array();


<% int i = 0;%>
 
<logic:iterate  id="image" name="imageListForm" property="list">
     <bean:define id="forumMessage" name="image" property="forumMessage"></bean:define>
    
     
   xsImgs[<%=i%>] =  "<%=request.getContextPath()%>/img/"+ "<bean:write name="image" property="imageId" />"+"-"+"<bean:write name="image" property="oid" />";
       <logic:equal name="forumMessage" property="root" value="true">
                xsImgLinks[<%=i%>] = '<%=request.getContextPath()%>/<bean:write name="forumMessage" property="forumThread.threadId"  />';                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
                xsImgLinks[<%=i%>] = '<%=request.getContextPath()%>/nav/<bean:write name="forumMessage" property="forumThread.threadId"  />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'; 
       </logic:equal>                          
   
   xsImgTexts[<%=i%>] = '<span style="font-size:12px;font-style:none;font-weight:none;text-decoration:none"><bean:write name="forumMessage" property="messageVO.subject" /></span>';
   <%  i++; %>
</logic:iterate>				

function slideout(){
  var slideHTML = "<style type=\"text/css\">#slidearea { width:" + xsImgSize[0] + "px; height:" + xsImgSize[1] + "px; overflow: hidden; margin: 0 auto; text-align: center; } #slidearea img { width: " + xsImgSize[0] + "px; height: " + xsImgSize[1] + "px; } #slidefooter {width:" + xsImgSize[0] + "px; } </style>"
   + "<div id=\"slidearea\">";
  if (xsImgs.length != 0) {
	 slideHTML = slideHTML + "<a href=\""+ xsImgLinks[0] +"\" target=\"_blank\"><img src=\"" + xsImgs[0] + "\" onload=\"imgLoadNotify();\" \/><\/a>";
  }
  slideHTML = slideHTML +"<\/div>";
  if (xsTextBar != 0) {
	slideHTML = slideHTML + "<div id=\"slidefooter\">";
	if (xsPlayBtn != 0) {
		slideHTML = slideHTML + "<a id=\"slideplay\" title=\" Play \" href=\""+ xsImgLinks[0] +"\">æ­æ¾<\/a>";
	}
	if (xsImgTexts.length != 0) {
		slideHTML = slideHTML + "<a id=\"slideprev\" title=\" Last \" href=\"#\" onclick=\"rewind();return false\">&laquo;<\/a><a id=\"slidenext\" title=\" Next \" href=\"#\" onclick=\"forward();return false\" style=\"text-align: center; text-decoration: none;\">&raquo;<\/a><p id=\"slidetext\"><a href=\""+ xsImgLinks[0] +"\" target=\"_blank\">"+ xsImgTexts[0] +"<\/a><\/p>";
	}
	slideHTML = slideHTML + "<\/div>";
  }
  document.getElementById("slideimg").innerHTML= slideHTML;

}
