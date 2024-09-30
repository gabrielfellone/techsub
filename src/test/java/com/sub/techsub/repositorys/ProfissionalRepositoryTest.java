package com.sub.techsub.repositorys;

import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.EstabelecimentoProfissionalRepository;
import com.sub.techsub.repository.EstabelecimentoRepository;
import com.sub.techsub.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfissionalRepositoryTest {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private EstabelecimentoProfissionalRepository estabelecimentoProfissionalRepository;

    private Profissional profissional;

    private ProfissionalRequest profissionalRequest;

    @BeforeEach
    public void setUp() {

        profissionalRequest = new ProfissionalRequest();
        profissionalRequest.setServico(1L);

        profissional = new Profissional(profissionalRequest);
        profissional.setId(1L);
        profissional.setNome("Ana Maria");
    }

    @Test
    @Rollback(false)
    public void testSaveAndFindById() {
        Long id = profissionalRepository.save(profissional).getId();
        Optional<Profissional> found = profissionalRepository.findById(id);
        assertTrue(found.isPresent(), "Profissional salvo e encontrado com sucesso pelo id");
        assertEquals(profissional.getNome(), found.get().getNome());
    }

    @Test
    @Rollback(false)
    public void testDelete() {
        profissionalRepository.save(profissional);
        estabelecimentoProfissionalRepository.deleteAll();
        profissionalRepository.delete(profissional);
        Optional<Profissional> found = profissionalRepository.findById(profissional.getId());
        assertFalse(found.isPresent(),"Profissional encontrado com sucesso no banco de dados");
    }
}
