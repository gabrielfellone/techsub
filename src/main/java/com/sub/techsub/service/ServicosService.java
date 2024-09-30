package com.sub.techsub.service;

import com.sub.techsub.entity.Servico;
import com.sub.techsub.repository.ServicosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServicosService {

    @Autowired
    ServicosRepository servicosRepository;

    public Servico buscaPorIdServico(Long id){
        log.info("Buscando o servico por ID ..");
        return servicosRepository.getReferenceById(id);
    }


}
