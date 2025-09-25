package com.fiap.fleetzone.dto;

public class PatioRelatorioDTO {
    private Long id;
    private String nome;
    private long totalMotos;
    private long motosDisponiveis;
    private long motosManutencao;
    private long motosAlugadas;
    private String localizacao;

    // Construtor original para compatibilidade
    public PatioRelatorioDTO(Long id, String nome, int quantidadeMotos) {
        this.id = id;
        this.nome = nome;
        this.totalMotos = quantidadeMotos;
        this.motosDisponiveis = 0;
        this.motosManutencao = 0;
        this.motosAlugadas = 0;
        this.localizacao = "";
    }

    // Construtor completo para relatório
    public PatioRelatorioDTO(Long id, String nome, long totalMotos, 
                             long motosDisponiveis, long motosManutencao, 
                             long motosAlugadas, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.totalMotos = totalMotos;
        this.motosDisponiveis = motosDisponiveis;
        this.motosManutencao = motosManutencao;
        this.motosAlugadas = motosAlugadas;
        this.localizacao = localizacao != null ? localizacao : "";
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeMotos() {
        return (int) totalMotos;
    }

    public long getTotalMotos() {
        return totalMotos;
    }

    public long getMotosDisponiveis() {
        return motosDisponiveis;
    }

    public long getMotosManutencao() {
        return motosManutencao;
    }

    public long getMotosAlugadas() {
        return motosAlugadas;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public double getPercentualDisponivel() {
        return totalMotos > 0 ? (motosDisponiveis * 100.0 / totalMotos) : 0;
    }

    public double getPercentualManutencao() {
        return totalMotos > 0 ? (motosManutencao * 100.0 / totalMotos) : 0;
    }

    public double getPercentualAlugadas() {
        return totalMotos > 0 ? (motosAlugadas * 100.0 / totalMotos) : 0;
    }
}
