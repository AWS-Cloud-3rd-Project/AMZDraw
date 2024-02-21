package com.amzmall.project.config;

import com.amzmall.project.domain.customer.CustomerDetailService;
import com.amzmall.project.enums.ECommerceRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf 설정
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                      //  .anyRequest().authenticated()
                        .requestMatchers("/customer/","/customer/login","customer/signup").authenticated()
                )
                //customer의 메인페이지, 로그인 페이지, 회원가입 페이지는 로그인없이 접근 가능
                        //.cors().disable();
                .formLogin(formLogin -> formLogin
                .loginPage("/customer/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                System.out.println("authentication" + authentication.getName());
                                response.sendRedirect("/");
                            }
                        })
                .failureUrl("/customer/login?error=true"))
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/cart/**").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .requestMatchers("/checkout/**").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .requestMatchers("/customer/my-page*").hasRole(String.valueOf(ECommerceRole.CUSTOMER))
                        .requestMatchers("/**").permitAll());

        http.exceptionHandling().authenticationEntryPoint(new SimpleAuthenticationEntryPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerDetailService).passwordEncoder(passwordEncoder());
    }
}