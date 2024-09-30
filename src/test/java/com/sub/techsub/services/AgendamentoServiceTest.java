package com.sub.techsub.services;


import com.sub.techsub.controller.resources.requests.AgendamentoRequest;
import com.sub.techsub.controller.resources.responses.AgendamentoDisponibilidadeResource;
import com.sub.techsub.entity.Agendamento;
import com.sub.techsub.entity.Cliente;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import com.sub.techsub.entity.reference.EstabelecimentoProfissional;
import com.sub.techsub.entity.reference.EstabelecimentoServico;
import com.sub.techsub.repository.AgendamentoRepository;
import com.sub.techsub.repository.ClienteRepository;
import com.sub.techsub.service.AgendamentoService;
import com.sub.techsub.service.EstabelecimentoService;
import com.sub.techsub.service.ProfissionalService;
import com.sub.techsub.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private ProfissionalService profissionalService;

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @Mock
    private ServicosService servicosService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private AgendamentoRequest agendamentoRequest;
    private Agendamento agendamento;
    private Estabelecimento estabelecimento;
    private Profissional profissional;
    private Cliente cliente;

    private Servico servico;
    private EstabelecimentoServico estabelecimentoServicos;

    private EstabelecimentoProfissional estabelecimentoProfissional;

    List<EstabelecimentoProfissional> estabelecimentoProfissionalList = new ArrayList<>();

    List<EstabelecimentoServico> estabelecimentoServicoList = new ArrayList<>();



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        agendamentoRequest = new AgendamentoRequest();
        agendamentoRequest.setEstabelecimento(1L);
        agendamentoRequest.setProfissional(1L);
        agendamentoRequest.setCliente(1L);
        agendamentoRequest.setDataAgendamento(LocalDateTime.now());

        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setHorarioFuncionamento("Segunda Domingo 00:01-23:59");

        profissional = new Profissional();
        profissional.setId(1L);
        profissional.setHorariosDisponiveis("Segunda Domingo 00:01-23:59");
        cliente = new Cliente();
        cliente.setId(1L);

        agendamento = new Agendamento();
        agendamento.setId(1L);

        servico = new Servico();
        servico.setId(1L);

        estabelecimentoProfissional = new EstabelecimentoProfissional();
        estabelecimentoServicos = new EstabelecimentoServico();

        estabelecimentoProfissional.setEstabelecimentoId(1L);
        estabelecimentoProfissional.setProfissional(profissional);
        estabelecimentoProfissional.setProfissionalId(1L);
        estabelecimentoServicos.setServicoId(1L);
        estabelecimentoServicos.setEstabelecimentoId(1L);
        estabelecimentoServicos.setServico(servico);


        estabelecimentoProfissionalList.add(estabelecimentoProfissional);
        estabelecimentoServicoList.add(estabelecimentoServicos);

        estabelecimento.setProfissionais(estabelecimentoProfissionalList);
        estabelecimento.setEstabelecimentoServicos(estabelecimentoServicoList);

    }

    @Test
    public void testRealizarAgendamento() {
        long id = 1L;
        when(estabelecimentoService.buscaEstabelecimentoPorId(id)).thenReturn(Optional.of(estabelecimento));
        when(profissionalService.buscarPerfilProfissional(id)).thenReturn(Optional.of(profissional));
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        Long idAgendamento = agendamentoService.realizarAgendamento(agendamentoRequest);

        assertNotNull(idAgendamento);
        assertEquals(agendamento.getId(), idAgendamento);
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }

    @Test
    public void testVerificaServicosProfissionaisDisponiveis() {
        long id = 1L;
        when(estabelecimentoService.buscaEstabelecimentoPorId(id)).thenReturn(Optional.of(estabelecimento));
        when(profissionalService.buscarPerfilProfissional(id)).thenReturn(Optional.of(profissional));
        when(servicosService.buscaPorIdServico(1L)).thenReturn(new Servico());

        AgendamentoDisponibilidadeResource disponibilidade
                = agendamentoService.verificaServicosProfissionaisDisponiveis(id);

        assertNotNull(disponibilidade);
        assertEquals(1, disponibilidade.getProfissionais().size());
        assertEquals(1, disponibilidade.getServicos().size());
    }

    @Test
    public void testListaTodosAgendamentos() {
        when(agendamentoRepository.findAll()).thenReturn(List.of(agendamento));
        assertEquals(1, agendamentoService.listaTodosAgendamentos().size());
    }

    @Test
    public void testValidaDataEHoraEstabelecimentoEstabelecimentoNaoEncontrado() {
        assertThrows(RuntimeException.class, () -> {
            agendamentoService.validaDataEHoraEstabelecimento(1L, LocalDateTime.now());
        });
    }

    @Test
    public void testValidaDataEHoraProfissionalProfissionalNaoEncontrado() {
        assertThrows(RuntimeException.class, () -> {
            agendamentoService.validaDataEHoraProfissional(1L, LocalDateTime.now());
        });
    }
}
