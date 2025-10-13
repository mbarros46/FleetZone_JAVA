package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private PatioService patioService;

    @GetMapping("/motos-por-patio")
    public String relatorioMotosPorPatio(Model model) {
        com.fiap.fleetzone.dto.PatioReportDTO report = patioService.gerarRelatorioMotosPorPatio();
        model.addAttribute("relatorio", report.getRelatorio());
        model.addAttribute("totalGeralMotos", report.getTotalGeralMotos());
        model.addAttribute("totalGeralDisponiveis", report.getTotalGeralDisponiveis());
        model.addAttribute("totalGeralManutencao", report.getTotalGeralManutencao());
        model.addAttribute("totalGeralAlugadas", report.getTotalGeralAlugadas());
        model.addAttribute("totalPatios", report.getRelatorio().size());
        return "relatorios/motos-por-patio";
    }
}