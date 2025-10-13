package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.MotoDTO;
import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.service.MotoService;
import com.fiap.fleetzone.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motos")
@Tag(name = "Motos", description = "API para gestão de motocicletas")
public class MotoRestController {

        @Autowired
        private MotoService motoService;

        @Autowired
        private PatioService patioService;

    @Operation(summary = "Listar todas as motos", description = "Retorna uma lista com todas as motocicletas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de motos retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<MotoDTO>> listar() {
        List<Moto> motos = motoService.listar();
        List<MotoDTO> motoDTOs = motos.stream()
                .map(MotoDTO::new)
                .toList();
        return ResponseEntity.ok(motoDTOs);
    }

    @Operation(summary = "Buscar moto por ID", description = "Retorna uma motocicleta específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moto encontrada"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> buscar(
            @Parameter(description = "ID da moto", example = "1")
            @PathVariable Long id) {
        Optional<Moto> moto = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst();
        return moto.map(value -> ResponseEntity.ok(new MotoDTO(value)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar nova moto", description = "Cadastra uma nova motocicleta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Moto cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<MotoDTO> cadastrar(
            @Parameter(description = "Dados da moto a ser cadastrada")
            @RequestBody @Valid MotoDTO motoDTO) {
        Moto moto = new Moto();
        moto.setModelo(motoDTO.getModelo());
        moto.setPlaca(motoDTO.getPlaca());
        moto.setStatus(motoDTO.getStatus());

        Moto salvo = motoService.salvar(moto);
        return ResponseEntity.created(URI.create("/api/motos/" + salvo.getId())).body(new MotoDTO(salvo));
    }

    @Operation(summary = "Atualizar moto", description = "Atualiza os dados de uma motocicleta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> atualizar(
            @Parameter(description = "ID da moto", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novos dados da moto")
            @RequestBody @Valid MotoDTO motoDTO) {
        Optional<Moto> existente = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst();
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        Moto moto = existente.get();
        moto.setModelo(motoDTO.getModelo());
        moto.setPlaca(motoDTO.getPlaca());
        moto.setStatus(motoDTO.getStatus());

        Moto atualizado = motoService.salvar(moto);
        return ResponseEntity.ok(new MotoDTO(atualizado));
    }

    @Operation(summary = "Mover moto entre pátios", description = "Move uma motocicleta de um pátio para outro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moto movida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto ou pátio não encontrado")
    })
    @PutMapping("/{id}/mover")
    public ResponseEntity<MotoDTO> moverMoto(
            @Parameter(description = "ID da moto", example = "1")
            @PathVariable Long id,
            @Parameter(description = "ID do pátio de destino", example = "2")
            @RequestParam Long patioId) {
        Optional<Moto> motoOpt = motoService.listar().stream().filter(m -> m.getId().equals(id)).findFirst();
        Optional<com.fiap.fleetzone.model.Patio> patioOpt = patioService.listar().stream().filter(p -> p.getId().equals(patioId)).findFirst();
        
        if (motoOpt.isEmpty() || patioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Moto moto = motoOpt.get();
        moto.setPatio(patioOpt.get());
        Moto atualizado = motoService.salvar(moto);
        return ResponseEntity.ok(new MotoDTO(atualizado));
    }

    @Operation(summary = "Remover moto", description = "Remove uma motocicleta do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Moto removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID da moto", example = "1")
            @PathVariable Long id) {
                if (motoService.listar().stream().noneMatch(m -> m.getId().equals(id))) return ResponseEntity.notFound().build();
                motoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}