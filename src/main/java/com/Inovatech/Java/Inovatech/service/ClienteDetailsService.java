package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.model.Cliente;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ClienteDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;  // Injetando o PasswordEncoder

    public ClienteDetailsService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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
}
