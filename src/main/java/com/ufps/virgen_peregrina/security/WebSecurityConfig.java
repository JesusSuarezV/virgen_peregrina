package com.ufps.virgen_peregrina.security;

import com.ufps.virgen_peregrina.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/js/**", "/css/**", "/assets/**", "/iniciar_sesion").permitAll()


                                .requestMatchers("/posts/crear", "/posts/guardar", "/posts/{idPost}/editar", "/posts/{idPost}/actualizar", "/posts/{idPost}/eliminar", "replicas/{idReplica}/editar", "replicas/{idReplica}/actualizar", "replicas/{idReplica}/eliminar","replicas/aprobar").hasAnyAuthority("ADMINISTRADOR", "SUPERADMINISTRADOR")
                                .requestMatchers("/usuarios/**").hasAuthority("SUPERADMINISTRADOR")
                                .requestMatchers("/", "/registrarse", "/registrar_usuario", "/recuperar/**", "/cambiar/{codigo}", "/actualizar/{codigo}", "/replicas", "/posts", "/posts/{idPost}", "/activar/{codigo}").permitAll()
                                .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/iniciar_sesion")
                        .defaultSuccessUrl("/?exitoLogin")
                        .permitAll())
                .logout(logout -> logout
                        // Configure logout behavior here
                        .invalidateHttpSession(true) // Invalidate session on logout
                        .clearAuthentication(true)   // Clear SecurityContext on logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/cerrar_sesion")) // Custom logout URL
                        .logoutSuccessUrl("/?logout")  // Redirect after successful logout
                );
        return httpSecurity.build();
    }
}

