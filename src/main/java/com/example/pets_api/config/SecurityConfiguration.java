package com.example.pets_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pets_api.services.MongoUserDetailsService;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  MongoUserDetailsService userDetailsService;
  
  /**
   * Disables CSRF protection, as it is uneccesary for an API
   * Explains that all requests to any endpoint must be authorizes, 
   * 	or else they should be rejected
   * Tells Spring to look for the HTTP Basic authentication method
   * Tells Spring not to hold session information for users, as this is uneccesary in an API
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests().anyRequest().authenticated()
      .and().httpBasic()
      .and().sessionManagement().disable();
  }
  
  /**
   * use the BCrypt encoder to hash and compare passwords
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
   return new BCryptPasswordEncoder();
  }
  
  /**
   * we must specify in our SecurityConfiguration that 
   * we want to use the MongoUserDetailsService for our authentication
   */
  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
   builder.userDetailsService(userDetailsService);
  }
  
}