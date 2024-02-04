package com.devkind.barebonesystem.config;

import com.devkind.barebonesystem.security.JwtAuthenticationFilter;
import com.devkind.barebonesystem.security.SecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    private final SecurityExceptionHandler securityExceptionHandler;

    /*
    * Configure CORS policy for the application*/
    @Bean
    public CorsConfiguration cors() {
        CorsConfiguration cors = new CorsConfiguration();
        List<String> allowMethods = Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList());
        cors.setAllowedMethods(allowMethods);
        cors.setAllowedOrigins(Arrays.asList("*"));
        cors.setAllowedHeaders(Arrays.asList("*"));
        return cors;
    }

    /* Configure Filter Registration for HTTP request */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setFilter(filter);
        return registrationBean;
    }

    /* Apply necessary configuration to the application */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // Configure CORS policy
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> cors()))
                // Disable CSRF
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeRequests()
                .requestMatchers("/auth/register", "/auth/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(securityExceptionHandler)
                        .authenticationEntryPoint(securityExceptionHandler)
                );

        return httpSecurity.build();

    }
}

