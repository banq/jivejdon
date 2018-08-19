
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.IPBanListManagerIF"%>

<%
try{
	IPBanListManagerIF iPBanListManager = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", request);
	iPBanListManager.addBannedIp(request.getRemoteAddr());
}catch(Exception e){
	response.sendError(404);
}
%>