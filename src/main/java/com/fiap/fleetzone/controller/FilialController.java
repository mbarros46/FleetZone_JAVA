package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Filial;
import com.fiap.fleetzone.repository.FilialRepository;
import com.fiap.fleetzone.repository.PatioRepository;
import com.fiap.fleetzone.repository.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filiais")
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;
    
    @Autowired
    private PatioRepository patioRepository;
    
    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public String listarFiliais(Model model) {
        java.util.List<Filial> filiais = filialRepository.findAll();
        
        // Estatísticas para o dashboard
        long totalFiliais = filialRepository.count();
        long filiaisAtivas = filiais.stream().mapToLong(f -> f.isAtiva() ? 1L : 0L).sum();
        long totalPatios = patioRepository.count();
        long totalMotos = motoRepository.count();
        
        model.addAttribute("filiais", filiais);
        model.addAttribute("totalFiliais", totalFiliais);
        model.addAttribute("filiaisAtivas", filiaisAtivas);
        model.addAttribute("totalPatios", totalPatios);
        model.addAttribute("totalMotos", totalMotos);
        
        return "filiais-simple";
    }

    // Exibir formulário de nova filial
    @GetMapping("/novo")
    public String novaFilialForm(Model model) {
        model.addAttribute("filial", new Filial());
        model.addAttribute("titulo", "Nova Filial");
        model.addAttribute("action", "/filiais/salvar");
        return "filial-form";
    }

    // Salvar nova filial
    @PostMapping("/salvar")
    public String salvarFilial(@Valid @ModelAttribute("filial") Filial filial, 
                               BindingResult result, 
                               Model model, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nova Filial");
            model.addAttribute("action", "/filiais/salvar");
            return "filial-form";
        }
        
        filialRepository.save(filial);
        redirectAttributes.addFlashAttribute("successMessage", "Filial cadastrada com sucesso!");
        return "redirect:/filiais";
    }

    // Exibir detalhes da filial
    @GetMapping("/{id}")
    public String detalhesFilial(@PathVariable Long id, Model model) {
        Filial filial = filialRepository.findById(id).orElseThrow();
        model.addAttribute("filial", filial);
        return "filial-detalhes";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarFilialForm(@PathVariable Long id, Model model) {
        Filial filial = filialRepository.findById(id).orElseThrow();
        model.addAttribute("filial", filial);
        model.addAttribute("titulo", "Editar Filial");
        model.addAttribute("action", "/filiais/atualizar/" + id);
        return "filial-form";
    }

    // Atualizar filial
    @PostMapping("/atualizar/{id}")
    public String atualizarFilial(@PathVariable Long id, 
                                 @Valid @ModelAttribute("filial") Filial filial, 
                                 BindingResult result, 
                                 Model model, 
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Filial");
            model.addAttribute("action", "/filiais/atualizar/" + id);
            return "filial-form";
        }
        
        filial.setId(id);
        filialRepository.save(filial);
        redirectAttributes.addFlashAttribute("successMessage", "Filial atualizada com sucesso!");
        return "redirect:/filiais";
    }

    // Excluir filial
    @GetMapping("/excluir/{id}")
    public String excluirFilial(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Filial filial = filialRepository.findById(id).orElseThrow();
            filialRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Filial '" + filial.getNome() + "' excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Erro ao excluir filial. Verifique se não há pátios associados.");
        }
        return "redirect:/filiais";
    }
}