package com.inovatech.java.email.consumer;

import com.inovatech.java.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "EmailCli")
    public void receberMensagem(Map<String, Object> dadosCadastro) {
        System.out.println("Mensagem recebida: " + dadosCadastro);

        // Extrair os dados do JSON
        String nome = (String) dadosCadastro.get("nome");
        String sobrenome = (String) dadosCadastro.get("sobrenome:");
        String email = (String) dadosCadastro.get("email:");

        // Montar o assunto e a mensagem do e-mail
        String assunto = "Bem-vindo ao Inovatech!";
        String mensagem = String.format(
            "Olá %s %s,\n\n" +
            "Seja bem-vindo(a) à Inovatech! Estamos muito felizes em tê-lo(a) como nosso cliente.\n" +
            "Atenciosamente,\nEquipe Inovatech",
            nome, sobrenome
        );

        // Enviar o e-mail
        emailService.enviarEmail(email, assunto, mensagem);
        System.out.println("E-mail enviado para: " + email);

        // Exibir todos os dados do cliente no console
        System.out.println("Dados do cliente usados para o envio do e-mail:");
        System.out.println("- Nome: " + nome);
        System.out.println("- Sobrenome: " + sobrenome);
        System.out.println("- E-mail: " + email);
    }
}