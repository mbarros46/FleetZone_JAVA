
package com.fiap.fleetzone.repository;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByStatus(String status);
    List<Moto> findByPatio(Patio patio);
    List<Moto> findByPatioId(Long patioId);
    Optional<Moto> findByPlaca(String placa);
}
