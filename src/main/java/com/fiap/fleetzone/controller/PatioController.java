package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/patios")
public class PatioController extends BaseController {

    @Autowired
    private com.fiap.fleetzone.service.PatioService patioService;

    @GetMapping
    public String listarPatios(Model model) {
    List<Patio> patios = patioService.listar();
        model.addAttribute("patios", patios);
        return "patios";
    }

    // Exibir formulário de novo pátio
    @GetMapping("/novo")
    public String novoPatioForm(Model model) {
        prepareFormModel(model, new Patio(), "Novo " + getEntityType(), "/patios/salvar");
        return "patio-form";
    }

    // Salvar novo pátio
    @PostMapping("/salvar")
    public String salvarPatio(@Valid @ModelAttribute("patio") Patio patio, 
                              BindingResult result, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Novo " + getEntityType(), "/patios/salvar")) {
            return "patio-form";
        }
        
    patioService.salvar(patio);
        addSuccessMessage(redirectAttributes, buildCreateSuccessMessage(getEntityType(), patio.getNome()));
        return getRedirectUrl();
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarPatioForm(@PathVariable Long id, Model model) {
    Patio patio = patioService.listar().stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
        prepareEditFormModel(model, patio, "Editar " + getEntityType(), "/patios/atualizar/" + id);
        return "patio-form";
    }

    // Atualizar pátio
    @PostMapping("/atualizar/{id}")
    public String atualizarPatio(@PathVariable Long id, 
                                @Valid @ModelAttribute("patio") Patio patio, 
                                BindingResult result, 
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Editar " + getEntityType(), "/patios/atualizar/" + id)) {
            return "patio-form";
        }
        
    patio.setId(id);
    patioService.salvar(patio);
        addSuccessMessage(redirectAttributes, buildUpdateSuccessMessage(getEntityType(), patio.getNome()));
        return getRedirectUrl();
    }

    // Exibir detalhes do pátio
    @GetMapping("/{id}")
    public String detalhesPatio(@PathVariable Long id, Model model) {
        Patio patio = patioService.listar().stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("patio", patio);
        return "patio-detalhes";
    }

    // Excluir pátio
    @GetMapping("/excluir/{id}")
    public String excluirPatio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Patio patio = patioService.listar().stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow();
            patioService.excluir(id);
            addSuccessMessage(redirectAttributes, buildDeleteSuccessMessage(getEntityType(), patio.getNome()));
        } catch (Exception e) {
            addErrorMessage(redirectAttributes, buildDeleteErrorMessage(getEntityType()));
        }
        return getRedirectUrl();
    }

    @Override
    protected String getEntityName() {
        return "patio";
    }

    @Override
    protected String getEntityType() {
        return "Pátio";
    }

    @Override
    protected String getRedirectUrl() {
        return "redirect:/patios";
    }
}