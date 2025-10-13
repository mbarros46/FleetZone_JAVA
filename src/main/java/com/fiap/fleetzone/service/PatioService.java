package com.fiap.fleetzone.service;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatioService {
    private final PatioRepository patios;

    public PatioService(PatioRepository patios) { this.patios = patios; }

    @Transactional(readOnly = true)
    public List<Patio> listar() { return patios.findAll(); }

    @Transactional
    public Patio salvar(Patio p) { return patios.save(p); }

    @Transactional
    public void excluir(Long id) { patios.deleteById(id); }

    @Transactional(readOnly = true)
    public com.fiap.fleetzone.dto.PatioReportDTO gerarRelatorioMotosPorPatio() {
    List<com.fiap.fleetzone.model.Patio> patiosList = patios.findAll();
    List<com.fiap.fleetzone.dto.PatioRelatorioDTO> relatorio = patiosList.stream()
        .map(patio -> {
            long totalMotos = patio.getMotos() != null ? patio.getMotos().size() : 0;
            long motosDisponiveis = patio.getMotos() != null ?
                patio.getMotos().stream().filter(m -> "DISPONIVEL".equals(m.getStatus())).count() : 0;
            long motosManutencao = patio.getMotos() != null ?
                patio.getMotos().stream().filter(m -> "MANUTENCAO".equals(m.getStatus())).count() : 0;
            long motosAlugadas = patio.getMotos() != null ?
                patio.getMotos().stream().filter(m -> "ALUGADA".equals(m.getStatus())).count() : 0;

            return new com.fiap.fleetzone.dto.PatioRelatorioDTO(patio.getId(), patio.getNome(),
                totalMotos, motosDisponiveis, motosManutencao, motosAlugadas, patio.getLocalizacao());
        })
        .toList();

    long totalGeralMotos = relatorio.stream().mapToLong(com.fiap.fleetzone.dto.PatioRelatorioDTO::getTotalMotos).sum();
    long totalGeralDisponiveis = relatorio.stream().mapToLong(com.fiap.fleetzone.dto.PatioRelatorioDTO::getMotosDisponiveis).sum();
    long totalGeralManutencao = relatorio.stream().mapToLong(com.fiap.fleetzone.dto.PatioRelatorioDTO::getMotosManutencao).sum();
    long totalGeralAlugadas = relatorio.stream().mapToLong(com.fiap.fleetzone.dto.PatioRelatorioDTO::getMotosAlugadas).sum();

    return new com.fiap.fleetzone.dto.PatioReportDTO(relatorio, totalGeralMotos, totalGeralDisponiveis,
        totalGeralManutencao, totalGeralAlugadas);
    }
}
