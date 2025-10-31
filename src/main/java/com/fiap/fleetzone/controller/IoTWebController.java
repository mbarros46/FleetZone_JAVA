package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.service.IoTIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/iot")
public class IoTWebController {
    
    @Autowired
    private IoTIntegrationService iotService;
    
    /**
     * Página de monitoramento IoT
     */
    @GetMapping("/monitoring")
    public String monitoringPage(Model model) {
        model.addAttribute("pageTitle", "Monitoramento IoT");
        return "iot-monitoring";
    }
    
    /**
     * Página de relatórios IoT
     */
    @GetMapping("/relatorios")
    public String relatoriosPage(Model model) {
        model.addAttribute("pageTitle", "Relatórios IoT");
        // Adicionar dados para relatórios se necessário
        return "iot-relatorios";
    }
}
