package com.amzmall.project.cognito.config;

import com.amzmall.project.cognito.config.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스를 스프링 설정 클래스로 지정
@EnableWebSecurity // 웹 보안을 활성화
public class SecurityConfig{

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // JwtRequestFilter 의존성 주입

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )// 세션을 사용하지 않고, STATELESS 정책을 적용하여 매 요청마다 인증이 필요함
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests // 요청에 대한 권한을 지정
                .requestMatchers("v3/api-docs/**","/swagger-ui/**","/auth/**","/api/v1/**").permitAll() // 로그인 및 회원가입 경로는 모두에게 접근 허용
                .anyRequest().authenticated());// 그 외 모든 요청은 인증된 사용자만 접근 가능
                //.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)); // JwtRequestFilter를 필터 체인에 추가하여 요청마다 토큰을 검증
        return http.build();
    }


    // 필요한 빈(Bean) 정의 및 추가적인 설정이 필요한 경우 여기에 구현
}

