package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * from ChatGPT
 */
public class IPRequestLimitFilter implements Filter {
    private static final int MAX_REQUESTS_PER_IP = 10; // 最大请求次数
    private static final long TIME_WINDOW_MILLIS = 60000; // 时间窗口，单位：毫秒
    private final Map<String, AtomicInteger> ipRequestCounts = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String remoteIP = request.getRemoteAddr();

        AtomicInteger count = ipRequestCounts.computeIfAbsent(remoteIP, k -> new AtomicInteger(0));
        long currentTime = System.currentTimeMillis();
        synchronized (count) {
            if (count.get() >= MAX_REQUESTS_PER_IP) {
                response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Service Unavailable");
                return;
            }
            // 如果上次请求时间超过时间窗口，则重置计数器
            if (currentTime - request.getSession().getLastAccessedTime() > TIME_WINDOW_MILLIS) {
                count.set(0);
            }
            count.incrementAndGet();
        }

        // 继续处理请求链
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 销毁方法
    }
}