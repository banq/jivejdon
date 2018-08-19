<%@page import="com.jdon.security.web.CookieUtil"%>

<div id="isNewMessage" data-role="header" data-theme="e" ></div>
<script>
checkmsg("<%=request.getContextPath() %>/mobile/checknewmessage.shtml");
</script>

<%-- 
<%
boolean isloged = false;
if (request.getUserPrincipal() != null)
	isloged = true;
else if (request.getUserPrincipal() == null){
	String username = CookieUtil.getUsername(request);
	String password = CookieUtil.getPassword(request);
	if ((username != null && username.length()!= 0) &&
		     (password != null && password.length()!= 0))
		isloged = true;
}
if (isloged){
%>
<script>
checkmsg("<%=request.getContextPath() %>/mobile/checknewmessage.shtml?Referer=https://"+ location.hostname);
</script>
<%} %>
--%>