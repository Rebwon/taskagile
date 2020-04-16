package com.rebwon.taskagile.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.rebwon.taskagile.domain.common.security.ApiRequestAccessDeniedExceptionTranslationFilter;
import com.rebwon.taskagile.web.apis.authenticate.AuthenticationFilter;
import com.rebwon.taskagile.web.apis.authenticate.SimpleAuthenticationFailureHandler;
import com.rebwon.taskagile.web.apis.authenticate.SimpleAuthenticationSuccessHandler;
import com.rebwon.taskagile.web.apis.authenticate.SimpleLogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC = new String[]{
    "/error", "/login", "/logout", "/register", "/api/registrations"};

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/static/**", "/js/**", "/css/**", "/images/**", "/favicon.ico");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
      .and()
        .authorizeRequests()
        .antMatchers(PUBLIC).permitAll()
        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
        .anyRequest().authenticated()
      .and()
        .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(apiRequestAccessDeniedExceptionTranslationFilter(), ExceptionTranslationFilter.class)
        .formLogin()
        .loginPage("/login")
      .and()
        .logout()
        .logoutUrl("/api/me/logout")
        .logoutSuccessHandler(logoutSuccessHandler())
      .and()
      .csrf().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationFilter authenticationFilter() throws Exception {
    AuthenticationFilter authenticationFilter = new AuthenticationFilter();
    authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
    authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new SimpleAuthenticationSuccessHandler();
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new SimpleAuthenticationFailureHandler();
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new SimpleLogoutSuccessHandler();
  }

  public AccessDeniedHandler accessDeniedHandler() {
    return new AccessDeniedHandlerImpl();
  }

  public ApiRequestAccessDeniedExceptionTranslationFilter apiRequestAccessDeniedExceptionTranslationFilter() {
    return new ApiRequestAccessDeniedExceptionTranslationFilter();
  }
}
