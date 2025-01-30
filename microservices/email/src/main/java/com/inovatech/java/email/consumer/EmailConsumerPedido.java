package com.inovatech.java.email.consumer;

import com.inovatech.java.email.model.Pedido;
import com.inovatech.java.email.model.Produto;
import com.inovatech.java.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerPedido {

    private final EmailService emailService;

    public EmailConsumerPedido(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "EmailPedidoCli")
    public void receberMensagem(Pedido pedido) {
        System.out.println("Mensagem recebida: " + pedido);

        // Verificar se o e-mail é nulo ou vazio
        if (pedido.getEmail() == null || pedido.getEmail().isEmpty()) {
            System.err.println("Erro: O e-mail do cliente é nulo ou vazio.");
            return;
        }

        // Montar o assunto e a mensagem do e-mail
        String assunto = "Detalhes do seu pedido na Inovatech";
        String mensagem = construirMensagemPedido(pedido);

        // Enviar o e-mail
        emailService.enviarEmail(pedido.getEmail(), assunto, mensagem);
        System.out.println("E-mail enviado para: " + pedido.getEmail());
    }

    private String construirMensagemPedido(Pedido pedido) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Olá ").append(pedido.getNome()).append(" ").append(pedido.getSobrenome()).append(",\n\n");
        mensagem.append("Aqui estão os detalhes do seu pedido:\n");
        mensagem.append("ID do Pedido: ").append(pedido.getIdPedido()).append("\n");
        mensagem.append("Data e Hora: ").append(pedido.getDataHora()).append("\n");
        mensagem.append("Valor Total: R$ ").append(String.format("%.2f", pedido.getValorTotal())).append("\n\n");
        mensagem.append("Produtos:\n");

        for (Produto produto : pedido.getProdutos()) {
            mensagem.append("- ").append(produto.getNome())
                    .append(" (Quantidade: ").append(produto.getQuantidade())
                    .append(", Preço Unitário: R$ ").append(String.format("%.2f", produto.getPrecoUnitario()))
                    .append(")\n");
        }

        mensagem.append("\nAtenciosamente,\nEquipe Inovatech");
        return mensagem.toString();
    }
}