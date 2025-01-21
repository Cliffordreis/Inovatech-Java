package com.Inovatech.Java.Inovatech.controller;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.entity.Pedido;
import com.Inovatech.Java.Inovatech.exception.EstoqueInsuficienteException;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.service.PedidoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrincipalController {


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "home");
        return "layouts/main"; // Retorna o layout principal
    }

//    @GetMapping("/carrinho")
//    public String carrinho(Model model) {
//        model.addAttribute("content", "carrinho");
//        return "layouts/main"; // Retorna o layout principal
//    }

}
