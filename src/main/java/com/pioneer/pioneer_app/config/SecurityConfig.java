package com.pioneer.pioneer_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    //전역 CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // 프론트 개발 주소만 정확히 허용
        cfg.setAllowedOrigins(List.of("http://localhost:3000"));
        // 필요한 메서드 허용
        cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        // 클라이언트에서 보낼 수 있는 헤더
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With"));
        // 쿠키/세션을 사용할 것이므로 true
        cfg.setAllowCredentials(true);
        // 프리플라이트 캐시 시간(초)
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    // Spring Security 체인
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API라면 보통 CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                // 요청별 인가 규칙
                .authorizeHttpRequests(auth -> auth
                        // 프리플라이트(OPTIONS) 통과
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 로그인/회원가입 등 공개 엔드포인트
                        .requestMatchers("/api/auth/**").permitAll()
                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                );

        // 세션 기반(서버 세션/쿠키) 사용 시 별도 세션정책 설정 불필요
        // (JWT로 바꾸면 아래처럼 STATELESS로 전환)
        // .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        return http.build();
    }
}