package com.sub.techsub.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.techsub.controller.AvaliacaoController;
import com.sub.techsub.controller.resources.requests.AvaliacaoRequest;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.EstabelecimentoRepository;
import com.sub.techsub.repository.ProfissionalRepository;
import com.sub.techsub.service.AvaliacaoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AvaliacaoService avaliacaoService;

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    private ObjectMapper objectMapper;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private Profissional profissional;

    private Estabelecimento estabelecimento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        profissional = new Profissional();
        profissional.setNome("Gabriel");

        estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Beleza Now");
        estabelecimento.setEndereco("Rua treze");

    }

    @Test
    public void testAvaliar() throws Exception {

        long idProfissional = profissionalRepository.save(profissional).getId();
        long idEstabelecimento = estabelecimentoRepository.save(estabelecimento).getId();

        AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
        avaliacaoRequest.setEstabelecimentoId(idEstabelecimento);
        avaliacaoRequest.setProfissionalId(idProfissional);
        avaliacaoRequest.setNota(5);
        avaliacaoRequest.setDescricao("Ótimo serviço");
        avaliacaoRequest.setTipo("Serviço");

        Long expectedId = 1L;

        when(avaliacaoService.realizarAvalicao(any(AvaliacaoRequest.class))).thenReturn(expectedId);

        mockMvc.perform(post("/api/avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoRequest)))
                .andExpect(status().isCreated());
    }
}
