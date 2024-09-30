package com.sub.techsub.service;

import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.ProfissionalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfissionalService {

    @Autowired
    ProfissionalRepository profissionalRepository;

    @Autowired
    ServicosService servicosService;

    public Optional<Profissional> buscarPerfilProfissional(Long id){
        log.info("Buscando o profissional por ID ..");
        return profissionalRepository.findById(id);
    }
    public Profissional buscaPorIdProfissional(Long id){
        return profissionalRepository.getReferenceById(id);
    }
    public Long cadastrarProfissional(ProfissionalRequest profissionalRequest){
        log.info("Cadastrando um novo profissional ...");
        Profissional profissional = new Profissional(profissionalRequest);
        profissional.setServico(servicosService.buscaPorIdServico(profissionalRequest.getServico()));
        return profissionalRepository.save(profissional).getId();
    }

    public List<Profissional> listarTodos() {
        log.info("Buscando todos os Profissionais na base");
        return profissionalRepository.findAll();
    }

    public void atualizaProfissional(Profissional profissional){
        log.info("Atualizando o Profissional {} na base", profissional);
        profissionalRepository.save(profissional);
    }



}
