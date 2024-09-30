package com.sub.techsub.controller;

import com.sub.techsub.controller.resources.requests.AvaliacaoRequest;
import com.sub.techsub.service.AvaliacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/avaliacao")
@CrossOrigin
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<Long> avaliar(@RequestBody AvaliacaoRequest avaliacaoRequest) {
        log.info("Realizando avaliacao... {}", avaliacaoRequest);
        return ResponseEntity.status(CREATED).body(avaliacaoService.realizarAvalicao(avaliacaoRequest));
    }

}
