package com.fiap.fleetzone.dto;

import java.util.List;

public class PatioReportDTO {
    private final List<PatioRelatorioDTO> relatorio;
    private final long totalGeralMotos;
    private final long totalGeralDisponiveis;
    private final long totalGeralManutencao;
    private final long totalGeralAlugadas;

    public PatioReportDTO(List<PatioRelatorioDTO> relatorio, long totalGeralMotos,
                          long totalGeralDisponiveis, long totalGeralManutencao,
                          long totalGeralAlugadas) {
        this.relatorio = relatorio;
        this.totalGeralMotos = totalGeralMotos;
        this.totalGeralDisponiveis = totalGeralDisponiveis;
        this.totalGeralManutencao = totalGeralManutencao;
        this.totalGeralAlugadas = totalGeralAlugadas;
    }

    public List<PatioRelatorioDTO> getRelatorio() { return relatorio; }
    public long getTotalGeralMotos() { return totalGeralMotos; }
    public long getTotalGeralDisponiveis() { return totalGeralDisponiveis; }
    public long getTotalGeralManutencao() { return totalGeralManutencao; }
    public long getTotalGeralAlugadas() { return totalGeralAlugadas; }
}
