package io.codelex.flightplanner.flights.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**").authenticated()
                .antMatchers("/admin-api/**").authenticated()
                .antMatchers("/testing-api/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .and()
                .httpBasic();

        http.headers().frameOptions().disable();

        return http.build();
    }

}