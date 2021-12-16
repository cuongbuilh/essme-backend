package org.vietsearch.essme.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/api/questions/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/requests/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .addFilterBefore(new FireBaseTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}