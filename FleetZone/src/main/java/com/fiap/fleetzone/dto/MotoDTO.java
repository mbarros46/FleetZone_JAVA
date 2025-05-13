package com.fiap.fleetzone.dto;

import com.fiap.fleetzone.model.Moto;

public class MotoDTO {
    private Long id;
    private String modelo;
    private String placa;
    private String status;

    public MotoDTO(Moto moto) {
        this.id = moto.getId();
        this.modelo = moto.getModelo();
        this.placa = moto.getPlaca();
        this.status = moto.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getStatus() {
        return status;
    }
}
