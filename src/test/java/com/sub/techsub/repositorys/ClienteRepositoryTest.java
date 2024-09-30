package com.sub.techsub.repositorys;


import com.sub.techsub.entity.Cliente;
import com.sub.techsub.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringJUnitConfig
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Gabriel F");
    }

    @Test
    public void testSaveCliente() {
        Cliente savedCliente = clienteRepository.save(cliente);
        assertThat(savedCliente.getId()).isNotNull();
        assertThat(savedCliente.getNome()).isEqualTo("Gabriel F");
    }

    @Test
    public void testFindClienteById() {
        Cliente savedCliente = clienteRepository.save(cliente);
        Cliente foundCliente = clienteRepository.findById(savedCliente.getId()).orElse(null);
        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getId()).isEqualTo(savedCliente.getId());
    }

    @Test
    public void testDeleteCliente() {
        Cliente savedCliente = clienteRepository.save(cliente);
        clienteRepository.delete(savedCliente);
        Cliente foundCliente = clienteRepository.findById(savedCliente.getId()).orElse(null);
        assertThat(foundCliente).isNull();
    }

    @Test
    public void testFindAllClientes() {
        int tamanhoAtual = clienteRepository.findAll().size();
        Cliente cliente2 = new Cliente();
        cliente2.setNome("Ana Maria");
        clienteRepository.save(cliente);
        clienteRepository.save(cliente2);
        assertThat(clienteRepository.findAll().size()).isNotSameAs(tamanhoAtual);
    }
}
