/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.util.UtilDateTime;

/**
 * tools for this project.
 * 
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
public class ToolsUtil {
	private static final long ROOT_PARENTMESSAGEID = 0;

	private static final char[] zeroArray = "0000000000000000".toCharArray();
	/**
	 * Used by the hash method.
	 */
	private static MessageDigest digest = null;

	public static boolean isDebug() throws UnknownHostException {
		String clientIp = InetAddress.getLocalHost().getHostAddress();
		if (clientIp.indexOf("192.168") != -1 || clientIp.indexOf("127.0.0.1") != -1) {
			System.err.println(clientIp + " is not server, so not send");
			return true;
		}
		return false;
	}

	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuilder buf = new StringBuilder(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	public static final String dateToMillis(long now) {
		return zeroPadString(Long.toString(now), 15);
	}

	public static Long getParentIDOfRoot() {
		return new Long(ROOT_PARENTMESSAGEID);
	}

	public static boolean isRoot(Long parentID) {
		if (parentID.longValue() == ROOT_PARENTMESSAGEID)
			return true;
		else
			return false;

	}

	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * the String can be as the key of cache
	 *
	 * @param date
	 * @return a long string with no Hour/Minute/Mills
	 */
	public static String dateToNoMillis(Date date) {
		// 001184284800000
		String s = dateToMillis(date);
		StringBuilder sb = new StringBuilder(s.substring(0, 10));
		sb.append("00000");
		return sb.toString();
	}

	/**
	 * Converts a date String and a time String into a Date
	 *
	 * @param date
	 *            The date String: YYYY-MM-DD
	 * @param time
	 *            The time String: either HH:MM or HH:MM:SS
	 * @return A Date made from the date and time Strings
	 */
	public static java.util.Date toDate(String date, String time, String split) {
		if (date == null || time == null)
			return null;
		String month;
		String day;
		String year;
		String hour;
		String minute;
		String second;

		int dateSlash1 = date.indexOf(split);
		int dateSlash2 = date.lastIndexOf(split);

		if (dateSlash1 <= 0 || dateSlash1 == dateSlash2)
			return null;
		int timeColon1 = time.indexOf(":");
		int timeColon2 = time.lastIndexOf(":");

		if (timeColon1 <= 0)
			return null;
		year = date.substring(0, dateSlash1);
		month = date.substring(dateSlash1 + 1, dateSlash2);
		day = date.substring(dateSlash2 + 1);
		hour = time.substring(0, timeColon1);

		if (timeColon1 == timeColon2) {
			minute = time.substring(timeColon1 + 1);
			second = "0";
		} else {
			minute = time.substring(timeColon1 + 1, timeColon2);
			second = time.substring(timeColon2 + 1);
		}

		return UtilDateTime.toDate(month, day, year, hour, minute, second);
	}

	public static String toDateString(java.util.Date date, String splite) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String monthStr;
		String dayStr;
		String yearStr;

		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		yearStr = "" + year;
		return yearStr + splite + monthStr + splite + dayStr;
	}

	public static String toDateHourString(java.util.Date date) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		StringBuilder sb = new StringBuilder(UtilDateTime.toDateString(date));
		sb.append(toHourString(calendar.get(Calendar.HOUR_OF_DAY)));
		return sb.toString();
	}

	private static String toHourString(int hour) {
		String hourStr;

		if (hour < 10) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		return hourStr;
	}

	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. " + "Jive will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		if (digest == null)
			return null;
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * Turns an array of bytes into a String representing each byte as an
	 * unsigned hex number.
	 * <p>
	 * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            an array of bytes to convert to a hex-string
	 * @return generated hex string
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameterValues()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramName
	 *            String
	 * @return String[] 返回多个同参数名的值
	 */
	public static String[] getParamsFromQueryString(HttpServletRequest request, String paramName) {
		return getParamsFromQueryString(request.getQueryString(), paramName);
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameterValues()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramName
	 *            String
	 * @return String[]
	 */
	public static String getParameterFromQueryString(HttpServletRequest request, String paramName) {
		return getParameterFromQueryString(request.getQueryString(), paramName);
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameter()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param queryString
	 *            String
	 * @param paramName
	 *            String
	 * @return String 只返回一个值
	 */
	public static String getParameterFromQueryString(String queryString, String paramName) {
		String[] s = getParamsFromQueryString(queryString, paramName);
		if (s != null && s.length >= 1) {
			return s[0];
		}
		return null;
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameter()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param queryString
	 *            String
	 * @param paramName
	 *            String
	 * @return String[] 返回多个同参数名的值
	 */
	public static String[] getParamsFromQueryString(String queryString, String paramName) {
		if (paramName == null || paramName.length() < 1 || paramName == null || paramName.length() < 1) {
			return new String[0];
		}
		List rsl = new ArrayList();
		String params[] = queryString.split("&");
		for (int i = 0; i < params.length; i++) {
			// System.out.println(params[i]);
			if (params[i] != null && params[i].startsWith(paramName + "=")) {
				try {
					rsl.add(java.net.URLDecoder.decode(params[i].substring(paramName.length() + 1), "UTF-8")); // 与tomcat中URIEncoding="UTF-8"。
				} catch (UnsupportedEncodingException ex) {
				}
			}
		}
		return (String[]) rsl.toArray(new String[0]);
	}

	public static String gbToUtf8(String str) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);
			if (s.charAt(0) > 0x80) {
				byte[] bytes = s.getBytes("Unicode");
				String binaryStr = "";
				StringBuilder buf = new StringBuilder();
				for (int j = 2; j < bytes.length; j += 2) {
					// the first byte
					String hexStr = getHexString(bytes[j + 1]);
					String binStr = getBinaryString(Integer.valueOf(hexStr, 16));
					buf.append(binStr);
					// the second byte
					hexStr = getHexString(bytes[j]);
					binStr = getBinaryString(Integer.valueOf(hexStr, 16));
					buf.append(binStr);
				}
				binaryStr = buf.toString();
				// convert unicode to utf-8
				String s1 = "1110" + binaryStr.substring(0, 4);
				String s2 = "10" + binaryStr.substring(4, 10);
				String s3 = "10" + binaryStr.substring(10, 16);
				byte[] bs = new byte[3];
				bs[0] = Integer.valueOf(s1, 2).byteValue();
				bs[1] = Integer.valueOf(s2, 2).byteValue();
				bs[2] = Integer.valueOf(s3, 2).byteValue();
				String ss = new String(bs, "UTF-8");
				sb.append(ss);
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	private static String getHexString(byte b) {
		String hexStr = Integer.toHexString(b);
		int m = hexStr.length();
		if (m < 2) {
			hexStr = "0" + hexStr;
		} else {
			hexStr = hexStr.substring(m - 2);
		}
		return hexStr;
	}

	private static String getBinaryString(int i) {
		String binaryStr = Integer.toBinaryString(i);
		int length = binaryStr.length();
		for (int l = 0; l < 8 - length; l++) {
			binaryStr = "0" + binaryStr;
		}
		return binaryStr;
	}

	public static String replaceBlank(String s, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(s);
		return m.replaceAll("");
	}

	public static String getHostName() {
		String hostName = "localhost";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// NO OP we will just use localhost
		}
		return hostName;
	}

    public static boolean checkHeaderCacheForum(long adddays, ServletContext sc, HttpServletRequest request,HttpServletResponse response) {
           return checkHeaderCacheExpire(adddays, request, response);
	}

    /**
     * checkHeaderCache 的重载版本：没有 modelLastModifiedDate 参数，使用当前系统时间作为资源最后修改时间。
     * 如果请求头 If-Modified-Since 与当前时间比较仍在 maxAgeSeconds 有效期内，则返回 304。
     *
     * @param maxAgeSeconds 缓存过期时间（秒）
     * @param request       HttpServletRequest
     * @param response      HttpServletResponse
     * @return 当需要继续处理请求（需要返回内容）返回 true；当已命中缓存并已返回 304 时返回 false
     */
    public static boolean checkHeaderCacheExpire(long maxAgeSeconds, HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("myExpire") != null) {
            System.err.println("checkHeaderCache called above twice times: " + request.getRequestURI());
            return true;
        }
        request.setAttribute("myExpire", maxAgeSeconds);

        try {
            // 使用 If-Modified-Since 验证: 如果客户端缓存时间距现在仍然在 maxAgeSeconds 内，则返回 304
            long header = request.getDateHeader("If-Modified-Since");
            long now = System.currentTimeMillis();
            if (header > 0) {
                long ageMillis = now - header;
                if (ageMillis <= maxAgeSeconds * 1000L) {
                    response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                    return false;
                }
            }

            // 未命中缓存或缓存已过期，设置新的响应头（基于当前时间）
            setRespHeaderCache(maxAgeSeconds, now, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }	

	//grok3
	public static boolean checkHeaderCache(long maxAgeSeconds, long modelLastModifiedDate, HttpServletRequest request, HttpServletResponse response) {
		if (request.getAttribute("myExpire") != null) {
			System.err.println("checkHeaderCache called above twice times: " + request.getRequestURI());
			return true;
		}
		request.setAttribute("myExpire", maxAgeSeconds);
	
		try {
			// ETag 验证
			String etag = request.getHeader("If-None-Match");
			if (etag != null) {
				String expectedEtag = "\"" + Long.toString(modelLastModifiedDate) + "\"";
				if (etag.equals(expectedEtag)) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				}
			}
	
			// If-Modified-Since 验证
			long header = request.getDateHeader("If-Modified-Since");
			if (header > 0) {
				if (modelLastModifiedDate <= header) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					return false;
				}
			}
	
			// 设置新的响应头
			setEtagHaeder(response, modelLastModifiedDate);
			setRespHeaderCache(maxAgeSeconds, modelLastModifiedDate, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void setEtagHaeder(HttpServletResponse response, long modelLastModifiedDate) {
		response.setHeader("ETag", "\"" + Long.toString(modelLastModifiedDate) + "\"");
	}
	
	public static boolean setRespHeaderCache(long maxAgeSeconds, long modelLastModifiedDate, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("myExpire", maxAgeSeconds);
	
		// 设置 Cache-Control
		String maxAgeDirective = "public, max-age=" + maxAgeSeconds + ", s-maxage=" + maxAgeSeconds * 2 + ", stale-while-revalidate=3600";
        response.setHeader("Cache-Control", maxAgeDirective);
	
		// 设置状态码
		response.setStatus(HttpServletResponse.SC_OK);
	
		// 设置 Last-Modified
		modelLastModifiedDate = modelLastModifiedDate / 1000 * 1000;
		response.addDateHeader("Last-Modified", modelLastModifiedDate);
	
		// 设置 Age（资源已经存在的秒数）
		long currentTime = System.currentTimeMillis();
		response.setDateHeader("Date", currentTime);
		long age = (currentTime - modelLastModifiedDate) / 1000;
		response.setHeader("Age", String.valueOf(age));
	
		// 设置 Expires，基于当前时间加上 maxAgeSeconds
		long expires = System.currentTimeMillis() + (maxAgeSeconds * 1000);
		response.addDateHeader("Expires", expires);
	
		return true;
	}

	// public static boolean checkHeaderCache(long adddays, long modelLastModifiedDate, HttpServletRequest request,
	// 		HttpServletResponse response) {

	// 	if (request.getAttribute("myExpire") != null) {
	// 		System.err.print(" checkHeaderCache called above twice times :" + request.getRequestURI());
	// 		return true;
	// 	}
	// 	request.setAttribute("myExpire", adddays);

	// 	// convert seconds to ms.
	// 	try {

	// 		// if over expire data, see the Etags;
	// 		// ETags if ETags no any modified
	// 		String etag = request.getHeader("If-None-Match");

	// 		if (etag != null) {
	// 			if (etag.equals(Long.toString(modelLastModifiedDate))) {
	// 				// not modified
	// 				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	// 				return false;
	// 			}
	// 		} else {
	// 			long header = request.getDateHeader("If-Modified-Since");
	// 			if (header > 0) {
	// 				if (modelLastModifiedDate <= header || (modelLastModifiedDate - header) < 1000) {
	// 					// during the period not happend modified
	// 					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	// 					return false;
	// 				}
	// 			}
	// 		}
	// 		// if th model has modified , setup the new modified date
	// 		setEtagHaeder(response, modelLastModifiedDate);
	// 		setRespHeaderCache(adddays, modelLastModifiedDate, request, response);
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// 	return true;
	// }

	
	// public static boolean setRespHeaderCache(long adddays, long modelLastModifiedDate,HttpServletRequest request, HttpServletResponse response) {
	// 	request.setAttribute("myExpire", adddays);

	// 	long adddaysM = new Long(adddays) * 1000;
	// 	String maxAgeDirective = "max-age=" + adddays;
	// 	response.setHeader("Cache-Control", maxAgeDirective);
	// 	response.setStatus(HttpServletResponse.SC_OK);
	// 	response.addDateHeader("Last-Modified", modelLastModifiedDate);
	// 	response.addDateHeader("Expires", System.currentTimeMillis() + adddaysM);
	// 	return true;
	// }



	public static String convertURL(String s) {
		Pattern patt = Pattern
				.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>???“”‘’]))");
		Matcher matcher = patt.matcher(s);
		if (matcher.find()) {
			if (matcher.group(1).startsWith("http://")) {
                return matcher.replaceAll("<a href=\"$1\">$1</a>");
            }else if (matcher.group(1).startsWith("https://")) {
                    return matcher.replaceAll("<a href=\"$1\">$1</a>");
			} else {
				return matcher.replaceAll("<a href=\"http://$1\">$1</a>");
			}
		} else {
			return s;
		}

	}

	
	/**
	 * //nginx : proxy_set_header X-Forwarded-Proto  $scheme;
	 * tomcat <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
                    scheme="https"
      redirectPort="8443"  proxyPort="443" /

	 * 
	 * scheme="https"
	 * @param request
	 * @return
	 */
	public static String getAppURL(HttpServletRequest request) {		
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		// url.append("//");		
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		return url.toString();
	 }

	public static void removeSessionCookie(HttpServletRequest request, HttpServletResponse response) {

		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setMaxAge(0); // 立即删除型
		cookie.setPath("/"); // 项目所有目录均有效，这句很关键，否则不敢保证删除
		cookie.setDomain(request.getHeader("host"));
		response.addCookie(cookie); // 重新写入，将覆盖之前的%>
	}

}
