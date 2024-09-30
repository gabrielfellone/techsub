package com.sub.techsub.service;

import com.sub.techsub.controller.resources.requests.AvaliacaoRequest;
import com.sub.techsub.entity.Avaliacao;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.repository.AvaliacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AvaliacaoService {

    @Autowired
    ProfissionalService profissionalService;
    @Autowired
    EstabelecimentoService estabelecimentoService;
    @Autowired
    AvaliacaoRepository avaliacaoRepository;
    public Long realizarAvalicao(AvaliacaoRequest avaliacaoRequest) {
        log.info("Realizando avaliação...");

        Optional<Estabelecimento> estabelecimento =
                estabelecimentoService.buscaEstabelecimentoPorId(avaliacaoRequest.getEstabelecimentoId());

        Optional<Profissional> profissional =
                profissionalService.buscarPerfilProfissional(avaliacaoRequest.getProfissionalId());

        if(estabelecimento.isPresent()
                && profissional.isPresent()){

            Avaliacao avaliacao = Avaliacao.builder()
                    .descricao(avaliacaoRequest.getDescricao())
                    .tipo(avaliacaoRequest.getTipo())
                    .nota(avaliacaoRequest.getNota()).build();

           Long idAvaliacao = avaliacaoRepository.save(avaliacao).getId();

           profissional.get().setAvaliacao(avaliacao);
           estabelecimento.get().setAvaliacao(avaliacao);

           log.info("Registrando ultima avaliacao para Estabelecimento e Profissional");

           profissionalService.atualizaProfissional(profissional.get());
           estabelecimentoService.atualizaEstabelecimento(estabelecimento.get());

           return idAvaliacao;

        } else throw new RuntimeException("Profissional ou Estabelecimento nao encontrados! Por favor insira outro ID.");
    }
}
