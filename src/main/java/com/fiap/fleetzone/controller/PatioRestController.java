package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import com.fiap.fleetzone.dto.PatioRelatorioDTO;
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
@RequestMapping("/api/patios")
@Tag(name = "Pátios", description = "API para gestão de pátios")
public class PatioRestController {

    @Autowired
    private PatioRepository patioRepository;

    @Operation(summary = "Listar todos os pátios", description = "Retorna uma lista com todos os pátios cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pátios retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Patio>> listar() {
        List<Patio> patios = patioRepository.findAll();
        return ResponseEntity.ok(patios);
    }

    @Operation(summary = "Buscar pátio por ID", description = "Retorna um pátio específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pátio encontrado"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Patio> buscar(
            @Parameter(description = "ID do pátio", example = "1")
            @PathVariable Long id) {
        Optional<Patio> patio = patioRepository.findById(id);
        return patio.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cadastrar novo pátio", description = "Cadastra um novo pátio no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pátio cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Patio> cadastrar(
            @Parameter(description = "Dados do pátio a ser cadastrado")
            @RequestBody @Valid Patio patio) {
        Patio salvo = patioRepository.save(patio);
        return ResponseEntity.created(URI.create("/api/patios/" + salvo.getId())).body(salvo);
    }

    @Operation(summary = "Atualizar pátio", description = "Atualiza os dados de um pátio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pátio atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Patio> atualizar(
            @Parameter(description = "ID do pátio", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do pátio")
            @RequestBody @Valid Patio patio) {
        if (!patioRepository.existsById(id)) return ResponseEntity.notFound().build();
        patio.setId(id);
        Patio atualizado = patioRepository.save(patio);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Remover pátio", description = "Remove um pátio do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pátio removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID do pátio", example = "1")
            @PathVariable Long id) {
        if (!patioRepository.existsById(id)) return ResponseEntity.notFound().build();
        patioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Relatório de motos por pátio", description = "Retorna um relatório com a quantidade de motos em cada pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    })
    @GetMapping("/relatorio")
    public ResponseEntity<List<PatioRelatorioDTO>> relatorioMotosPorPatio() {
        List<Patio> patios = patioRepository.findAll();
        List<PatioRelatorioDTO> relatorio = patios.stream()
                .map(p -> new PatioRelatorioDTO(p.getId(), p.getNome(), p.getMotos() != null ? p.getMotos().size() : 0))
                .toList();
        return ResponseEntity.ok(relatorio);
    }
}