package com.jdon.jivejdon.presentation.action.query;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.jivejdon.presentation.servlet.CounterServlet;
import com.jdon.jivejdon.spi.component.viewcount.ThreadViewCounterJob;
import com.jdon.util.UtilValidate;

public class RefererCaptchaAction extends Action {

    private static final String APPROVAL_STORAGE_KEY = "JDON_CAPTCHA_APPROVAL";
    private static final String APPROVAL_COOKIE_NAME = "JDON_CAPTCHA_APPROVAL";
    private static final long APPROVAL_DURATION_MILLIS = 5L * 60 * 1000;
    private static final int APPROVAL_DURATION_SECONDS = 5 * 60;
    private static final String TOKEN_PREFIX = "jdon-captcha-v2:";
    private static final byte[] SIGNING_KEY = createSigningKey();
    private ThreadViewCounterJob threadViewCounterJob;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setNoCacheHeaders(response);
        byte[] signingKey = SIGNING_KEY;

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            if ("1".equals(request.getParameter("checkIpCache"))) {
                checkIpCache(request, response);
                return null;
            }
            showCaptcha(request, response, signingKey);
            return null;
        }
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            verifyAndRedirect(request, response, signingKey);
            return null;
        }

        response.setHeader("Allow", "GET, POST");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return null;
    }

    private void checkIpCache(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getParameter("referer");
        if (!isSameOriginReferer(referer, request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        boolean cached = getThreadViewCounterJob().hasRecentViewFromIp(CounterServlet.getClientIp(request));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(cached ? "{\"cached\":true}" : "{\"cached\":false}");
    }

    private void showCaptcha(HttpServletRequest request, HttpServletResponse response, byte[] signingKey)
            throws IOException, GeneralSecurityException {
        String referer = request.getHeader("Referer");
        if (!isSameOriginReferer(referer, request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (hasValidApprovalCookie(request, signingKey)) {
            response.sendRedirect(referer);
            return;
        }
        clearApprovalCookie(response, request);
        if (getThreadViewCounterJob().hasRecentViewFromIp(CounterServlet.getClientIp(request))) {
            response.sendRedirect(referer);
            return;
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        response.getWriter().write("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">");
        response.getWriter().write("<title>安全验证</title><script>");
        response.getWriter().write("var referer=document.referrer;");
        response.getWriter().write("var approvalStorageKey='" + APPROVAL_STORAGE_KEY + "';");
        response.getWriter().write("function addCaptchaField(form,name,value){var input=document.createElement('input');");
        response.getWriter().write("input.type='hidden';input.name=name;input.value=value;form.appendChild(input);}");
        response.getWriter().write("function submitApprovalToken(token){var form=document.createElement('form');");
        response.getWriter().write("form.method='post';form.action=window.location.pathname;");
        response.getWriter().write("addCaptchaField(form,'referer',referer);");
        response.getWriter().write("addCaptchaField(form,'approvalToken',token);");
        response.getWriter().write("document.body.appendChild(form);form.submit();}");
        response.getWriter().write("function showCaptcha(){var captchaScript=document.createElement('script');");
        response.getWriter().write("captchaScript.src='https://ssl.captcha.qq.com/TCaptcha.js';");
        response.getWriter().write("captchaScript.onload=function(){if(window.TencentCaptcha){");
        response.getWriter().write("new TencentCaptcha('2050847547',window.refererCaptchaCallback).show();}");
        response.getWriter().write("else{console.error('Tencent captcha script loaded without TencentCaptcha.');}};");
        response.getWriter().write("captchaScript.onerror=function(){console.error('Unable to load Tencent captcha script.');};");
        response.getWriter().write("document.head.appendChild(captchaScript);}");
        response.getWriter().write("window.refererCaptchaCallback=function(res){if(res&&res.ret===0){");
        response.getWriter().write("var form=document.createElement('form');form.method='post';");
        response.getWriter().write("form.action=window.location.pathname;");
        response.getWriter().write("addCaptchaField(form,'referer',referer);");
        response.getWriter().write("addCaptchaField(form,'ticket',res.ticket);addCaptchaField(form,'randstr',res.randstr);");
        response.getWriter().write("document.body.appendChild(form);form.submit();}};");
        response.getWriter().write("window.onload=function(){var approvalToken=null;");
        response.getWriter().write("try{approvalToken=localStorage.getItem(approvalStorageKey);}");
        response.getWriter().write("catch(error){console.warn('Unable to read captcha approval token.',error);}");
        response.getWriter().write("if(approvalToken){submitApprovalToken(approvalToken);}else{showCaptcha();}};");
        response.getWriter().write("</script></head><body></body></html>");
    }

    private ThreadViewCounterJob getThreadViewCounterJob() {
        if (threadViewCounterJob == null) {
            threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob",
                    servlet.getServletContext());
        }
        return threadViewCounterJob;
    }

    private void verifyAndRedirect(HttpServletRequest request, HttpServletResponse response, byte[] signingKey)
            throws IOException, GeneralSecurityException {
        String referer = request.getParameter("referer");
        String approvalToken = request.getParameter("approvalToken");
        String ticket = request.getParameter("ticket");
        String randstr = request.getParameter("randstr");

        if (!UtilValidate.isEmpty(approvalToken)) {
            if (!isSameOriginReferer(referer, request)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (isValidApprovalToken(approvalToken, signingKey)) {
                response.sendRedirect(referer);
                return;
            }
            if (getThreadViewCounterJob().hasRecentViewFromIp(CounterServlet.getClientIp(request))) {
                completeApproval(referer, request, response, signingKey);
            } else {
                showCaptcha(request, response, referer, true);
            }
            return;
        }

        if ("1".equals(request.getParameter("ipCacheApproval"))) {
            if (!isSameOriginReferer(referer, request)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (getThreadViewCounterJob().hasRecentViewFromIp(CounterServlet.getClientIp(request))) {
                completeApproval(referer, request, response, signingKey);
            } else {
                showCaptcha(request, response, referer, false);
            }
            return;
        }

        if (UtilValidate.isEmpty(ticket) || UtilValidate.isEmpty(randstr)
                || !isSameOriginReferer(referer, request)
                || !SkinUtils.verifyQQRegisterCode(ticket, randstr, request.getRemoteAddr())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        completeApproval(referer, request, response, signingKey);
    }

    private void completeApproval(String referer, HttpServletRequest request, HttpServletResponse response,
            byte[] signingKey) throws IOException, GeneralSecurityException {
        long expiresAt = System.currentTimeMillis() + APPROVAL_DURATION_MILLIS;
        String token = createApprovalToken(expiresAt, signingKey);
        addApprovalCookie(response, request, token);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        response.getWriter().write("<title>安全验证</title></head><body><a id=\"captchaReturn\" href=\"");
        response.getWriter().write(escapeHtml(referer));
        response.getWriter().write("\"></a><script>var referer=document.getElementById('captchaReturn').href;");
        response.getWriter().write("try{localStorage.setItem('" + APPROVAL_STORAGE_KEY + "','" + token + "');}");
        response.getWriter().write("catch(error){console.warn('Unable to save captcha approval token.',error);}");
        response.getWriter().write("window.location.replace(referer);</script></body></html>");
    }

    private void showCaptcha(HttpServletRequest request, HttpServletResponse response, String referer,
            boolean clearInvalidToken) throws IOException {
        if (!isSameOriginReferer(referer, request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        clearApprovalCookie(response, request);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        response.getWriter().write("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">");
        response.getWriter().write("<title>安全验证</title></head><body>");
        response.getWriter().write("<input type=\"hidden\" id=\"captchaReferer\" value=\"");
        response.getWriter().write(escapeHtml(referer));
        response.getWriter().write("\"><script>");
        response.getWriter().write("var referer=document.getElementById('captchaReferer').value;");
        response.getWriter().write("var approvalStorageKey='" + APPROVAL_STORAGE_KEY + "';");
        if (clearInvalidToken) {
            response.getWriter().write("try{localStorage.removeItem(approvalStorageKey);}");
            response.getWriter().write("catch(error){console.warn('Unable to clear invalid captcha approval token.',error);}");
        }
        response.getWriter().write("function addCaptchaField(form,name,value){var input=document.createElement('input');");
        response.getWriter().write("input.type='hidden';input.name=name;input.value=value;form.appendChild(input);}");
        response.getWriter().write("window.refererCaptchaCallback=function(res){if(res&&res.ret===0){");
        response.getWriter().write("var form=document.createElement('form');form.method='post';");
        response.getWriter().write("form.action=window.location.pathname;");
        response.getWriter().write("addCaptchaField(form,'referer',referer);");
        response.getWriter().write("addCaptchaField(form,'ticket',res.ticket);addCaptchaField(form,'randstr',res.randstr);");
        response.getWriter().write("document.body.appendChild(form);form.submit();}};");
        response.getWriter().write("var captchaScript=document.createElement('script');");
        response.getWriter().write("captchaScript.src='https://ssl.captcha.qq.com/TCaptcha.js';");
        response.getWriter().write("captchaScript.onload=function(){if(window.TencentCaptcha){");
        response.getWriter().write("new TencentCaptcha('2050847547',window.refererCaptchaCallback).show();}");
        response.getWriter().write("else{console.error('Tencent captcha script loaded without TencentCaptcha.');}};");
        response.getWriter().write("captchaScript.onerror=function(){console.error('Unable to load Tencent captcha script.');};");
        response.getWriter().write("document.head.appendChild(captchaScript);");
        response.getWriter().write("</script></body></html>");
    }

    private static byte[] createSigningKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return key;
    }

    private String createApprovalToken(long expiresAt, byte[] signingKey) throws GeneralSecurityException {
        String payload = TOKEN_PREFIX + expiresAt;
        String encodedPayload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        byte[] signature = sign(payload, signingKey);
        return encodedPayload + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
    }

    private boolean hasValidApprovalCookie(HttpServletRequest request, byte[] signingKey)
            throws GeneralSecurityException {
        Cookie approvalCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (APPROVAL_COOKIE_NAME.equals(cookie.getName())) {
                if (approvalCookie != null) {
                    return false;
                }
                approvalCookie = cookie;
            }
        }
        if (approvalCookie == null) {
            return false;
        }

        return isValidApprovalToken(approvalCookie.getValue(), signingKey);
    }

    private boolean isValidApprovalToken(String token, byte[] signingKey) throws GeneralSecurityException {
        String[] tokenParts = token.split("\\.", -1);
        if (tokenParts.length != 2) {
            return false;
        }
        try {
            String payload = new String(Base64.getUrlDecoder().decode(tokenParts[0]), StandardCharsets.UTF_8);
            if (!payload.startsWith(TOKEN_PREFIX)) {
                return false;
            }
            long expiresAt = Long.parseLong(payload.substring(TOKEN_PREFIX.length()));
            if (expiresAt <= System.currentTimeMillis()) {
                return false;
            }

            byte[] providedSignature = Base64.getUrlDecoder().decode(tokenParts[1]);
            byte[] expectedSignature = sign(payload, signingKey);
            return MessageDigest.isEqual(expectedSignature, providedSignature);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private byte[] sign(String payload, byte[] signingKey) throws GeneralSecurityException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
        return mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
    }

    private void addApprovalCookie(HttpServletResponse response, HttpServletRequest request, String token) {
        response.addHeader("Set-Cookie", approvalCookieHeader(request, token, APPROVAL_DURATION_SECONDS));
    }

    private void clearApprovalCookie(HttpServletResponse response, HttpServletRequest request) {
        response.addHeader("Set-Cookie", approvalCookieHeader(request, "", 0));
    }

    private String approvalCookieHeader(HttpServletRequest request, String value, int maxAge) {
        String path = request.getContextPath() + "/captcha";
        return APPROVAL_COOKIE_NAME + "=" + value + "; Path=" + path + "; Max-Age=" + maxAge
                + "; HttpOnly; Secure; SameSite=Lax";
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;").replace("'", "&#39;");
    }

    private boolean isSameOriginReferer(String referer, HttpServletRequest request) {
        if (UtilValidate.isEmpty(referer)) {
            return false;
        }
        try {
            URI uri = new URI(referer);
            String scheme = uri.getScheme();
            if (!uri.isAbsolute() || uri.getHost() == null || uri.getUserInfo() != null
                    || !("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))
                    || !uri.getHost().equalsIgnoreCase(request.getServerName())
                    || !scheme.equalsIgnoreCase(request.getScheme())) {
                return false;
            }
            int refererPort = uri.getPort() < 0 ? defaultPort(scheme) : uri.getPort();
            int requestPort = request.getServerPort() < 0 ? defaultPort(request.getScheme()) : request.getServerPort();
            return refererPort == requestPort;
        } catch (Exception e) {
            return false;
        }
    }

    private int defaultPort(String scheme) {
        return "https".equalsIgnoreCase(scheme) ? 443 : 80;
    }

    private void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }
}