package com.Inovatech.Java.Inovatech.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "home");
        return "layouts/main"; // Retorna o layout principal
    }





}
