package com.fiap.fleetzone.repository;

import com.fiap.fleetzone.model.MotoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MotoMovimentoRepository extends JpaRepository<MotoMovimento, Long> {
    
    // Buscar movimentos por placa
    List<MotoMovimento> findByPlacaOrderByDataMovimentoDesc(String placa);
    
    // Buscar movimentos por pátio
    List<MotoMovimento> findByPatioIdOrderByDataMovimentoDesc(Long patioId);
    
    // Buscar movimentos por período
    @Query("SELECT m FROM MotoMovimento m WHERE m.dataMovimento BETWEEN :inicio AND :fim ORDER BY m.dataMovimento DESC")
    List<MotoMovimento> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    // Buscar último movimento de uma moto
    Optional<MotoMovimento> findFirstByPlacaOrderByDataMovimentoDesc(String placa);
    
    // Buscar movimentos por tipo
    List<MotoMovimento> findByTipoMovimentoOrderByDataMovimentoDesc(String tipoMovimento);
    
    // Contar movimentos por pátio
    @Query("SELECT COUNT(m) FROM MotoMovimento m WHERE m.patioId = :patioId")
    Long countByPatioId(@Param("patioId") Long patioId);
    
    // Buscar movimentos recentes (últimas 24 horas)
    @Query("SELECT m FROM MotoMovimento m WHERE m.dataMovimento >= :dataInicio ORDER BY m.dataMovimento DESC")
    List<MotoMovimento> findMovimentosRecentes(@Param("dataInicio") LocalDateTime dataInicio);
}
