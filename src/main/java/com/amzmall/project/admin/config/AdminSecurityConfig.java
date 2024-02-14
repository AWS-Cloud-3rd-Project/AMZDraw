package com.amzmall.project.admin.config;

import com.amzmall.project.admin.config.AdminAuthenticationEntryPoint;
import com.amzmall.project.admin.service.AdminUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AdminSecurityConfig {

    public static final String DEFAULT_HOME_URL = "/";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AdminUserDetailService adminUserDetailService) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(
                                        "/img/**", "/js/**", "/css/**", "/scss/**", "/vendor/**",
                                        "/users/sign-up", "/users/login", "/error",
                                        "/hello",
                                        "/users/register"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .defaultSuccessUrl(DEFAULT_HOME_URL)
                                .usernameParameter("email")
                                .failureUrl("/users/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/users/login")
                                .invalidateHttpSession(true)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new AdminAuthenticationEntryPoint())
                );

        http.userDetailsService(adminUserDetailService)
                .passwordEncoder(passwordEncoder());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
