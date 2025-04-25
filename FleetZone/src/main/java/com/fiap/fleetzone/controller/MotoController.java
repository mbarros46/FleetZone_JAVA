
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.MotoDTO;
import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.MotoRepository;
import com.fiap.fleetzone.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    @Cacheable("motos")
    public List<Moto> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        return motoRepository.findAll(PageRequest.of(page, size, Sort.by("modelo"))).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> getById(@PathVariable Long id) {
        return motoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Moto> getByStatus(@RequestParam String status) {
        return motoRepository.findByStatus(status);
    }

    @PostMapping
    public ResponseEntity<Moto> create(@RequestBody @Valid MotoDTO dto) {
        Patio patio = patioRepository.findById(dto.patioId()).orElse(null);
        if (patio == null) return ResponseEntity.badRequest().build();
        Moto moto = new Moto();
        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
        moto.setStatus(dto.status());
        moto.setPatio(patio);
        return ResponseEntity.ok(motoRepository.save(moto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> update(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        return motoRepository.findById(id)
                .map(m -> {
                    m.setModelo(dto.modelo());
                    m.setPlaca(dto.placa());
                    m.setStatus(dto.status());
                    Patio p = patioRepository.findById(dto.patioId()).orElse(null);
                    m.setPatio(p);
                    return ResponseEntity.ok(motoRepository.save(m));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!motoRepository.existsById(id)) return ResponseEntity.notFound().build();
        motoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
