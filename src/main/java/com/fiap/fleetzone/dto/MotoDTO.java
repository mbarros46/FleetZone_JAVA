
package com.fiap.fleetzone.dto;

import com.fiap.fleetzone.model.Moto;
import jakarta.validation.constraints.NotBlank;

public class MotoDTO {

    private Long id;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "Placa é obrigatória")
    private String placa;

    private String status;

    public MotoDTO() {}

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

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
