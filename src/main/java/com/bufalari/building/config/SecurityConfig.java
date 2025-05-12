package com.bufalari.building.config;

import com.bufalari.building.security.JwtAuthenticationFilter; // <<<--- Pacote correto para o filtro JWT
import lombok.RequiredArgsConstructor; // Para injeção de construtor
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider; // Importar
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Importar
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService; // Importar
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar
import org.springframework.security.crypto.password.PasswordEncoder; // Importar
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor // Injeta o JwtAuthenticationFilter
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final String[] PUBLIC_MATCHERS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/actuator/**" // CUIDADO: Expor apenas endpoints seguros do actuator
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 AuthenticationProvider authenticationProvider,
                                                 CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource)) // Usa o bean injetado
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                // --- REGRAS ESPECÍFICAS PARA PROJECT MANAGEMENT ---
                .requestMatchers(HttpMethod.POST, "/api/projects/**").hasAnyRole("ADMIN", "PROJECT_MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasAnyRole("ADMIN", "PROJECT_MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "VIEWER") // Exemplo
                // Endpoints de Rooms (podem ter suas próprias regras)
                .requestMatchers("/api/rooms/**").hasAnyRole("ADMIN", "PROJECT_MANAGER")
                // Endpoints de Walls (podem ter suas próprias regras)
                .requestMatchers("/api/walls/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "ENGINEER")
                // Outros endpoints podem ser adicionados aqui
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider) // Usa o bean injetado
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            @Qualifier("projectUserDetailsService") UserDetailsService userDetailsService, // <<< Qualificador do UserDetailsService deste serviço
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // RESTRINJA EM PRODUÇÃO
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization", "Location"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}