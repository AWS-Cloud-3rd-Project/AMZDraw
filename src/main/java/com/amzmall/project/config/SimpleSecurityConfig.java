package com.amzmall.project.config;

import com.amzmall.project.domain.customer.CustomerDetailService;
import com.amzmall.project.enums.ECommerceRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SimpleSecurityConfig {
    @Autowired
    private CustomerDetailService customerDetailService;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(); //password암호화를 위해 BCryptPasswordEncorder클래스 생성하여 빈에 등록
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }//resources 접근할 수 있도록 빈 추가
    @Bean
    //
    //protected void configure(HttpSecurity httpSecurity) throws Exception {
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //html공격 막기 위한 csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/customer/")
                        .cors().disable();

        httpSecurity
                .formLogin()
                .loginPage("/customer/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/customer/login?error=true")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/cart/**").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .antMatchers("/checkout/**").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .antMatchers("/customer/my-page*").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .antMatchers("/**").permitAll());

        httpSecurity.exceptionHandling().authenticationEntryPoint(new SimpleAuthenticationEntryPoint());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailService).passwordEncoder(passwordEncoder());
    }
}
