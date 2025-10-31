package com.fiap.fleetzone.service;

import com.fiap.fleetzone.dto.IoTDataDTO;
import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.model.MotoMovimento;
import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.MotoMovimentoRepository;
import com.fiap.fleetzone.repository.MotoRepository;
import com.fiap.fleetzone.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IoTIntegrationService {
    
    @Autowired
    private MotoMovimentoRepository motoMovimentoRepository;
    
    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private PatioRepository patioRepository;
    
    /**
     * Processa dados recebidos do sistema IoT
     */
    public MotoMovimento processarDadosIoT(IoTDataDTO iotData) {
        System.out.println("📡 Processando dados IoT: " + iotData);
        
        // Criar movimento
        MotoMovimento movimento = new MotoMovimento();
        movimento.setPlaca(iotData.getPlaca());
        movimento.setPatioId(iotData.getPatioId());
        movimento.setEndereco(iotData.getEndereco());
        movimento.setDataMovimento(iotData.getData());
        movimento.setTipoMovimento(iotData.getTipoMovimento() != null ? iotData.getTipoMovimento() : "ENTRADA");
        
        // Buscar e associar moto
        Optional<Moto> motoOpt = motoRepository.findByPlaca(iotData.getPlaca());
        if (motoOpt.isPresent()) {
            movimento.setMoto(motoOpt.get());
            
            // Atualizar pátio da moto se necessário
            if (iotData.getPatioId() != null) {
                Optional<Patio> patioOpt = patioRepository.findById(iotData.getPatioId());
                if (patioOpt.isPresent()) {
                    movimento.setPatio(patioOpt.get());
                    
                    // Atualizar pátio atual da moto
                    Moto moto = motoOpt.get();
                    if (!patioOpt.get().equals(moto.getPatio())) {
                        moto.setPatio(patioOpt.get());
                        motoRepository.save(moto);
                        movimento.setTipoMovimento("TRANSFERENCIA");
                        System.out.println("🚚 Moto " + iotData.getPlaca() + " transferida para pátio: " + patioOpt.get().getNome());
                    }
                }
            }
        } else {
            System.out.println("⚠️ Moto não encontrada: " + iotData.getPlaca());
        }
        
        // Salvar movimento
        MotoMovimento movimentoSalvo = motoMovimentoRepository.save(movimento);
        System.out.println("✅ Movimento IoT registrado: ID " + movimentoSalvo.getId());
        
        return movimentoSalvo;
    }
    
    /**
     * Simular recebimento de dados IoT (para testes)
     */
    public MotoMovimento simularDadosIoT(String placa, Long patioId, String endereco) {
        IoTDataDTO iotData = new IoTDataDTO(placa, patioId, endereco, LocalDateTime.now());
        return processarDadosIoT(iotData);
    }
    
    /**
     * Buscar histórico de movimentos de uma moto
     */
    public List<IoTDataDTO> buscarHistoricoMoto(String placa) {
        List<MotoMovimento> movimentos = motoMovimentoRepository.findByPlacaOrderByDataMovimentoDesc(placa);
        return movimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar movimentos de um pátio
     */
    public List<IoTDataDTO> buscarMovimentosPatio(Long patioId) {
        List<MotoMovimento> movimentos = motoMovimentoRepository.findByPatioIdOrderByDataMovimentoDesc(patioId);
        return movimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar movimentos recentes (últimas 24 horas)
     */
    public List<IoTDataDTO> buscarMovimentosRecentes() {
        LocalDateTime dataInicio = LocalDateTime.now().minusHours(24);
        List<MotoMovimento> movimentos = motoMovimentoRepository.findMovimentosRecentes(dataInicio);
        return movimentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar último movimento de uma moto
     */
    public Optional<IoTDataDTO> buscarUltimoMovimento(String placa) {
        Optional<MotoMovimento> movimento = motoMovimentoRepository.findFirstByPlacaOrderByDataMovimentoDesc(placa);
        return movimento.map(this::convertToDTO);
    }
    
    /**
     * Converter MotoMovimento para IoTDataDTO
     */
    private IoTDataDTO convertToDTO(MotoMovimento movimento) {
        IoTDataDTO dto = new IoTDataDTO();
        dto.setId(movimento.getId());
        dto.setPlaca(movimento.getPlaca());
        dto.setPatioId(movimento.getPatioId());
        dto.setEndereco(movimento.getEndereco());
        dto.setData(movimento.getDataMovimento());
        dto.setTipoMovimento(movimento.getTipoMovimento());
        return dto;
    }
}
