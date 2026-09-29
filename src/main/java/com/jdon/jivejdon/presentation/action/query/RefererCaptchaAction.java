package com.jdon.jivejdon.presentation.action.query;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.util.UtilValidate;

public class RefererCaptchaAction extends Action {

    private static final String PENDING_REFERERS = RefererCaptchaAction.class.getName() + ".pending";
    private static final long FLOW_TIMEOUT_MILLIS = 10 * 60 * 1000L;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setNoCacheHeaders(response);

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            showCaptcha(request, response);
            return null;
        }
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            verifyAndRedirect(request, response);
            return null;
        }

        response.setHeader("Allow", "GET, POST");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return null;
    }

    private void showCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("Referer");
        if (!isSameOriginReferer(referer, request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession session = request.getSession(true);
        String flowId = UUID.randomUUID().toString();
        synchronized (session) {
            @SuppressWarnings("unchecked")
            Map<String, PendingReferer> pending = (Map<String, PendingReferer>) session.getAttribute(PENDING_REFERERS);
            if (pending == null) {
                pending = new HashMap<String, PendingReferer>();
                session.setAttribute(PENDING_REFERERS, pending);
            }
            long now = System.currentTimeMillis();
            Iterator<Map.Entry<String, PendingReferer>> entries = pending.entrySet().iterator();
            while (entries.hasNext()) {
                if (now - entries.next().getValue().createdAt > FLOW_TIMEOUT_MILLIS) {
                    entries.remove();
                }
            }
            pending.put(flowId, new PendingReferer(referer, now));
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\">");
        response.getWriter().write("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">");
        response.getWriter().write("<title>安全验证</title><script>");
        response.getWriter().write("window.refererCaptchaCallback=function(res){if(res&&res.ret===0){");
        response.getWriter().write("document.getElementById('ticket').value=res.ticket;");
        response.getWriter().write("document.getElementById('randstr').value=res.randstr;");
        response.getWriter().write("document.getElementById('captchaForm').submit();}};");
        response.getWriter().write("</script><script src=\"https://ssl.captcha.qq.com/TCaptcha.js\"></script></head><body>");
        response.getWriter().write("<form id=\"captchaForm\" method=\"post\" action=\"");
        response.getWriter().write(escapeHtml(request.getRequestURI()));
        response.getWriter().write("\"><input type=\"hidden\" name=\"flowId\" value=\"");
        response.getWriter().write(escapeHtml(flowId));
        response.getWriter().write("\"><input type=\"hidden\" id=\"ticket\" name=\"ticket\">");
        response.getWriter().write("<input type=\"hidden\" id=\"randstr\" name=\"randstr\">");
        response.getWriter().write("<button type=\"button\" id=\"TencentCaptcha\" data-appid=\"2050847547\" ");
        response.getWriter().write("data-cbfn=\"refererCaptchaCallback\">道场验证</button></form></body></html>");
    }

    private void verifyAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flowId = request.getParameter("flowId");
        String ticket = request.getParameter("ticket");
        String randstr = request.getParameter("randstr");
        HttpSession session = request.getSession(false);
        PendingReferer pendingReferer = null;

        if (session != null && !UtilValidate.isEmpty(flowId)) {
            synchronized (session) {
                @SuppressWarnings("unchecked")
                Map<String, PendingReferer> pending = (Map<String, PendingReferer>) session.getAttribute(PENDING_REFERERS);
                if (pending != null) {
                    pendingReferer = pending.remove(flowId);
                }
            }
        }

        if (pendingReferer == null || System.currentTimeMillis() - pendingReferer.createdAt > FLOW_TIMEOUT_MILLIS
                || UtilValidate.isEmpty(ticket) || UtilValidate.isEmpty(randstr)
                || !isSameOriginReferer(pendingReferer.url, request)
                || !SkinUtils.verifyQQRegisterCode(ticket, randstr, request.getRemoteAddr())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.sendRedirect(pendingReferer.url);
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

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;").replace("'", "&#39;");
    }

    private void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }

    private static final class PendingReferer {
        private final String url;
        private final long createdAt;

        private PendingReferer(String url, long createdAt) {
            this.url = url;
            this.createdAt = createdAt;
        }
    }
}