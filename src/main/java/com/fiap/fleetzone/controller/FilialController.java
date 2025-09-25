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

import java.util.List;

@Controller
@RequestMapping("/filiais")
public class FilialController extends BaseController {

    @Autowired
    private FilialRepository filialRepository;
    
    @Autowired
    private PatioRepository patioRepository;
    
    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public String listarFiliais(Model model) {
        List<Filial> filiais = filialRepository.findAll();
        
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
        prepareFormModel(model, new Filial(), "Nova " + getEntityType(), "/filiais/salvar");
        return "filial-form";
    }

    // Salvar nova filial
    @PostMapping("/salvar")
    public String salvarFilial(@Valid @ModelAttribute("filial") Filial filial, 
                               BindingResult result, 
                               Model model, 
                               RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Nova " + getEntityType(), "/filiais/salvar")) {
            return "filial-form";
        }
        
        filialRepository.save(filial);
        addSuccessMessage(redirectAttributes, buildCreateSuccessMessage(getEntityType(), filial.getNome()));
        return getRedirectUrl();
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
        prepareEditFormModel(model, filial, "Editar " + getEntityType(), "/filiais/atualizar/" + id);
        return "filial-form";
    }

    // Atualizar filial
    @PostMapping("/atualizar/{id}")
    public String atualizarFilial(@PathVariable Long id, 
                                 @Valid @ModelAttribute("filial") Filial filial, 
                                 BindingResult result, 
                                 Model model, 
                                 RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Editar " + getEntityType(), "/filiais/atualizar/" + id)) {
            return "filial-form";
        }
        
        filial.setId(id);
        filialRepository.save(filial);
        addSuccessMessage(redirectAttributes, buildUpdateSuccessMessage(getEntityType(), filial.getNome()));
        return getRedirectUrl();
    }

    // Excluir filial
    @GetMapping("/excluir/{id}")
    public String excluirFilial(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Filial filial = filialRepository.findById(id).orElseThrow();
            filialRepository.deleteById(id);
            addSuccessMessage(redirectAttributes, buildDeleteSuccessMessage(getEntityType(), filial.getNome()));
        } catch (Exception e) {
            addErrorMessage(redirectAttributes, buildDeleteErrorMessage(getEntityType()));
        }
        return getRedirectUrl();
    }

    @Override
    protected String getEntityName() {
        return "filial";
    }

    @Override
    protected String getEntityType() {
        return "Filial";
    }

    @Override
    protected String getRedirectUrl() {
        return "redirect:/filiais";
    }
}