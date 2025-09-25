
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
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
@RequestMapping("/motos")
public class MotoController extends BaseController {

    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private com.fiap.fleetzone.repository.PatioRepository patioRepository;

    @GetMapping
    public String listarMotos(Model model) {
        List<Moto> motos = motoRepository.findAll();
        model.addAttribute("motos", motos);
        return "motos";
    }

    // Exibir formulário de nova moto
    @GetMapping("/novo")
    public String novaMotoForm(@RequestParam(required = false) Long patioId, Model model) {
        Moto moto = new Moto();
        if (patioId != null) {
            // Pre-selecionar o pátio se fornecido via parâmetro
            patioRepository.findById(patioId).ifPresent(moto::setPatio);
        }
        prepareFormModel(model, moto, "Nova " + getEntityType(), "/motos/salvar");
        // Adicionar lista de pátios para o formulário
        model.addAttribute("patios", patioRepository.findAll());
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
        
        motoRepository.save(moto);
        addSuccessMessage(redirectAttributes, buildCreateSuccessMessage(getEntityType(), moto.getModelo()));
        return getRedirectUrl();
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id).orElseThrow();
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
        motoRepository.save(moto);
        addSuccessMessage(redirectAttributes, buildUpdateSuccessMessage(getEntityType(), moto.getModelo()));
        return getRedirectUrl();
    }

    // Exibir detalhes da moto
    @GetMapping("/{id}")
    public String detalhesMoto(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id).orElseThrow();
        model.addAttribute("moto", moto);
        return "moto-detalhes";
    }

    @GetMapping("/disponveis")
    public String motosDisponiveis(Model model) {
        List<Moto> motos = motoRepository.findByStatus("DISPONIVEL");
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos Disponíveis");
        model.addAttribute("filtro", "disponivel");
        return "motos";
    }

    @GetMapping("/manutencao")
    public String motosManutencao(Model model) {
        List<Moto> motos = motoRepository.findByStatus("MANUTENCAO");
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos em Manutenção");
        model.addAttribute("filtro", "manutencao");
        return "motos";
    }

    // Excluir moto
    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Moto moto = motoRepository.findById(id).orElseThrow();
            motoRepository.deleteById(id);
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
