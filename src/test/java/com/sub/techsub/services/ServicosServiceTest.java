package com.sub.techsub.services;

import com.sub.techsub.entity.Servico;
import com.sub.techsub.repository.ServicosRepository;
import com.sub.techsub.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicosServiceTest {

    @InjectMocks
    private ServicosService servicosService;

    @Mock
    private ServicosRepository servicosRepository;

    private Servico servico;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servico = new Servico();
        servico.setId(1L);
        servico.setNome("Teste classe de service do Servico");
    }
    @Test
    public void testBuscaPorIdServico() {
        Long id = 1L;
        when(servicosRepository.getReferenceById(id)).thenReturn(servico);
        Servico result = servicosService.buscaPorIdServico(id);
        assertEquals(servico, result);
    }
}
