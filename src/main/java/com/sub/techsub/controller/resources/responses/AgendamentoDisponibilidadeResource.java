package com.sub.techsub.controller.resources.responses;



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
public class AgendamentoDisponibilidadeResource {

    private List<Profissional> profissionais;
    private List<Servico> servicos;

}
