package com.sub.techsub.controllers;


import com.sub.techsub.controller.AgendamentoController;
import com.sub.techsub.controller.resources.responses.AgendamentoDisponibilidadeResource;
import com.sub.techsub.controller.resources.requests.AgendamentoRequest;
import com.sub.techsub.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoService agendamentoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRealizarAgendamento() {
        AgendamentoRequest agendamentoRequest = new AgendamentoRequest();
        agendamentoRequest.setEstabelecimento(1L);
        agendamentoRequest.setProfissional(1L);
        agendamentoRequest.setCliente(1L);
        Long expectedId = 1L;
        when(agendamentoService.realizarAgendamento(any(AgendamentoRequest.class))).thenReturn(expectedId);
        ResponseEntity<Long> response = agendamentoController.realizarAgendamento(agendamentoRequest);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(expectedId);
    }

    @Test
    public void testVerificaServicosProfissionaisDisponiveis() {
        Long estabelecimentoId = 1L;
        AgendamentoDisponibilidadeResource expectedResource = new AgendamentoDisponibilidadeResource();
        when(agendamentoService.verificaServicosProfissionaisDisponiveis(estabelecimentoId)).thenReturn(expectedResource);
        AgendamentoDisponibilidadeResource response = agendamentoController.verificaServicosProfissionaisDisponiveis(estabelecimentoId);
        assertThat(response).isEqualTo(expectedResource);
    }
}
