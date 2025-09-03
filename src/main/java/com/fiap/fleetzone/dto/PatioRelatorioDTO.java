package com.fiap.fleetzone.dto;

public class PatioRelatorioDTO {
    private Long id;
    private String nome;
    private int quantidadeMotos;

    public PatioRelatorioDTO(Long id, String nome, int quantidadeMotos) {
        this.id = id;
        this.nome = nome;
        this.quantidadeMotos = quantidadeMotos;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeMotos() {
        return quantidadeMotos;
    }
}
