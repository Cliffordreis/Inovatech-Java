package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.entity.Pedido;
import com.Inovatech.Java.Inovatech.exception.EstoqueInsuficienteException;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.service.PedidoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;  // Injetando o PedidoService
    private final ClienteRepository clienteRepository;  // Injetando o ClienteRepository

    // Construtor para injeção do PedidoService e ClienteRepository
    public PedidoController(PedidoService pedidoService, ClienteRepository clienteRepository) {
        this.pedidoService = pedidoService;
        this.clienteRepository = clienteRepository;  // Corrigido aqui, agora está injetado corretamente
    }

//    @PostMapping("/pedido/criar")
//    public String criarPedido(@RequestParam Integer produtoId,
//                              @RequestParam int quantidade,
//                              Authentication authentication,
//                              RedirectAttributes redirectAttributes) {
//        try {
//            String email = authentication.getName();  // Obtém o email do cliente autenticado
//
//            // Busca o cliente pelo email usando a instância injetada do ClienteRepository
//            Cliente cliente = clienteRepository.findByEmailCliente(email);  // Corrigido aqui
//            if (cliente == null) {
//                throw new IllegalArgumentException("Cliente não encontrado");
//            }
//
//            Integer clienteId = cliente.getIdCliente();  // Obtém o clienteId do cliente encontrado
//
//            // Cria o pedido, passando o clienteId, produtoId e quantidade
//            Pedido pedido = pedidoService.criarPedido(clienteId, produtoId, quantidade);  // Passa o clienteId
//
//            // Usando o RedirectAttributes para passar o pedido
//            redirectAttributes.addFlashAttribute("pedido", pedido);
//            return "redirect:/";  // Redireciona para o home
//        } catch (EstoqueInsuficienteException e) {  // Captura a EstoqueInsuficienteException
//            redirectAttributes.addFlashAttribute("erro", e.getMessage());
//            return "redirect:/";  // Retorna para a página inicial com erro
//        } catch (Exception e) {  // Captura outras exceções genéricas
//            redirectAttributes.addFlashAttribute("erro", "Erro ao criar pedido: " + e.getMessage());
//            return "redirect:/";  // Retorna para a página inicial com erro
//        }
//    }
}
