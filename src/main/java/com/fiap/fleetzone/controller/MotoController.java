
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/motos")
public class MotoController extends BaseController {

    @Autowired
    private com.fiap.fleetzone.service.MotoService motoService;

    @Autowired
    private com.fiap.fleetzone.service.PatioService patioService;

    @GetMapping
    public String listarMotos(Model model) {
        List<Moto> motos = motoService.listar();
        model.addAttribute("motos", motos);
        return "motos";
    }

    // Exibir formulário de nova moto
    @GetMapping("/novo")
    public String novaMotoForm(@RequestParam(required = false) Long patioId, Model model) {
        Moto moto = new Moto();
        if (patioId != null) patioService.listar().stream().filter(p -> p.getId().equals(patioId)).findFirst().ifPresent(moto::setPatio);
        prepareFormModel(model, moto, "Nova " + getEntityType(), "/motos/salvar");
        // Adicionar lista de pátios para o formulário
        model.addAttribute("patios", patioService.listar());
        return "moto-form";
    }

    // Salvar nova moto
    @PostMapping("/salvar")
    public String salvarMoto(@Valid @ModelAttribute("moto") Moto moto, 
                            BindingResult result, 
                            Model model, 
                            RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Nova " + getEntityType(), "/motos/salvar")) {
            return "moto-form";
        }
        
        motoService.salvar(moto);
        addSuccessMessage(redirectAttributes, buildCreateSuccessMessage(getEntityType(), moto.getModelo()));
        return getRedirectUrl();
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst().orElseThrow();
        prepareEditFormModel(model, moto, "Editar " + getEntityType(), "/motos/atualizar/" + id);
        return "moto-form";
    }

    // Atualizar moto
    @PostMapping("/atualizar/{id}")
    public String atualizarMoto(@PathVariable Long id, 
                               @Valid @ModelAttribute("moto") Moto moto, 
                               BindingResult result, 
                               Model model, 
                               RedirectAttributes redirectAttributes) {
        if (hasValidationErrors(result, model, "Editar " + getEntityType(), "/motos/atualizar/" + id)) {
            return "moto-form";
        }
        
        moto.setId(id);
        motoService.salvar(moto);
        addSuccessMessage(redirectAttributes, buildUpdateSuccessMessage(getEntityType(), moto.getModelo()));
        return getRedirectUrl();
    }

    // Exibir detalhes da moto
    @GetMapping("/{id}")
    public String detalhesMoto(@PathVariable Long id, Model model) {
        Moto moto = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("moto", moto);
        return "moto-detalhes";
    }

    @GetMapping("/disponveis")
    public String motosDisponiveis(Model model) {
        List<Moto> motos = motoService.listarPorStatus("DISPONIVEL");
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos Disponíveis");
        model.addAttribute("filtro", "disponivel");
        return "motos";
    }

    @GetMapping("/manutencao")
    public String motosManutencao(Model model) {
        List<Moto> motos = motoService.listarPorStatus("MANUTENCAO");
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos em Manutenção");
        model.addAttribute("filtro", "manutencao");
        return "motos";
    }

    // Excluir moto
    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Moto moto = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst().orElseThrow();
            motoService.excluir(id);
            addSuccessMessage(redirectAttributes, 
                buildDeleteSuccessMessage(getEntityType(), moto.getModelo() + " (Placa: " + moto.getPlaca() + ")"));
        } catch (Exception e) {
            addErrorMessage(redirectAttributes, buildDeleteErrorMessage(getEntityType()));
        }
        return getRedirectUrl();
    }

    @Override
    protected String getEntityName() {
        return "moto";
    }

    @Override
    protected String getEntityType() {
        return "Moto";
    }

    @Override
    protected String getRedirectUrl() {
        return "redirect:/motos";
    }
}
