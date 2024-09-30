package com.sub.techsub.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.techsub.controller.ProfissionalController;
import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import com.sub.techsub.repository.ProfissionalRepository;
import com.sub.techsub.repository.ServicosRepository;
import com.sub.techsub.service.ProfissionalService;
import com.sub.techsub.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.MockMvc.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProfissionalControllerTest {

    @Mock
    private ProfissionalService profissionalService;

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private ProfissionalController profissionalController;

    private ObjectMapper objectMapper;

    @Mock
    private ServicosService servicosService;

    private Servico servico;

    @Autowired
    private ServicosRepository servicosRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        servico = new Servico();
        servico.setId(1L);
        servico.setNome("Manicure");
    }

    @Test
    public void testCadastrar() throws Exception {
        ProfissionalRequest profissionalRequest = new ProfissionalRequest();
        Long servicoId = servicosRepository.save(servico).getId();
        profissionalRequest.setServico(servicoId);
        profissionalRequest.setNome("Gabriel F");
        profissionalRequest.setTarifas(100.00);
        profissionalRequest.setHorariosDisponiveis("Segunda Sexta 9:00-19:00");

        Long idProfissionalEsperado = 1L;
        when(profissionalService.cadastrarProfissional(any(ProfissionalRequest.class))).thenReturn(idProfissionalEsperado);

        mockMvc.perform(post("/api/profissional")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profissionalRequest)))
                .andExpect(status().is(CREATED.value()));
    }

    @Test
    public void testListarTodos() throws Exception {
        Profissional profissional = new Profissional();
        List<Profissional> profissionais = Collections.singletonList(profissional);

        when(profissionalService.listarTodos()).thenReturn(profissionais);

        mockMvc.perform(get("/api/profissional")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarPerfilProfissional() throws Exception {
        Long id = 1L;
        Profissional profissional = new Profissional();

        when(profissionalService.buscarPerfilProfissional(id)).thenReturn(Optional.of(profissional));

        mockMvc.perform(get("/api/profissional/perfil/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
