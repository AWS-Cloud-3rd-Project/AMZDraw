package com.amzmall.project.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    //세션 정책
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy(){
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //해시 알고리즘 생성 후 반환(해시 기반 암호화)
    }
    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(Customizer.withDefaults())
//                //.and()
//                .authorizeRequests(authz -> authz.requestMatchers("/")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .oauth2Login(Customizer.withDefaults())
//                //.and()
//                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
//        return http.build();
//    }
//}

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .authorizeHttpRequests()//인가를 위함
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers("/public/**", "/v3/api-docs/**", "/swagger-ui/**",
                        "/swagger-resources/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(jwtAuthConverter); //jwt내 데이터 파싱하여 사용
        return http.build();
    }
}