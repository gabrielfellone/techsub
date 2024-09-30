package com.sub.techsub.service;

import com.sub.techsub.controller.resources.responses.AgendamentoDisponibilidadeResource;
import com.sub.techsub.controller.resources.requests.AgendamentoRequest;
import com.sub.techsub.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.sub.techsub.repository.AgendamentoRepository;
import com.sub.techsub.repository.ClienteRepository;
import com.sub.techsub.utils.FormatadorFuncionamentoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AgendamentoService {

    @Autowired
    ProfissionalService profissionalService;

    @Autowired
    EstabelecimentoService estabelecimentoService;

    @Autowired
    ServicosService servicosService;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public Long realizarAgendamento(AgendamentoRequest agendamentoRequest) {

        LocalDateTime dataAgendamento = agendamentoRequest.getDataAgendamento();

        validaDataEHoraEstabelecimento(agendamentoRequest.getEstabelecimento(), dataAgendamento);
        validaDataEHoraProfissional(agendamentoRequest.getProfissional(), dataAgendamento);

        log.info("Data e Hora validada, criando agendamento...");

        Agendamento agendamento = Agendamento.builder()
                .status("AGENDADO")
                .dataAgendamento(formataDataParaAgendamento(dataAgendamento))
                .horaAgendamento(formataHoraParaAgendamento(dataAgendamento))
                .profissional(profissionalService.buscaPorIdProfissional(agendamentoRequest.getProfissional()))
                .estabelecimento(estabelecimentoService.buscaEstabelecimentoPorId(agendamentoRequest.getEstabelecimento()).get())
                .cliente(clienteRepository.findById(agendamentoRequest.getCliente()).get())
                .build();

        return agendamentoRepository.save(agendamento).getId();

    }

    public AgendamentoDisponibilidadeResource
    verificaServicosProfissionaisDisponiveis(Long idEstabelecimento){

        log.info("Verificando estabelecimento...");

        Optional<Estabelecimento> estabelecimento
                = estabelecimentoService.buscaEstabelecimentoPorId(idEstabelecimento);

        if(estabelecimento.isPresent()){
            List<Profissional> profissionais = new ArrayList<>();
            List<Servico> servicos = new ArrayList<>();
            estabelecimento.get().getProfissionais().forEach(prof -> {
               profissionais.add(profissionalService.buscarPerfilProfissional(prof.getProfissionalId()).get());
            });

            estabelecimento.get().getEstabelecimentoServicos().forEach(serv ->{
                servicos.add(servicosService.buscaPorIdServico(serv.getServicoId()));
            });
            return AgendamentoDisponibilidadeResource.builder()
                    .profissionais(profissionais)
                    .servicos(servicos)
                    .build();
        } else throw new RuntimeException("Estabelecimento nao encontrado, por favor, insira outro ID");

    }

    public List<Agendamento> listaTodosAgendamentos (){
        log.info("Buscando todos os agendamentos...");
        return agendamentoRepository.findAll();
    }
    public LocalDate formataDataParaAgendamento(LocalDateTime dataAgendamento) {
        LocalDate localDate = dataAgendamento.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = localDate.format(formatter);
        return LocalDate.parse(dataFormatada);

    }

    public LocalTime formataHoraParaAgendamento(LocalDateTime dataAgendamento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String dataFormatada = dataAgendamento.format(formatter);
        return LocalTime.parse(dataFormatada);
    }

    public void validaDataEHoraEstabelecimento(Long idEstabelecimento, LocalDateTime dataAgendamento) {
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.buscaEstabelecimentoPorId(idEstabelecimento);
        if (estabelecimento.isPresent()) {
            log.info("Validando Data e hora do Estabelecimento...");
            FormatadorFuncionamentoData.formataEValidaDataHoraAgendamento(estabelecimento.get().getHorarioFuncionamento(), dataAgendamento);
        } else throw new RuntimeException("Estabelecimento não encontrado");
    }

    public void validaDataEHoraProfissional(Long idProfissional, LocalDateTime dataAgendamento) {
        Optional<Profissional> profissional = profissionalService.buscarPerfilProfissional(idProfissional);
        if (profissional.isPresent()) {
            log.info("Validando Data e hora do Profissional...");
            FormatadorFuncionamentoData.formataEValidaDataHoraAgendamento(profissional.get().getHorariosDisponiveis(), dataAgendamento);
        } else throw new RuntimeException("Profissional não encontrado");
    }


}
