<%@ page contentType="text/html; charset=UTF-8"  isErrorPage="true"%>
<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page import="java.io.*,java.util.*"%>

<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF,com.jdon.jivejdon.spi.component.email.*"%>
<%

   String isSendMail=(String)this.getServletContext().getAttribute("500"); 
   if (isSendMail == null)
	{		
		EmailHelper emailHelper = (EmailHelper)WebAppUtil.getComponentInstance("emailHelper", this.getServletContext());
     	String subject = request.getRemoteAddr();     
		String body =  request.getRequestURI() + "<br>\r\n"  ;


		Enumeration<String> params = request.getParameterNames(); 
        while(params.hasMoreElements()){
               String paramName = params.nextElement();
			   body=body + paramName +":" + request.getParameter(paramName) +"<br>\r\n";

        }
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			// 排除Cookie字段
			if (key.equalsIgnoreCase("Cookie")) {
				continue;
			}
			String value = request.getHeader(key);
			body=body + key  +":" +  value +"<br>\r\n";
		}    	

		

    	String toEmail = "banq@163.com";
    	String toName = "banq";
    	String fromEmail = "banq@163.com";
    	EmailVO emailVO = new EmailVO(toName, toEmail, "", fromEmail, subject, body + " from:" + fromEmail, com.jdon.jivejdon.util.EmailTask.NOHTML_FORMAT);
    	emailHelper.send(emailVO);
		this.getServletContext().setAttribute("500","true");
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
<H3>错误......</H3>
发生错误<bean:write name="error" />
<br>
 <p>
 <form  method="post" action="/query/threadViewQuery.shtml">
		查找：<input type="text"  value="" name="query" size="40">
</form>

<br>
<p> <a href="<%=request.getContextPath() %>/" class="btn">Jdon.com首页</a>
</center>
<%-- <script>
    try{
         alert('ERROR: 发生系统错误 <bean:write name="error" /> ');
    }catch(ex){}

   
   </script> --%>

<%@include file="../common/IncludeBottom.jsp"%>

