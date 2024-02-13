package com.codewithvarsha.blogapp2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithvarsha.blogapp2.security.CustomDetailService;
import com.codewithvarsha.blogapp2.security.JwtAuthenticationEntryPoint;
import com.codewithvarsha.blogapp2.security.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {"/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

    };

    @Autowired
    private CustomDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                csrf(csrf -> csrf
                .disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(PUBLIC_URLS)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET) 
                        //this means that all get mapping can be accessed all role users
                        //post mapping,put mapping and delete mapping is allowed by admin only
                        .permitAll()
                        .anyRequest()
                        .authenticated()).exceptionHandling(handling -> handling
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

     */

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }


    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    FilterRegistrationBean coresFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(-110);

        return bean;
    }

}