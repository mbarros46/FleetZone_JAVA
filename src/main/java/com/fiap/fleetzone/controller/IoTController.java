package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.dto.IoTDataDTO;
import com.fiap.fleetzone.model.MotoMovimento;
import com.fiap.fleetzone.service.IoTIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/iot")
@CrossOrigin(origins = "*")
public class IoTController {
    
    @Autowired
    private IoTIntegrationService iotService;
    
    /**
     * Endpoint para receber dados do sistema IoT
     * POST /api/iot/movimento
     */
    @PostMapping("/movimento")
    public ResponseEntity<?> receberDadosIoT(@RequestBody IoTDataDTO iotData) {
        try {
            System.out.println("📡 Recebendo dados IoT: " + iotData);
            MotoMovimento movimento = iotService.processarDadosIoT(iotData);
            return ResponseEntity.ok(movimento);
        } catch (Exception e) {
            System.err.println("❌ Erro ao processar dados IoT: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao processar dados: " + e.getMessage());
        }
    }
    
    /**
     * Simular entrada de dados IoT (para testes)
     * POST /api/iot/simular
     */
    @PostMapping("/simular")
    public ResponseEntity<?> simularDadosIoT(
            @RequestParam String placa,
            @RequestParam Long patioId,
            @RequestParam String endereco) {
        try {
            MotoMovimento movimento = iotService.simularDadosIoT(placa, patioId, endereco);
            return ResponseEntity.ok(movimento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro na simulação: " + e.getMessage());
        }
    }
    
    /**
     * Buscar histórico de movimentos de uma moto
     * GET /api/iot/historico/{placa}
     */
    @GetMapping("/historico/{placa}")
    public ResponseEntity<List<IoTDataDTO>> buscarHistorico(@PathVariable String placa) {
        List<IoTDataDTO> historico = iotService.buscarHistoricoMoto(placa);
        return ResponseEntity.ok(historico);
    }
    
    /**
     * Buscar movimentos de um pátio
     * GET /api/iot/patio/{patioId}
     */
    @GetMapping("/patio/{patioId}")
    public ResponseEntity<List<IoTDataDTO>> buscarMovimentosPatio(@PathVariable Long patioId) {
        List<IoTDataDTO> movimentos = iotService.buscarMovimentosPatio(patioId);
        return ResponseEntity.ok(movimentos);
    }
    
    /**
     * Buscar último movimento de uma moto
     * GET /api/iot/ultimo/{placa}
     */
    @GetMapping("/ultimo/{placa}")
    public ResponseEntity<?> buscarUltimoMovimento(@PathVariable String placa) {
        Optional<IoTDataDTO> ultimoMovimento = iotService.buscarUltimoMovimento(placa);
        if (ultimoMovimento.isPresent()) {
            return ResponseEntity.ok(ultimoMovimento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Buscar movimentos recentes (últimas 24 horas)
     * GET /api/iot/recentes
     */
    @GetMapping("/recentes")
    public ResponseEntity<List<IoTDataDTO>> buscarMovimentosRecentes() {
        List<IoTDataDTO> movimentos = iotService.buscarMovimentosRecentes();
        return ResponseEntity.ok(movimentos);
    }
    
    /**
     * Endpoint de teste para verificar se a API está funcionando
     * GET /api/iot/test
     */
    @GetMapping("/test")
    public ResponseEntity<String> testeEndpoint() {
        return ResponseEntity.ok("🚀 API IoT FleetZone funcionando! " + LocalDateTime.now());
    }
}
