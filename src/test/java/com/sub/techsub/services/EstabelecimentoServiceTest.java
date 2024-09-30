package com.sub.techsub.services;


import com.sub.techsub.controller.resources.requests.EstabelecimentoRequest;
import com.sub.techsub.controller.resources.responses.EstabelecimentoResource;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import com.sub.techsub.entity.reference.EstabelecimentoProfissional;
import com.sub.techsub.entity.reference.EstabelecimentoServico;
import com.sub.techsub.repository.EstabelecimentoProfissionalRepository;
import com.sub.techsub.repository.EstabelecimentoRepository;
import com.sub.techsub.repository.EstabelecimentoServicoRepository;
import com.sub.techsub.service.EstabelecimentoService;
import com.sub.techsub.service.ProfissionalService;
import com.sub.techsub.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstabelecimentoServiceTest {

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @Mock
    private ServicosService servicosService;

    @Mock
    private ProfissionalService profissionalService;

    @Mock
    private EstabelecimentoProfissionalRepository estabelecimentoProfissionalRepository;

    @Mock
    private EstabelecimentoServicoRepository estabelecimentoServicoRepository;

    private EstabelecimentoRequest estabelecimentoRequest;
    private Estabelecimento estabelecimento;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ArrayList<String> fotos = new ArrayList<>();
        List<EstabelecimentoServico> estabelecimentoServicos = new ArrayList<>();
        List<EstabelecimentoProfissional> profissionais = new ArrayList<>();


        fotos.add("Foto1.jpg");
        fotos.add("Foto2.jpg");

        estabelecimentoServicos.add(new EstabelecimentoServico());
        profissionais.add(new EstabelecimentoProfissional());

        estabelecimentoRequest = new EstabelecimentoRequest();
        estabelecimentoRequest.setNome("Estabelecimento Teste");
        estabelecimentoRequest.setEndereco("Rua Teste");
        estabelecimentoRequest.setServicos(Arrays.asList(1L, 2L));
        estabelecimentoRequest.setProfissionais(Arrays.asList(1L, 2L));
        estabelecimentoRequest.setFotos(fotos);


        estabelecimento = new Estabelecimento(estabelecimentoRequest);
        estabelecimento.setId(1L);
        estabelecimento.setEstabelecimentoServicos(estabelecimentoServicos);
        estabelecimento.setProfissionais(profissionais);
    }

    @Test
    public void testCadastrarEstabelecimento() {
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimento);
        when(servicosService.buscaPorIdServico(1L)).thenReturn(new Servico());
        when(servicosService.buscaPorIdServico(2L)).thenReturn(new Servico());
        when(profissionalService.buscaPorIdProfissional(1L)).thenReturn(new Profissional());
        when(profissionalService.buscaPorIdProfissional(2L)).thenReturn(new Profissional());

        Long id = estabelecimentoService.cadastrarEstabelecimento(estabelecimentoRequest);

        assertEquals(estabelecimento.getId(), id);
        verify(estabelecimentoServicoRepository, times(2)).save(any(EstabelecimentoServico.class));
        verify(estabelecimentoProfissionalRepository, times(2)).save(any(EstabelecimentoProfissional.class));
    }

    @Test
    public void testListarTodos() {
        when(estabelecimentoRepository.findAll()).thenReturn(Arrays.asList(estabelecimento));

        List<EstabelecimentoResource> recursos = estabelecimentoService.listarTodos();

        assertFalse(recursos.isEmpty());
        assertEquals(1, recursos.size());
        assertEquals(estabelecimento.getNome(), recursos.get(0).getNome());
    }

    @Test
    public void testBuscaEstabelecimentoPorId() {
        when(estabelecimentoRepository.findById(1L)).thenReturn(Optional.of(estabelecimento));

        Optional<Estabelecimento> found = estabelecimentoService.buscaEstabelecimentoPorId(1L);

        assertTrue(found.isPresent());
        assertEquals(estabelecimento.getId(), found.get().getId());
    }

    @Test
    public void testAtualizaEstabelecimento() {
        estabelecimentoService.atualizaEstabelecimento(estabelecimento);

        verify(estabelecimentoRepository, times(1)).save(estabelecimento);
    }

    @Test
    public void testFiltroEstabelecimento() {
        when(estabelecimentoRepository.findAll()).thenReturn(Arrays.asList(estabelecimento));
        when(servicosService.buscaPorIdServico(1L)).thenReturn(new Servico());
        when(profissionalService.buscaPorIdProfissional(1L)).thenReturn(new Profissional());

        List<EstabelecimentoResource> filtrados = estabelecimentoService.filtroEstabelecimento("Estabelecimento Teste", null, null, null, null, null);

        assertEquals(1, filtrados.size());
        assertEquals("Estabelecimento Teste", filtrados.get(0).getNome());
    }
}
