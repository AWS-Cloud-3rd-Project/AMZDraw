package com.amzmall.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//인증되지 않은 사용자(비회원)가 보호된 리소스에 접근할 때의 처리 정의
@Slf4j
@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    public static final String XML_HTTP_REQUEST_VALUE = "XMLHttpRequest";
    public static final String X_REQUESTED_WITH_HEADER_KEY = "x-requested-with";
    public static final String CUSTOMER_LOGIN = "/customer/login";

    @Override
    //사용자가 인증되지 않은 경우의 메서드
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if (XML_HTTP_REQUEST_VALUE.equals(request.getHeader(X_REQUESTED_WITH_HEADER_KEY))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String redirectUrl = determineRedirectUrl(request);
            response.sendRedirect(redirectUrl);
        } //http요청 헤더 중 xmlhttprequest인지를 나타냄 (주로 ajax요청에서 사용됨)
    }//요청이 Ajax 요청 -> 401 Unauthorized 상태 코드 응답 후 종료

    private String determineRedirectUrl(HttpServletRequest request) {
        String redirectUrl = CUSTOMER_LOGIN;

        if (request.getSession(false) != null && request.getSession(false).getAttribute("customerEmail") != null) {
            redirectUrl = "/"; // Redirect to the home page for authenticated users
        }
        return redirectUrl;
    }
}
    //그렇지 않은 경우 -> /customer/login으로 redirect
