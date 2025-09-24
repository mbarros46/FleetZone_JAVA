package com.fiap.fleetzone.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 200)
    private String endereco;
    
    @Column(length = 100)
    private String cidade;
    
    @Column(length = 2)
    private String estado;
    
    @Column(length = 10)
    private String cep;
    
    @Column(length = 20)
    private String telefone;
    
    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private Boolean ativa = true;

    @Column(length = 20)
    private String codigo;

    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL)
    private List<Patio> patios;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public boolean isAtiva() {
        return ativa != null && ativa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Patio> getPatios() {
        return patios;
    }

    public void setPatios(List<Patio> patios) {
        this.patios = patios;
    }

    // Métodos auxiliares para o template
    public int getTotalPatios() {
        return patios != null ? patios.size() : 0;
    }

    public int getTotalMotos() {
        return patios != null ? patios.stream().mapToInt(p -> p.getMotos() != null ? p.getMotos().size() : 0).sum() : 0;
    }
}