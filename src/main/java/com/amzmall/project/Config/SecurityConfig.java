package com.amzmall.project.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .and()
                .authorizeRequests(authz -> authz.mvcMatchers("/")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessUrl("/");
        return http.build();
    }
}