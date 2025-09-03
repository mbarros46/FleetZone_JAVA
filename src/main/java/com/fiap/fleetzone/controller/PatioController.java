package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import com.fiap.fleetzone.dto.PatioRelatorioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patios")
public class PatioController {
    // Exibir formulário de novo pátio
    @GetMapping("/novo")
    public String novoPatioForm(org.springframework.ui.Model model) {
        model.addAttribute("patio", new com.fiap.fleetzone.model.Patio());
        model.addAttribute("titulo", "Novo Pátio");
        model.addAttribute("action", "/patios/salvar");
        return "patio-form";
    }

    // Salvar novo pátio
    @PostMapping("/salvar")
    public String salvarPatio(@org.springframework.web.bind.annotation.ModelAttribute("patio") com.fiap.fleetzone.model.Patio patio) {
        patioRepository.save(patio);
        return "redirect:/patios";
    }

    // Exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarPatioForm(@PathVariable Long id, org.springframework.ui.Model model) {
        com.fiap.fleetzone.model.Patio patio = patioRepository.findById(id).orElseThrow();
        model.addAttribute("patio", patio);
        model.addAttribute("titulo", "Editar Pátio");
        model.addAttribute("action", "/patios/atualizar/" + id);
        return "patio-form";
    }

    // Atualizar pátio
    @PostMapping("/atualizar/{id}")
    public String atualizarPatio(@PathVariable Long id, @org.springframework.web.bind.annotation.ModelAttribute("patio") com.fiap.fleetzone.model.Patio patio) {
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

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping("/relatorio")
    public List<PatioRelatorioDTO> relatorioMotosPorPatio() {
        List<Patio> patios = patioRepository.findAll();
        return patios.stream()
                .map(p -> new PatioRelatorioDTO(p.getId(), p.getNome(), p.getMotos() != null ? p.getMotos().size() : 0))
                .toList();
    }

    @GetMapping
    public String listarPatios(org.springframework.ui.Model model) {
        java.util.List<Patio> patios = patioRepository.findAll();
        model.addAttribute("patios", patios);
        return "patios";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patio> buscar(@PathVariable Long id) {
        Optional<Patio> patio = patioRepository.findById(id);
        return patio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patio> cadastrar(@RequestBody @Valid Patio patio) {
        Patio salvo = patioRepository.save(patio);
        return ResponseEntity.created(URI.create("/patios/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patio> atualizar(@PathVariable Long id, @RequestBody @Valid Patio patio) {
        if (!patioRepository.existsById(id)) return ResponseEntity.notFound().build();
        patio.setId(id);
        return ResponseEntity.ok(patioRepository.save(patio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!patioRepository.existsById(id)) return ResponseEntity.notFound().build();
        patioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
