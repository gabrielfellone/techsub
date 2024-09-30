package com.sub.techsub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sub.techsub.controller.EstabelecimentoController;
import com.sub.techsub.controller.resources.requests.EstabelecimentoRequest;
import com.sub.techsub.controller.resources.responses.EstabelecimentoResource;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import com.sub.techsub.entity.reference.EstabelecimentoProfissional;
import com.sub.techsub.entity.reference.EstabelecimentoServico;
import com.sub.techsub.repository.ProfissionalRepository;
import com.sub.techsub.repository.ServicosRepository;
import com.sub.techsub.service.EstabelecimentoService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class EstabelecimentoControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private ServicosService servicosService;

    @InjectMocks
    private EstabelecimentoController estabelecimentoController;

    private ObjectMapper objectMapper;

    private EstabelecimentoRequest estabelecimentoRequest;

    @Mock
    private ProfissionalService profissionalService;

    private Profissional profissional;

    private Servico servico;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ServicosRepository servicosRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        profissional = new Profissional();
        profissional.setNome("Gabriel");

        servico = new Servico();
        servico.setId(1L);
        servico.setNome("Massagem");

        ArrayList<String> fotos = new ArrayList<>();
        List<EstabelecimentoServico> estabelecimentoServicos = new ArrayList<>();
        List<EstabelecimentoProfissional> profissionais = new ArrayList<>();

        EstabelecimentoServico estabelecimentoServicoUm = new EstabelecimentoServico();
        estabelecimentoServicoUm.setEstabelecimentoId(1L);
        estabelecimentoServicoUm.setServicoId(1L);

        fotos.add("Foto1.jpg");
        fotos.add("Foto2.jpg");

        estabelecimentoServicos.add(estabelecimentoServicoUm);
        profissionais.add(new EstabelecimentoProfissional());

        estabelecimentoRequest = new EstabelecimentoRequest();
        estabelecimentoRequest.setNome("Estabelecimento Teste");
        estabelecimentoRequest.setEndereco("Rua Teste");
        estabelecimentoRequest.setServicos(Arrays.asList(1L, 2L));
        estabelecimentoRequest.setProfissionais(Arrays.asList(1L, 2L));
        estabelecimentoRequest.setFotos(fotos);

    }

    @Test
    public void testCadastrar() throws Exception {
        Long idEsperado = 1L;
        long idProfissional = profissionalRepository.save(profissional).getId();

        List<Long> profs = new ArrayList<>();
        profs.add(idProfissional);
        estabelecimentoRequest.setProfissionais(profs);

        long idServico = servicosRepository.save(servico).getId();

        List<Long> servicos = new ArrayList<>();
        servicos.add(idServico);
        estabelecimentoRequest.setServicos(servicos);

        when(estabelecimentoService.cadastrarEstabelecimento(any(EstabelecimentoRequest.class))).thenReturn(idEsperado);
        when(profissionalService.buscaPorIdProfissional(1L)).thenReturn(new Profissional());
        when(servicosService.buscaPorIdServico(1L)).thenReturn(new Servico());


        mockMvc.perform(post("/api/estabelecimento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoRequest)))
                .andExpect(status().is(CREATED.value()));
    }

    @Test
    public void testListarTodos() throws Exception {
        EstabelecimentoResource estabelecimentoResource = new EstabelecimentoResource();
        List<EstabelecimentoResource> estabelecimentos = Collections.singletonList(estabelecimentoResource);

        when(estabelecimentoService.listarTodos()).thenReturn(estabelecimentos);

        mockMvc.perform(get("/api/estabelecimento")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFiltrarEstabelecimento() throws Exception {
        String nome = "Estabelecimento Teste";
        List<EstabelecimentoResource> estabelecimentosFiltrados = Collections.emptyList();
        when(estabelecimentoService.filtroEstabelecimento(any(), any(), any(), any(), any(), any()))
                .thenReturn(estabelecimentosFiltrados);
        mockMvc.perform(get("/api/estabelecimento/filtro")
                        .param("nome", nome)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
