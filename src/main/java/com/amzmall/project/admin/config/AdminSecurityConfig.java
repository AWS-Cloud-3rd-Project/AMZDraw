package com.amzmall.project.admin.config;
import com.amzmall.project.admin.service.AdminUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AdminSecurityConfig {

    public static final String DEFAULT_HOME_URL = "/";

    @Autowired
    private AdminUserDetailService adminUserDetailService;

    @Bean
    protected DefaultSecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()
                .cors(cors -> {
                    try {
                        cors.disable()

                        .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/img/**", "/js/**", "/css/**", "/scss/**", "/vendor/**",
                                "/users/sign-up", "/users/login", "/error",
                                "/hello",
                                "/users/register"
                        )
                        .permitAll()
                        .anyRequest().authenticated())
                        .formLogin(formLogin->formLogin
                        .loginPage("/users/login")
                        .defaultSuccessUrl(DEFAULT_HOME_URL)
                        .usernameParameter("email")
                        .failureUrl("/users/login?error=true")
                        .permitAll())

                        .logout(logout -> logout
                        .logoutSuccessUrl("/users/login")
                        .invalidateHttpSession(true)
                        )
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                };

                return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder bPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailService).passwordEncoder(bPasswordEncoder());
    }
}