package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.entity.CarrinhoItem;
import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.service.PedidoService; // Importando o serviço
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository; // Importando o repositório Cliente
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired; // Adicionando a importação
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder; // Para pegar o usuário autenticado
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private PedidoService pedidoService; // Injetando o PedidoService

    @Autowired
    private ClienteRepository clienteRepository; // Injetando o ClienteRepository

    @GetMapping
    public String visualizarCarrinho(HttpSession session, Model model) {
        // Recupera o carrinho da sessão
        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new ArrayList<>(); // Cria um carrinho vazio, se não existir
            session.setAttribute("carrinho", carrinho);
        }

        // Calcula o total do carrinho
        BigDecimal total = carrinho.stream()
                .map(CarrinhoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Adiciona os atributos necessários ao modelo
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", total);

        // Define o conteúdo para o layout principal
        model.addAttribute("content", "carrinho"); // Aqui indica que o fragmento "carrinho" será carregado no layout

        return "layouts/main"; // Retorna o layout principal
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam Integer produtoId,
                                   @RequestParam String nome,
                                   @RequestParam BigDecimal preco,
                                   @RequestParam int quantidade,
                                   HttpSession session) {
        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        CarrinhoItem itemExistente = carrinho.stream()
                .filter(item -> item.getProdutoId().equals(produtoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
            itemExistente.setSubtotal(itemExistente.getPrecoUnitario()
                    .multiply(BigDecimal.valueOf(itemExistente.getQuantidade())));
        } else {
            CarrinhoItem novoItem = new CarrinhoItem();
            novoItem.setProdutoId(produtoId);
            novoItem.setNome(nome);
            novoItem.setPrecoUnitario(preco);
            novoItem.setQuantidade(quantidade);
            novoItem.setSubtotal(preco.multiply(BigDecimal.valueOf(quantidade)));
            carrinho.add(novoItem);
        }

        return "redirect:/carrinho";
    }

    @PostMapping("/remover")
    public String removerProduto(@RequestParam Integer produtoId, HttpSession session) {
        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.removeIf(item -> item.getProdutoId().equals(produtoId));
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/alterarQuantidade")
    public String alterarQuantidade(@RequestParam Integer produtoId,
                                    @RequestParam String quantidade,
                                    HttpSession session) {
        // Recupera o carrinho da sessão
        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        // Busca o item no carrinho
        CarrinhoItem item = carrinho.stream()
                .filter(i -> i.getProdutoId().equals(produtoId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            if ("increase".equals(quantidade)) {
                item.setQuantidade(item.getQuantidade() + 1); // Aumenta a quantidade
            } else if ("decrease".equals(quantidade) && item.getQuantidade() > 1) {
                item.setQuantidade(item.getQuantidade() - 1); // Diminui a quantidade (não permite abaixo de 1)
            }

            // Recalcula o subtotal
            item.setSubtotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    @PostMapping("/finalizar")
    public String finalizarPedido(HttpSession session) {
        // Obtém o email do usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Aqui é o email do usuário

        // Busca o cliente no banco de dados com o email
        Cliente cliente = clienteRepository.findByEmailCliente(email); // Agora utilizando o findByEmail

        if (cliente == null) {
            return "redirect:/carrinho?erro=Cliente não encontrado";
        }

        List<CarrinhoItem> carrinho = (List<CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.isEmpty()) {
            return "redirect:/carrinho?erro=Carrinho está vazio";
        }

        try {
            // Chama o serviço para finalizar o pedido, passando o clienteId
            pedidoService.finalizarPedido(carrinho, cliente.getIdCliente()); // Passando o clienteId

            // Limpa o carrinho após finalizar
            session.removeAttribute("carrinho");
            return "redirect:/carrinho?sucesso=Pedido finalizado com sucesso";

        } catch (Exception e) {
            return "redirect:/carrinho?erro=" + e.getMessage();
        }
    }

}
