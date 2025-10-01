package com.fiap.fleetzone.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        // Se o usuário não está autenticado, redireciona para login
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        model.addAttribute("titulo", "FleetZone - Gestão de Motos");
        return "index";
    }
}