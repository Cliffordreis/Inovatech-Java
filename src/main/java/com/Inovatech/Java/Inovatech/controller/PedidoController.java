package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.model.Cliente;
import com.Inovatech.Java.Inovatech.model.Pedido;
import com.Inovatech.Java.Inovatech.model.PedidoHasProduto;
import com.Inovatech.Java.Inovatech.model.StatusCache;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.service.PedidoService;
import com.Inovatech.Java.Inovatech.service.StatusCacheService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteRepository clienteRepository;
    private final StatusCacheService statusCacheService;

    public PedidoController(PedidoService pedidoService, ClienteRepository clienterepository, StatusCacheService statusCacheService) {
        this.pedidoService = pedidoService;
        this.clienteRepository = clienterepository;
        this.statusCacheService = statusCacheService;
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

        // Busca os PedidoHasProduto do cliente
        List<PedidoHasProduto> pedidoHasProdutos = pedidoService.findByPedido_Cliente_idCliente(cliente.getIdCliente());

        // Adiciona as listas ao modelo para Thymeleaf
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("pedidoHasProdutos", pedidoHasProdutos);
        model.addAttribute("content", "meuspedidos");
        return "layouts/main"; // Retorna o layout principal



    }
    @GetMapping("/meuspedidos/{idPedido}")
    public String detalhes(@PathVariable Integer idPedido, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Cliente cliente = clienteRepository.findByEmailCliente(email);

        // Buscar todos os pedidos do cliente
        List<Pedido> pedidos = pedidoService.findByCliente_idCliente(cliente.getIdCliente());

        // Encontrar o pedido específico com idPedido
        Pedido pedido = pedidos.stream()
                .filter(p -> p.getIdPedido().equals(idPedido))
                .findFirst()
                .orElse(null);

        if (pedido == null) {
            return "redirect:/"; // Redireciona para a página inicial se o pedido não for encontrado
        }

        // Agora buscamos o StatusCache baseado no objeto Pedido
        List<StatusCache> statuscache = statusCacheService.getStatusByPedido(pedido);

        // Adicionando os atributos ao modelo
        model.addAttribute("statuscache", statuscache);
        model.addAttribute("pedido", pedido);
        model.addAttribute("content", "detalhes");

        return "layouts/main";  // Retorna a view com os detalhes
    }


}
