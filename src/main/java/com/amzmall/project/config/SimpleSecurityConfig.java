package com.amzmall.project.config;

import com.amzmall.project.service.customer.CustomerDetailService;
import com.amzmall.project.enums.ECommerceRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SimpleSecurityConfig {

    @Autowired
    private CustomerDetailService customerDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/customer/", "/customer/login", "/customer/signup").authenticated()
                                .requestMatchers("/cart/**").hasRole(ECommerceRole.CUSTOMER.name())
                                .requestMatchers("/checkout/**").hasRole(ECommerceRole.CUSTOMER.name())
                                .requestMatchers("/customer/my-page*").hasRole(ECommerceRole.CUSTOMER.name())
                                .requestMatchers("/**").permitAll()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/customer/login")
                                .defaultSuccessUrl("/")
                                .usernameParameter("email")
                                .successHandler((request, response, authentication) -> {
                                    request.getSession().setMaxInactiveInterval(1800); // Set session timeout (seconds)
                                    response.sendRedirect("/");
                                })
                                .failureUrl("/customer/login?error=true")
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .maximumSessions(1) // Allow only one session per user
                                .maxSessionsPreventsLogin(true)
                                .expiredUrl("/customer/login?expired=true")
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailService).passwordEncoder(passwordEncoder());
    }
}
