package com.fiap.fleetzone.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "moto_movimento")
public class MotoMovimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 10)
    private String placa;
    
    @Column(name = "patio_id")
    private Long patioId;
    
    @Column(length = 200)
    private String endereco;
    
    @Column(name = "data_movimento", nullable = false)
    private LocalDateTime dataMovimento;
    
    @Column(length = 50)
    private String tipoMovimento; // ENTRADA, SAIDA, TRANSFERENCIA
    
    @ManyToOne
    @JoinColumn(name = "moto_id")
    private Moto moto;
    
    @ManyToOne
    @JoinColumn(name = "patio_destino_id")
    private Patio patio;
    
    // Construtores
    public MotoMovimento() {}
    
    public MotoMovimento(String placa, Long patioId, String endereco, LocalDateTime dataMovimento) {
        this.placa = placa;
        this.patioId = patioId;
        this.endereco = endereco;
        this.dataMovimento = dataMovimento;
        this.tipoMovimento = "ENTRADA";
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
    
    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }
    
    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }
    
    public String getTipoMovimento() {
        return tipoMovimento;
    }
    
    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }
    
    public Moto getMoto() {
        return moto;
    }
    
    public void setMoto(Moto moto) {
        this.moto = moto;
    }
    
    public Patio getPatio() {
        return patio;
    }
    
    public void setPatio(Patio patio) {
        this.patio = patio;
    }
}
