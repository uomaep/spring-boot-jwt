package uomaep.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private AuthenticationManager manager;

    @Autowired private LoginAuthProvider loginAuthProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        http.addFilterBefore(
                new LoginAuthFilter(authenticationManager(http)),
                UsernamePasswordAuthenticationFilter.class
        );

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/signup").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated();

        http.authenticationManager(authenticationManager(http));
        return http.build();
    }

    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        if(this.manager != null) return this.manager;

        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        this.manager = managerBuilder.build();

        return this.manager;
    }
}
