package com.fiap.fleetzone.dto;

import java.time.LocalDateTime;

public class IoTDataDTO {
    
    private Long id;
    private String placa;
    private Long patioId;
    private String endereco;
    private LocalDateTime data;
    private String tipoMovimento;
    
    // Construtores
    public IoTDataDTO() {}
    
    public IoTDataDTO(String placa, Long patioId, String endereco, LocalDateTime data) {
        this.placa = placa;
        this.patioId = patioId;
        this.endereco = endereco;
        this.data = data;
        this.tipoMovimento = "ENTRADA";
    }
    
    public IoTDataDTO(Long id, String placa, Long patioId, String endereco, LocalDateTime data) {
        this.id = id;
        this.placa = placa;
        this.patioId = patioId;
        this.endereco = endereco;
        this.data = data;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public Long getPatioId() {
        return patioId;
    }
    
    public void setPatioId(Long patioId) {
        this.patioId = patioId;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public LocalDateTime getData() {
        return data;
    }
    
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    
    public String getTipoMovimento() {
        return tipoMovimento;
    }
    
    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }
    
    @Override
    public String toString() {
        return "IoTDataDTO{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", patioId=" + patioId +
                ", endereco='" + endereco + '\'' +
                ", data=" + data +
                ", tipoMovimento='" + tipoMovimento + '\'' +
                '}';
    }
}
