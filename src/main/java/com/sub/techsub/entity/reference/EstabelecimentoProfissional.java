package com.sub.techsub.entity.reference;

import com.sub.techsub.entity.Estabelecimento;
import com.sub.techsub.entity.Profissional;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estabelecimento_profissional")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EstabelecimentoProfissionalId.class)
public class EstabelecimentoProfissional {

    @Id
    @Column(name = "estabelecimento_id")
    private Long estabelecimentoId;

    @Id
    @Column(name = "profissional_id")
    private Long profissionalId;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", insertable = false, updatable = false)
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JoinColumn(name = "profissional_id", insertable = false, updatable = false)
    private Profissional profissional;

}
