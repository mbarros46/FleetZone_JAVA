
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.MotoDTO;
import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patios")
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    public List<Patio> listar() {
        return patioRepository.findAll();
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
