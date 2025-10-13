package com.fiap.fleetzone.service;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.model.Patio;
import com.fiap.fleetzone.repository.MotoRepository;
import com.fiap.fleetzone.repository.PatioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OperacaoService {
    private final MotoRepository motoRepository;
    private final PatioRepository patioRepository;

    public OperacaoService(MotoRepository motoRepository, PatioRepository patioRepository) {
        this.motoRepository = motoRepository;
        this.patioRepository = patioRepository;
    }

    @Transactional
    public String transferirMoto(Long motoId, Long patioOrigemId, Long patioDestinoId, String observacoes) {
        if (motoId == null || patioOrigemId == null || patioDestinoId == null) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
        }

        if (patioOrigemId.equals(patioDestinoId)) {
            throw new IllegalArgumentException("O pátio de origem deve ser diferente do pátio de destino.");
        }

        Moto moto = motoRepository.findById(motoId).orElseThrow(() -> new IllegalArgumentException("Moto não encontrada."));
        Patio patioOrigem = patioRepository.findById(patioOrigemId).orElseThrow(() -> new IllegalArgumentException("Pátio de origem não encontrado."));
        Patio patioDestino = patioRepository.findById(patioDestinoId).orElseThrow(() -> new IllegalArgumentException("Pátio de destino não encontrado."));

        if (moto.getPatio() == null || !moto.getPatio().getId().equals(patioOrigemId)) {
            throw new IllegalArgumentException("A moto " + moto.getModelo() + " não está localizada no pátio " + patioOrigem.getNome() + ".");
        }

        if ("ALUGADA".equals(moto.getStatus())) {
            throw new IllegalArgumentException("Não é possível transferir a moto " + moto.getModelo() + " pois ela está alugada.");
        }

        moto.setPatio(patioDestino);
        motoRepository.save(moto);

        String mensagem = String.format("Moto %s transferida com sucesso do pátio '%s' para o pátio '%s'.",
                moto.getModelo(), patioOrigem.getNome(), patioDestino.getNome());

        if (observacoes != null && !observacoes.trim().isEmpty()) {
            mensagem += " Observações: " + observacoes.trim();
        }

        return mensagem;
    }

    @Transactional(readOnly = true)
    public List<Moto> buscarMotosPorPatio(Long patioId) {
        return patioRepository.findById(patioId)
                .map(p -> motoRepository.findByPatio(p))
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public List<Patio> listarPatios() { return patioRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<Moto> listarMotos() { return motoRepository.findAll(); }
}
