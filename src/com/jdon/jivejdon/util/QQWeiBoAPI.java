package com.jdon.jivejdon.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;

import com.tencent.weibo.oauthv1.Base64Encoder;

/**
 * weiboApi
 * 
 * @author liqiang
 * 
 */
public class QQWeiBoAPI {
	/** get方式的请求 */
	public final static String HTTP_METHOD_GET = "GET";
	/** post方式的请求 */
	public final static String HTTP_METHOD_POST = "POST";
	/** 用户Key的key */
	public final static String CONSUMER_KEY_KEY = "oauth_consumer_key";
	/** 用户oauth_token的key */
	public final static String OAUTH_TOKEN_KEY = "oauth_token";
	/** oauth_nonce的key */
	public final static String OAUTH_NONCE_KEY = "oauth_nonce";
	/** oauth_timestamp的key */
	public final static String OAUTH_TIMESTAMP_KEY = "oauth_timestamp";
	/** oauth_version的key */
	public final static String OAUTH_VERSION_KEY = "oauth_version";

	/** 发送IP地址的key */
	public final static String API_CLIENTIP_KEY = "clientip";
	/** 发送内容的key */
	public final static String API_CONTENT_KEY = "content";
	/** 发送格式的key */
	public final static String API_FORMAT_KEY = "format";

	/** ALGORITHM */
	public final static String ALGORITHM = "HmacSHA1";
	/** ASCII */
	public final static String ASCII = "US-ASCII";

	/** oauth_version */
	public final static String OAUTH_VERSION = "1.0";
	public final static String OAUTH_SIGNATURE_METHOD_KEY = "oauth_signature_method";

	public final static String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
	/** 发送IP地址 */
	public final static String API_CLIENTIP = "127.0.0.1";
	/** json的发送格式 */
	public final static String API_FORMAT_JSON = "json";
	/** xml的发送格式 */
	public final static String API_FORMAT_XML = "xml";

	public final static String CONSUMER_KEY = "";
	public final static String CONSUMER_SECRET = "";

	public final static String OAUTH_TOKEN = "";

	public final static String SEND_REFERER = "";
	public final static String OAUTH_TOKEN_SECRET = "";

	/** 发送文本消息 */
	public final static String SEND_TEXT_URL = "";
	/** 字符集编码 */
	public final static String encoding = "";

	/**
	 * 发送文本格式的内容
	 * 
	 * @param message
	 * @return
	 */
	public static boolean sendTextMsg(String message) {
		boolean result = false;

		Map<String, String> parse = new HashMap<String, String>();

		parse.put(API_CLIENTIP_KEY, API_CLIENTIP); // 发送ip
		parse.put(API_CONTENT_KEY, message); // 发送内容
		parse.put(API_FORMAT_KEY, API_FORMAT_XML); // 发送格式
		parse.put(CONSUMER_KEY_KEY, CONSUMER_KEY);
		parse.put(OAUTH_NONCE_KEY, getAauth_nonce());
		parse.put(OAUTH_SIGNATURE_METHOD_KEY, OAUTH_SIGNATURE_METHOD);
		parse.put(OAUTH_TIMESTAMP_KEY, getAauth_timestamp());
		parse.put(OAUTH_TOKEN_KEY, OAUTH_TOKEN);
		parse.put(OAUTH_VERSION_KEY, OAUTH_VERSION);

		System.out.println("QQ微博发表内容：" + message);
		String resultString = requestUrl(SEND_TEXT_URL, HTTP_METHOD_POST, parse);

		System.out.println("返回结果：" + resultString);

		if (resultString.indexOf("<msg>ok</msg>") > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 获取随机的 oauth_nonce
	 * 
	 * @return
	 */
	private static String getAauth_nonce() {
		return RandomStringUtils.randomAlphanumeric(32);
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	private static String getAauth_timestamp() {
		return String.valueOf(System.currentTimeMillis()).substring(0, 10);
	}

	private static String requestUrl(String url, String method, Map<String, String> parse) {
		String resultString = null;

		try {
			String quaryParse = getQuaryParse(url, method, parse);
			System.out.println("quaryParse=" + quaryParse);
			String oauthSignature = getOauthSignature(url, method, quaryParse);

			String sendUrlString = url + "?" + quaryParse + "&oauth_signature=" + URLEncoder.encode(oauthSignature, encoding);

			if (HTTP_METHOD_POST.equals(method)) { // post 表单
				quaryParse = quaryParse + "&oauth_signature=" + URLEncoder.encode(oauthSignature, encoding);
				System.out.println("POST提交地址：" + url + "?" + quaryParse);
				resultString = HttpClientUtil.httpPost(url, quaryParse, encoding);
			} else {
				System.out.println("GET发送地址：" + sendUrlString);
				resultString = HttpClientUtil.get(sendUrlString, encoding);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 获取 oauth_signature
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static String getOauthSignature(String url, String method, String quaryParse) throws Exception {
		byte[] byteHMAC = null;

		String bss = method + "&" + URLEncoder.encode(url, encoding) + "&";
		String bsss = URLEncoder.encode(quaryParse, encoding);
		String urlString = bss + bsss;
		// System.out.println(urlString);

		String oauthKey = URLEncoder.encode(CONSUMER_SECRET, encoding) + "&"
				+ ((OAUTH_TOKEN_SECRET == null || OAUTH_TOKEN_SECRET.equals("")) ? "" : URLEncoder.encode(OAUTH_TOKEN_SECRET, encoding));

		Mac mac = Mac.getInstance(ALGORITHM);
		SecretKeySpec spec = new SecretKeySpec(oauthKey.getBytes(ASCII), ALGORITHM);
		mac.init(spec);
		byteHMAC = mac.doFinal((urlString).getBytes(ASCII));

		String oauthSignature = new Base64Encoder().encode(byteHMAC);
		return oauthSignature;
	}

	/**
	 * 拼接所有参数
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getQuaryParse(String url, String method, Map<String, String> parse) throws Exception {
		Object[] parseKeys = parse.keySet().toArray();

		Arrays.sort(parseKeys);

		StringBuffer urlBuffer = new StringBuffer();

		for (int i = 0; i < parseKeys.length; i++) {
			if (i > 0) {
				urlBuffer.append("&");
			}

			urlBuffer.append(parseKeys[i] + "=" + URLEncoder.encode(parse.get(parseKeys[i]), encoding));
		}

		return urlBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		// String msg =
		// "：geelou专业个人网站一篇名称为新增发布文章时同步发布QQ微博通知的文章发表了新的评论，文章访问地址：http://www.geelou.com/article/56.html，大家快去看看吧！";
		//
		// boolean result = QQWeiBoAPI.sendTextMsg(msg);
		// if(result){
		// System.out.println("发送成功");
		// }
		String url = "http://open.t.qq.com/api/t/add";
		String method = "POST";
		String quaryParse = "clientip=127.0.0.1&content=aaaaaa&format=xml&oauth_consumer_key=3c22f8387ac8431596314e3c62d376ab&oauth_nonce=stdDekWqhcWiCxIWEH2vo4Vdwf6439R6&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1294998987&oauth_token=2f0441ad8d8542908b924ee0b7f146bc&oauth_version=1.0";
		String oauthSignature = getOauthSignature(url, method, quaryParse);
		System.out.println(oauthSignature);
	}
}
