package com.sub.techsub.controller.resources.requests;



import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoRequest {

    @Schema(description = "ID do Estabelecimento", example = "1")
    private Long estabelecimento;
    @Schema(description = "ID do Profissional", example = "1")
    private Long profissional;
    @Schema(description = "ID do Cliente", example = "1")
    private Long cliente;
    @Schema(description = "Status do Agendamento", example = "Agendar")
    private String status;
    @Schema(description = "Hora agendamento padrao: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", example = "2024-09-16T14:38:19.619Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataAgendamento; //exemplo de input 2024-09-16T14:38:19.619Z

}
