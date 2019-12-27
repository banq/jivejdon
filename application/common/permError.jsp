<%@ page contentType="text/html; charset=UTF-8"  isErrorPage="true"%>
<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page import="java.io.*,java.util.*"%>

<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF,com.jdon.jivejdon.spi.component.email.*"%>
<%
   String isSendMail=(String)this.getServletContext().getAttribute(request.getRemoteAddr()+"500"); 
   if (isSendMail == null)
	{		
		EmailHelper emailHelper = (EmailHelper)WebAppUtil.getComponentInstance("emailHelper", this.getServletContext());
     	String subject = request.getRemoteAddr();     	
    	String body =  request.getHeader("Referer") + " " + request.getRequestURI() + " check: localhost.log ";
    	String toEmail = "13801923488@139.com";
    	String toName = "banq";
    	String fromEmail = "429722485@qq.com";
    	EmailVO emailVO = new EmailVO(toName, toEmail, "", fromEmail, subject, body + " from:" + fromEmail, com.jdon.jivejdon.util.EmailTask.NOHTML_FORMAT);
    	emailHelper.send(emailVO);
		this.getServletContext().setAttribute(request.getRemoteAddr()+"500","true");
	}
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
if (errorBlocker.checkRate(request.getRemoteAddr(), 5)){
    return;
}

%>

<bean:define id="title" value=" 错误" />
<%@ include file="../common/IncludeTop.jsp"%>
<bean:parameter id="error" name="error" value="" />
<center>
<H3>服务器内部错误......</H3>
发生系统错误<bean:write name="error" />，<a href='javascript:contactAdmin()'>请反馈 帮助完善开源JiveJdon</a>
<br>
<br>
<br>
<p>返回<html:link page="/"> 首页</html:link>
</center>
<script>
    try{
         alert('ERROR: 发生系统错误 <bean:write name="error" /> ');
    }catch(ex){}

    function contactAdmin(){
    	location.href = '<%=request.getContextPath()%>/forum/feed/feedback.jsp?subject=fromjivejdon3&body=<%=request.getHeader("Referer")%>_<bean:write name="error" />'
        	    + location.href;
    }
   </script>

<%@include file="../common/IncludeBottom.jsp"%>


<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>