package com.amzmall.project.admin.config;

import com.amzmall.project.admin.enums.AdminUserRole;
import com.amzmall.project.admin.service.AdminUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {

    //권한 계층 설정
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy("ROLE_SUPER_AD > ROLE_PRODUCT_AD and ROLE_SUPER_AD > ROLE_MEMBER_AD and ROLE_SUPER_AD > ROLE_ORDER_AD");

        return roleHierarchy;
    }

    private final AdminUserDetailService adminUserDetailService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/", "/login/**").permitAll()
                                .requestMatchers("/product_admin/**").hasRole(String.valueOf(AdminUserRole.PRODUCT_AD))
                                .requestMatchers("/order_admin/**").hasRole(String.valueOf(AdminUserRole.ORDER_AD))
                                .requestMatchers("/member_admin/**").hasRole(String.valueOf(AdminUserRole.MEMBER_AD))
                                .requestMatchers("/","/super_admin/**","/admin").hasRole(String.valueOf(AdminUserRole.SUPER_AD))
                                .anyRequest().authenticated()
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