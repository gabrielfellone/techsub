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
public class EstabelecimentoRequest {

    @Schema(description = "Nome do Estabelecimento", example = "Beleza")
    private String nome;

    @Schema(description = "Endereco do Estabelecimento", example = "Rua Antonio Martins")

    private String endereco;

    @Schema(description = "IDS dos profissionais")

    private List<Long> profissionais;

    @Schema(description = "IDs dos servicos")

    private List<Long> servicos;

    @Schema(description = "Horario de Funcionamento Seguir padrao exemplo Segunda Sexta 9:00-19:00", example = "Segunda Sexta 9:00-19:00")

    private String horarioFuncionamento; //exemplo de valor: Segunda Sexta 9:00-19:00

    @Schema(description = "Lista de fotos")

    private List<String> fotos;

}
