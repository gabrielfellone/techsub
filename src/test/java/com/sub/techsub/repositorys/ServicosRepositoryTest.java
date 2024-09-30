package com.sub.techsub.repositorys;

import com.sub.techsub.entity.Servico;
import com.sub.techsub.repository.ServicosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServicosRepositoryTest {

    @Autowired
    private ServicosRepository servicosRepository;

    private Servico servico;

    @BeforeEach
    public void setUp() {
        servico = new Servico();
        servico.setId(1L);
        servico.setNome("Servico Repository Teste");
    }

    @Test
    public void testSaveAndFindServico() {
        servicosRepository.save(servico);
        Optional<Servico> foundServico = servicosRepository.findById(servico.getId());
        assertTrue(foundServico.isPresent());
        assertEquals(servico.getNome(), foundServico.get().getNome());
    }

    @Test
    public void testDeleteServico() {
        servicosRepository.save(servico);
        servicosRepository.delete(servico);
        Optional<Servico> foundServico = servicosRepository.findById(servico.getId());
        assertTrue(foundServico.isEmpty());
    }
}
