<%@ page trimDirectiveWhitespaces="true" %>
<script>
   var isLogin = false; 
</script>
<%
if ( request.getUserPrincipal() != null){
    request.setAttribute("principal", request.getUserPrincipal());
    request.getSession().setAttribute("online",request.getUserPrincipal().toString());
    %>
<script>   isLogin = true;</script>
<%
}else{
	request.removeAttribute("principal");
	<%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
	  request.getSession().removeAttribute("online");
	%>
<script>   isLogin = false;</script>
<%
}
%>
