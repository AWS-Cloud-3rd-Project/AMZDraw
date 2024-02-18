package com.amzmall.project.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String XML_HTTP_REQUEST_VALUE = "XMLHttpRequest";
    public static final String X_REQUESTED_WITH_HEADER_KEY = "x-requested-with";
    public static final String CUSTOMER_LOGIN = "/customer/login";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (XML_HTTP_REQUEST_VALUE.equals(request.getHeader(X_REQUESTED_WITH_HEADER_KEY))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.sendRedirect(CUSTOMER_LOGIN);
    }
}