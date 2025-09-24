package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patios")
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    public String listarPatios(Model model) {
        java.util.List<Patio> patios = patioRepository.findAll();
        model.addAttribute("patios", patios);
        return "patios";
    }

    // Exibir formulário de novo pátio
    @GetMapping("/novo")
    public String novoPatioForm(Model model) {
        model.addAttribute("patio", new Patio());
        model.addAttribute("titulo", "Novo Pátio");
        model.addAttribute("action", "/patios/salvar");
        return "patio-form";
    }

    // Salvar novo pátio
    @PostMapping("/salvar")
    public String salvarPatio(@ModelAttribute("patio") Patio patio) {
        patioRepository.save(patio);
        return "redirect:/patios";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarPatioForm(@PathVariable Long id, Model model) {
        Patio patio = patioRepository.findById(id).orElseThrow();
        model.addAttribute("patio", patio);
        model.addAttribute("titulo", "Editar Pátio");
        model.addAttribute("action", "/patios/atualizar/" + id);
        return "patio-form";
    }

    // Atualizar pátio
    @PostMapping("/atualizar/{id}")
    public String atualizarPatio(@PathVariable Long id, @ModelAttribute("patio") Patio patio) {
        patio.setId(id);
        patioRepository.save(patio);
        return "redirect:/patios";
    }

    // Excluir pátio
    @GetMapping("/excluir/{id}")
    public String excluirPatio(@PathVariable Long id) {
        patioRepository.deleteById(id);
        return "redirect:/patios";
    }
}