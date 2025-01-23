package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.entity.Pedido;
import com.Inovatech.Java.Inovatech.entity.PedidoHasProduto;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.service.PedidoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteRepository clienteRepository;

    public PedidoController(PedidoService pedidoService, ClienteRepository clienterepository) {
        this.pedidoService = pedidoService;
        this.clienteRepository = clienterepository;
    }

    @GetMapping("/meuspedidos")
    public String meusPedidos(Model model) {
        // Recupera o email do usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Busca o cliente no banco de dados com o email
        Cliente cliente = clienteRepository.findByEmailCliente(email);

        if (cliente == null) {
            return "redirect:/carrinho?erro=Cliente não encontrado";
        }

        // Busca os pedidos do cliente
        List<Pedido> pedidos = pedidoService.findByCliente_idCliente(cliente.getIdCliente());

        DecimalFormat decimalFormat = new DecimalFormat("R$ #,##0.00");

        // Busca os PedidoHasProduto do cliente
        List<PedidoHasProduto> pedidoHasProdutos = pedidoService.findByPedido_Cliente_idCliente(cliente.getIdCliente());

        // Adiciona as listas ao modelo para Thymeleaf
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("pedidoHasProdutos", pedidoHasProdutos);
        model.addAttribute("content", "meuspedidos");
        return "layouts/main"; // Retorna o layout principal
    }

}
