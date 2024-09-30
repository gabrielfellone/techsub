package com.sub.techsub.controller;

import com.sub.techsub.controller.resources.requests.EstabelecimentoRequest;
import com.sub.techsub.controller.resources.responses.EstabelecimentoResource;
import com.sub.techsub.service.EstabelecimentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/estabelecimento")
@CrossOrigin
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(@RequestBody EstabelecimentoRequest estabelecimento) {
        log.info("Cadastrando um novo estabelecimento... {}", estabelecimento);
        return ResponseEntity.status(CREATED).body(estabelecimentoService.cadastrarEstabelecimento(estabelecimento));
    }

    @GetMapping
    public List<EstabelecimentoResource> listarTodos(){
        log.info("Listando todos os Estabelecimento");
        return estabelecimentoService.listarTodos();
    }
    @GetMapping("/filtro")
    public List<EstabelecimentoResource> filtrarEstabelecimento(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) String servicoOferecido,
            @RequestParam(required = false) Double avaliacaoMin,
            @RequestParam(required = false) Double faixaPrecoMin,
            @RequestParam(required = false) Double faixaPrecoMax) {

        log.info("Buscando estabelecimentos pelos filtros oferecidos ...");
        return estabelecimentoService.filtroEstabelecimento(nome, localizacao, servicoOferecido,
                avaliacaoMin, faixaPrecoMin, faixaPrecoMax);
    }
}
