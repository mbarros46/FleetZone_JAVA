package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
// ...existing code...
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/operacoes")
public class OperacaoController {

    @Autowired
    private com.fiap.fleetzone.service.OperacaoService operacaoService;

    /**
     * Exibe o formulário de transferência de moto entre pátios
     */
    @GetMapping("/transferir-moto")
    public String exibirFormularioTransferencia(Model model) {
    model.addAttribute("patios", operacaoService.listarPatios());
    model.addAttribute("motos", operacaoService.listarMotos());
        
        return "operacoes/transferir-moto";
    }

    /**
     * Processa a transferência de moto entre pátios
     */
    @PostMapping("/transferir-moto")
    public String transferirMoto(
            @RequestParam("motoId") Long motoId,
            @RequestParam("patioOrigemId") Long patioOrigemId,
            @RequestParam("patioDestinoId") Long patioDestinoId,
            @RequestParam(value = "observacoes", required = false) String observacoes,
            RedirectAttributes redirectAttributes) {

        try {
            String mensagem = operacaoService.transferirMoto(motoId, patioOrigemId, patioDestinoId, observacoes);
            redirectAttributes.addFlashAttribute("mensagemSucesso", mensagem);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro interno ao transferir moto: " + e.getMessage());
        }

        return "redirect:/operacoes/transferir-moto";
    }

    /**
     * API para buscar motos por pátio (para AJAX)
     */
    @GetMapping("/api/motos-por-patio/{patioId}")
    @ResponseBody
    public List<Moto> buscarMotosPorPatio(@PathVariable Long patioId) {
        return operacaoService.buscarMotosPorPatio(patioId);
    }
}