package com.exercise.guangqi.config;

import com.exercise.guangqi.service.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity.formLogin().loginPage("/login").permitAll();

        httpSecurity.formLogin().defaultSuccessUrl("/home",true);

        httpSecurity.logout().deleteCookies("remove").invalidateHttpSession(false).
                logoutUrl("/logout").logoutSuccessUrl("/login");
    }
}
