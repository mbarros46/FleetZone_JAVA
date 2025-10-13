package com.fiap.fleetzone.service;

import com.fiap.fleetzone.model.Filial;
import com.fiap.fleetzone.repository.FilialRepository;
import com.fiap.fleetzone.repository.PatioRepository;
import com.fiap.fleetzone.repository.MotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilialService {
    private final FilialRepository filialRepository;
    private final PatioRepository patioRepository;
    private final MotoRepository motoRepository;

    public FilialService(FilialRepository filialRepository, PatioRepository patioRepository, MotoRepository motoRepository) {
        this.filialRepository = filialRepository;
        this.patioRepository = patioRepository;
        this.motoRepository = motoRepository;
    }

    @Transactional(readOnly = true)
    public List<Filial> listar() { return filialRepository.findAll(); }

    @Transactional(readOnly = true)
    public long contarFiliais() { return filialRepository.count(); }

    @Transactional(readOnly = true)
    public long contarPatios() { return patioRepository.count(); }

    @Transactional(readOnly = true)
    public long contarMotos() { return moto_repository_count(); }

    // wrapper to avoid direct field usage in stream contexts
    private long moto_repository_count() { return motoRepository.count(); }

    @Transactional
    public Filial salvar(Filial f) { return filialRepository.save(f); }

    @Transactional(readOnly = true)
    public Filial buscar(Long id) { return filialRepository.findById(id).orElseThrow(); }

    @Transactional
    public void excluir(Long id) { filialRepository.deleteById(id); }
}
