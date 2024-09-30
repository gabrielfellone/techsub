package com.sub.techsub.controller.resources.responses;



import com.sub.techsub.entity.Agendamento;
import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import com.sub.techsub.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentosRealizados {

    private Profissional profissionais;
    private Servico servicos;
    private String endereco;
    private String nomeEstabelecimento;
    private String nomeCliente;
    private String statusAgendamento;

    public AgendamentosRealizados(Agendamento agendamento) {
        this.profissionais = agendamento.getProfissional();
        this.servicos = agendamento.getProfissional().getServico();
        this.endereco = agendamento.getEstabelecimento().getEndereco();
        this.nomeEstabelecimento = agendamento.getEstabelecimento().getNome();
        this.nomeCliente = agendamento.getCliente().getNome();
        this.statusAgendamento = agendamento.getStatus();
    }
}
