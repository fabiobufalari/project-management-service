// Path: project-management-service/src/main/java/com/bufalari/building/config/SecurityConfig.java
package com.bufalari.building.config;

// --- Imports related to security configuration ---
// --- Importações relacionadas à configuração de segurança ---
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // For disabling CSRF cleanly
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// --- Import your custom JWT filter (assuming it's in the 'security' package) ---
// --- Importe seu filtro JWT customizado (assumindo que está no pacote 'security') ---
import com.bufalari.building.security.JwtAuthenticationFilter; // Adjust package if needed

import java.util.Arrays;
import java.util.List;

/**
 * Security configuration for the Project Management Service.
 * Configures JWT authentication, CORS, and endpoint authorization.
 * Configuração de segurança para o Serviço de Gerenciamento de Projetos.
 * Configura autenticação JWT, CORS e autorização de endpoints.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security's web security support / Habilita o suporte de segurança web do Spring Security
@EnableMethodSecurity(prePostEnabled = true) // Enables method-level security like @PreAuthorize / Habilita segurança a nível de método como @PreAuthorize
public class SecurityConfig {

    // Inject the custom JWT filter / Injeta o filtro JWT customizado
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Constructor injection / Injeção via construtor
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Define public endpoints that don't require authentication
    // Define endpoints públicos que não requerem autenticação
    private static final String[] PUBLIC_MATCHERS = {
            "/swagger-ui/**",          // Swagger UI resources / Recursos da UI do Swagger
            "/v3/api-docs/**",         // OpenAPI specification / Especificação OpenAPI
            "/swagger-resources/**",   // Additional Swagger resources / Recursos adicionais do Swagger
            "/webjars/**",             // Webjar resources / Recursos Webjar
            "/actuator/health"         // Health check endpoint / Endpoint de verificação de saúde
    };

    /**
     * Configures the main security filter chain.
     * Configura a cadeia principal de filtros de segurança.
     *
     * @param http The HttpSecurity object to configure. / O objeto HttpSecurity a ser configurado.
     * @return The configured SecurityFilterChain bean. / O bean SecurityFilterChain configurado.
     * @throws Exception If an error occurs during configuration. / Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection as it's typically not needed for stateless REST APIs with JWT
            // Desabilita a proteção CSRF, pois geralmente não é necessária para APIs REST stateless com JWT
            .csrf(AbstractHttpConfigurer::disable)

            // Configure CORS using the corsConfigurationSource bean
            // Configura o CORS usando o bean corsConfigurationSource
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Configure authorization rules for HTTP requests
            // Configura as regras de autorização para requisições HTTP
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PUBLIC_MATCHERS).permitAll() // Allow public access to specified endpoints / Permite acesso público aos endpoints especificados
                // .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow CORS preflight requests / Permite requisições preflight CORS
                .anyRequest().authenticated() // Require authentication for all other requests / Exige autenticação para todas as outras requisições
            )

            // Configure session management to be stateless, as JWT is used
            // Configura o gerenciamento de sessão para ser stateless, já que JWT é usado
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Add the custom JWT filter before the standard UsernamePasswordAuthenticationFilter
            // Adiciona o filtro JWT customizado antes do UsernamePasswordAuthenticationFilter padrão
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings.
     * Configura as definições de CORS (Compartilhamento de Recursos de Origem Cruzada).
     * IMPORTANT: Restrict allowedOrigins in production environments!
     * IMPORTANTE: Restrinja allowedOrigins em ambientes de produção!
     *
     * @return A CorsConfigurationSource bean. / Um bean CorsConfigurationSource.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from any origin (USE WITH CAUTION IN PRODUCTION)
        // Permite requisições de qualquer origem (USE COM CUIDADO EM PRODUÇÃO)
        configuration.setAllowedOrigins(List.of("*")); // Replace "*" with specific origins in production / Substitua "*" por origens específicas em produção
        // Allowed HTTP methods / Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // Allowed HTTP headers / Cabeçalhos HTTP permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"));
        // Whether credentials (like cookies) are supported (usually false for stateless JWT)
        // Se credenciais (como cookies) são suportadas (geralmente false para JWT stateless)
        configuration.setAllowCredentials(false); // Typically false for JWT APIs / Geralmente false para APIs JWT
        // Max age for CORS preflight cache / Tempo máximo de cache para preflight CORS
        configuration.setMaxAge(3600L); // 1 hour / 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all paths / Aplica esta configuração a todos os caminhos
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // --- Other Beans (If needed and not already @Component/@Service) ---
    // --- Outros Beans (Se necessário e ainda não forem @Component/@Service) ---

    // NOTE: JwtAuthenticationFilter, JwtUtil, UserDetailsService (e.g., CustomUserDetailsService),
    //       and AuthServiceClient should be defined as beans (@Component, @Service, @FeignClient)
    //       and will be automatically injected where needed (like in the SecurityConfig constructor
    //       or into JwtAuthenticationFilter). You usually don't need to declare them as @Beans here
    //       unless you need specific custom configuration for their creation.
    // NOTA: JwtAuthenticationFilter, JwtUtil, UserDetailsService (ex: CustomUserDetailsService),
    //       e AuthServiceClient devem ser definidos como beans (@Component, @Service, @FeignClient)
    //       e serão injetados automaticamente onde necessário (como no construtor da SecurityConfig
    //       ou no JwtAuthenticationFilter). Você geralmente não precisa declará-los como @Beans aqui
    //       a menos que precise de configuração customizada específica para sua criação.

}