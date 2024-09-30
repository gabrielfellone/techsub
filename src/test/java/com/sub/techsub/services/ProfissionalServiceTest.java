package com.sub.techsub.services;


import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import com.sub.techsub.repository.ProfissionalRepository;
import com.sub.techsub.service.ProfissionalService;
import com.sub.techsub.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfissionalServiceTest {
    @InjectMocks
    private ProfissionalService profissionalService;

    @Mock
    private ProfissionalRepository profissionalRepository;
    @Mock
    private ServicosService servicosService;

    private ProfissionalRequest profissionalRequest;
    private Profissional profissional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        profissionalRequest = new ProfissionalRequest();
        profissionalRequest.setServico(1L);

        profissional = new Profissional(profissionalRequest);
        profissional.setId(1L);
    }

    @Test
    public void testBuscarPerfilProfissional() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));

        Optional<Profissional> found = profissionalService.buscarPerfilProfissional(1L);

        assertTrue(found.isPresent(), "Profissional encontrado");
        assertEquals(profissional.getId(), found.get().getId());
    }

    @Test
    public void testBuscaPorIdProfissional() {
        when(profissionalRepository.getReferenceById(1L)).thenReturn(profissional);

        Profissional found = profissionalService.buscaPorIdProfissional(1L);

        assertEquals(profissional.getId(), found.getId(), "Profissional encontrado por ID");
    }

    @Test
    public void testCadastrarProfissional() {
        when(servicosService.buscaPorIdServico(1L)).thenReturn(new Servico());
        when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissional);

        Long id = profissionalService.cadastrarProfissional(profissionalRequest);

        assertEquals(profissional.getId(), id,"Profissional salvo com sucesso");
        verify(profissionalRepository, times(1)).save(any(Profissional.class));
    }

    @Test
    public void testListarTodos() {
        List<Profissional> profissionais = new ArrayList<>();
        profissionais.add(profissional);
        when(profissionalRepository.findAll()).thenReturn(profissionais);
        List<Profissional> found = profissionalService.listarTodos();
        assertEquals(1, found.size(), "Lista de profissional retornando maior que 1");
        assertEquals(profissional.getId(), found.get(0).getId());
    }

    @Test
    public void testAtualizaProfissional() {
        profissionalService.atualizaProfissional(profissional);
        verify(profissionalRepository, times(1)).save(profissional);
    }
}
