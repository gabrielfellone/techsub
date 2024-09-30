package com.sub.techsub.services;


import com.sub.techsub.controller.resources.requests.AvaliacaoRequest;
import com.sub.techsub.entity.Avaliacao;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.AvaliacaoRepository;
import com.sub.techsub.service.AvaliacaoService;
import com.sub.techsub.service.EstabelecimentoService;
import com.sub.techsub.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private ProfissionalService profissionalService;

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    private AvaliacaoRequest avaliacaoRequest;
    private Avaliacao avaliacao;
    private Estabelecimento estabelecimento;
    private Profissional profissional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        avaliacaoRequest = new AvaliacaoRequest();
        avaliacaoRequest.setDescricao("Ótimo serviço");
        avaliacaoRequest.setTipo("Serviço");
        avaliacaoRequest.setNota(5);
        avaliacaoRequest.setEstabelecimentoId(1L);
        avaliacaoRequest.setProfissionalId(1L);

        avaliacao = Avaliacao.builder()
                .descricao(avaliacaoRequest.getDescricao())
                .tipo(avaliacaoRequest.getTipo())
                .nota(avaliacaoRequest.getNota())
                .build();

        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        profissional = new Profissional();
        profissional.setId(1L);
    }

    @Test
    public void testRealizarAvaliacao() {
        when(estabelecimentoService.buscaEstabelecimentoPorId(avaliacaoRequest.getEstabelecimentoId())).thenReturn(Optional.of(estabelecimento));
        when(profissionalService.buscarPerfilProfissional(avaliacaoRequest.getProfissionalId())).thenReturn(Optional.of(profissional));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao);

        avaliacaoService.realizarAvalicao(avaliacaoRequest);

        assertEquals(avaliacao.getDescricao(), avaliacaoRequest.getDescricao());

        assertEquals(avaliacao, profissional.getAvaliacao());
        assertEquals(avaliacao, estabelecimento.getAvaliacao());

        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
        verify(profissionalService, times(1)).atualizaProfissional(profissional);
        verify(estabelecimentoService, times(1)).atualizaEstabelecimento(estabelecimento);
    }

    @Test
    public void testRealizarAvaliacaoEstabelecimentoNaoEncontrado() {
        when(estabelecimentoService.buscaEstabelecimentoPorId(1L)).thenReturn(Optional.empty());
        when(profissionalService.buscarPerfilProfissional(1L)).thenReturn(Optional.of(profissional));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.realizarAvalicao(avaliacaoRequest);
        });

        assertEquals("Profissional ou Estabelecimento nao encontrados! Por favor insira outro ID.", exception.getMessage());
    }

    @Test
    public void testRealizarAvaliacaoProfissionalNaoEncontrado() {
        when(estabelecimentoService.buscaEstabelecimentoPorId(1L)).thenReturn(Optional.of(estabelecimento));
        when(profissionalService.buscarPerfilProfissional(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            avaliacaoService.realizarAvalicao(avaliacaoRequest);
        });

        assertEquals("Profissional ou Estabelecimento nao encontrados! Por favor insira outro ID.", exception.getMessage());
    }
}
