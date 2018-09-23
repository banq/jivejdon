
<%
String url = (String)this.getServletContext().getAttribute("HOSTURL");
if (url == null){
	java.net.URL reconstructedURL = new  java.net.URL(request.getScheme(),
            request.getServerName(),
            request.getServerPort(),
            request.getContextPath());
    url = reconstructedURL.toString().replace(":80/", "/");
    this.getServletContext().setAttribute("HOSTURL", url);
}

%>