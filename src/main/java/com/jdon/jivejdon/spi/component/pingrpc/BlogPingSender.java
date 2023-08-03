package com.jdon.jivejdon.spi.component.pingrpc;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jdon.jivejdon.domain.model.realtime.ForumMessageDTO;
import com.jdon.jivejdon.domain.model.realtime.Notification;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.StringUtil;

public class BlogPingSender extends Thread {
    private final BaiduSearchTempParams baiduSearchTempParams;
    private final Notification notification;

    public BlogPingSender(Notification notification, BaiduSearchTempParams baiduSearchTempParams) {
        this.notification = notification;
        this.baiduSearchTempParams = baiduSearchTempParams;
    }

    public void run() {
        try {
            if (ToolsUtil.isDebug())
            return;
            ForumMessageDTO message = (ForumMessageDTO) notification.getSource();
            if (message == null) {
                return;
            }

            String UrlChanges = StringUtil.replace(baiduSearchTempParams.getUrltemp(),
                    "#threadId#", Long.toString(message.getThreadId()));
            pushPost(baiduSearchTempParams.getLinkSubmitUrl(), baiduSearchTempParams.getHost(),
                    baiduSearchTempParams.getDomain(),
                    baiduSearchTempParams.getBdToken(), UrlChanges);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 百度链接实时推送
     * 
     * @param postUrl 需要推送的内容链接
     * @return
     */
    public String pushPost(String linkSubmitUrl, String host, String domain, String bdToken, String postUrl) {
        // // 推送链接的域名，即 你的网站的域名
        // String domain = "https://www.xxxxx.com";
        // // 百度主动推送链接准入密钥，即获取到的token
        // String bdToken = "xxxxxxxxxxxxxxxxx";
        // String linkSubmitUrl = "http://data.zz.baidu.com/urls";
        // String host = "data.zz.baidu.com";
        linkSubmitUrl += "?site=" + domain + "&token=" + bdToken;
        String result = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient client = httpClientBuilder.build();
        client = (CloseableHttpClient) wrapClient(client);
        Map<String, String> msg = new HashMap<>();
        HttpPost post = new HttpPost(linkSubmitUrl);
        // 发送请求参数
        try {
            StringEntity s = new StringEntity(postUrl, "utf-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.setHeader("Host", host);
            post.setHeader("User-Agent", "curl/7.12.1");
            post.setHeader("Content-Type", "text/plain");
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String str = EntityUtils.toString(entity, "utf-8");
            result = str;

        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    private static org.apache.http.client.HttpClient wrapClient(HttpClient client) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[] { "TLSv1" }, null,
                    new DefaultHostnameVerifier());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;

        } catch (Exception ex) {
            return null;
        }
    }

}
