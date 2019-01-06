<%
  String ip = request.getParameter("ip");
  if (ip.length() == 0) {
    ip = request.getRemoteAddr();
  }
  if (ip.indexOf("127.0.0.1") != -1)
    return;
  try {
    Runtime sys = Runtime.getRuntime();
    sys.exec("/usr/bin/sudo -u root /sbin/iptables -I INPUT -s " + ip + " -j DROP");
  } catch (Exception e) {
    System.err.println("banqip error=" + e);
  }
%>
OK