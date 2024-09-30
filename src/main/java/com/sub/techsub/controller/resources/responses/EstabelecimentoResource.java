package com.sub.techsub.controller.resources.responses;


import com.sub.techsub.entity.Avaliacao;
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
public class EstabelecimentoResource {

    private String nome;

    private String endereco;

    private List<Servico> servicos;

    private List<Profissional> profissionals;

    private String horarioFuncionamento;

    private List<String> fotos;

    private Avaliacao avaliacao;

}
