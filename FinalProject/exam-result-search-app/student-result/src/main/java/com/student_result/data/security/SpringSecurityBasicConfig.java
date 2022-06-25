package com.student_result.data.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityBasicConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/storeresult").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/updateresult").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/getresult").hasAnyRole("USER")
                .antMatchers(HttpMethod.DELETE, "/deleteresult").hasAnyRole("ADMIN").and().csrf().disable().headers()
                .frameOptions().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("student1").password("{noop}password123").roles("USER")
                .and().withUser("schooladmin1").password("{noop}password124").roles("ADMIN")
                .and().withUser("student2").password("{noop}password123").roles("USER")
                .and().withUser("schooladmin2").password("{noop}password124").roles("ADMIN");
    }
}
