package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String salvarPatio(@Valid @ModelAttribute("patio") Patio patio, 
                              BindingResult result, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Novo Pátio");
            model.addAttribute("action", "/patios/salvar");
            return "patio-form";
        }
        
        patioRepository.save(patio);
        redirectAttributes.addFlashAttribute("successMessage", "Pátio cadastrado com sucesso!");
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
    public String atualizarPatio(@PathVariable Long id, 
                                @Valid @ModelAttribute("patio") Patio patio, 
                                BindingResult result, 
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Pátio");
            model.addAttribute("action", "/patios/atualizar/" + id);
            return "patio-form";
        }
        
        patio.setId(id);
        patioRepository.save(patio);
        redirectAttributes.addFlashAttribute("successMessage", "Pátio atualizado com sucesso!");
        return "redirect:/patios";
    }

    // Exibir detalhes do pátio
    @GetMapping("/{id}")
    public String detalhesPatio(@PathVariable Long id, Model model) {
        Patio patio = patioRepository.findById(id).orElseThrow();
        model.addAttribute("patio", patio);
        return "patio-detalhes";
    }

    // Excluir pátio
    @GetMapping("/excluir/{id}")
    public String excluirPatio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Patio patio = patioRepository.findById(id).orElseThrow();
            patioRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Pátio '" + patio.getNome() + "' excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Erro ao excluir pátio. Verifique se não há motos associadas.");
        }
        return "redirect:/patios";
    }
}