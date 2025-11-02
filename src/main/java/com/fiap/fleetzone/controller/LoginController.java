package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.AuthDTOs.AuthRegisterRequest;
import com.fiap.fleetzone.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "Email ou senha inválidos.");
        }
        
        if (logout != null) {
            model.addAttribute("successMessage", "Logout realizado com sucesso.");
        }
        
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        // show empty form
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam("nome") String nome,
                                 @RequestParam("email") String email,
                                 @RequestParam("senha") String senha,
                                 Model model) {
        try {
            AuthRegisterRequest req = new AuthRegisterRequest();
            req.nome = nome;
            req.email = email;
            req.senha = senha;
            authService.register(req);

            // redirect to login with success message
            model.addAttribute("successMessage", "Registro efetuado com sucesso. Faça login.");
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "register";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
            return "register";
        }
    }
}