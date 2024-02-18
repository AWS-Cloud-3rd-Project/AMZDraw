package com.amzmall.project.Config;

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