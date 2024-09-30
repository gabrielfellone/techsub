package com.sub.techsub.service.schedules;

import com.sub.techsub.entity.Agendamento;
import com.sub.techsub.entity.Cliente;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.integration.GoogleCalendario;
import com.sub.techsub.service.AgendamentoService;
import com.sub.techsub.service.ProfissionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScheduledLembreteAutomatico {

    @Autowired
    ProfissionalService profissionalService;

    @Autowired
    AgendamentoService agendamentoService;

    GoogleCalendario googleCalendario;

    @Scheduled(fixedRate = 900000)  // a cada 15 minutos
    public void lembreteAgendamento() {
        log.info("Lembrete de agendamento iniciado...");

       List<Agendamento> agendamentos = agendamentoService.listaTodosAgendamentos()
               .stream()
               .filter(agendamento -> agendamento.getStatus().equalsIgnoreCase("AGENDADO"))
               .toList();

       log.info("Apenas agendamentos com status AGENDADO...");

       if(!agendamentos.isEmpty()){
           agendamentos.forEach(agendamento -> {
               notificaCliente(agendamento.getCliente(), agendamento);
               notificaProfissional(agendamento.getProfissional(), agendamento);
           });
       }
    }

    private void notificaProfissional(Profissional profissional, Agendamento agendamento) {
        log.info("Notificando Profissional {} ...", profissional);
        //googleCalendario.criarAgendamento(agendamento);

    }

    private void notificaCliente(Cliente cliente, Agendamento agendamento) {
        log.info("Notificando Cliente {} ...", cliente);
        //googleCalendario.criarAgendamento(agendamento);

    }
}
