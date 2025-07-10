package br.pucpr.authserver.security

import jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig(private val jwtTokenFilter: JwtTokenFilter) {

    @Bean
    fun filterChain(security: HttpSecurity): DefaultSecurityFilterChain =

        security
            .cors { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { it
                .authenticationEntryPoint { _, response, authException ->
                    response.sendError(SC_NOT_FOUND, authException.message)
                }
            }
            .headers { it
                .frameOptions { frameOptions -> frameOptions.disable() }
            }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/mesa/{mesa}").permitAll()
                    .requestMatchers(HttpMethod.POST).hasRole("ADM")
                    .requestMatchers(HttpMethod.PUT).hasRole("ADM")
                    .requestMatchers(HttpMethod.DELETE).hasRole("ADM")
                    .requestMatchers("/error/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()

            }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @Bean
    fun corsFilter() =
        CorsConfiguration().apply{
            addAllowedHeader("*")
            addAllowedOrigin("*")
            addAllowedMethod("*")
        } .let{
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", it)
            }
        } .let { CorsFilter(it) }
}