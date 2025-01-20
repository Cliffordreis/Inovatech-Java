package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    private final ClienteRepository clienteRepository;
    private final ClienteDetailsService clienteDetailsService;

    public CadastroService(ClienteRepository clienteRepository, ClienteDetailsService clienteDetailsService) {
        this.clienteRepository = clienteRepository;
        this.clienteDetailsService = clienteDetailsService;
    }

    public void cadastrarNovoCliente(Cliente cliente) {
        // Criptografar a senha antes de salvar no banco
        String senhaCriptografada = clienteDetailsService.encodePassword(cliente.getSenhaCliente());
        cliente.setSenhaCliente(senhaCriptografada);  // Define a senha criptografada no cliente

        clienteRepository.save(cliente);  // Salva o cliente com a senha criptografada no banco de dados
    }
}
