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
		String body =  request.getRequestURI() + "<br>\r\n"  ;

		Enumeration<?> e = getServletContext().getAttributeNames();
		while (e.hasMoreElements())
		{
    		String name = (String) e.nextElement();

    		Object value = getServletContext().getAttribute(name);

    		if (value instanceof Map) {
        		for (Map.Entry<?, ?> entry : ((Map<?, ?>)value).entrySet()) {
            		body=body +entry.getKey() + "=" + entry.getValue() + "<br>\r\n";
    		    }
    		} else if (value instanceof List) {
    		    for (Object element : (List)value) {
            		body=body +element + "<br>\r\n";
       		 }
		    }
		}

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
发生系统错误<bean:write name="error" />
<br>
<br>
<br>
<p>返回<html:link page="/"> 首页</html:link>
</center>
<%-- <script>
    try{
         alert('ERROR: 发生系统错误 <bean:write name="error" /> ');
    }catch(ex){}

   
   </script> --%>

<%@include file="../common/IncludeBottom.jsp"%>

