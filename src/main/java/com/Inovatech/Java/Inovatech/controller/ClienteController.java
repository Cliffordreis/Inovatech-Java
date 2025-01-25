package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.model.Cliente;
import com.Inovatech.Java.Inovatech.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    private final CadastroService cadastroService;  // Serviço de cadastro para salvar o cliente

    @Autowired
    public ClienteController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("cliente", new Cliente()); // Instância vazia de Cliente
        model.addAttribute("content", "cadastro"); // Especifica o conteúdo do formulário
        return "layouts/main"; // Retorna o layout principal
    }

    @PostMapping("/cadastro")
    public String cadastrarCliente(Cliente cliente) {
        cadastroService.cadastrarNovoCliente(cliente); // Chama o serviço para cadastrar com a senha criptografada
        return "redirect:/login"; // Redireciona para a página de login após o cadastro
    }


}
