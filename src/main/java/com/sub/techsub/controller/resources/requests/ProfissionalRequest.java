package com.sub.techsub.controller.resources.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfissionalRequest {

    @Schema(description = "Nome do Profissional", example = "Maria Roberta")
    private String nome;
    @Schema(description = "ID do Servico", example = "1")
    private Long servico;
    @Schema(description = "Horario de Funcionamento Seguir padrao exemplo Segunda Sexta 9:00-19:00", example = "Segunda Sexta 9:00-19:00")
    private String horariosDisponiveis; //exemplo de valor: Segunda Sexta 9:00-19:00
    @Schema(description = "Valor do Profissional", example = "100.00")
    private Double tarifas;

}
