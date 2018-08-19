<%@page import="com.jdon.jivejdon.util.BanIPUtils;"%>


<%
String ip = request.getParameter("ip");
try {
Runtime sys = Runtime.getRuntime();
 sys.exec("/usr/bin/sudo -u root /sbin/iptables -I INPUT -s "+ ip +" -j DROP");
} catch (Exception e) {
	out.print("error");
}
%>
OK