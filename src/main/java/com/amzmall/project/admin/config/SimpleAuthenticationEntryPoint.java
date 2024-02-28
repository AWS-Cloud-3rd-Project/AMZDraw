package com.amzmall.project.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public static final String XML_HTTP_REQUEST_VALUE = "XMLHttpRequest";
    public static final String X_REQUESTED_WITH_HEADER_KEY = "x-requested-with";
    public static final String ADMIN_LOGIN = "/admin/login";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (XML_HTTP_REQUEST_VALUE.equals(request.getHeader(X_REQUESTED_WITH_HEADER_KEY))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.sendRedirect(ADMIN_LOGIN);
    }
}