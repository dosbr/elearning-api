package br.com.dos.elearning.security;

import br.com.dos.elearning.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = userService;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            System.out.println("Unauthorized request");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }).and();
        http
                .authorizeRequests().antMatchers("/users/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.eraseCredentials(false)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

}
