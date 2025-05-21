
package com.fiap.fleetzone.repository;

import com.fiap.fleetzone.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByStatus(String status);
}
