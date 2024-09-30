package com.sub.techsub.entity.reference;

import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Servico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estabelecimento_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EstabelecimentoServicoId.class)
public class EstabelecimentoServico {

    @Id
    @Column(name = "estabelecimento_id")
    private Long estabelecimentoId;

    @Id
    @Column(name = "servico_id")
    private Long servicoId;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", insertable = false, updatable = false)
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JoinColumn(name = "servico_id", insertable = false, updatable = false)
    private Servico servico;

}
