package com.sub.techsub.entity.reference;

import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoProfissionalId implements Serializable {

    private Long estabelecimentoId;
    private Long profissionalId;
}
