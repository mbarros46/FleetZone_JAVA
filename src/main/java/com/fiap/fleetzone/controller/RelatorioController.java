package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.PatioRepository;
import com.fiap.fleetzone.repository.MotoRepository;
import com.fiap.fleetzone.dto.PatioRelatorioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private PatioRepository patioRepository;
    
    @Autowired
    private MotoRepository motoRepository;

    @GetMapping("/motos-por-patio")
    public String relatorioMotosPorPatio(Model model) {
        List<Patio> patios = patioRepository.findAll();
        
        // Calcula estatísticas por pátio
        List<PatioRelatorioDTO> relatorio = patios.stream()
            .map(patio -> {
                long totalMotos = patio.getMotos() != null ? patio.getMotos().size() : 0;
                long motosDisponiveis = patio.getMotos() != null ? 
                    patio.getMotos().stream()
                        .filter(moto -> "DISPONIVEL".equals(moto.getStatus()))
                        .count() : 0;
                long motosManutencao = patio.getMotos() != null ? 
                    patio.getMotos().stream()
                        .filter(moto -> "MANUTENCAO".equals(moto.getStatus()))
                        .count() : 0;
                long motosAlugadas = patio.getMotos() != null ? 
                    patio.getMotos().stream()
                        .filter(moto -> "ALUGADA".equals(moto.getStatus()))
                        .count() : 0;
                        
                return new PatioRelatorioDTO(patio.getId(), patio.getNome(), totalMotos, 
                    motosDisponiveis, motosManutencao, motosAlugadas, patio.getLocalizacao());
            })
            .collect(Collectors.toList());

        // Totalizadores gerais
        long totalGeralMotos = relatorio.stream().mapToLong(PatioRelatorioDTO::getTotalMotos).sum();
        long totalGeralDisponiveis = relatorio.stream().mapToLong(PatioRelatorioDTO::getMotosDisponiveis).sum();
        long totalGeralManutencao = relatorio.stream().mapToLong(PatioRelatorioDTO::getMotosManutencao).sum();
        long totalGeralAlugadas = relatorio.stream().mapToLong(PatioRelatorioDTO::getMotosAlugadas).sum();
        
        model.addAttribute("relatorio", relatorio);
        model.addAttribute("totalGeralMotos", totalGeralMotos);
        model.addAttribute("totalGeralDisponiveis", totalGeralDisponiveis);
        model.addAttribute("totalGeralManutencao", totalGeralManutencao);
        model.addAttribute("totalGeralAlugadas", totalGeralAlugadas);
        model.addAttribute("totalPatios", patios.size());
        
        return "relatorios/motos-por-patio";
    }
}