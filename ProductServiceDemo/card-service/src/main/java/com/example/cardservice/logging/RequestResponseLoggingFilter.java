package com.example.cardservice.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - start;
            String reqBody = getPayload(wrappedRequest.getContentAsByteArray());
            String resBody = getPayload(wrappedResponse.getContentAsByteArray());

            StringBuilder headers = new StringBuilder();
            Enumeration<String> names = request.getHeaderNames();
            if (names != null) {
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    headers.append(name).append("=").append(request.getHeader(name)).append(";");
                }
            }

            logger.info("REQUEST {} {} headers={} body={}", request.getMethod(), request.getRequestURI(),
                    headers.toString(), reqBody);
            logger.info("RESPONSE status={} durationMs={} body={}", wrappedResponse.getStatus(), duration, resBody);

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String getPayload(byte[] buf) {
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, 1024 * 10); // limit to 10KB
        return new String(buf, 0, length, StandardCharsets.UTF_8);
    }
}
