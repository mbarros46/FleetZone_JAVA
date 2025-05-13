package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.dto.MotoDTO;
import com.fiap.fleetzone.repository.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(motoRepository.findAll());
    }

    @GetMapping("/paginado")
    @Cacheable("motos")
    public Page<MotoDTO> listarPaginado(Pageable pageable) {
        return motoRepository.findAll(pageable).map(MotoDTO::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscar(@PathVariable Long id) {
        Optional<Moto> moto = motoRepository.findById(id);
        return moto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Moto> cadastrar(@RequestBody @Valid Moto moto) {
        Moto salvo = motoRepository.save(moto);
        return ResponseEntity.created(URI.create("/motos/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody @Valid Moto moto) {
        if (!motoRepository.existsById(id)) return ResponseEntity.notFound().build();
        moto.setId(id);
        return ResponseEntity.ok(motoRepository.save(moto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!motoRepository.existsById(id)) return ResponseEntity.notFound().build();
        motoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
