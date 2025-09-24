
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public String listarMotos(Model model) {
        java.util.List<Moto> motos = motoRepository.findAll();
        model.addAttribute("motos", motos);
        return "motos";
    }

    // Exibir formulário de nova moto
    @GetMapping("/novo")
    public String novaMotoForm(Model model) {
        model.addAttribute("moto", new Moto());
        model.addAttribute("titulo", "Nova Moto");
        model.addAttribute("action", "/motos/salvar");
        return "moto-form";
    }

    // Salvar nova moto
    @PostMapping("/salvar")
    public String salvarMoto(@ModelAttribute("moto") Moto moto) {
        motoRepository.save(moto);
        return "redirect:/motos";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id).orElseThrow();
        model.addAttribute("moto", moto);
        model.addAttribute("titulo", "Editar Moto");
        model.addAttribute("action", "/motos/atualizar/" + id);
        return "moto-form";
    }

    // Atualizar moto
    @PostMapping("/atualizar/{id}")
    public String atualizarMoto(@PathVariable Long id, @ModelAttribute("moto") Moto moto) {
        moto.setId(id);
        motoRepository.save(moto);
        return "redirect:/motos";
    }

    // Exibir detalhes da moto
    @GetMapping("/{id}")
    public String detalhesMoto(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id).orElseThrow();
        model.addAttribute("moto", moto);
        return "moto-detalhes";
    }

    // Listar motos disponíveis
    @GetMapping("/disponveis")
    public String motosDisponiveis(Model model) {
        // Assumindo que há um campo status na Moto
        // List<Moto> motos = motoRepository.findByStatus("DISPONIVEL");
        java.util.List<Moto> motos = motoRepository.findAll(); // Por enquanto, todas as motos
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos Disponíveis");
        model.addAttribute("filtro", "disponivel");
        return "motos";
    }

    // Listar motos em manutenção
    @GetMapping("/manutencao")
    public String motosManutencao(Model model) {
        // Assumindo que há um campo status na Moto
        // List<Moto> motos = motoRepository.findByStatus("MANUTENCAO");
        java.util.List<Moto> motos = motoRepository.findAll(); // Por enquanto, todas as motos
        model.addAttribute("motos", motos);
        model.addAttribute("titulo", "Motos em Manutenção");
        model.addAttribute("filtro", "manutencao");
        return "motos";
    }

    // Excluir moto
    @GetMapping("/excluir/{id}")
    public String excluirMoto(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos";
    }
}
