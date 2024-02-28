package com.amzmall.project.admin.config;

import com.amzmall.project.admin.enums.AdminUserRole;
import com.amzmall.project.admin.service.AdminUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)//요청이 지나가는 필터 정보 확인 가능
public class AdminSecurityConfig {
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(buildRoleHierarchy());
        return roleHierarchy;
    }
//계층 권한 설정
    private String buildRoleHierarchy() {
        return String.format(
                "%s > %s and %s > %s and %s > %s",
                AdminUserRole.SUPER_AD.getKey(), AdminUserRole.PRODUCT_AD.getKey(),
                AdminUserRole.SUPER_AD.getKey(), AdminUserRole.MEMBER_AD.getKey(),
                AdminUserRole.SUPER_AD.getKey(), AdminUserRole.ORDER_AD.getKey()
        );
    }

    private final AdminUserDetailService adminUserDetailService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        http
                .csrf(AbstractHttpConfigurer::disable //csrf filter 비활성화
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests //http요청으로 들어오는 모든 것에 대해 매칭
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/", "/login").permitAll() // login 페이지에 대한 접근은 모두 허용
                                .requestMatchers("/product_admin/**").hasRole(AdminUserRole.PRODUCT_AD.getKey())
                                .requestMatchers("/order_admin/**").hasRole(AdminUserRole.ORDER_AD.getKey())
                                .requestMatchers("/member_admin/**").hasRole(AdminUserRole.MEMBER_AD.getKey())
                                .requestMatchers("/super_admin/**").hasRole(AdminUserRole.SUPER_AD.getKey())
                                .anyRequest().authenticated() //총 관리자는 모두 다 접근 가능 , 그리고 설정된 값 외엔 인가 필요
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/")
                                .defaultSuccessUrl("/admin")
                                .usernameParameter("email")
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig
                                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/accessDenied")
                ));
        return http.build();
    }

//    @Bean
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminUserDetailService).passwordEncoder(passwordEncoder())
//                .roleHierarchy(roleHierarchy());
//    }
}