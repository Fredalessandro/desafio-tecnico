package com.desafio.credito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.desafio.credito.service.UsuarioService;
import com.desafio.credito.entity.Usuario;
import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Collections.singletonList("*"));
    configuration.setAllowedHeaders(Collections.singletonList("*"));
    configuration.setAllowCredentials(false);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil,
      UsuarioService usuarioService) throws Exception {

    return http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> {
          // Swagger/OpenAPI - TODAS as URLs possíveis (mais específicas primeiro)
          auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
              "/v3/api-docs", "/v3/api-docs/swagger-config", // URL específica que estava faltando
              "/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui/index.html",
              "/configuration/ui", "/configuration/security").permitAll();

          // Endpoints públicos da API
          auth.requestMatchers( "/usuarios", "/usuarios/login").permitAll();
          auth.requestMatchers(HttpMethod.POST, "/test").permitAll();
          //auth.requestMatchers(HttpMethod.GET, "/creditos").permitAll();

          // Qualquer outra requisição precisa de autenticação
          auth.anyRequest().authenticated();
        }).addFilterBefore(new JwtAuthFilter(jwtUtil, usuarioService),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  public static class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtUtil jwtUtil, UsuarioService usuarioService) {
      this.jwtUtil = jwtUtil;
      this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

      String requestPath = request.getRequestURI();

      // Debug temporário
      System.out.println("🔍 Filtro JWT - URL: " + requestPath);
      System.out.println("🔍 É URL pública? " + isPublicUrl(requestPath));

      if (isPublicUrl(requestPath)) {
        System.out.println("✅ URL pública ignorada: " + requestPath);
        filterChain.doFilter(request, response);
        return;
      }

      String authHeader = request.getHeader("Authorization");
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        if (jwtUtil.validarToken(token)) {
          String login = jwtUtil.extrairLogin(token);
          Usuario usuario = usuarioService.buscarPorLogin(login).orElse(null);
          if (usuario != null) {
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario.getLogin(), null,
                    Collections.emptyList());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
      filterChain.doFilter(request, response);
    }

    /**
     * Verifica se a URL é pública e não deve ser processada pelo filtro JWT
     */
    private boolean isPublicUrl(String requestPath) {
      return requestPath.equals("/usuarios") || requestPath.equals("/usuarios/login")
          || requestPath.equals("/creditos/status") || requestPath.equals("/test") ||
          // Swagger URLs - lista mais completa
          requestPath.equals("/swagger-ui.html") || requestPath.startsWith("/swagger-ui/")
          || requestPath.startsWith("/v3/api-docs")
          || requestPath.equals("/v3/api-docs/swagger-config") || // URL específica
          requestPath.startsWith("/api-docs/") || requestPath.startsWith("/swagger-resources/")
          || requestPath.startsWith("/webjars/") || requestPath.equals("/configuration/ui")
          || requestPath.equals("/configuration/security");
    }
  }
}
