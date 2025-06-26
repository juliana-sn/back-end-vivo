package br.purpletech.vivo.config;

import br.purpletech.vivo.services.imp.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.purpletech.vivo.security.UserDetailsServiceImp;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;

    public SecurityConfig(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //auth
                        .requestMatchers("/auth/**").permitAll()

                        //onboarding
                        .requestMatchers(HttpMethod.GET, "/onboardings").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/onboardings/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/onboardings").hasAnyRole("HR", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/onboardings/manager/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/onboardings/buddy/**").hasRole("BUDDY")
                        .requestMatchers(HttpMethod.DELETE, "/onboardings/{id}").hasAnyRole("HR", "MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/onboardings/{id}").hasAnyRole("HR", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/onboardings/{id}/users/**").hasAnyRole("HR", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/onboardings/{id}/users/**").hasAnyRole("HR", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/onboardings/{id}/chat").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/onboardings/{id}/steps").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/onboardings/{id}/steps/{idStep}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/onboardings/{id}/next-step").hasRole("COLLABORATOR")
                        .requestMatchers(HttpMethod.POST, "/onboardings/{id}/reports").hasRole("COLLABORATOR")
                        .requestMatchers(HttpMethod.GET, "/onboardings/{id}/reports").authenticated()

                        //platform
                        .requestMatchers(HttpMethod.GET, "/platforms").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/platforms/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/platforms").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/platforms/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/platforms/**").hasRole("MANAGER")

                        //step
                        .requestMatchers(HttpMethod.POST, "/steps").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/steps/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/steps/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/steps/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/steps/{id}/tasks").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/steps//{id}/tasks/{idTask}").hasRole("MANAGER")

                        //task
                        .requestMatchers(HttpMethod.GET, "/tasks").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/tasks/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/steps/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/steps/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/steps/{id}").hasRole("MANAGER")

                        //team
                        .requestMatchers(HttpMethod.GET, "/teams").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/teams/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/teams").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/teams/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{id}/users/{idUser}").hasAnyRole("MANAGER", "HR")
                        .requestMatchers(HttpMethod.POST, "/teams/{id}/platforms/{idPlatform}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{id}/platforms/{idPlatform}").hasRole("MANAGER")

                        //user
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/team").hasRole("HR")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("HR")
                        .requestMatchers(HttpMethod.POST, "/users/{idSender}/chat/{idReceiver}/message").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/{id}/chat/manager").hasRole("COLLABORATOR")
                        .requestMatchers(HttpMethod.GET, "/users/{id}/chat/buddy").hasRole("COLLABORATOR")
                        .requestMatchers(HttpMethod.GET, "/{senderId}/chat/{receiverId}").hasAnyRole("MANAGER", "BUDDY")
                        .requestMatchers(HttpMethod.GET, "/users/{userId}/chats").hasAnyRole("MANAGER", "BUDDY")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

