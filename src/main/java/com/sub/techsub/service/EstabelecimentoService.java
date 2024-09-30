package com.sub.techsub.service;

import com.sub.techsub.controller.resources.requests.EstabelecimentoRequest;
import com.sub.techsub.controller.resources.responses.EstabelecimentoResource;
import com.sub.techsub.entity.*;
import com.sub.techsub.entity.reference.EstabelecimentoProfissional;
import com.sub.techsub.entity.reference.EstabelecimentoServico;
import com.sub.techsub.exception.ProfissionalException;
import com.sub.techsub.exception.ServicosException;
import com.sub.techsub.repository.EstabelecimentoProfissionalRepository;
import com.sub.techsub.repository.EstabelecimentoRepository;
import com.sub.techsub.repository.EstabelecimentoServicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EstabelecimentoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ServicosService servicosService;

    @Autowired
    ProfissionalService profissionalService;

    @Autowired
    EstabelecimentoProfissionalRepository estabelecimentoProfissionalRepository;

    @Autowired
    EstabelecimentoServicoRepository estabelecimentoServicoRepository;

    public Long cadastrarEstabelecimento(EstabelecimentoRequest request){

        Estabelecimento estabelecimento = new Estabelecimento(request);

        Long id = estabelecimentoRepository.save(estabelecimento).getId();

        estabelecimento.setId(id);

        validaSalvaServicos(request.getServicos(), estabelecimento);

        validaSalvaProfissionais(request.getProfissionais(), estabelecimento);

        return id;

    }

    public void validaSalvaServicos(List<Long> servicosids, Estabelecimento estabelecimento){
        servicosids.forEach(id -> {
            try {
                EstabelecimentoServico estabelecimentoServico = new EstabelecimentoServico();
                Servico servico = servicosService.buscaPorIdServico(id);

                estabelecimentoServico.setEstabelecimentoId(estabelecimento.getId());
                estabelecimentoServico.setServicoId(servico.getId());
                estabelecimentoServicoRepository.save(estabelecimentoServico);
            } catch (RuntimeException e){
                throw new ServicosException("Servicos nao encontrados");
            }
        });
    }

    public void validaSalvaProfissionais(List<Long> profissionaisIds, Estabelecimento estabelecimento){
        profissionaisIds.forEach(id -> {
            try {
                EstabelecimentoProfissional estabelecimentoProfissional = new EstabelecimentoProfissional();
                Profissional profissional = profissionalService.buscaPorIdProfissional(id);

                estabelecimentoProfissional.setEstabelecimentoId(estabelecimento.getId());
                estabelecimentoProfissional.setProfissionalId(profissional.getId());
                estabelecimentoProfissionalRepository.save(estabelecimentoProfissional);
            } catch (RuntimeException e){
                throw new ProfissionalException("Profissionais nao encontrados");
            }
        });
    }


    public List<EstabelecimentoResource> listarTodos() {
        log.info("Buscando todos os estabelecimentos na base...");
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        return estabelecimentos.stream()
                .filter(estabelecimento -> !estabelecimento.getEstabelecimentoServicos().isEmpty())
                .map(this::convertToEstabelecimentoResource)
                .collect(Collectors.toList());
    }

    private EstabelecimentoResource convertToEstabelecimentoResource(Estabelecimento estabelecimento) {
        List<Servico> servicos = estabelecimento.getEstabelecimentoServicos().stream()
                .map(servico -> servicosService.buscaPorIdServico(servico.getServicoId()))
                .collect(Collectors.toList());
        List<Profissional> profissionais = estabelecimento.getProfissionais().stream()
                .map(profissional -> profissionalService.buscaPorIdProfissional(profissional.getProfissionalId()))
                .collect(Collectors.toList());

        return EstabelecimentoResource.builder()
                .nome(estabelecimento.getNome())
                .endereco(estabelecimento.getEndereco())
                .horarioFuncionamento(estabelecimento.getHorarioFuncionamento())
                .fotos(Arrays.stream(estabelecimento.getFotos()).collect(Collectors.toList()))
                .servicos(servicos)
                .profissionals(profissionais)
                .avaliacao(estabelecimento.getAvaliacao())
                .build();
    }

    public Optional<Estabelecimento> buscaEstabelecimentoPorId(Long id){
        return estabelecimentoRepository.findById(id);
    }

    public void atualizaEstabelecimento(Estabelecimento estabelecimento){
        log.info("Atualizando o estabelecimento {} na base", estabelecimento);
        estabelecimentoRepository.save(estabelecimento);
    }


    public List<EstabelecimentoResource> filtroEstabelecimento(String nome, String localizacao, String servicoOferecido,
                                                               Double avaliacaoMin, Double faixaPrecoMin, Double faixaPrecoMax) {

        List<EstabelecimentoResource> estabelecimentos = this.listarTodos();
        List<EstabelecimentoResource> estabelecimentoFiltrado = new ArrayList<>();

        log.info("Realizando Filtro no Estabelecimento por base nos inputs...");


        if(nome != null){
            log.info("Procurando estabelecimento com o nome {}", nome);
            estabelecimentoFiltrado = estabelecimentos.stream().filter(e -> e.getNome().equalsIgnoreCase(nome)).collect(Collectors.toList());
        }

        if(localizacao != null){
            log.info("Procurando estabelecimento com a localizacao exata {}", localizacao);
            estabelecimentoFiltrado = estabelecimentos.stream().filter(e -> e.getEndereco().equalsIgnoreCase(localizacao)).collect(Collectors.toList());
        }

        if(servicoOferecido != null){
            log.info("Procurando servicos ofertados pelo estabelecimento {}", servicoOferecido);
            if(estabelecimentoFiltrado.isEmpty()) {
                estabelecimentoFiltrado.addAll(estabelecimentos);
            }

            List<EstabelecimentoResource> filtroTemp = new ArrayList<>();

            estabelecimentoFiltrado.forEach(e -> {
                e.getServicos().forEach(servico -> {
                    if(servico.getNome().equalsIgnoreCase(servicoOferecido)){
                        filtroTemp.add(e);
                    }
                });
            });

            estabelecimentoFiltrado = filtroTemp;
        }

        if(avaliacaoMin != null){
            log.info("Procurando estabelecimentos com avaliacao minima de {}", avaliacaoMin);
            estabelecimentoFiltrado = estabelecimentos.stream().filter(e -> e.getAvaliacao().getNota() >= avaliacaoMin).collect(Collectors.toList());
        }

        if(faixaPrecoMin != null && faixaPrecoMax != null){
            log.info("Procurando estabelecimentos com profissioanais nesta faixa de preco min {} e max {}", faixaPrecoMin,faixaPrecoMax );
            if(estabelecimentoFiltrado.isEmpty()) {
                estabelecimentoFiltrado.addAll(estabelecimentos);
            }

            List<EstabelecimentoResource> filtroTemp = new ArrayList<>();

            estabelecimentoFiltrado.forEach(e -> {
                e.getProfissionals().forEach(profissional -> {
                    Double precoProfissional = profissional.getTarifas();
                    if(precoProfissional >= faixaPrecoMin && precoProfissional <= faixaPrecoMax){
                        filtroTemp.add(e);
                    }

                });
            });

            estabelecimentoFiltrado = filtroTemp;
        }

        return estabelecimentoFiltrado;
    }
}
