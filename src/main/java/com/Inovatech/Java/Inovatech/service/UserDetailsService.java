package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserDetailsService{

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;  // Injetando o PasswordEncoder

    public UserDetailsService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentando autenticar o usuário: " + email);  // Log para depuração

        Cliente cliente = clienteRepository.findByEmailCliente(email);

        if (cliente == null) {
            System.out.println("Usuário não encontrado no banco de dados: " + email);  // Log para depuração
            throw new UsernameNotFoundException("Usuário não encontrado com esse email: " + email);

        }

        return User.builder()
                .username(cliente.getEmailCliente())
                .password(cliente.getSenhaCliente())  // Senha criptografada
                .roles("USER")
                .build();
    }



    // Método para encriptar a senha durante o cadastro
    public String encodePassword(String senha) {
        return passwordEncoder.encode(senha);  // Criptografando a senha
    }

    // Método para verificar se a senha fornecida corresponde à criptografada
//    public boolean matches(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
}
