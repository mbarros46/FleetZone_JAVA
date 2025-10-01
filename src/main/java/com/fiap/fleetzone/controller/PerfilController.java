package com.fiap.fleetzone.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("nomeUsuario", authentication.getName());
            model.addAttribute("titulo", "Meu Perfil - FleetZone");
        }
        return "perfil";
    }
    
    @GetMapping("/configuracoes")
    public String configuracoes(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("nomeUsuario", authentication.getName());
            model.addAttribute("titulo", "Configurações - FleetZone");
        }
        return "configuracoes";
    }
}