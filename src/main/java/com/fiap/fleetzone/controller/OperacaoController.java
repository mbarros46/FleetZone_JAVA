package com.fiap.fleetzone.controller;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.MotoRepository;
import com.fiap.fleetzone.repository.PatioRepository;
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
    private MotoRepository motoRepository;

    @Autowired
    private PatioRepository patioRepository;

    /**
     * Exibe o formulário de transferência de moto entre pátios
     */
    @GetMapping("/transferir-moto")
    public String exibirFormularioTransferencia(Model model) {
        List<Patio> patios = patioRepository.findAll();
        List<Moto> motos = motoRepository.findAll();
        
        model.addAttribute("patios", patios);
        model.addAttribute("motos", motos);
        
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
            // Validações básicas
            if (motoId == null || patioOrigemId == null || patioDestinoId == null) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Todos os campos obrigatórios devem ser preenchidos.");
                return "redirect:/operacoes/transferir-moto";
            }

            if (patioOrigemId.equals(patioDestinoId)) {
                redirectAttributes.addFlashAttribute("mensagemErro", "O pátio de origem deve ser diferente do pátio de destino.");
                return "redirect:/operacoes/transferir-moto";
            }

            // Buscar entidades
            Optional<Moto> motoOpt = motoRepository.findById(motoId);
            Optional<Patio> patioOrigemOpt = patioRepository.findById(patioOrigemId);
            Optional<Patio> patioDestinoOpt = patioRepository.findById(patioDestinoId);

            if (motoOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Moto não encontrada.");
                return "redirect:/operacoes/transferir-moto";
            }

            if (patioOrigemOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Pátio de origem não encontrado.");
                return "redirect:/operacoes/transferir-moto";
            }

            if (patioDestinoOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("mensagemErro", "Pátio de destino não encontrado.");
                return "redirect:/operacoes/transferir-moto";
            }

            Moto moto = motoOpt.get();
            Patio patioOrigem = patioOrigemOpt.get();
            Patio patioDestino = patioDestinoOpt.get();

            // Validar se a moto está realmente no pátio de origem
            if (!moto.getPatio().getId().equals(patioOrigemId)) {
                redirectAttributes.addFlashAttribute("mensagemErro", 
                    "A moto " + moto.getModelo() + " não está localizada no pátio " + patioOrigem.getNome() + ".");
                return "redirect:/operacoes/transferir-moto";
            }

            // Validar se a moto pode ser transferida (não está alugada)
            if ("ALUGADA".equals(moto.getStatus())) {
                redirectAttributes.addFlashAttribute("mensagemErro", 
                    "Não é possível transferir a moto " + moto.getModelo() + " pois ela está alugada.");
                return "redirect:/operacoes/transferir-moto";
            }

            // Realizar a transferência
            moto.setPatio(patioDestino);
            motoRepository.save(moto);

            // Mensagem de sucesso
            String mensagem = String.format("Moto %s transferida com sucesso do pátio '%s' para o pátio '%s'.", 
                moto.getModelo(), patioOrigem.getNome(), patioDestino.getNome());
            
            if (observacoes != null && !observacoes.trim().isEmpty()) {
                mensagem += " Observações: " + observacoes.trim();
            }

            redirectAttributes.addFlashAttribute("mensagemSucesso", mensagem);
            return "redirect:/operacoes/transferir-moto";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", 
                "Erro interno ao transferir moto: " + e.getMessage());
            return "redirect:/operacoes/transferir-moto";
        }
    }

    /**
     * API para buscar motos por pátio (para AJAX)
     */
    @GetMapping("/api/motos-por-patio/{patioId}")
    @ResponseBody
    public List<Moto> buscarMotosPorPatio(@PathVariable Long patioId) {
        Optional<Patio> patioOpt = patioRepository.findById(patioId);
        if (patioOpt.isPresent()) {
            return motoRepository.findByPatio(patioOpt.get());
        }
        return List.of();
    }
}