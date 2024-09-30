package com.sub.techsub.repositorys;


import com.sub.techsub.entity.Avaliacao;
import com.sub.techsub.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvaliacaoRepositoryTest {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private Avaliacao avaliacao;

    @BeforeEach
    public void setUp() {
        avaliacao = new Avaliacao();
        avaliacao.setDescricao("Excelente atendimento");
        avaliacao.setNota(5);
    }

    @Test
    @Rollback(false)
    public void testSaveAndFindById() {
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        Optional<Avaliacao> found = avaliacaoRepository.findById(savedAvaliacao.getId());

        assertTrue(found.isPresent());
        assertEquals(avaliacao.getDescricao(), found.get().getDescricao());
        assertEquals(avaliacao.getNota(), found.get().getNota());
    }

    @Test
    @Rollback(false)
    public void testDelete() {
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        avaliacaoRepository.delete(savedAvaliacao);
        Optional<Avaliacao> found = avaliacaoRepository.findById(savedAvaliacao.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void testFindAll() {
        int tamanhoAtual = avaliacaoRepository.findAll().size();
        avaliacaoRepository.save(avaliacao);
        assertEquals(tamanhoAtual+1, avaliacaoRepository.findAll().size());
    }
}
