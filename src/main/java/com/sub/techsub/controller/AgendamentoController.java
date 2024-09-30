package com.sub.techsub.controller;

import com.sub.techsub.controller.resources.responses.AgendamentoDisponibilidadeResource;
import com.sub.techsub.controller.resources.requests.AgendamentoRequest;
import com.sub.techsub.controller.resources.responses.AgendamentosRealizados;
import com.sub.techsub.service.AgendamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(value = "/api/agendamento")
@CrossOrigin
public class AgendamentoController {

    @Autowired
    AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Long> realizarAgendamento(@RequestBody AgendamentoRequest agendamentoRequest) {
        log.info("Realizando um agendamento... {}", agendamentoRequest);
        return ResponseEntity.status(CREATED).body(agendamentoService.realizarAgendamento(agendamentoRequest));
    }

    @GetMapping
    public List<AgendamentosRealizados> listarTodos(){
        log.info("Listando todos os agendamentos realizados..");
        return agendamentoService.listarTodos();
    }
    @GetMapping("/disponibilidade/{id}")
    public AgendamentoDisponibilidadeResource
    verificaServicosProfissionaisDisponiveis(@PathVariable("id") Long id){
        log.info("Listando Profissionais e Servicos do Estabelecimento com ID {}", id);
        return agendamentoService.verificaServicosProfissionaisDisponiveis(id);
    }
}
