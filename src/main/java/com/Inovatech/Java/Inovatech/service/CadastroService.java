package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.model.Cliente;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CadastroService {

    private final ClienteRepository clienteRepository;
    private final ClienteDetailsService clienteDetailsService;

    @Autowired
    private RabbitTemplate rabbitTemplate; // Para enviar mensagens ao RabbitMQ
    private String statusQueue = "EmailCli";

    public CadastroService(ClienteRepository clienteRepository, ClienteDetailsService clienteDetailsService) {
        this.clienteRepository = clienteRepository;
        this.clienteDetailsService = clienteDetailsService;
    }

    public void cadastrarNovoCliente(Cliente cliente) {
        // Verifica se o cliente já existe no banco
        Cliente clienteExistente = clienteRepository.findByEmailCliente(cliente.getEmailCliente());

        if (clienteExistente != null) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado!");
        }

        // Criptografa a senha antes de salvar
        String senhaCriptografada = clienteDetailsService.encodePassword(cliente.getSenhaCliente());
        cliente.setSenhaCliente(senhaCriptografada);

        // Salva o cliente no banco
        clienteRepository.save(cliente);

        // Envia e-mail de boas-vindas
        EnviarEmailBoasVindas(cliente.getNomeCliente(), cliente.getSobrenomeCliente(), cliente.getEmailCliente());
    }


    private void EnviarEmailBoasVindas(String NomeCliente, String SobrenomeCliente, String EmailCliente) {
    // Cria uma mensagem de status
    Map<String, Object> mensagem = new HashMap<>();
    mensagem.put("nome", NomeCliente);
    mensagem.put("sobrenome:", SobrenomeCliente);
    mensagem.put("email:", EmailCliente);
    mensagem.put("Cadastro criado em:", LocalDateTime.now());

    // Publica a mensagem no RabbitMQ
    rabbitTemplate.convertAndSend(statusQueue, mensagem);
}




}
