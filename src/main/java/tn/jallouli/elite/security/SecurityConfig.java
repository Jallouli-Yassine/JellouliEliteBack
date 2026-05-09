package tn.jallouli.elite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JWTAuthEntryPoint jwtAuthEntryPoint;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(JWTAuthEntryPoint jwtAuthEntryPoint, JWTAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTGenerator jwtGenerator, CustomUserDetailsService userDetailsService) throws Exception {
        //creation autorisation w authentication
        //oel haja cors w csrf
        http.cors(withDefaults())
                //nekhdmo stateless donc ndesactiviw csrf
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(new JWTAuthenticationFilter(jwtGenerator, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }


}