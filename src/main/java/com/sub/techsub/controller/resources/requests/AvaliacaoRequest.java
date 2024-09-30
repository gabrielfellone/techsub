package com.sub.techsub.controller.resources.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvaliacaoRequest {

    @Schema(description = "Qual avaliacao", example = "Muito bom o serviço")
    private String descricao;

    @Schema(description = "Nota de 0 - 5 ", example = "4")
    @Min(value = 0, message = "A nota deve ser pelo menos 0")
    @Max(value = 5, message = "A nota não pode ser maior que 5")
    private Integer nota;

    @Schema(description = "Tipo de Serviço ofertado", example = "Massagem")
    private String tipo;

    @Schema(description = "ID do Profissional que realizou", example = "1")
    private Long profissionalId;

    @Schema(description = "ID do Estabelecimento que realizou", example = "1")

    private Long estabelecimentoId;

}
