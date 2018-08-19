<%@ page import="java.io.BufferedReader,
                 java.io.InputStreamReader,
                 java.io.IOException,
                 java.io.UnsupportedEncodingException,
                 java.net.URL,
                 java.net.URLEncoder,
                 java.util.ArrayList,
                 java.util.List" %>
<%!

private static final String PAGEAD =
    "http://pagead2.googlesyndication.com/pagead/ads?";

private void googleAppendUrl(StringBuilder url, String param, String value)
    throws UnsupportedEncodingException {
  if (value != null) {
    String encodedValue = URLEncoder.encode(value, "UTF-8");
    url.append("&").append(param).append("=").append(encodedValue);
  }
}

private void googleAppendColor(StringBuilder url, String param,
    String value, long random) {
  String[] colorArray = value.split(",");
  url.append("&").append(param).append("=").append(
      colorArray[(int)(random % colorArray.length)]);
}

private void googleAppendScreenRes(StringBuilder url, String uaPixels,
    String xUpDevcapScreenpixels, String xJphoneDisplay) {
  String screenRes = uaPixels;
  if (screenRes == null) {
    screenRes = xUpDevcapScreenpixels;
  }
  if (screenRes == null) {
    screenRes = xJphoneDisplay;
  }
  if (screenRes != null) {
    String[] resArray = screenRes.split("[x,*]");
    if (resArray.length == 2) {
      url.append("&u_w=").append(resArray[0]);
      url.append("&u_h=").append(resArray[1]);
    }
  }
}

private void googleAppendMuid(StringBuilder url, List<String> muids) {
  for (String muid : muids) {
    if (muid != null) {
      url.append("&muid=").append(muid);
      return;
    }
  }
}

private void googleAppendViaAndAccept(StringBuilder url, String via,
    String accept) throws UnsupportedEncodingException {
  googleAppendUrl(url, "via", via);
  googleAppendUrl(url, "accept", accept);
}

%>
<%

long googleDt = System.currentTimeMillis();
StringBuilder googleAdUrlStr = new StringBuilder(PAGEAD);
googleAdUrlStr.append("&client=ca-mb-pub-7573657117119544");
googleAdUrlStr.append("&dt=").append(googleDt);
googleAppendUrl(googleAdUrlStr, "ip", request.getRemoteAddr());
googleAdUrlStr.append("&markup=xhtml");
googleAdUrlStr.append("&output=xhtml");
googleAppendUrl(googleAdUrlStr, "ref", request.getHeader("Referer"));
String googleUrl = request.getRequestURL().toString();
if (request.getQueryString() != null) {
  googleUrl += "?" + request.getQueryString().toString();
}
googleAdUrlStr.append("&slotname=6933384340");
googleAppendUrl(googleAdUrlStr, "url", googleUrl);
String googleUserAgent = request.getHeader("User-Agent");
googleAppendUrl(googleAdUrlStr, "useragent", googleUserAgent);
googleAppendScreenRes(googleAdUrlStr, request.getHeader("UA-pixels"),
    request.getHeader("x-up-devcap-screenpixels"),
    request.getHeader("x-jphone-display"));
List<String> googleMuids = new ArrayList<String>();
googleMuids.add(request.getHeader("X-DCMGUID"));
googleMuids.add(request.getHeader("X-UP-SUBNO"));
googleMuids.add(request.getHeader("X-JPHONE_UID"));
googleMuids.add(request.getHeader("X-EM-UID"));
googleAppendMuid(googleAdUrlStr, googleMuids);
if (googleUserAgent == null || googleUserAgent.length() == 0) {
  googleAppendViaAndAccept(googleAdUrlStr, request.getHeader("Via"),
      request.getHeader("Accept"));
}
try {
  URL googleAdUrl = new URL(googleAdUrlStr.toString());
  BufferedReader reader = new BufferedReader(
      new InputStreamReader(googleAdUrl.openStream(), "AUTO_DETECT"));
  for (String line; (line = reader.readLine()) != null;) {
    out.println(line);
  }
} catch (IOException e) {}

%>