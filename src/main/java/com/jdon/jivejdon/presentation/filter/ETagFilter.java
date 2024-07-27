/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * check if modified in all forums, if no, return 304;
 * 
 * from chatGPT
 * 
 * <filter>
    <filter-name>ETagFilter</filter-name>
    <filter-class>com.jdon.jivejdon.presentation.filter.ETagFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>ETagFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
 * 
 * </filter>
 * 
 * 
 */
public class ETagFilter implements Filter {

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法（如果需要）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 处理静态资源
        String uri = httpRequest.getRequestURI();
        if (isStaticResource(uri)) {
            String eTag = generateETag(uri);

            // 1. 设置 ETag 头
            httpResponse.setHeader("ETag", eTag);

            // 2. 检查 If-None-Match 头
            String ifNoneMatch = httpRequest.getHeader("If-None-Match");
            if (eTag.equals(ifNoneMatch)) {
                httpResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 清理方法（如果需要）
    }

    private boolean isStaticResource(String uri) {
        // 简单判断是否为静态资源的条件
        return uri.endsWith(".eot") ||  uri.endsWith(".svg") ||  uri.endsWith(".ttf") || uri.endsWith(".woff") || uri.endsWith(".ico") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png") || uri.endsWith(".css") || uri.endsWith(".js");
    }

    private String generateETag(String uri) {
        try {
            // 简单的 ETag 生成方式，可以根据实际需求进行改进
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(uri.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("ETag generation error", e);
        }
    }

}
