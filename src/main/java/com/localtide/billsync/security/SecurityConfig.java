package com.localtide.billsync.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	
	 @Autowired
	    public SecurityConfig(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	        
	    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    .addFilterBefore(new JWTAuthenticationProcessingFilter(userDetailsService), BasicAuthenticationFilter.class)
	    .headers()
	    .and()
	        .cors().and()
	        .csrf().disable()
	        .authorizeRequests()
	        .requestMatchers("/public/**", "/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**").permitAll()
	            .requestMatchers("/api/authenticate").permitAll()
	            .requestMatchers("/api/user/enroll").permitAll()
	            .requestMatchers("/api/signup").permitAll()
	            .requestMatchers("/api/forgotPassword").permitAll()
	            .requestMatchers("/api/resetPassword").permitAll()
	            .requestMatchers("/api/verifyotp").permitAll()
	            .requestMatchers("/api/generateotp").permitAll()
	            .requestMatchers("/api/[a-zA-Z0-9/?=&-_]+").authenticated()
	            .requestMatchers("/v2/api-docs").permitAll()
	            .requestMatchers("/swagger-ui/").permitAll()
	            .and()
	        .formLogin()
	            .and()
	        .logout();
	          //  .and()
	       
	       
	
	    return http.build();
	}


}
