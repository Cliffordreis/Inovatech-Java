package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final ClienteRepository clienteRepository;

    // Injeta o repositório
    public GlobalModelAttributes(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        // Obtém a autenticação atual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {
            // Obtém os detalhes do usuário autenticado
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            String email = principal.getUsername();

            // Busca o cliente no banco de dados pelo email
            Cliente cliente = clienteRepository.findByEmailCliente(email);

            // Adiciona o nome do cliente ao modelo, se encontrado
            if (cliente != null) {
                model.addAttribute("nomeCliente", cliente.getNomeCliente());
            }
        } else {
            // Define um valor padrão para quando o usuário não está autenticado
            model.addAttribute("nomeCliente", null);
        }
    }
}

