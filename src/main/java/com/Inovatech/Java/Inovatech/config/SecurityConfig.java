package com.Inovatech.Java.Inovatech.config;

import com.Inovatech.Java.Inovatech.service.ClienteDetailsService;  // Não precisa mais se preocupar com o nome específico
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;  // Injeção do UserDetailsService genérico

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/", "/carrinho", "/carrinho/adicionar", "/carrinho/remover", "/carrinho/alterarQuantidade", "/logout", "/login",
                        "/cadastro", "/assets/**", "/img/**", "/js/**", "/css/**")
                .permitAll() // Essas rotas não exigem autenticação
                .anyRequest().authenticated() // Exige autenticação para o restante
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Garante redirecionamento para "/" após login
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usando BCrypt para criptografar senhas
    }

    // Configuração do AuthenticationManager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService) //
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
