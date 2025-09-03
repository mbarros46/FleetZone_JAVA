
package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.MotoDTO;
import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.repository.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private com.fiap.fleetzone.repository.PatioRepository patioRepository;


    @GetMapping
    public String listarMotos(org.springframework.ui.Model model) {
        java.util.List<Moto> motos = motoRepository.findAll();
        model.addAttribute("motos", motos);
        return "motos";
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> buscar(@PathVariable Long id) {
        Optional<Moto> moto = motoRepository.findById(id);
        return moto.map(value -> ResponseEntity.ok(new MotoDTO(value)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MotoDTO> cadastrar(@RequestBody @Valid MotoDTO motoDTO) {
        Moto moto = new Moto();
        moto.setModelo(motoDTO.getModelo());
        moto.setPlaca(motoDTO.getPlaca());
        moto.setStatus(motoDTO.getStatus());

        Moto salvo = motoRepository.save(moto);
        return ResponseEntity.created(URI.create("/motos/" + salvo.getId())).body(new MotoDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO motoDTO) {
        Optional<Moto> existente = motoRepository.findById(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        Moto moto = existente.get();
        moto.setModelo(motoDTO.getModelo());
        moto.setPlaca(motoDTO.getPlaca());
        moto.setStatus(motoDTO.getStatus());

        Moto atualizado = motoRepository.save(moto);
        return ResponseEntity.ok(new MotoDTO(atualizado));
    }

    // Movimentação de moto entre pátios
    @PutMapping("/{id}/mover")
    public ResponseEntity<MotoDTO> moverMoto(@PathVariable Long id, @RequestParam Long patioId) {
        Optional<Moto> motoOpt = motoRepository.findById(id);
        Optional<com.fiap.fleetzone.model.Patio> patioOpt = patioRepository.findById(patioId);
        if (motoOpt.isEmpty() || patioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Moto moto = motoOpt.get();
        moto.setPatio(patioOpt.get());
        Moto atualizado = motoRepository.save(moto);
        return ResponseEntity.ok(new MotoDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!motoRepository.existsById(id)) return ResponseEntity.notFound().build();
        motoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
