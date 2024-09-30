package com.sub.techsub.repositorys;


import com.sub.techsub.entity.Agendamento;
import com.sub.techsub.entity.Cliente;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.AgendamentoRepository;
import com.sub.techsub.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    private Agendamento agendamento;

    private Agendamento agendamento2;

    private Profissional profissional;

    private Estabelecimento estabelecimento;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        profissional = new Profissional();
        profissional.setNome("Gabriel");

        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);

        cliente = new Cliente();
        cliente.setId(1L);

        agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setHoraAgendamento(LocalTime.from(LocalDateTime.now()));
        agendamento.setDataAgendamento(LocalDate.from(LocalDateTime.now()));
        agendamento.setEstabelecimento(estabelecimento);
        agendamento.setId(1L);
        agendamento.setStatus("AGENDADO");

        agendamento2 = new Agendamento();
        agendamento2.setCliente(cliente);
        agendamento2.setHoraAgendamento(LocalTime.from(LocalDateTime.now()));
        agendamento2.setDataAgendamento(LocalDate.from(LocalDateTime.now()));
        agendamento2.setEstabelecimento(estabelecimento);
        agendamento2.setId(1L + 1);
        agendamento2.setStatus("CANCELADO");

    }

    @Test
    public void testSaveAgendamento() {
        long id = profissionalRepository.save(profissional).getId();
        profissional.setId(id);
        agendamento.setProfissional(profissional);

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        assertThat(savedAgendamento.getId()).isNotNull();
        assertThat(savedAgendamento.getStatus()).isEqualTo("AGENDADO");
    }

    @Test
    public void testFindAgendamentoById() {
        long id = profissionalRepository.save(profissional).getId();
        profissional.setId(id);
        agendamento.setProfissional(profissional);

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);

        Agendamento foundAgendamento = agendamentoRepository.findById(savedAgendamento.getId()).orElse(null);
        assertThat(foundAgendamento).isNotNull();
        assertThat(foundAgendamento.getId()).isEqualTo(savedAgendamento.getId());
    }

    @Test
    public void testDeleteAgendamento() {

        long id = profissionalRepository.save(profissional).getId();
        profissional.setId(id);
        agendamento.setProfissional(profissional);

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        agendamentoRepository.delete(savedAgendamento);

        Agendamento foundAgendamento = agendamentoRepository.findById(savedAgendamento.getId()).orElse(null);
        assertThat(foundAgendamento).isNull();
    }

    @Test
    public void testFindAllAgendamentos() {
        long id = profissionalRepository.save(profissional).getId();
        profissional.setId(id);
        agendamento.setProfissional(profissional);
        agendamento2.setProfissional(profissional);

        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
        assertThat(savedAgendamento.getId()).isNotNull();
        assertThat(savedAgendamento.getStatus()).isEqualTo("AGENDADO");

        Agendamento savedAgendamento2 = agendamentoRepository.save(agendamento2);
        assertThat(savedAgendamento2.getId()).isNotNull();
        assertThat(savedAgendamento2.getStatus()).isEqualTo("CANCELADO");

        assertThat(agendamentoRepository.findAll()).hasSize(2);
    }
}
