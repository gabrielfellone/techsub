package com.sub.techsub.controller;

import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.service.ProfissionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/profissional")
@CrossOrigin
public class ProfissionalController {

    @Autowired
    ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(@RequestBody ProfissionalRequest profissional) {
        log.info("Cadastrando um novo profissional... {}", profissional);
        return ResponseEntity.status(CREATED).body(profissionalService.cadastrarProfissional(profissional));
    }

    @GetMapping
    public List<Profissional> listarTodos(){
        log.info("Listando todos os profissionais");
        return profissionalService.listarTodos();
    }

    @GetMapping("/perfil/{id}")
    public Optional<Profissional> buscarPerfilProfissional(@PathVariable("id") Long id){
        log.info("Mostrando o perfil do usuario {} ", id);
        return profissionalService.buscarPerfilProfissional(id);
    }

}
