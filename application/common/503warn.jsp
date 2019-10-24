<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle,com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame,com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF"%>

<%
	try{
	CustomizedThrottle customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", this.getServletContext());
	HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), "503");
	if (customizedThrottle.processHit(hitKey)){
		customizedThrottle.addBanned(request.getRemoteAddr());	
	}
}catch(Exception e){
}
%>