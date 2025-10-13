package com.fiap.fleetzone.service;

import com.fiap.fleetzone.model.Moto;
import com.fiap.fleetzone.repository.MotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotoService {
    private final MotoRepository motos;

    public MotoService(MotoRepository motos) { this.motos = motos; }

    @Transactional(readOnly = true)
    public List<Moto> listar() { return motos.findAll(); }

    @Transactional
    public Moto salvar(Moto m) { return motos.save(m); }

    @Transactional
    public void excluir(Long id) { motos.deleteById(id); }

    @Transactional(readOnly = true)
    public List<Moto> listarPorStatus(String status) { return motos.findByStatus(status); }
}
